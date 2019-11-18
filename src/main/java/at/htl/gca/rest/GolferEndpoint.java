package at.htl.gca.rest;

import at.htl.gca.business.GolferPanacheRepo;
import at.htl.gca.model.Golfer;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/golfer")
@Produces(MediaType.APPLICATION_JSON)
public class GolferEndpoint {

    @Inject
    GolferPanacheRepo golferPanacheRepo;

    @GET
    public Response findAll(){
        return Response.ok(golferPanacheRepo.listAll()).build();
    }

    /*
    http://localhost:8080/golfer
    {"name":"Max Mustermann","hcp":-45,"age":45}
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response save(Golfer entity){
        try {
            entity = golferPanacheRepo.save(entity);
            return Response.ok().entity(entity).build();
        }catch(Exception e){
            return Response.serverError().build();
        }
    }

    /*
    http://localhost:8080/golfer?id=1
    {"id":1,"name":"Leon Kuchinka","hcp":-1.3,"age":19}
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response update(@QueryParam("id") Long id, Golfer entity) {
        try {
            Golfer current = golferPanacheRepo.findById(id);
            current.setAge(entity.getAge());
            current.setHcp(entity.getHcp());
            current.setName(entity.getName());
            entity = golferPanacheRepo.update(current);
            return Response.ok().entity(entity).build();
        }catch(Exception e){
            return Response.serverError().build();
        }
    }

    /*
    http://localhost:8080/golfer?id=1
     */
    @DELETE
    @Transactional
    public Response delete(@QueryParam("id") Long id){
        try {
            Golfer golfer = golferPanacheRepo.findById(id);
            golferPanacheRepo.delete(golfer);
            return Response.ok().entity(golfer).build();
        }catch(Exception e){
            return Response.serverError().build();
        }
    }
}
