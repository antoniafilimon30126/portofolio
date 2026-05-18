import os
import time
from pathlib import Path

import requests
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel


def _load_dotenv() -> None:
    env_file = Path(__file__).resolve().parent / ".env"
    if not env_file.is_file():
        return
    for line in env_file.read_text(encoding="utf-8").splitlines():
        line = line.strip()
        if not line or line.startswith("#") or "=" not in line:
            continue
        key, _, value = line.partition("=")
        key, value = key.strip(), value.strip().strip('"').strip("'")
        if key and key not in os.environ:
            os.environ[key] = value


_load_dotenv()

app = FastAPI(title="AI Lab Local Prototype")

# Hugging Face Inference Providers (chat API). flan-t5/gpt2 are not on hf-inference anymore.
HF_MODEL = os.getenv("HF_MODEL", "meta-llama/Llama-3.2-1B-Instruct")
HF_CHAT_URL = "https://router.huggingface.co/v1/chat/completions"

MAX_RETRIES = 5
RETRY_DELAY_SEC = 3
REQUEST_TIMEOUT_SEC = 90


def _hf_headers() -> dict[str, str]:
    token = os.getenv("HF_TOKEN") or os.getenv("HUGGINGFACE_API_KEY")
    if not token:
        return {}
    return {"Authorization": f"Bearer {token}"}


def _parse_chat_reply(data: object) -> str | None:
    if not isinstance(data, dict):
        return None

    choices = data.get("choices")
    if isinstance(choices, list) and choices:
        message = choices[0].get("message") if isinstance(choices[0], dict) else None
        if isinstance(message, dict) and message.get("content"):
            return str(message["content"]).strip()

    return None


def generate_with_hf(prompt: str, max_length: int = 100) -> str:
    payload = {
        "model": HF_MODEL,
        "messages": [{"role": "user", "content": prompt}],
        "max_tokens": max_length,
    }

    last_error: str | None = None

    for attempt in range(MAX_RETRIES):
        try:
            response = requests.post(
                HF_CHAT_URL,
                headers={**_hf_headers(), "Content-Type": "application/json"},
                json=payload,
                timeout=REQUEST_TIMEOUT_SEC,
            )
        except requests.RequestException as exc:
            raise HTTPException(
                status_code=502,
                detail=f"Hugging Face request failed: {exc}",
            ) from exc

        if response.status_code == 401:
            raise HTTPException(
                status_code=401,
                detail=(
                    "Hugging Face Inference now requires a free access token. "
                    "Create one at https://huggingface.co/settings/tokens "
                    "and set HF_TOKEN in your environment, then restart the server."
                ),
            )

        if response.status_code == 503:
            last_error = response.text
            time.sleep(RETRY_DELAY_SEC)
            continue

        if not response.ok:
            detail = response.text
            try:
                body = response.json()
                if isinstance(body, dict) and "error" in body:
                    detail = str(body["error"])
            except ValueError:
                pass
            raise HTTPException(
                status_code=response.status_code,
                detail=f"Hugging Face Inference API error: {detail}",
            )

        data = response.json()

        if isinstance(data, dict) and "error" in data:
            raise HTTPException(status_code=502, detail=str(data["error"]))

        generated = _parse_chat_reply(data)
        if generated is not None:
            return generated

        raise HTTPException(
            status_code=502,
            detail=f"Unexpected Hugging Face response format: {data!r}",
        )

    raise HTTPException(
        status_code=503,
        detail=(
            f"Model '{HF_MODEL}' is still loading on Hugging Face. "
            f"Try again shortly. Last response: {last_error}"
        ),
    )


class UserMessage(BaseModel):
    message: str


@app.get("/")
def home():
    return {
        "status": "AI Backend is running",
        "inference": "huggingface_chat_completions",
        "model": HF_MODEL,
        "endpoint": HF_CHAT_URL,
    }


@app.post("/chat")
def chat_with_ai(user_input: UserMessage):
    try:
        ai_reply = generate_with_hf(user_input.message, max_length=100)

        if not ai_reply:
            ai_reply = (
                "I processed your request, but the model generated an empty response."
            )

        return {"response": ai_reply}

    except HTTPException:
        raise
    except Exception as exc:
        raise HTTPException(
            status_code=500,
            detail=f"Hugging Face Inference Error: {exc}",
        ) from exc
