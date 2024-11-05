package dat.Routes;


import dat.config.Populator;
import dat.controllers.TripController;
import io.javalin.apibuilder.EndpointGroup;
import static io.javalin.apibuilder.ApiBuilder.*;

public class TripRoutes {
    private final TripController tripController = new TripController();

    public EndpointGroup getRoutes() {

        return () -> {
            get("/trips", tripController::getAll);
            get("/trips/{id}", tripController::getById);
            post("/trips", tripController::create);
            put("/trips/{id}", tripController::update);
            delete("/trips/{id}", tripController::delete);
            put("/trips/{tripId}/guides/{guideId}", tripController::addGuideToTrip);
            post("/trips/populate", tripController::populate);
            get("/trips/category/{category}", tripController::filterByCategory);
            get("/trips/prices",tripController::totalPrice);
            get("/trips/fetch/{category}", tripController::fetch);
        };
    }
}

