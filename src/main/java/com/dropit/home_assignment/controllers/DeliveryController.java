package com.dropit.home_assignment.controllers;

import com.dropit.home_assignment.model.Delivery;
import com.dropit.home_assignment.requests.DeliveryRequest;
import com.dropit.home_assignment.services.DeliveryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

public class DeliveryController {

    DeliveryService deliveryService = new DeliveryService(new ObjectMapper(), new Gson());


    @PostMapping(value = "/deliveries")
    public ResponseEntity<Delivery> bookDelivery(@RequestBody DeliveryRequest request) {
        return deliveryService.bookDelivery(request);
    }

    @PostMapping(value = "/deliveries/{deliveryId}/complete")
    public ResponseEntity markDeliveryAsComplete(@PathVariable("deliveryId") String deliveryId) {
        return deliveryService.markDeliveryAsComplete(deliveryId);
    }

    @DeleteMapping(value = "/deliveries/{deliveryId}")
    public ResponseEntity markDeliveryAsCanceled(@PathVariable("deliveryId") String deliveryId) {
        return deliveryService.markDeliveryAsCanceled(deliveryId);
    }
}
