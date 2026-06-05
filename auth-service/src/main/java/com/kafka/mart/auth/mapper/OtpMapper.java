package com.kafka.mart.auth.mapper;

import com.kafka.mart.auth.entity.OtpVerification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OtpMapper {

    OtpVerification toEntity(OtpVerification otpVerification);
}
