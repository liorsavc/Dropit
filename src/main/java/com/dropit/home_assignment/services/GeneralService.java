package com.dropit.home_assignment.services;

import com.dropit.home_assignment.model.Address;
import com.dropit.home_assignment.requests.FormattedAddressRequest;
import com.dropit.home_assignment.model.OneLineAddress;
import com.dropit.home_assignment.model.Timeslot;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Component
@Slf4j
public class GeneralService {
    ObjectMapper objectMapper;
    Gson gson;
    List<Timeslot> courierTimeSlots;




    public GeneralService(ObjectMapper objectMapper, Gson gson) {
        this.objectMapper = objectMapper;
        this.gson = gson;
        this.courierTimeSlots = new ArrayList<>();
        try {
            readCourierTimeSlots("src/main/resources/static/courierTimeSlots.json");
        } catch (IOException e) {
            log.warn("error while reading courier time slots");
        }
    }

    private void readCourierTimeSlots(String path) throws IOException {
        Map itemsInFile = objectMapper.readValue(new File(path), LinkedHashMap.class);
        for (Object key: itemsInFile.keySet()) {
            String itemKey = (String)key;
            String itemJson = gson.toJson(itemsInFile.get(itemKey), LinkedHashMap.class);
            Timeslot timeslot = objectMapper.readValue(itemJson, Timeslot.class);
            timeslot.setId(Integer.valueOf(itemKey));
            courierTimeSlots.add(timeslot);
        }
    }


    public List<Timeslot> retrieveAvailableTimeslots(FormattedAddressRequest formattedAddress) {
        List<Timeslot> availableTimeslots = new ArrayList<>();
        Address addressToLookFor = formattedAddress.getAddress();
        for (Timeslot timeslot: courierTimeSlots) {
            for (OneLineAddress onelineAddress : timeslot.getSupportedAddresses()) {
                Address address = resolveAddress(onelineAddress).getBody();
                if (address.equals(addressToLookFor) && isLessThanTwoOrders(timeslot)) {
                    availableTimeslots.add(timeslot);
                }
            }
        }
        return availableTimeslots;
    }

    private boolean isLessThanTwoOrders(Timeslot timeslot) {
        return 2 < timeslot.getPlacedOrdersNumber();
    }

    public void incrementTimeslotPlacedOrders(String timeslotId){
        Timeslot timeslot = courierTimeSlots.stream().filter(ts -> ts.getId() == Integer.valueOf(timeslotId)).findFirst().get();
        int placedOrders = timeslot.getPlacedOrdersNumber();
        timeslot.setPlacedOrdersNumber(++placedOrders);
    }

    public ResponseEntity<Address> resolveAddress(OneLineAddress address) {
        try {
            //build request:
            StringBuilder stringBuilder = new StringBuilder("https://api.geoapify.com/v1/geocode/search?text=");
            stringBuilder.append(address.getSearchTerm());
            stringBuilder.append("&apiKey=4b24ee59b2844a6487aa37a8eb7e8dbf");
            URL url = new URL(stringBuilder.toString());
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestProperty("Accept", "application/json");

            //parse response:
            BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            http.disconnect();
            Address addressToReturn = buildAddressFromResponse(content);
            return new ResponseEntity<>(addressToReturn, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    private Address buildAddressFromResponse(StringBuffer content) throws JsonProcessingException {
        Map addressMap = objectMapper.readValue(content.toString(),new TypeReference<Map<String,Object>>(){});
        Map addressFeatures = (Map)((Map)addressMap.get("query")).get("parsed");
        String addressJson = gson.toJson(addressFeatures, LinkedHashMap.class);
        Address address = objectMapper.readValue(addressJson, Address.class);
        return address;
    }


}
