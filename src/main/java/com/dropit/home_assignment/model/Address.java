package com.dropit.home_assignment.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Objects;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    private String street;
    private String housenumber;
    private String district;
    private String country;
    private String postcode;
    private String state;
    private String city;
    private String suburb;
    private String expected_type;
    private String county;
    private String country_code;
    private int lon;
    private int lat;
    private String formatted;
    private String address_line1;
    private String address_line2;
    private String state_code;
    private String result_type;
    private String place_id;
    private Rank rank;



    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    private class Rank {
        private int importance;
        private int popularity;
        private int confidence;
        private int confidence_city_level;
        private int confidence_street_level;
        private String match_type;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return lon == address.lon && lat == address.lat && Objects.equals(street, address.street) && Objects.equals(housenumber, address.housenumber) && Objects.equals(district, address.district) && Objects.equals(country, address.country) && Objects.equals(postcode, address.postcode) && Objects.equals(state, address.state) && Objects.equals(city, address.city) && Objects.equals(suburb, address.suburb) && Objects.equals(expected_type, address.expected_type) && Objects.equals(county, address.county) && Objects.equals(country_code, address.country_code) && Objects.equals(formatted, address.formatted) && Objects.equals(address_line1, address.address_line1) && Objects.equals(address_line2, address.address_line2) && Objects.equals(state_code, address.state_code) && Objects.equals(result_type, address.result_type) && Objects.equals(place_id, address.place_id) && Objects.equals(rank, address.rank);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, housenumber, district, country, postcode, state, city, suburb, expected_type, county, country_code, lon, lat, formatted, address_line1, address_line2, state_code, result_type, place_id, rank);
    }
}

