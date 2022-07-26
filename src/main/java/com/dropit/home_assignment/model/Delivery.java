package com.dropit.home_assignment.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
public class Delivery {

    private Status status;
    private String timeslotId;

    private String user;
    private int id;

    public Delivery(int id, String timeslotId, String user) {
        this.user = user;
        this.timeslotId = timeslotId;
        status = Status.PENDING;
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Delivery delivery = (Delivery) o;
        return Objects.equals(timeslotId, delivery.timeslotId) && Objects.equals(user, delivery.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeslotId, user);
    }

    public enum Status {
        PENDING,
        COMPLETE,
        IN_PROGRESS,
        CANCELED,
    }
}

