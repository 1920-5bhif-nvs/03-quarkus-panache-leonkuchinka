package at.htl.gca.business;

import at.htl.gca.model.Golfer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class GolferPanacheRepo implements PanacheRepository<Golfer> {

    @Transactional
    public Golfer save(Golfer entity) {
        this.persistAndFlush(entity);
        return findById(entity.getId());
    }

    @Transactional
    public Golfer update(Golfer entity) {
        this.save(entity);
        return findById(entity.getId());
    }
}
