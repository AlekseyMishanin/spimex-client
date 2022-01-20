package ru.spimex.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Endpoint for working with organizations
 */
@Path(Urls.ORGANIZATIONS_V1)
public interface OrganizationRsService {

    /**
     * Count the number of active organizations in the selected region
     *
     * @param regionId Region ID
     * @return number of active organizations
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    long countOrganizationByRegion(@QueryParam("region") int regionId);
}
