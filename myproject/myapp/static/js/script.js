function sendMessage() {
    const userInput = document.getElementById('user-input').value;
    document.getElementById('user-input').value = ''; // Clear input field

    const chatContainer = document.getElementById('chat-container');
    //Display user's message on the chat
    chatContainer.innerHTML += `<p>You: ${userInput}</p>`;
    //Scroll to the bottom after a message is sent
    chatContainer.scrollTop = chatContainer.scrollHeight;

    fetch('/chatbot/', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                'X-CSRFToken': getCookie('csrftoken'), // Important for Django security!
            },
            body: `user_input=${encodeURIComponent(userInput)}`,
        })
        .then(response => response.json())
        .then(data => {
            const chatbotResponse = data.response;
            chatContainer.innerHTML += `<p>Chatbot: ${chatbotResponse}</p>`;
            //Scroll to the bottom after response is received
            chatContainer.scrollTop = chatContainer.scrollHeight;
        });
}

function getCookie(name) {
    let cookieValue = null;
    if (document.cookie && document.cookie !== '') {
        const cookies = document.cookie.split(';');
        for (let i = 0; i < cookies.length; i++) {
            const cookie = cookies[i].trim();
            // Does this cookie string begin with the name we want?
            if (cookie.startsWith(name + '=')) {
                cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                break;
            }
        }
    }
    return cookieValue;
}