package dat.config;

import dat.daos.GuideDAO;
import dat.daos.TripDAO;
import dat.dtos.GuideDTO;
import dat.dtos.TripDTO;
import dat.entities.Category;
import dat.entities.Guide;
import dat.entities.Trip;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Populator {
    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("guide");

    private static TripDAO tripDAO = new TripDAO();
    private static GuideDAO guideDAO = new GuideDAO();

    public static void populate() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            TripDTO trip1 = new TripDTO(1,LocalTime.of(9, 0), LocalTime.of(12, 0), "Start Point A", "Trip A", 100.0, Category.BEACH);
            TripDTO trip2 = new TripDTO(2,LocalTime.of(14, 0), LocalTime.of(17, 0), "Start Point B", "Trip B", 150.0, Category.SEA);
            TripDTO trip3 = new TripDTO(3,LocalTime.of(10, 0), LocalTime.of(13, 0), "Start Point C", "Trip C", 120.0, Category.FOREST);
            TripDTO trip4 = new TripDTO(4,LocalTime.of(15, 0), LocalTime.of(18, 0), "Start Point D", "Trip D", 180.0, Category.SNOW);

            GuideDTO guide1 = new GuideDTO(1,"John", "Doe", "john.doe@example.com", "1234567890", 10);
            GuideDTO guide2 = new GuideDTO(2,"Jane", "Smith", "jane.smith@example.com", "0987654321", 8);

            guideDAO.create(guide1);
            guideDAO.create(guide2);

            tripDAO.addGuideToTrip(trip1.getId(), guide1.getId());
            tripDAO.addGuideToTrip(trip2.getId(), guide1.getId());
            tripDAO.addGuideToTrip(trip3.getId(), guide2.getId());
            tripDAO.addGuideToTrip(trip4.getId(), guide2.getId());
            tripDAO.create(trip1);
            tripDAO.create(trip2);
            tripDAO.create(trip3);
            tripDAO.create(trip4);

            em.getTransaction().commit();
        }
    }
}