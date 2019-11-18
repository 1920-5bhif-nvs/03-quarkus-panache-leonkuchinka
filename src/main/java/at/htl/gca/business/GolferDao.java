package at.htl.gca.business;

import at.htl.gca.model.Golfer;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class GolferDao {

    @PersistenceContext
    public EntityManager em;

    public Golfer findById(Long id){
        return em.find(Golfer.class, id);
    }
}
