var city = "germiston";

$.getJSON("https://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=metric&APPID=26fcc08dad0ed82d01ee107d8a8b1d36", function(data) {
    //"https://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=imperial&APPID=26fcc08dad0ed82d01ee107d8a8b1d36", function(data) {
    console.log(data);

    var icon = "https://openweathermap.org/img/wn/" + data.weather[0].icon + "@2x.png"; //.png
    var temp = data.main.temp;
    var name = data.name;
    var weather = data.weather[0].main
    $('.icon').attr('src', icon);
    $('.weather').append(weather);
    $('.temp').append(temp);
    $('.name').append(name);



})