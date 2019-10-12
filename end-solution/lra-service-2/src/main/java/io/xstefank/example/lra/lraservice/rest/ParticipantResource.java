package io.xstefank.example.lra.lraservice.rest;

import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.Complete;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/lra")
@ApplicationScoped
public class ParticipantResource {

    @GET
    @Path("/perform")
    @LRA(end = false)
    public Response performLRA(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) URI lraId,
                               @HeaderParam(LRA.LRA_HTTP_RECOVERY_HEADER) URI recoveryId) {
        System.out.println("PERFORMING LRA work " + lraId);
        System.out.println("PERFORM recovery id: " + recoveryId);

        return Response.ok(lraId.toString()).build();
    }

    @PUT
    @Path("/compensate")
    @Compensate
    public Response compensate(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) URI lraId,
                               @HeaderParam(LRA.LRA_HTTP_RECOVERY_HEADER) URI recoveryId) {
        System.out.println("COMPENSATING LRA " + lraId);
        System.out.println("COMPENSATE recovery id: " + recoveryId);

        return Response.ok().build();
    }

    @PUT
    @Path("/complete")
    @Complete
    public Response complete(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) URI lraId,
                             @HeaderParam(LRA.LRA_HTTP_RECOVERY_HEADER) URI recoveryId) {
        System.out.println("COMPLETING LRA " + lraId);
        System.out.println("COMPLETE recovery id: " + recoveryId);

        return Response.ok().build();
    }
}
