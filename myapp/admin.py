from django.contrib import admin
from .models import Login,Register
from .forms import LoginForm, RegisterForm

# Register your models here.
admin.site.register(Login)
admin.site.register(Register)