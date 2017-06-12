<!DOCTYPE html>
<html>

<head>
    <title>Blog</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <link rel="stylesheet" href="css/Blog.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <script src="http://code.jquery.com/jquery-latest.js"></script>
</head>

<body>

<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="/home">Blog PUCMM </a>
        </div>
        <ul class="nav navbar-nav">
            <li class="active"><a href="/home">Home</a></li>

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
            <h1>Práctica 3</h1>

            <#if User??>
                <#if User.administrator || User.author>
                    <a href="/agregarArticulo"><button type="button" style="height: 50px" class="btn btn-primary btn-block"><span class="glyphicon glyphicon-plus"></span> Crear Nuevo Post</button></a>
                </#if>

            </#if>
        <#if listaArticulos?size gt 0>
                <#list listaArticulos as articulo>
                    <a href="/post/${articulo.id}">
                        <div class="col-lg-12 col-md-12">
                             <div class="col-md-12 col-lg-12 PostContainer">

                                 <div class="col-lg-3 col-md-3 text-center" style="background-color: #d1d1d1">
                                     <img src="/img/post.png" width="180" style="margin: 12px;" class="text-center"/>
                                 </div>

                                 <div class="col-lg-9 col-md-9">
                                     <div style="style="word-wrap: break-word;">
                                 <h2 class="text-justify text-uppercase">${articulo.titulo}</h2>

                                 </div>
                                 <div class="text-justify">
                                    <h4>${articulo.cuerpo}</h4>
                                 </div>


                                 <div style="word-wrap: break-word;" class="col-md-12">
                                     <h3 class="text-left">Escrito por <span>${articulo.autor.nombre}</span> el <span>${articulo.fecha}</span></h3>

                                     <h3 class="text-left">Etiquetas:
                                         <#if articulo.listaEtiquetas??>
                                             <#list articulo.listaEtiquetas as etiqueta>
                                                 <span >&nbsp;  ${etiqueta.etiqueta}  &nbsp;</span>
                                             </#list>
                                         </#if>
                                     </h3>
                                 </div>

                             </div>
                        </div>
                        </div>
                    </a>
                </#list>
            </#if>
        </div>
    </div>
</body>
</html>