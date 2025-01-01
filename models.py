from django.db import models

# Create your models here.

class Register(models.Model):
    fname= models.CharField(max_length=100)
    lname= models.CharField(max_length=100)
    passwd= models.CharField(max_length=100)
    email= models.EmailField(max_length=100)

    def __str__(self):
        return self.fname + self.lname + self.passwd + self.email
    
class Login(models.Model):
    fname= models.CharField(max_length=100)
    passwd1= models.CharField(max_length=100)

    def __str__(self):
        return self.fname + self.passwd1

