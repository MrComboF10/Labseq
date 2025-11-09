package pt.alticelabs.challenge.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import pt.alticelabs.challenge.dto.LabseqResponse;
import pt.alticelabs.challenge.service.LabseqService;

import java.math.BigInteger;

@Path("/labseq")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Labseq API", description = "Computes Labseq sequence values efficiently with caching")
public class LabseqResource {

    @Inject
    LabseqService service;

    @GET
    @Path("/{n}")
    @Operation(
            summary = "Get Labseq value by index",
            description = "Computes or retrieves from cache the Labseq value for a given index"
    )
    @APIResponse(
            responseCode = "200",
            description = "Labseq value successfully retrieved",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = LabseqResponse.class),
                    examples = {
                            @ExampleObject(
                                    name = "labseq(0)",
                                    value = "{\"index\":0,\"value\":0}"
                            ),
                            @ExampleObject(
                                    name = "labseq(5)",
                                    value = "{\"index\":5,\"value\":1}"
                            ),
                            @ExampleObject(
                                    name = "labseq(10)",
                                    value = "{\"index\":10,\"value\":3}"
                            )
                    }
            )
    )
    @APIResponse(responseCode = "400", description = "Invalid index (negative number)")
    public Response getLabseqValue(
            @PathParam("n")
            @org.eclipse.microprofile.openapi.annotations.parameters.Parameter(
                    description = "Non-negative index of the Labseq sequence",
                    examples = {
                            @ExampleObject(
                                    name = "labseq(0)",
                                    value = "0"
                            ),
                            @ExampleObject(
                                    name = "labseq(5)",
                                    value = "5"
                            ),
                            @ExampleObject(
                                    name = "labseq(10)",
                                    value = "10"
                            )
                    }
            )
            long n
    ) {
        BigInteger value = service.computeLabseq(n);
        return Response.ok(new LabseqResponse(n, value)).build();
    }
}
