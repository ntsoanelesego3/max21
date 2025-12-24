<html>
<head>
    <meta charset="UTF-8">
    <title>Page Title</title>
</head>
<body>

<!-- Page Content -->
<h1>Welcome</h1>
<p>to add an Alien</p>
<form action="addAlien">
    <input type="text" name="aid" placeholder="Id"></br>
    <input type="text" name="aname" placeholder="Name"></br>
    <input type="text" name="tech" placeholder="Tech"></br>
    <button>submit</button>
</form>

<p>to get an Alien</p>
<form action="aliens"> <!- getAlien-->
    <input type="text" name="aid" placeholder="Id"></br>
    <button>submit</button>
</form>

<p>to remove an Alien</p>
<form action="removeAlienAid">
    <input type="text" name="aid" placeholder="Id"></br>
    <button>remove</button>
</form>

<form action="updateAlien">
    <input type="text" name="aid" placeholder="Id"></br>
    <input type="text" name="aname" placeholder="Name"></br>
    <input type="text" name="tech" placeholder="Tech"></br>
    <button>submit</button>
</form>

</body>
</html>
