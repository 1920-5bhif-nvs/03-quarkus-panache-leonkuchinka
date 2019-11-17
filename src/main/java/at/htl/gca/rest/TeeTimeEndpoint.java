package at.htl.gca.rest;

import at.htl.gca.business.GolferPanacheRepo;
import at.htl.gca.business.TeeTimePanacheRepo;
import at.htl.gca.model.Golfer;
import at.htl.gca.model.TeeTime;
import io.quarkus.panache.common.Parameters;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Path("/teetime")
@Produces(MediaType.APPLICATION_JSON)
public class TeeTimeEndpoint {

    @Inject
    TeeTimePanacheRepo teeTimePanacheRepo;

    @Inject
    GolferPanacheRepo golferPanacheRepo;

    @GET
    public Response findTeeTimesForGolfer(@QueryParam("golferId") Long id){
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        teeTimePanacheRepo
                .find("select distinct t from TeeTime t join t.players p where p.id = :id",
                        Parameters.with("id", id))
                .list()
                .stream()
                .forEach(o -> {
                    arrayBuilder.add(Json.createObjectBuilder()
                            .add("id", o.getId())
                            .add("time", o.getTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                        .build());
                });

        return Response.ok().entity(arrayBuilder.build().toString()).build();
    }

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTeeTime(TeeTime teeTime){
        teeTime.setTime(LocalDateTime.now());

        for (Golfer golfer: new ArrayList<Golfer>(teeTime.getPlayers())
             ) {
            Golfer temp = golferPanacheRepo.findById(golfer.getId());
            teeTime.removePlayer(golfer);
            teeTime.addPlayer(temp);
        }

        teeTimePanacheRepo.persistAndFlush(teeTime);
        return Response.ok(teeTime).build();
    }

    @GET
    public Response findAll(){
        return Response.ok(teeTimePanacheRepo.listAll()).build();
    }
}
