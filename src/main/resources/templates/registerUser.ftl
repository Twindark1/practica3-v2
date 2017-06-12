<!DOCTYPE html>
<html>

<head>
    <title>Blog, Practica 3</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <link rel="stylesheet" href="css/loginStyle.css">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>


</head>
<body>

<div class="wrapper">
    <form class="form-signin" method="post" action="/userRegister">

        <a href="/home"><h1 style="font-size: 60px; font-family: 'Helvetica Neue'" class="text-center text-primary text-uppercase">Blog</h1> </a>

        <h2 class="title form-signin-heading text-center">Registrate Gratis</h2>
        <h4 class="subtitle text-center">Crea tu cuenta </h4>

        <div>
            <label for="name">Name: </label>
            <input type="text" class="form-control" name="name" placeholder="Nombre" required="" autofocus="" />
        </div>

        <div>
            <label for="user">Username: </label>
            <input id= "user" type="text" title="Email example: Practica3@gmail.com" class="form-control" name="username" pattern="^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$" placeholder="Email Address" required autofocus="" />
        </div>

        <div>
            <label for="pass">Password: </label>
            <input id= "pass" type="password" class="form-control" name="password" placeholder="Password" required/>
        </div>

        <button class="btn btn-lg btn-success btn-block" type="submit">Crear</button>
    </form>
</div>

    <script src="http://code.jquery.com/jquery-latest.js"></script>

</body>
</html>