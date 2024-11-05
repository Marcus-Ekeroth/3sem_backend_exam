package dat.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dat.config.Populator;
import dat.daos.GuideDAO;
import dat.daos.TripDAO;
import dat.dtos.GuideDTO;
import dat.dtos.TripDTO;
import dat.entities.Category;
import dat.entities.Trip;
import dat.exceptions.Message;
import io.javalin.http.Context;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TripController {
    private TripDAO tripDAO = new TripDAO();

    private GuideDAO guideDAO = new GuideDAO();

    private ObjectMapper objectMapper = new ObjectMapper();

    String url = "https://packingapi.cphbusinessapps.dk/packinglist/{category}";


    public void getAll(Context ctx) {
        List<TripDTO> trips = tripDAO.getAll();
        ctx.res().setStatus(200);
        ctx.json(trips, TripDTO.class);
    }

    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            TripDTO trip = tripDAO.getById(id);
            ctx.res().setStatus(200);
            ctx.json(trip, TripDTO.class);
        } catch (NullPointerException e) {
            ctx.res().setStatus(404);
            ctx.json(new Message(404, "Trip not found " + e.getMessage()));
        }
    }

    public void create(Context ctx) {
        TripDTO jsonRequest = ctx.bodyAsClass(TripDTO.class);
        TripDTO trip = tripDAO.create(jsonRequest);
        ctx.res().setStatus(201);
        ctx.json(trip, TripDTO.class);
    }

    public void update(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        TripDTO jsonRequest = ctx.bodyAsClass(TripDTO.class);
        TripDTO trip = tripDAO.update(id, jsonRequest);
        ctx.res().setStatus(200);
        ctx.json(trip, Trip.class);
    }

    public void delete(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            //TripDTO trip = tripDAO.getById(id);
            tripDAO.delete(id);
            ctx.res().setStatus(204);
        } catch (NullPointerException e) {
            ctx.res().setStatus(404);
            ctx.json(new Message(404, "Trip not found " + e.getMessage()));
        }
    }

    public void addGuideToTrip(Context ctx) {
        int tripId = Integer.parseInt(ctx.pathParam("tripId"));
        int guideId = Integer.parseInt(ctx.pathParam("guideId"));
        tripDAO.addGuideToTrip(tripId, guideId);
        TripDTO trip = tripDAO.getById(tripId);
        ctx.res().setStatus(200);
        ctx.json(trip, TripDTO.class);
    }

    public void populate(Context ctx) {
        Populator.populate();
        ctx.res().setStatus(200);
        ctx.json(new Message(200, "Database populated"));
    }

    public void filterByCategory(Context ctx) {
        Category category = Category.valueOf(ctx.pathParam("category"));
        List<TripDTO> trips = tripDAO.filterByCategory(category);
        ctx.res().setStatus(200);
        ctx.json(trips, TripDTO.class);
    }

    public void totalPrice(Context ctx) {
        Map<Integer, Double> guideAndPrice = new HashMap<>();
        List<GuideDTO> guides = guideDAO.getAll();

        for (GuideDTO guide : guides) {
            double totalPrice = guide.getTrips().stream()
                    .mapToDouble(TripDTO::getPrice)
                    .sum();
            guideAndPrice.put(guide.getId(), totalPrice);
        }

        ctx.res().setStatus(200);
        ctx.json(guideAndPrice);
    }


    public void fetch(Context ctx) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        Category category = Category.valueOf(ctx.pathParam("category"));
        try {
            String categoryUrl = url.replace("{category}", category.name());
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest
                    .newBuilder()
                    .header("Accept", "application/json")
                    .uri(new URI(categoryUrl))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                ctx.res().setStatus(200);
                ctx.result(response.body());
            } else {
                ctx.res().setStatus(response.statusCode());
                ctx.result("Failed to fetch data: " + response.body());
            }
        } catch (Exception e) {
            e.printStackTrace();
            ctx.res().setStatus(500);
            ctx.json(new Message(500, "Internal Server Error: " + e.getMessage()));
        }
    }

}