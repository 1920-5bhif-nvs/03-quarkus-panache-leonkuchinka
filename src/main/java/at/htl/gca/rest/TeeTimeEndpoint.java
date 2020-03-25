package at.htl.gca.rest;

import at.htl.gca.business.GolferDao;
import at.htl.gca.business.GolferPanacheRepo;
import at.htl.gca.business.TeeTimePanacheRepo;
import at.htl.gca.model.Golfer;
import at.htl.gca.model.TeeTime;
import io.quarkus.hibernate.orm.panache.Panache;
import io.quarkus.panache.common.Parameters;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/teetime")
@Produces(MediaType.APPLICATION_JSON)
public class TeeTimeEndpoint {

    @Inject
    TeeTimePanacheRepo teeTimePanacheRepo;

    @Inject
    GolferDao golferPanacheRepo;

    @GET
    @Path("/golfer")
    public Response findTeeTimesForGolfer(@QueryParam("golferId") Long id){
        Golfer golfer = golferPanacheRepo.findById(id);
        if(golfer == null){
            return Response.status(404).build();
        }
        return Response.ok(teeTimePanacheRepo
                    .find("select t from TeeTime t join fetch t.players where :golfer member of t.players",
                            Parameters.with("golfer", golfer))
                    .list())
                .build();
    }

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTeeTime(TeeTime teeTime){
        teeTimePanacheRepo.persistAndFlush(teeTime);
        return Response.noContent().build();
    }

    @GET
    @Retry(maxRetries = 1)
    @Fallback(fallbackMethod = "fallbackMethod")
    @Timeout(2000)
    @Counted(name = "someMethod_called" )
    @Timed(name= "timer", description = "How long it takes to perform this task", unit = MetricUnits.MILLISECONDS)
    public Response findAll(){
        return Response.ok(teeTimePanacheRepo.listAll()).build();
    }

    public Response fallbackMethod() {
        return Response.serverError().build();
    }

}
