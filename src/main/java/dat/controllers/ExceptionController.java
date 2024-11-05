package dat.controllers;


import dat.Routes.Routes;
import dat.exceptions.ApiException;
import dat.exceptions.Message;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class ExceptionController {
    private final Logger LOGGER = LoggerFactory.getLogger(Routes.class);

    private LocalDateTime timestamp = LocalDateTime.now();

    public void apiExceptionHandler(ApiException e, Context ctx) {
        LOGGER.error(ctx.attribute("requestInfo") + " " + ctx.res().getStatus() + " " + e.getMessage());
        ctx.status(e.getStatusCode());
        ctx.json(new Message(e.getStatusCode(), e.getMessage()));
    }
    public void exceptionHandler(Exception e, Context ctx) {
        LOGGER.error(ctx.attribute("requestInfo") + " " + ctx.res().getStatus() + " " + e.getMessage());
        ctx.status(500);
        ctx.json(new Message(500, e.getMessage()));
    }

}
