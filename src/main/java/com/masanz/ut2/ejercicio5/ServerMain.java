package com.masanz.ut2.ejercicio5;

import com.masanz.ut2.ejercicio5.dto.Objeto;
import com.masanz.ut2.ejercicio5.dto.Usuario;
import com.masanz.ut2.ejercicio5.init.InicializarBD;
import freemarker.template.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class ServerMain {

    private static final Logger logger = LogManager.getLogger(ServerMain.class);
    private static Usuario usuarioAutenticado;

    public static void main(String[] args) {

        // Tipos de logger: TRACE, DEBUG, INFO, WARN, ERROR
        logger.info("PODEIS EMPLEAR ESTE METODO PARA SACAR INFORMACION, COMO SI FUESE UN SOUT");
        logger.error("ESTE SIRVE PARA VOLCAR ERRORES, POR EJEMPLO, LAS EXCEPCIONES");

        staticFileLocation("/public");
        port(8080);

        InicializarBD db = new InicializarBD(
                "pruebas_acad","root",""
        );
        db.conectar();
        FreeMarkerEngine freeMarker = new FreeMarkerEngine();
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_0);
        configuration.setClassForTemplateLoading(ServerMain.class, "/templates");
        freeMarker.setConfiguration(configuration);

        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("saludo", "¡Hola desde Java!");
            return new ModelAndView(model, "login.ftl");
        }, freeMarker);

        post("/login", (request, response) -> {

            String username = request.queryParams("username");
            String password = request.queryParams("password");

            boolean authenticated = db.autenticar(username,password);

            if (authenticated) {
                usuarioAutenticado = db.getDatosUsuario(username,password);
                response.redirect("/home.html");
            } else {
                response.redirect("/");
            }
            return null;
        });

        get("/articulos", (request, response) -> {
            ArrayList<Objeto> articulos = db.obtenerObjetosUsuario(usuarioAutenticado.getId());
            System.out.println(articulos);
            Map<String, Object> model = new HashMap<>();
            model.put("mostrarIdExtra", false);
            model.put("usuario", usuarioAutenticado);
            model.put("articulos", articulos);
            return new ModelAndView(model, "articulos.ftl");
        }, freeMarker);

        get("/articulos/:id", (request, response) -> {
            int userId = Integer.parseInt(request.params(":id"));
            logger.info("BUSCANDO ARTICULOS DEL USUARIO CON ID: "+userId);
            Map<String, Object> model = new HashMap<>();
            model.put("mostrarIdExtra", false);
            model.put("mostrarIdExtra", true);
            //model.put("articulos", articulos);
            return new ModelAndView(model, "articulos.ftl");
        }, freeMarker);

        get("/usuarios", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            //model.put("usuarios", usuarios);
            return new ModelAndView(model, "usuarios.ftl");
        }, freeMarker);

        get("/usuario/:id", (request, response) -> {
            int userId = Integer.parseInt(request.params(":id"));
            logger.info("BUSCANDO INFORMACION DEL USUARIO CON ID: "+userId);
            Map<String, Object> model = new HashMap<>();
            model.put("usuario",usuarioAutenticado);
            return new ModelAndView(model, "usuario.ftl");
        }, freeMarker);

        get("/comprar/:id", (request, response) -> {
            int articuloId = Integer.parseInt(request.params(":id"));
            logger.info("COMPRANDO ARTICULO ID: "+articuloId);
            response.redirect("/home.html");
            return null;
        }, freeMarker);

        init();
    }
}