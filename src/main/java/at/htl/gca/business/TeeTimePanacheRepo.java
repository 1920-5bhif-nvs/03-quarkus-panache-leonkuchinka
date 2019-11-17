package at.htl.gca.business;

import at.htl.gca.model.TeeTime;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TeeTimePanacheRepo implements PanacheRepository<TeeTime> {

}
