import google.generativeai as genai
import os
from django.shortcuts import render
from django.http import JsonResponse
from dotenv import load_dotenv
from .models import Register, Login
from .forms import LoginForm, RegisterForm



# Create your views here.
def homepage(request):
    return render(request, 'chat/homepage.html')



load_dotenv()

# Get API Key from environment variable
GOOGLE_API_KEY = os.getenv("GOOGLE_API_KEY")

# Configure the API key - alternative method to using environment variable
genai.configure(api_key="AIzaSyDYHaMoWxFofOparFpPXtU0TFkWTG3wfK4") # use this if environment variable is not loading


def chatbot_view(request):
    if request.method == 'POST':
        user_input = request.POST.get('user_input', '')

        if user_input.lower() == "exit":
            return JsonResponse({'response': 'Goodbye!'})

        model = genai.GenerativeModel('gemini-pro')
        chat = model.start_chat(history=[])  
        response = chat.send_message(user_input)
        return JsonResponse({'response': response.text})
    else:
       return render(request, 'chat/chatbot.html')
    

def login_views(request):
    return render(request, 'chat/login.html')


def register_views(request):
    return render(request, 'chat/register.html')

