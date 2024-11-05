package dat.Routes;

import dat.controllers.TripController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes {
    private final TripRoutes tripRoutes = new TripRoutes();

    public EndpointGroup getRoutes() {
        return () -> {
            path("/", tripRoutes.getRoutes());
        };
    }
}