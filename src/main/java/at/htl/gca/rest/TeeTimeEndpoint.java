package at.htl.gca.rest;

import at.htl.gca.business.GolferDao;
import at.htl.gca.business.GolferPanacheRepo;
import at.htl.gca.business.TeeTimePanacheRepo;
import at.htl.gca.model.Golfer;
import at.htl.gca.model.TeeTime;
import io.quarkus.panache.common.Parameters;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
        return Response.ok(teeTime).build();
    }

    @GET
    public Response findAll(){
        return Response.ok(teeTimePanacheRepo.listAll()).build();
    }
}
