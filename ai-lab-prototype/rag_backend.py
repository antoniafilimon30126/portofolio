import math
import os
from pathlib import Path

import requests

# Load .env (same pattern as main.py)
_env_file = Path(__file__).resolve().parent / ".env"
if _env_file.is_file():
    for line in _env_file.read_text(encoding="utf-8").splitlines():
        line = line.strip()
        if not line or line.startswith("#") or "=" not in line:
            continue
        key, _, value = line.partition("=")
        key, value = key.strip(), value.strip().strip('"').strip("'")
        if key and key not in os.environ:
            os.environ[key] = value

HF_TOKEN = os.getenv("HF_TOKEN") or os.getenv("HUGGINGFACE_API_KEY")
HF_CHAT_MODEL = os.getenv("HF_MODEL", "meta-llama/Llama-3.2-1B-Instruct")
HF_EMBED_MODEL = os.getenv("HF_EMBED_MODEL", "BAAI/bge-small-en-v1.5")
DATA_FILE = Path(__file__).resolve().parent / "date_companie.txt"

# Small docs: use the whole file as context (more reliable than 1 chunk).
FULL_DOC_CHARS = int(os.getenv("RAG_FULL_DOC_CHARS", "2000"))
TOP_K = int(os.getenv("RAG_TOP_K", "2"))
CHUNK_SIZE = int(os.getenv("RAG_CHUNK_SIZE", "400"))
REQUEST_TIMEOUT = 90

HF_CHAT_URL = "https://router.huggingface.co/v1/chat/completions"
HF_EMBED_URL = f"https://router.huggingface.co/hf-inference/models/{HF_EMBED_MODEL}"


def _headers() -> dict[str, str]:
    if not HF_TOKEN:
        raise RuntimeError(
            "Set HF_TOKEN in .env (https://huggingface.co/settings/tokens)"
        )
    return {"Authorization": f"Bearer {HF_TOKEN}", "Content-Type": "application/json"}


def _load_and_chunk(path: Path) -> list[str]:
    text = path.read_text(encoding="utf-8").strip()
    if not text:
        return []

    # Tiny files: one chunk = entire document (best accuracy for PoC).
    if len(text) <= FULL_DOC_CHARS:
        return [text]

    # Prefer paragraph / line chunks over fixed character windows.
    paragraphs = [p.strip() for p in text.split("\n\n") if p.strip()]
    if len(paragraphs) > 1:
        return paragraphs

    lines = [line.strip() for line in text.split("\n") if line.strip()]
    if len(lines) > 1:
        return lines

    # Fallback for long single-block text.
    chunks: list[str] = []
    start = 0
    while start < len(text):
        end = start + CHUNK_SIZE
        chunks.append(text[start:end].strip())
        if end >= len(text):
            break
        start = end - 40
    return [c for c in chunks if c]


def _embed(text: str) -> list[float]:
    response = requests.post(
        HF_EMBED_URL,
        headers=_headers(),
        json={"inputs": text},
        timeout=REQUEST_TIMEOUT,
    )
    if not response.ok:
        raise RuntimeError(
            f"Embedding API error ({response.status_code}): {response.text}"
        )
    vector = response.json()
    if not isinstance(vector, list) or not vector:
        raise RuntimeError(f"Unexpected embedding response: {vector!r}")
    if isinstance(vector[0], list):
        return vector[0]
    return vector


def _cosine_similarity(a: list[float], b: list[float]) -> float:
    dot = sum(x * y for x, y in zip(a, b))
    norm_a = math.sqrt(sum(x * x for x in a))
    norm_b = math.sqrt(sum(x * x for x in b))
    if norm_a == 0 or norm_b == 0:
        return 0.0
    return dot / (norm_a * norm_b)


def _chat(system: str, user: str, max_tokens: int = 100) -> str:
    response = requests.post(
        HF_CHAT_URL,
        headers=_headers(),
        json={
            "model": HF_CHAT_MODEL,
            "messages": [
                {"role": "system", "content": system},
                {"role": "user", "content": user},
            ],
            "max_tokens": max_tokens,
            "temperature": 0.1,
        },
        timeout=REQUEST_TIMEOUT,
    )
    if not response.ok:
        raise RuntimeError(f"Chat API error ({response.status_code}): {response.text}")
    data = response.json()
    return data["choices"][0]["message"]["content"].strip()


def _build_rag_prompt(context: str, question: str) -> tuple[str, str]:
    system_prompt = (
        "You answer questions using ONLY the context below.\n"
        "The answer is always stated explicitly in the context when it exists.\n"
        "Reply in one short sentence, in the same language as the question.\n"
        "Do not say you don't know if the context contains the answer."
    )
    user_prompt = (
        f"--- CONTEXT ---\n{context}\n--- END CONTEXT ---\n\n"
        f"Question: {question}\n\n"
        "Short answer:"
    )
    return system_prompt, user_prompt


class RagIndex:
    def __init__(self, chunks: list[str], vectors: list[list[float]]) -> None:
        self.chunks = chunks
        self.vectors = vectors

    @classmethod
    def build(cls, data_file: Path = DATA_FILE) -> "RagIndex":
        print("1. Se încarcă documentul local...")
        chunks = _load_and_chunk(data_file)
        print(f"2. S-au creat {len(chunks)} bucăți de text.")

        print("3. Se generează embeddings prin API Hugging Face (fără PyTorch)...")
        vectors = [_embed(chunk) for chunk in chunks]
        print("Indexul RAG este gata în memorie.")
        return cls(chunks, vectors)

    def get_context(self, query: str, k: int = TOP_K) -> str:
        if len(self.chunks) == 1:
            return self.chunks[0]

        query_vec = _embed(query)
        scored = [
            (_cosine_similarity(query_vec, vec), chunk)
            for chunk, vec in zip(self.chunks, self.vectors)
        ]
        scored.sort(key=lambda item: item[0], reverse=True)

        seen: set[str] = set()
        selected: list[str] = []
        for _, chunk in scored:
            if chunk in seen:
                continue
            seen.add(chunk)
            selected.append(chunk)
            if len(selected) >= k:
                break

        return "\n\n".join(selected)


def intreaba_rag(intrebare_utilizator: str, index: RagIndex | None = None) -> dict[str, str]:
    if index is None:
        index = RagIndex.build()

    context = index.get_context(intrebare_utilizator)
    system_prompt, user_prompt = _build_rag_prompt(context, intrebare_utilizator)
    raspuns = _chat(system_prompt, user_prompt)

    return {"raspuns": raspuns, "context_folosit": context}


if __name__ == "__main__":
    rag_index = RagIndex.build()
    test_query = "Who leads the AI Lab team in Cluj?"
    print(f"\nTesting RAG with query: '{test_query}'")
    rezultat = intreaba_rag(test_query, index=rag_index)
    print(f"AI Response: {rezultat['raspuns']}")
    print(f"Context retrieved from DB: {rezultat['context_folosit']}")
