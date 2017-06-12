package Ruta;

import spark.Request;
import spark.Response;
import spark.Session;
import spark.Spark;
import static spark.Spark.*;

import java.io.StringWriter;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;

import Logica.Articulo;
import Logica.Comentario;
import Logica.Etiqueta;
import Logica.Usuario;

public class Web {

    private static final String baseDeDatos = "jdbc:h2:tcp://localhost/~/practica3";
    private static final String us = "sa";
    private static final String psw = "";

    private static ArrayList<Articulo> arts;
    private static String modificar = "false";

    private static void crearTablas()
    {
        EjecutarQuery("create table if not exists Usuarios\n" +
                "  (\n" +
                "    username varchar(1000) PRIMARY KEY,\n" +
                "    nombre varchar(1000),\n" +
                "    password varchar(1000),\n" +
                "    administrador boolean,\n" +
                "    autor boolean\n" +
                "  )");

        EjecutarQuery("create table if not exists Articulos\n" +
                "  (\n" +
                "    id bigint auto_increment PRIMARY KEY,\n" +
                "    titulo varchar(1000),\n" +
                "    cuerpo CLOB,\n" +
                "    autor varchar(1000),\n" +
                "    fecha date,\n" +
                "    FOREIGN KEY (autor) REFERENCES Usuarios(username)\n" +
                "  )");

        EjecutarQuery("create table if not exists Comentarios\n" +
                "  (\n" +
                "    id bigint auto_increment PRIMARY KEY,\n" +
                "    comentario varchar(1000),\n" +
                "    autor varchar(1000),\n" +
                "    articulo bigint,\n" +
                "    FOREIGN KEY (autor) REFERENCES Usuarios(username),\n" +
                "    FOREIGN KEY (articulo) REFERENCES Articulos(id)\n" +
                "  )");

        EjecutarQuery("create table if not exists Etiquetas\n" +
                "  (\n" +
                "    id bigint auto_increment PRIMARY KEY,\n" +
                "    etiqueta varchar(1000)\n" +
                "  )");

        EjecutarQuery("create table if not exists ArticulosEtiquetas\n" +
                "  (\n" +
                "    id bigint auto_increment PRIMARY KEY,\n" +
                "    articulo bigint,\n" +
                "    etiqueta bigint,\n" +
                "    FOREIGN KEY (articulo) REFERENCES Articulos(id),\n" +
                "    FOREIGN KEY (etiqueta) REFERENCES Etiquetas(id)\n" +
                "  )");
    }

    public static void StartProject() {

        crearTablas();
        staticFiles.location("/public");

        final Configuration configuration = new Configuration(new Version(2, 3, 0));
        configuration.setClassForTemplateLoading(Web.class, "/");

        Spark.get("/home", (request, response) -> {

            Usuario user = request.session(true).attribute("usuario");
            Template resultTemplate = configuration.getTemplate("templates/home.ftl");

            StringWriter writer = new StringWriter();
            Map<String, Object> attributes = new HashMap<>();

            arts = tomarArticulos();
            ArrayList<Articulo> Tarts = new ArrayList<>();

            for (Articulo a: arts)
            {
                Tarts.add(new Articulo(a.getId(), a.getTitulo(), get70firstChar(a.getCuerpo()), a.getAutor(), a.getFecha(), a.getListaComentarios(), a.getListaEtiquetas()));
            }

            attributes.put("User",user);
            attributes.put("listaArticulos", Tarts);

            resultTemplate.process(attributes, writer);
            return writer;
        });

        Spark.get("/post/:artID", (request, response) -> {

            Usuario user = request.session(true).attribute("usuario");
            Template resultTemplate = configuration.getTemplate("templates/post.ftl");

            StringWriter writer = new StringWriter();
            Map<String, Object> attributes = new HashMap<>();

            String etiquetas = "";

            if (modificar.equalsIgnoreCase("true"))
            {
                ArrayList<Articulo> arts = tomarArticulos();
                for (Articulo a: arts)
                {
                    if(a.getId() == Long.parseLong(request.params("artID")))
                    {
                        boolean primer = true;
                        for (Etiqueta e: a.getListaEtiquetas())
                        {
                            if(primer)
                            {
                                etiquetas += e.getEtiqueta();
                                primer = false;

                            }else
                            {
                                etiquetas += "," + e.getEtiqueta();
                            }
                        }
                    }
                }
            }

            arts = tomarArticulos();

            attributes.put("User",user);
            attributes.put("listaArticulos", arts);
            attributes.put("artID", request.params("artID"));
            attributes.put("modificar", modificar);
            attributes.put("etiquetas", etiquetas);
            resultTemplate.process(attributes, writer);

            return writer;
        });

        Spark.get("/login", (request, response) ->
        {

            Template resultTemplate = configuration.getTemplate("templates/login.ftl");
            StringWriter writer = new StringWriter();
            Map<String, Object> attributes = new HashMap<>();

            resultTemplate.process(attributes, writer);
            return writer;
        });

        Spark.get("/borrarComentario/:artID/:ID", (request, response) ->
        {

            EjecutarQuery("delete from Comentarios where id = " + request.params("ID"));
            response.redirect("/post/"+request.params("artID"));

            return "";
        });

        Spark.get("/adminUsuarios", (request, response) -> 
        {
            
            Usuario user = request.session().attribute("usuario");

            Template resultTemplate = configuration.getTemplate("templates/adminUsuarios.ftl");
            StringWriter writer = new StringWriter();
            Map<String, Object> attributes = new HashMap<>();

            ArrayList<Usuario> urs = tomarUsuarios();
            attributes.put("User", user);
            attributes.put("listaUsuarios", urs);

            resultTemplate.process(attributes, writer);
            return writer;
        });

        Spark.get("/asignarAdmin/:username/:administrador", (request, response) ->
        {
            if(request.params("administrador").equals("true"))
            {
                EjecutarQuery("update Usuarios set administrador = 0 where username = '" + request.params("username") + "'");
            } else
            {
                EjecutarQuery("update Usuarios set administrador = 1, autor = 1  where username = '" + request.params("username") + "'");
            }
            response.redirect("/adminUsuarios");

            return "";
        });

        Spark.get("/asignarAutor/:username/:autor", (request, response) -> 
        {

            if(request.params("autor").equals("true"))
            {
                EjecutarQuery("update Usuarios set administrador = 0, autor = 0 where username = '" + request.params("username") + "'");
            } else
                {
                EjecutarQuery("update Usuarios set autor = 1 where username = '" + request.params("username") + "'");
            }
            response.redirect("/adminUsuarios");

            return "";
        });

        Spark.get("/logout", (resquest, response) ->
        {

            Session ses = resquest.session(true);
            ses.invalidate();
            response.redirect("/home");
            return "";
        });

        Spark.get("/adminRegister", (request, response) ->
        {

            Template resultTemplate = configuration.getTemplate("templates/registerAdmin.ftl");
            StringWriter writer = new StringWriter();
            Map<String, Object> attributes = new HashMap<>();

            resultTemplate.process(attributes, writer);
            return writer;
        });

        Spark.get("/userRegister", (request, response) ->
        {

            Template resultTemplate = configuration.getTemplate("templates/registerUser.ftl");
            StringWriter writer = new StringWriter();
            Map<String, Object> attributes = new HashMap<>();

            resultTemplate.process(attributes, writer);
            return writer;
        });

        Spark.get("/modificarArticulo/:artID", (request, response) ->
        {

            modificar = "true";
            response.redirect("/post/" + request.params("artID"));

            return "";
        });

        Spark.get("/eliminarArticulo/:artID", (request, response) -> 
        {

            EjecutarQuery("delete from ArticulosEtiquetas where articulo = " + request.params("artID"));
            EjecutarQuery("delete from Comentarios where articulo = " + request.params("artID"));
            EjecutarQuery("delete from articulos where id = " + request.params("artID"));
            response.redirect("/home");

            return "";
        });

        Spark.get("/agregarArticulo", (request, response) -> 
        {

            Template resultTemplate = configuration.getTemplate("templates/agregarArt.ftl");
            StringWriter writer = new StringWriter();
            Map<String, Object> attributes = new HashMap<>();

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate localDate = LocalDate.now();

            Usuario us = request.session(true).attribute("usuario");

            attributes.put("usuario", us);
            attributes.put("fecha", dtf.format(localDate).toString());

            resultTemplate.process(attributes, writer);
            return writer;
        });

        Spark.post("/adminRegister", (request, response) -> 
        {

            try {
                Usuario admin = new Usuario(request.queryParams("username"), request.queryParams("name"), request.queryParams("password"),true,true);

                EjecutarQuery("insert into Usuarios (username, nombre, password, administrador, autor) values('" + admin.getUsername() +"','" + admin.getNombre() +"','" + admin.getPassword() + "'," + admin.isAdministrator() +"," + admin.isAuthor() +") ");

                request.session(true);
                request.session().attribute("usuario", admin);
                response.redirect("/home");

            }catch (Exception e){
                System.out.println(e);
                response.redirect("/home");
            }
            return "";
        });

        Spark.post("/login", (request, response) -> 
        {

            Usuario usr = existeUsuario(request.queryParams("username"), request.queryParams("password"));

            if(usr != null)
            {
                request.session(true);
                request.session().attribute("usuario", usr);
                response.redirect("/home");

            }else
            {
                response.redirect("/login");
            }
            return "";
        });

        Spark.post("/userRegister", (request, response) -> 
        {

            try {
                Usuario user = new Usuario(request.queryParams("username"), request.queryParams("name"), request.queryParams("password"),false,false);

                EjecutarQuery("insert into Usuarios (username, nombre, password, administrador, autor) values('" + user.getUsername() +"','" + user.getNombre() +"','" + user.getPassword() + "'," + user.isAdministrator() +"," + user.isAuthor() +") ");

                request.session(true);
                request.session().attribute("usuario", user);
                response.redirect("/home");
            }catch (Exception e){
                System.out.println(e);
                response.redirect("/home");
            }
            return "";
        });

        Spark.post("/modificarArticulo/:artID", (request, response) -> {

            ArrayList<Etiqueta> tags = tomarEtiquetas();
            modificar = "false";

            EjecutarQuery("delete from ArticulosEtiquetas where articulo = " + request.params("artID"));
            String[] etiquetas = request.queryParams("etiquetas").split(",");

            for (int i = 0; i < etiquetas.length; i++)
            {
                boolean esta = false;
                for (Etiqueta e: tags)
                {
                    if(e.getEtiqueta().equals(etiquetas[i]))
                    {
                        esta = true;
                    }
                }
                if(!esta)
                {
                    EjecutarQuery("insert into Etiquetas (etiqueta) values ('" + etiquetas[i] + "')");
                }


                long tagID = selectID("select * from Etiquetas where etiqueta ='" + etiquetas[i] + "'");
                EjecutarQuery("insert into ArticulosEtiquetas (articulo, etiqueta) values(" + request.params("artID")  + ", " + tagID + ")");
            }

            EjecutarQuery("update Articulos set titulo ='" + request.queryParams("titulo")  + "', cuerpo = '" + request.queryParams("cuerpo") + "' where id = " + request.params("artID"));
            response.redirect("/post/" + request.params("artID"));

            return "";
        });

        Spark.post("/agregarArticulo", (request, response) -> {

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.now();
            String[] etiquetas = request.queryParams("etiquetas").split(",");
            ArrayList<Etiqueta> tags = tomarEtiquetas();

            Usuario us = request.session(true).attribute("usuario");
            EjecutarQuery("insert into Articulos (titulo, cuerpo, autor, fecha) values('" + request.queryParams("titulo") +"', '" + request.queryParams("cuerpo")  + "', '" + us.getUsername() + "', '" + dtf.format(localDate).toString()  + "')");
           
            long artID = selectID("select * from Articulos");

            for (int i = 0; i < etiquetas.length; i++) 
            {
                boolean encontrado = false;
                for (Etiqueta e: tags)
                {
                    if(e.getEtiqueta().equals(etiquetas[i]))
                    {
                        encontrado = true;
                    }
                }
                if(!encontrado)
                {
                    EjecutarQuery("insert into Etiquetas (etiqueta) values ('" + etiquetas[i] + "')");
                }

                long tagID = selectID("select * from Etiquetas where etiqueta ='" + etiquetas[i] + "'");
                EjecutarQuery("insert into ArticulosEtiquetas (articulo, etiqueta) values(" + artID +", " + tagID +")");
            }

            response.redirect("/home");
            return "";
        });

        Spark.post("/agregarComentario/:artID", (request, response) -> {

            Usuario user = request.session().attribute("usuario");
            EjecutarQuery("insert into Comentarios (comentario, autor, articulo) values('" + request.queryParams("comentario") + "', '" + user.getUsername() + "', " + request.params("artID") + ")");
            response.redirect("/post/" + request.params("artID"));
            return "";
        });

        before("/agregarComentario/*",(request, response) ->
        {
            autorizado(request, response);
        });

        before("/agregarArticulo",(request, response) ->
        {
            autorizado(request, response);
        });

        before("/borrarComentario/*",(request, response) ->
        {
            autorizado(request, response);
        });

        before("/eliminarArticulo/*",(request, response) ->
        {
            autorizado(request, response);
        });

        before("/modificarArticulo/*",(request, response) ->
        {
            autorizado(request, response);
        });

        before("/*",(request, response) ->
        {

            try {
                String[] s = request.splat();

                if(!s[0].equals("adminRegister"))
                {
                    if(!existeAdmin())
                    {
                        response.redirect("/adminRegister");
                    }
                }
            } catch (Exception e) {
                response.redirect("/home");
            }
        });

        before("/adminRegister",(request, response) ->
        {
            if(existeAdmin())
            {
                response.redirect("/login");
            }
        });


        before("/adminUsuarios",(request, response) ->
        {
            Usuario usuario = request.session().attribute("usuario");
            if(usuario == null){
                response.redirect("/login");
            }
            else{
                if(!usuario.isAdministrator()){
                    response.redirect("/login");
                }
            }
        });

        before("/asignarAdmin/:*/:*", (request, response) ->
        {
            response.redirect("/adminUsuarios");
        });

        before("/asignarUser/:*/:*", (request, response) ->
        {
            response.redirect("/adminUsuarios");
        });

        after("/post/:*", (request, response) ->
        {
            modificar = "false";
        });
    }

    private static String get70firstChar(String text)
    {

        int cont = 0;
        String finalText = "";

        for(int x = 0; x < text.length(); x++)
        {
            finalText += text.charAt(x);
            cont++;

            if (cont != 70)
            {
                continue;
            }
            break;
     }

     return finalText+"...";
    }

    private static boolean existeAdmin()
    {
        boolean existencia = false;

        try
        {
            Class.forName("org.h2.Driver");
            Connection con = DriverManager.getConnection(baseDeDatos, us, psw);
            Statement statement = con.createStatement();

            ResultSet rs = statement.executeQuery("select * from Usuarios");

            while (rs.next())
            {
                existencia = true;
                break;
            }
            statement.close();
            con.close();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }

        return existencia;
    }

    private static Usuario existeUsuario(String username, String password)
    {
        try
        {
            Class.forName("org.h2.Driver");
            Connection con = DriverManager.getConnection(baseDeDatos, us, psw);
            Statement statement = con.createStatement();

            ResultSet rs = statement.executeQuery("select * from Usuarios where username = '" + username +"' and password = '" + password + "'");
            while (rs.next())
            {
                return new Usuario(rs.getString("username"), rs.getString("nombre"), rs.getString("password"), rs.getBoolean("administrador"), rs.getBoolean("autor"));
            }
            statement.close();
            con.close();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private static ArrayList<Articulo> tomarArticulos() {

        ArrayList<Articulo> articulos = new ArrayList<>();

        Statement statement1;
        Statement statement2;
        Statement statement3;
        Statement statement4;
        Statement statement5;

        try
        {
            Class.forName("org.h2.Driver");
            Connection con = DriverManager.getConnection(baseDeDatos, us, psw);
            Statement statement = con.createStatement();

            ResultSet rs = statement.executeQuery("select * from articulos order by id desc, fecha desc");
            while (rs.next())
            {
                statement1 = con.createStatement();
                ResultSet rs1 = statement1.executeQuery("select * from articulosetiquetas");
                ArrayList<Etiqueta> etiquetas =  new ArrayList<>();;
                while (rs1.next())
                {
                    if(rs1.getLong("articulo") == rs.getLong("id"))
                    {

                        statement2 = con.createStatement();
                        ResultSet rs2 = statement2.executeQuery("select * from etiquetas where id =" + rs1.getLong("etiqueta"));

                        while (rs2.next())
                        {
                            etiquetas.add(new Etiqueta(rs2.getLong("id"), rs2.getString("etiqueta")));
                        }
                        statement2.close();
                    }
                }
                statement1.close();

                statement3 = con.createStatement();
                ResultSet rs3 = statement3.executeQuery("select * from comentarios");
                ArrayList<Comentario> comentarios = new ArrayList<>();

                while (rs3.next())
                {
                    if(rs3.getLong("articulo") == rs.getLong("id"))
                    {
                        statement4 = con.createStatement();
                        ResultSet rs4 = statement4.executeQuery("select * from usuarios");
                        Usuario user = null;

                        while (rs4.next())
                        {
                            if(rs4.getString("username") == rs3.getString("autor")) {
                                 user = new Usuario(rs4.getString("username"), rs4.getString("nombre"), rs4.getString("password"),rs4.getBoolean("administrador"), rs4.getBoolean("autor"));
                            }
                        }

                        comentarios.add(new Comentario(rs3.getLong("id"), rs3.getString("comentario"), user));
                        statement4.close();
                    }
                }
                statement3.close();


                statement5 = con.createStatement();
                ResultSet rs4 = statement5.executeQuery("select * from usuarios");
                Usuario user = null;

                while (rs4.next())
                {
                    if(rs4.getString("username") == rs.getString("autor")) {
                        user = new Usuario(rs4.getString("username"), rs4.getString("nombre"), rs4.getString("password"),rs4.getBoolean("administrador"), rs4.getBoolean("autor"));
                    }
                }

                articulos.add(new Articulo(rs.getLong("id"), rs.getNString("titulo"), rs.getNString("cuerpo"), user, rs.getDate("fecha"), comentarios, etiquetas));
            }

            statement.close();
            con.close();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        return articulos;
    }

    private static ArrayList<Etiqueta> tomarEtiquetas()
    {
        ArrayList<Etiqueta> tags = new ArrayList<Etiqueta>();
        try
        {
            Class.forName("org.h2.Driver");
            Connection con = DriverManager.getConnection(baseDeDatos, us, psw);
            Statement statement = con.createStatement();

            ResultSet rs = statement.executeQuery("select * from Etiquetas");
            while (rs.next())
            {
                tags.add(new Etiqueta(rs.getLong("id"), rs.getString("etiqueta")));
            }
            statement.close();
            con.close();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        return tags;
    }

    private static ArrayList<Usuario> tomarUsuarios()
    {
        ArrayList<Usuario> usrs = new ArrayList<>();

        try
        {
            Class.forName("org.h2.Driver");
            Connection con = DriverManager.getConnection(baseDeDatos, us, psw);
            Statement statement = con.createStatement();

            ResultSet rs = statement.executeQuery("select * from Usuarios order by username desc");
            while (rs.next())
            {
                usrs.add(new Usuario(rs.getString("username"), rs.getString("nombre"), rs.getString("password"), rs.getBoolean("administrador"), rs.getBoolean("autor")));
            }
            statement.close();
            con.close();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        return usrs;
    }

    private static long selectID(String query)
    {
        long id = -1;

        try
        {
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection(baseDeDatos, us, psw);
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(query);
            while (rs.next())
            {
                id = rs.getLong("id");
            }
            statement.close();
            connection.close();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        return id;
    }

    private static void EjecutarQuery(String query)
    {
        try
        {
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection(baseDeDatos, us, psw);
            Statement statement = connection.createStatement();

            statement.executeUpdate(query);
            statement.close();
            connection.close();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    private static void autorizado(Request request, Response response) 
    {
        Session seccion = request.session(true);
        Usuario user = seccion.attribute("usuario");

        if(user == null)
        {
            response.redirect("/login");
        }
        else if(!user.isAuthor())
        {
            halt(401, "No Autorizado");
        }
    }
}
