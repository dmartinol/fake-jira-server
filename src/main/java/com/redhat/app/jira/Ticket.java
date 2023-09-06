package com.redhat.app.jira;

import java.util.UUID;

public class Ticket {
    public enum Status {
        Created,
        Approved
    }

    public String id;
    public String title;
    public Status status;
    public Long creationTimeMillis;
    public Long minutesAfterCreation;

    public Ticket() {
    }

    static Ticket newFromTitle(String title) {
        Ticket ticket = new Ticket();
        ticket.title = title;
        ticket.id = UUID.randomUUID().toString();
        ticket.status = Status.Created;
        ticket.creationTimeMillis = System.currentTimeMillis();
        ticket.minutesAfterCreation = 0L;
        return ticket;
    }

    Ticket update() {
        this.minutesAfterCreation = (System.currentTimeMillis() - creationTimeMillis) / (1000 * 60);
        return this;
    }
}
