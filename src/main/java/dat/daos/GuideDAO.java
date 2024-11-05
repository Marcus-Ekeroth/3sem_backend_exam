package dat.daos;

import dat.config.HibernateConfig;
import dat.dtos.GuideDTO;
import dat.dtos.TripDTO;
import dat.entities.Guide;
import dat.entities.Trip;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class GuideDAO implements IDAO<GuideDTO> {

    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("guide");

    @Override
    public List<GuideDTO> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT g FROM Guide g", GuideDTO.class).getResultList();
        }
    }

    @Override
    public GuideDTO getById(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            Guide guide =  em.find(Guide.class, id);
            return new GuideDTO(guide);
        }
    }

    @Override
    public GuideDTO create(GuideDTO guideDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Guide guide = new Guide(guideDTO);
            em.merge(guide);
            em.getTransaction().commit();
            return new GuideDTO(guide);
        }
    }

    @Override
    public GuideDTO update(int id, GuideDTO guideDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Guide guide = em.find(Guide.class, id);
            guide.setFirstName(guideDTO.getFirstName());
            guide.setLastName(guideDTO.getLastName());
            guide.setEmail(guideDTO.getEmail());
            guide.setPhone(guideDTO.getPhone());
            guide.setYearsOfExperience(guideDTO.getYearsOfExperience());
            Guide mergedGuide = em.merge(guide);
            em.getTransaction().commit();
            return new GuideDTO(mergedGuide);
        }
    }

    @Override
    public void delete(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            GuideDTO guide = em.find(GuideDTO.class, id);
            em.remove(guide);
            em.getTransaction().commit();
        }
    }
}