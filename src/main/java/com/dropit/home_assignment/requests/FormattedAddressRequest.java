package com.dropit.home_assignment.requests;

import com.dropit.home_assignment.model.Address;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FormattedAddressRequest {
    Address address;
}
