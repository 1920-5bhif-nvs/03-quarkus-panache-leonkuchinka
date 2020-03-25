package at.htl.gca.business;

import at.htl.gca.model.Golfer;
import io.quarkus.hibernate.orm.panache.Panache;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityGraph;
import javax.transaction.Transactional;
import java.util.HashMap;

@ApplicationScoped
public class GolferPanacheRepo implements PanacheRepository<Golfer> {

    @Inject
    TeeTimePanacheRepo teeTimePanacheRepo;

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

    @Transactional
    public void remove(Golfer entity) {
        teeTimePanacheRepo
                .find("select t from TeeTime t join fetch t.players where :golfer member of t.players",
                        Parameters.with("golfer", entity))
                .list().stream()
                .forEach(teeTime -> {
                    teeTime.removePlayer(entity);
                    teeTimePanacheRepo.persist(teeTime);
                });
        this.delete(entity);

    }
}
