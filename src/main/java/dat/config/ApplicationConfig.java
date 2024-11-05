package dat.config;

import dat.Routes.Routes;

import dat.controllers.ExceptionController;
import dat.exceptions.ApiException;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ApplicationConfig {

    private static Routes routes;
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfig.class);

    private static final ExceptionController exceptionController = new ExceptionController();



    public static void configuration(JavalinConfig config) {
        config.router.contextPath = "";
        config.showJavalinBanner = false;
        config.http.defaultContentType = "application/json";
        config.router.apiBuilder(routes.getRoutes());

        config.bundledPlugins.enableRouteOverview("/routes");
    }

    public static String getProperty(String propName) throws IOException
    {
        try (InputStream is = HibernateConfig.class.getClassLoader().getResourceAsStream("properties-from-pom.properties"))
        {
            Properties prop = new Properties();
            prop.load(is);
            return prop.getProperty(propName);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new IOException("Could not read property from pom file. Build Maven!");
        }
    }

    public static Javalin startServer(int port) {
        routes = new Routes();
        var app = Javalin.create(ApplicationConfig::configuration);
        //app.exception(ApiException.class, exceptionController::apiExceptionHandler);
        //app.exception(Exception.class, exceptionController::exceptionHandler);
        app.start(port);
        return app;
    }

    public static void stopServer(Javalin app) {
        app.stop();
    }
}
