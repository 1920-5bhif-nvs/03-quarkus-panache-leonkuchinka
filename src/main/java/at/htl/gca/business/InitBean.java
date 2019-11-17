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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

        TeamPlayer leonKuchinka = new TeamPlayer("Leon Kuchinka", -1.7, 17, true, 2015);
        leonKuchinka.setTeam(mensTeam);
        em.persist(leonKuchinka);
        TeamPlayer christophBleier = new TeamPlayer("Christoph Bleier", 2.4, 17, true, 2015);
        christophBleier.setTeam(mensTeam);
        em.persist(christophBleier);
        TeamPlayer philipPfeifenberger = new TeamPlayer("Philip Pfeifenberger", -2.7, 22, false, 2011);
        philipPfeifenberger.setTeam(mensTeam);
        em.persist(philipPfeifenberger);
        TeamPlayer michaelFehringer = new TeamPlayer("Michael Fehringer", -0.8, 23, true, 2010);
        michaelFehringer.setTeam(mensTeam);
        em.persist(michaelFehringer);
        TeamPlayer alexBinder = new TeamPlayer("Alex Binder", -1.6, 23, true, 2009);
        alexBinder.setTeam(mensTeam);
        em.persist(alexBinder);
        HobbyPlayer julianNobis = new HobbyPlayer("Julian Nobis", -54, 18, false);
        em.persist(julianNobis);

        TeeTime firstFlight = new TeeTime(LocalDateTime.parse("2019-04-23T08:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        firstFlight.addPlayer(leonKuchinka);
        firstFlight.addPlayer(christophBleier);
        firstFlight.addPlayer(philipPfeifenberger);
        em.persist(firstFlight);

        TeeTime secondFlight = new TeeTime(LocalDateTime.parse("2019-04-23T12:30:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        secondFlight.addPlayer(leonKuchinka);
        secondFlight.addPlayer(michaelFehringer);
        secondFlight.addPlayer(julianNobis);
        em.persist(secondFlight);
    }
}
