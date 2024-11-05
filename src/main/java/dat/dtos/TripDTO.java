package dat.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import dat.entities.Category;
import dat.entities.Trip;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor

public class TripDTO {
    private int id;
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime startTime;
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime endTime;
    private String startPosition;
    private String name;
    private double price;
    private Category category;


    public TripDTO(Trip trip) {
        this.id = trip.getId();
        this.startTime = trip.getStartTime();
        this.endTime = trip.getEndTime();
        this.startPosition = trip.getStartPosition();
        this.name = trip.getName();
        this.price = trip.getPrice();
        this.category = trip.getCategory();
    }

    public TripDTO(int id, LocalTime startTime, LocalTime endTime, String startPosition, String name, double price, Category category) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startPosition = startPosition;
        this.name = name;
        this.price = price;
        this.category = category;
    }
}