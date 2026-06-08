package com.kafka.mart.auth.mapper;

import com.kafka.mart.auth.entity.User;
import com.kafka.mart.auth.kafka.event.UserRegisteredEvent;
import com.kafka.mart.auth.kafka.event.UserVerifiedEvent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {

    UserRegisteredEvent
    toUserRegisteredEvent(
            User user
    );

    UserVerifiedEvent
    toUserVerifiedEvent(
            User user
    );
}