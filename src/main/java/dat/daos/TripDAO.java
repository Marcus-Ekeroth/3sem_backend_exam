package dat.daos;

import dat.config.HibernateConfig;
import dat.dtos.GuideDTO;
import dat.dtos.TripDTO;
import dat.entities.Category;
import dat.entities.Guide;
import dat.entities.Trip;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TripDAO implements IDAO<TripDTO>, ITripGuideDAO {

    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("guide");

    @Override
    public List<TripDTO> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT d FROM Trip d", TripDTO.class).getResultList();
        }
    }

    @Override
    public TripDTO getById(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            Trip trip =  em.find(Trip.class, id);
            return new TripDTO(trip);
        }
    }


    @Override
    public TripDTO create(TripDTO tripDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Trip trip = new Trip(tripDTO);
            em.merge(trip);
            em.getTransaction().commit();
            return new TripDTO(trip);
        }
    }

    @Override
    public TripDTO update(int id, TripDTO tripDTO) {
        {
            try (EntityManager em = emf.createEntityManager()) {
                em.getTransaction().begin();
                Trip trip = em.find(Trip.class, id);
                trip.setStartTime(tripDTO.getStartTime());
                trip.setEndTime(tripDTO.getEndTime());
                trip.setStartPosition(tripDTO.getStartPosition());
                trip.setName(tripDTO.getName());
                trip.setPrice(tripDTO.getPrice());
                trip.setCategory(tripDTO.getCategory());
                Trip updatedTrip = em.merge(trip);
                em.getTransaction().commit();
                return new TripDTO(updatedTrip);
            }
        }
    }

        @Override
        public void delete (int id){
            try (EntityManager em = emf.createEntityManager()) {
                em.getTransaction().begin();
                TripDTO trip = em.find(TripDTO.class, id);
                em.remove(trip);
                em.getTransaction().commit();

            }
        }

        @Override
        public void addGuideToTrip ( int tripId, int guideId){
            try (EntityManager em = emf.createEntityManager()) {
                em.getTransaction().begin();
                Trip trip = em.find(Trip.class, tripId);
                Guide guide = em.find(Guide.class, guideId);
                guide.getTrips().add(trip);
                em.getTransaction().commit();
            }
        }

        @Override
        public Set<TripDTO> getTripsByGuide ( int guideId){
            try (EntityManager em = emf.createEntityManager()) {
                Guide guide = em.find(Guide.class, guideId);
                Set<TripDTO> tripDTOS = new HashSet<>();
                Set<Trip> trips = guide.getTrips();
                for(Trip trip : trips) {
                    tripDTOS.add(new TripDTO(trip));
                }
                return tripDTOS;
            }
        }

    public List<TripDTO> filterByCategory(Category category) {
        try (EntityManager em = emf.createEntityManager()) {
            List<Trip> trips = em.createQuery("SELECT t FROM Trip t", Trip.class).getResultList();
            return trips.stream()
                    .filter(trip -> trip.getCategory().equals(category))
                    .map(TripDTO::new)
                    .collect(Collectors.toList());
        }
    }
    }

