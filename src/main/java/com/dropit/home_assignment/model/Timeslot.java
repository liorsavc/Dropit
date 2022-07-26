package com.dropit.home_assignment.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
public class Timeslot {
    private int id;
    private long startTime;
    private long endTime;
    private Set<OneLineAddress> supportedAddresses;
    private int placedOrdersNumber = 0;

//    public Timeslot(long startTime, long endTime, List<Address> addresses) {
//        this.startTime = startTime;
//        this.endTime = endTime;
//        this.supportedAddresses = new HashSet<>();
//        this.supportedAddresses = filterSupportedAddresses(addresses);
//    }

    private HashSet<OneLineAddress> filterSupportedAddresses(List<Address> addresses) {
        //support only US country
        //not implemented yet
        return null;
    }

}
