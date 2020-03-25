package at.htl.gca.business;

import io.agroal.pool.DataSource;
import io.smallrye.health.HealthStatus;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.Liveness;
import org.eclipse.microprofile.health.Readiness;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.Entity;
import javax.persistence.EntityManager;

@ApplicationScoped
public class ApplicationHealthChecks {

    @Inject
    EntityManager em;

    @Liveness
    @Produces
    public HealthCheck liveCheck(){
        return HealthStatus.up("App");
    }

    @Readiness
    @Produces
    public HealthCheck dbHealthCheck() {
        try{
            Object obj = em.createNativeQuery("select 1").getResultList();
            return HealthStatus.state("Database", obj != null);
        } catch (Exception e) {
            return HealthStatus.down("Database");
        }
    }
}
