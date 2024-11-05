package dat.entities;

import dat.dtos.TripDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "trip")

public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @Setter
    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Setter
    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Setter
    @Column(name = "start_position", nullable = false)
    private String startPosition;

    @Setter
    @Column(name = "name", nullable = false)
    private String name;

    @Setter
    @Column(name = "price", nullable = false)
    private double price;

    @Setter
    @Column(name = "category", nullable = false)
    private Category category;

    public Trip(LocalTime startTime, LocalTime endTime, String startPosition, String name, double price, Category category) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.startPosition = startPosition;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public Trip(TripDTO trip) {
        this.id = trip.getId();
        this.startTime = trip.getStartTime();
        this.endTime = trip.getEndTime();
        this.startPosition = trip.getStartPosition();
        this.name = trip.getName();
        this.price = trip.getPrice();
        this.category = trip.getCategory();
    }
}
