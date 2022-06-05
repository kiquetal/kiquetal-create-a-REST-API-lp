package io.chillplus;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/api/tv")
public class TvShowResrouce
{
    private Long serial = 0L;
    private List<TvShow> arrayList = new ArrayList<>();
    @GET
    @Produces("application/json")
    public List<TvShow> getAll()
    {

        return this.arrayList;
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response create(TvShow tvShow)
    {
        if (tvShow.getId() != null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        if (tvShow.getTitle().trim().isEmpty())
            return Response.status(Response.Status.BAD_REQUEST).build();

        tvShow.setId(++this.serial);
        this.arrayList.add(tvShow);
        return Response.ok(tvShow).build();
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getOneById(@PathParam("id") Long id)
    {
        System.out.println("getOneById"+ id);
        System.out.println("getOneById"+ this.arrayList);
        for (TvShow tvShow : this.arrayList)
        {
            if (tvShow.getId().equals(id))
                return Response.ok(tvShow).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    @DELETE
    @Produces("application/json")
    public Response deleteAll() {
        this.arrayList.clear();
        return Response.ok().build();
    }
    @DELETE
    @Path("/{id}")
    @Produces("application/json")
    public Response deleteOne(@PathParam("id") Long id) {
        for (TvShow tvShow : this.arrayList) {
            if (tvShow.getId().equals(id)) {
                this.arrayList.remove(tvShow);
                return Response.ok().build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

}
