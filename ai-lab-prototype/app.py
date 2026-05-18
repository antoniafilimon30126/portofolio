import os

import requests
import streamlit as st

st.set_page_config(page_title="AI Lab Demo", page_icon="🤖")
st.title("🤖 My First AI Lab Prototype")
st.write("Acest chatbot comunică cu backend-ul nostru în FastAPI.")

# Inițializăm istoricul chat-ului în memorie
if "messages" not in st.session_state:
    st.session_state.messages = []

# Afișăm mesajele anterioare
for msg in st.session_state.messages:
    with st.chat_message(msg["role"]):
        st.markdown(msg["content"])

# Căsuța de input pentru utilizator
if user_query := st.chat_input("Scrie ceva aici..."):
    # Afișăm mesajul utilizatorului
    with st.chat_message("user"):
        st.markdown(user_query)
    st.session_state.messages.append({"role": "user", "content": user_query})

    # Trimitem întrebarea către API-ul nostru FastAPI (cel de la Pasul anterior)
    try:
        backend_url = os.getenv("BACKEND_URL", "http://127.0.0.1:8000/chat")
        payload = {"message": user_query}
        response = requests.post(backend_url, json=payload)
        
        if response.status_code == 200:
            ai_response = response.json()["response"]
        else:
            ai_response = f"Eroare API: {response.text}"
            
    except Exception as e:
        ai_response = "Nu am putut contacta backend-ul. Verifică dacă uvicorn rulează!"

    # Afișăm răspunsul AI-ului
    with st.chat_message("assistant"):
        st.markdown(ai_response)
    st.session_state.messages.append({"role": "assistant", "content": ai_response})