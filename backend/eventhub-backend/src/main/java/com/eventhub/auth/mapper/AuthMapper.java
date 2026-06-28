package com.eventhub.auth.mapper;

import org.mapstruct.Mapper;

import com.eventhub.auth.dto.RegisterDTO;
import com.eventhub.user.entity.User;

@Mapper(componentModel = "spring")
public interface AuthMapper {

	User toUser(RegisterDTO dto);
}
