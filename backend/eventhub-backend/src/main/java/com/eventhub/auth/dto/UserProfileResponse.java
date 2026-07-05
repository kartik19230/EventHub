package com.eventhub.auth.dto;

import com.eventhub.user.enums.Role;

import lombok.Getter;
import lombok.Setter;

public record UserProfileResponse(Integer id,String email,Role role) {

}
