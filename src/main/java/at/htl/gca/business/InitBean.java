package at.htl.gca.business;

import at.htl.gca.model.*;
import io.quarkus.runtime.StartupEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@ApplicationScoped
public class InitBean {

    @Inject
    EntityManager em;

    @Transactional
    void init(@Observes StartupEvent ev){
        System.err.println("------init------");


        Team mensTeam = new Team("Mens Team");
        em.persist(mensTeam);

        TeamPlayer leonKuchinka = new TeamPlayer("Leon Kuchinka", -1.3, 18, true, 2015);
        leonKuchinka.setTeam(mensTeam);
        em.persist(leonKuchinka);
        TeamPlayer christophBleier = new TeamPlayer("Christoph Bleier", 4.1, 18, true, 2015);
        christophBleier.setTeam(mensTeam);
        em.persist(christophBleier);
        TeamPlayer philipPfeifenberger = new TeamPlayer("Philip Pfeifenberger", -2.2, 23, false, 2011);
        philipPfeifenberger.setTeam(mensTeam);
        em.persist(philipPfeifenberger);
        TeamPlayer michaelFehringer = new TeamPlayer("Michael Fehringer", -0.0, 24, true, 2010);
        michaelFehringer.setTeam(mensTeam);
        em.persist(michaelFehringer);
        TeamPlayer alexBinder = new TeamPlayer("Alex Binder", -1.4, 24, true, 2009);
        alexBinder.setTeam(mensTeam);
        em.persist(alexBinder);
        HobbyPlayer julianNobis = new HobbyPlayer("Julian Nobis", -54, 18, false);
        em.persist(julianNobis);

        TeeTime firstFlight = new TeeTime(LocalDateTime.now());
        firstFlight.addPlayer(leonKuchinka);
        firstFlight.addPlayer(christophBleier);
        firstFlight.addPlayer(philipPfeifenberger);
        em.persist(firstFlight);

        TeeTime secondFlight = new TeeTime(LocalDateTime.now());
        secondFlight.addPlayer(leonKuchinka);
        secondFlight.addPlayer(michaelFehringer);
        secondFlight.addPlayer(julianNobis);
        em.persist(secondFlight);
    }
}
