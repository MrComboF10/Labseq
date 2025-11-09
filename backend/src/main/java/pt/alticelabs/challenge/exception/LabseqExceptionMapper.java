package pt.alticelabs.challenge.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.Map;

@Provider
public class LabseqExceptionMapper implements ExceptionMapper<LabseqException> {

    @Override
    public Response toResponse(LabseqException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("error", exception.getMessage()))
                .build();
    }
}
