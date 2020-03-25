package at.htl.gca.business;

import at.htl.gca.model.Golfer;
import at.htl.gca.model.TeeTime;
import io.quarkus.hibernate.orm.panache.Panache;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityGraph;
import java.util.HashMap;

@ApplicationScoped
public class TeeTimePanacheRepo implements PanacheRepository<TeeTime> {

    public TeeTime getById(long id) {
        EntityGraph eg = Panache.getEntityManager().getEntityGraph("teetime-entity-graph");
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.fetchgraph", eg);
        return Panache.getEntityManager().find(TeeTime.class, id, properties);
    }
}
