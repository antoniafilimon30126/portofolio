from unittest.mock import MagicMock, patch

from fastapi.testclient import TestClient

from main import app

client = TestClient(app)


def test_home_endpoint():
    response = client.get("/")
    assert response.status_code == 200
    data = response.json()
    assert data["status"] == "AI Backend is running"
    assert data["inference"] == "huggingface_chat_completions"
    assert "model" in data
    assert "endpoint" in data


@patch("main.requests.post")
def test_chat_endpoint(mock_post: MagicMock):
    mock_response = MagicMock()
    mock_response.ok = True
    mock_response.status_code = 200
    mock_response.json.return_value = {
        "choices": [{"message": {"role": "assistant", "content": "Hello from test!"}}]
    }
    mock_post.return_value = mock_response

    response = client.post("/chat", json={"message": "Hello"})
    assert response.status_code == 200
    assert response.json()["response"] == "Hello from test!"
    mock_post.assert_called_once()


@patch("main.requests.post")
def test_chat_missing_token(mock_post: MagicMock):
    mock_response = MagicMock()
    mock_response.ok = False
    mock_response.status_code = 401
    mock_response.text = "Unauthorized"
    mock_response.json.return_value = {"error": "Unauthorized"}
    mock_post.return_value = mock_response

    response = client.post("/chat", json={"message": "Hello"})
    assert response.status_code == 401
