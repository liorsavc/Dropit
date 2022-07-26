package com.dropit.home_assignment.services;

import com.dropit.home_assignment.model.Delivery;
import com.dropit.home_assignment.model.Timeslot;
import com.dropit.home_assignment.requests.DeliveryRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;

@Component
@Slf4j
public class DeliveryService {
    ObjectMapper objectMapper;
    Gson gson;
    Collection<Delivery> deliveries;
    int deliveryIdCounter;

    GeneralService service = new GeneralService(new ObjectMapper(), new Gson());

    public DeliveryService(ObjectMapper objectMapper, Gson gson) {

        this.objectMapper = objectMapper;
        this.gson = gson;
        this.deliveries = new HashSet<>();
        this.deliveryIdCounter = 0;
    }

    public ResponseEntity<Delivery> bookDelivery(DeliveryRequest request) {
        String timeslotId = request.getTimeslotid();
        Timeslot timeslot = service.courierTimeSlots.stream().filter(ts -> ts.getId() == Integer.valueOf(timeslotId)).findFirst().get();
        if (timeslot.getPlacedOrdersNumber() >= 2) {
            return new ResponseEntity("selected time slot has more than 2 orders",HttpStatus.INTERNAL_SERVER_ERROR);

        }
        ResponseEntity<Delivery> responseEntity;
        try {
            deliveryIdCounter++;
            Delivery delivery = new Delivery(deliveryIdCounter, timeslotId, request.getUser());
            deliveries.add(delivery);
            service.incrementTimeslotPlacedOrders(timeslotId);
            responseEntity = new ResponseEntity<>(delivery, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    public ResponseEntity<Delivery> markDeliveryAsComplete(String id) {
        for (Delivery delivery: deliveries) {
            if (delivery.getId() == Integer.valueOf(id)) {
                delivery.setStatus(Delivery.Status.COMPLETE);
                return new ResponseEntity(delivery, HttpStatus.OK);
            }
        }
        return new ResponseEntity("id doesn't exist", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity markDeliveryAsCanceled(String id) {
        for (Delivery delivery: deliveries) {
            if (delivery.getId() == Integer.valueOf(id)) {
                delivery.setStatus(Delivery.Status.CANCELED);
                return new ResponseEntity(delivery, HttpStatus.OK);
            }
        }
        return new ResponseEntity("id doesn't exist", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
