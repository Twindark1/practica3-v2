<!DOCTYPE html>
<html>

<head>
    <title>Blog - Practica 3</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <link rel="stylesheet" href="css/loginStyle.css">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>


</head>
<body>

<div class="wrapper">
    <form class="form-signin" method="post" action="/adminRegister">

        <h2 class="title form-signin-heading text-center">Bienvenido Administrador</h2>
        <h4 class="subtitle text-center">Crea tu cuenta de acceso aqui</h4>

        <input type="text" class="form-control" name="name" placeholder="Nombre" required autofocus="" />
        <br>
        <input type="text" title="Email example: Practica3@gmail.com" class="form-control" name="username" placeholder="Email Address" pattern="^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$" required autofocus="" />
        <br>
        <input type="password" class="form-control" name="password" placeholder="Password" required/>
        <br>
        <button class="btn btn-lg btn-success btn-block" type="submit">Crear</button>
    </form>
</div>


    <script src="http://code.jquery.com/jquery-latest.js"></script>


</body>
</html>