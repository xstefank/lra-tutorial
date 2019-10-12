package io.xstefank.example.lra.lraservice.rest;

import org.eclipse.microprofile.lra.annotation.AfterLRA;
import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.Complete;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/lra")
@ApplicationScoped
public class LraResource {

    @GET
    @Path("/perform")
    @LRA(end = false)
    public Response performLRA(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) URI lraId,
                               @HeaderParam(LRA.LRA_HTTP_RECOVERY_HEADER) URI recoveryId) {
        System.out.println("PERFORMING LRA work " + lraId);
        System.out.println("PERFORM recovery id: " + recoveryId);

        // call lra-service-2
        ClientBuilder.newClient().target("http://localhost:8082/lra/perform")
            .request().get();

        return Response.ok(lraId.toString()).build();
    }

    @GET
    @Path("/end")
    @LRA(value = LRA.Type.MANDATORY)
    public Response endLRA(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) URI lraId) {
        System.out.println("ENDING LRA " + lraId);

        return Response.ok().build();
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

    @PUT
    @Path("/after")
    @AfterLRA
    public Response after(@HeaderParam(LRA.LRA_HTTP_ENDED_CONTEXT_HEADER) URI endedLRA) {
        System.out.println("AFTER_LRA ended LRA: " + endedLRA);

        return Response.ok().build();
    }
}
