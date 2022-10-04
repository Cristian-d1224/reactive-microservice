package com.cristian.userService.util;

import com.cristian.userService.dto.TransactionRequestDto;
import com.cristian.userService.dto.TransactionResponseDto;
import com.cristian.userService.dto.TransactionStatus;
import com.cristian.userService.dto.UserDto;
import com.cristian.userService.entity.User;
import com.cristian.userService.entity.UserTransaction;
import org.springframework.beans.BeanUtils;

public class EntityDtoUtil {

    public static UserDto toDto(User user){
        UserDto dto = new UserDto();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }

    public static User toEntity(UserDto dto){
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        return user;
    }

    public static UserTransaction toEntity(TransactionRequestDto requestDto){
        UserTransaction ut = new UserTransaction();
        BeanUtils.copyProperties(requestDto, ut);
        return ut;
    }

    public static TransactionResponseDto toDto(TransactionRequestDto requestDto, TransactionStatus transactionStatus){
        TransactionResponseDto responseDto = new TransactionResponseDto();
        BeanUtils.copyProperties(requestDto, responseDto);
        return responseDto;
    }

}
