/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.abel.prax.peterbox.rest;

import java.io.File;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import org.codehaus.jackson.map.ObjectMapper;
import za.co.abel.prax.peterbox.data.FileData;
import za.co.abel.prax.peterbox.data.SharedFileBean;

/**
 * REST Web Service
 *
 * @author Abel Mapharisa <abel.mapharisa@imqs.co.za>
 */
@Path("box")
@RequestScoped
public class FilesService {

    private SharedFileBean bean = new SharedFileBean();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of FilesService
     */
    public FilesService() {
    }

    /**
     * Retrieves representation of an instance of
     * za.co.abel.prax.peterbox.data.FilesService
     *
     * @return an instance of za.co.abel.prax.peterbox.data.SharedFileBean
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAllFiles() {
        Response resp = null;

        try {
            List<FileData> files = bean.getFiles();
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(files);
            resp = Response.ok(jsonString).build();

            return resp;
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex).build();
        }

    }

    @GET
    @Path("{fileName}")
    @Produces("application/zip")
    public Response download(@PathParam("fileName") String fileName) {
        ResponseBuilder response = null;

        try {
            List<FileData> dataList = bean.getFiles();

            for (FileData data : dataList) {
                String name = fileName.replaceAll("_", "");
                if (data.getName().equalsIgnoreCase(name)) {
                    String path = data.getPath();
                    File file = new File(path);

                    response = Response.ok((Object) file);
                    response.header("Content-Disposition",
                            "attachment; filename=" + data.getName());
                    break;
                }
            }
            return response.build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("File [" + fileName + "] not found").build();
        }

    }

}
