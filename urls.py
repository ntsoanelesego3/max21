from django.urls import path
from . import views

urlpatterns = [
    path('', views.homepage, name='homepage'), 
    path('chatbot/', views.chatbot_view, name='chatbot'),
    path('login/',views.login_views, name="login"),
    path('register/',views.register_views, name="register"),
]