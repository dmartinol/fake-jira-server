package com.redhat.app.jira;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.app.jira.Ticket.Status;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/tickets")
public class TicketResource {

    private Logger logger = LoggerFactory.getLogger(TicketResource.class);

    private Map<String, Ticket> tickets = Collections.synchronizedMap(new LinkedHashMap<>());

    public TicketResource() {
    }

    @GET
    public Response list() {
        logger.info("Reading list of tickets");
        return Response.ok(tickets.values().stream().map(t->t.update()).toArray()).build();
    }

    @GET
    @Path("{id}")
    public Response ticket(@PathParam("id") String id) {
        logger.info("Reading ticket {}", id);
        Ticket ticket = tickets.get(id);
        if (ticket == null) {
            return Response.status(jakarta.ws.rs.core.Response.Status.NOT_FOUND).build();
        }

        return Response.ok(ticket.update()).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Ticket add(String title) {
        logger.info("Adding ticket {}", title);

        Ticket ticket = Ticket.newFromTitle(title);
        this.tickets.put(ticket.id, ticket);
        return ticket;
    }

    @PUT
    @Path("{id}/approve")
    public Response approve(@PathParam("id") String id) {
        logger.info("Approving ticket {}", id);
        Ticket ticket = tickets.get(id);
        if (ticket == null) {
            return Response.status(jakarta.ws.rs.core.Response.Status.NOT_FOUND).build();
        }
        ticket.status = Status.Approved;
        return Response.ok(ticket).build();
    }

    @DELETE
    public Response delete(Ticket ticket) {
        logger.info("Deleting ticket {}", ticket.id);
        this.tickets.remove(ticket.id);
        return list();
    }
}