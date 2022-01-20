package ru.spimex.rest;

import javax.ws.rs.*;
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
