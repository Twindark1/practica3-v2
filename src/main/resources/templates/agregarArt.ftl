<!DOCTYPE html>
<html>
    <head>
        <title>Blog</title>
        <meta charset="utf-8">

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
        <link rel="stylesheet" href="css/bloggify.css">

        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
        <script src="http://code.jquery.com/jquery-latest.js"></script>
    </head>
    <body>
        <nav class="navbar navbar-inverse">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="/home">Blog Pucmm</a>
                </div>
                <ul class="nav navbar-nav">
                    <li><a href="/home">Home</a></li>
                    <#if User??>
                        <#if User.administrator>
                            <li><a href="/adminUsuarios">Admin</a></li>
                        </#if>
                    </#if>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <#if User??>
                        <li><a><span class="glyphicon glyphicon-user"></span> ${User.nombre} </a></li>
                        <li><a href="/logout"><span class="glyphicon glyphicon-log-in"></span> Desconectarse</a></li>
                    <#else>
                        <li><a href="/userRegister"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
                        <li><a href="/login"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
                    </#if>
                </ul>
            </div>
        </nav>

        <div class="container">
            <div class="row">
                <div class="col-lg-12 col-md-12">

                    <h1 class="text-primary">Crear Articulo Nuevo</h1>

                    <form action="/agregarArticulo" method="post">

                        <div class="form-group">
                            <label for="titles">Titulo</label>
                            <input id="titles" type="text" class="form-control full-input" name="titulo" placeholder="Agrega el titulo del Post">
                        </div>

                        <div class="form-group">
                            <label for="content">Contenido</label>
                            <textarea id="content" class="span6 form-control full-input" rows="6"  name="cuerpo" placeholder="Agrega el cuerpo del post" required></textarea>
                        </div>

                        <div class="form-group">
                            <label for="etiqueta">Etiquetas</label>
                            <input id= "etiqueta" type="text" class="form-control full-input" name="etiquetas" placeholder="etiqueta1,etiqueta2,...">
                        </div>

                        <div class="align-content-center text-center">
                             <input style="margin-top: 8px; width:200px;" type="submit" class="btn btn-success" value="Agregar">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>