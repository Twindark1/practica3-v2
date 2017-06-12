<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <title>Bloggify - Admin Panel</title>
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
               <li class="active"><a href="/adminUsuarios">Admin</a></li>

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
        <h2>Gestionar Usuarios</h2>
        <table class="table">
            <thead>
                <tr><td><strong>Username</strong></td><td><strong>Nombre</strong></td><td align="center"><strong>Administrador</strong></td><td align="center"><strong>Autor</strong></td></tr>
            </thead>
            <tbody>
                <#if listaUsuarios??>
                    <#list listaUsuarios as usuario>
                        <#if User.username != usuario.username>
                        <tr>
                            <td>${usuario.username}</td>
                            <td>${usuario.nombre}</td>
                            <#if usuario.administrator>

                            <td align="center"><a href="/asignarAdmin/${usuario.username}/${usuario.administrator?c}"><button style="width: 60px" class="btn btn-success">${usuario.administrator?c}</button></a></td>
                            <#else>
                                <td align="center"><a href="/asignarAdmin/${usuario.username}/${usuario.administrator?c}"><button style="width: 60px" class="btn btn-danger">${usuario.administrator?c}</button></a></td>
                            </#if>

                            <#if usuario.author>

                                <td align="center"><a href="/asignarAutor/${usuario.username}/${usuario.author?c}"><button style="width: 60px" class="btn btn-success">${usuario.author?c}</button></a></td>
                            <#else>
                                <td align="center"><a href="/asignarAutor/${usuario.username}/${usuario.author?c}"><button style="width: 60px" class="btn btn-danger">${usuario.author?c}</button></a></td>
                            </#if>
                        </tr>
                    </#if>

                    </#list>
                </#if>
            </tbody>
        </table>
    </div>
</body>
</html>