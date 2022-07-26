package com.dropit.home_assignment.controllers;

import com.dropit.home_assignment.model.Address;
import com.dropit.home_assignment.requests.FormattedAddressRequest;
import com.dropit.home_assignment.model.OneLineAddress;
import com.dropit.home_assignment.model.Timeslot;
import com.dropit.home_assignment.services.GeneralService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
public class GeneralController {

    GeneralService service = new GeneralService(new ObjectMapper(), new Gson());


    @PostMapping(value = "/resolve-address", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Address> resolveAddress(@RequestBody OneLineAddress request) {
         return service.resolveAddress(request);
    }


    @PostMapping(value = "/timeslots")
    public List<Timeslot> retrieveAvailableTimeslots(@RequestBody FormattedAddressRequest request) {
        return service.retrieveAvailableTimeslots(request);
    }



}
