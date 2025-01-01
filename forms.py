from django import forms
from .models import Register
from .models import Login

class RegisterForm(forms.ModelForm):
    class meta:
        model = Register
        field = [
            'fname','lname','email','passwd'
        ]

class LoginForm(forms.ModelForm):
    class meta:
        model = Login
        field = [
            'fname','passwd1'
        ]
