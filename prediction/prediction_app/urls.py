from django.urls import path
from . import views

app_name = 'prediction_app'

urlpatterns = [
    path('',views.home_page, name="home_page"),
    path('about/',views.about_page, name="about_page"),
    #path('prediction/',views.prediction_page, name="prediction_page"),
    path('prediction_page/',views.predict_view, name="prediction_page"),
]


