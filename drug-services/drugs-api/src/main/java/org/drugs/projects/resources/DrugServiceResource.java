package org.drugs.projects.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.drugs.projects.model.Drug;
import org.drugs.projects.services.DrugService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

@Singleton
@Path("/")
@Slf4j
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DrugServiceResource {

    private DrugService drugService;
    @Inject
    public DrugServiceResource(DrugService drugService) {
        this.drugService = drugService;
    }


    @Timed
    @GET
    @Path("find")
    public List<Drug> find(@Valid @NotEmpty @QueryParam("brand_name") String brand_name, @Context HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        return drugService.findByBrandName(brand_name);
    }

    @Timed
    @GET
    @Path("{id}")
    public Drug find(@Valid @Positive @PathParam("id") int id, @Context HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        return drugService.findById(id);
    }
}
