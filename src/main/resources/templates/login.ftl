<!DOCTYPE html>
<html>

<head>
    <title>Blog</title>

    <meta charset="utf-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <link rel="stylesheet" href="css/loginStyle.css">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

</head>
<body>

<div class="wrapper">
    <form class="form-signin" method="post">
        <a href="/home"><h1 style="font-size: 60px; font-family: 'Helvetica Neue'" class="text-center text-primary text-capitalize">Blog Pucmm</h1> </a>

        <h2 class="form-signin-heading text-center">Login</h2>
        <div style="clear: right">

        <label for="user"> Username: </label>
            <br>
        <input id = "user"type="text" class="form-control" name="username" placeholder="Email Address" required="" autofocus="" />

        </div>

        <div style="clear: right">
            <label for="password">Password: </label>
            <br>
            <input id = password" type="password" class="form-control" name="password" placeholder="Password" required=""/>
        </div>

        <br>

        <button class="btn btn-lg btn-success btn-block" type="submit">Ingresar</button>
    </form>
</div>

    <script src="http://code.jquery.com/jquery-latest.js"></script>

</body>
</html>