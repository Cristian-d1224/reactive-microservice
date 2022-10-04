package com.cristian.userService.service;

import com.cristian.userService.dto.TransactionRequestDto;
import com.cristian.userService.dto.TransactionResponseDto;
import com.cristian.userService.dto.TransactionStatus;
import com.cristian.userService.repository.UserRepository;
import com.cristian.userService.repository.UserTransactionRepository;
import com.cristian.userService.util.EntityDtoUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class TransactionService {

    private final UserRepository userRepository;
    private final UserTransactionRepository transactionRepository;

    public TransactionService(UserRepository userRepository,
                              UserTransactionRepository transactionRepository){
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    public Mono<TransactionResponseDto> createTransaction(final TransactionRequestDto requestDto){
        return this.userRepository.updateUserBalance(requestDto.getUserId(), requestDto.getAmount())
                .filter(Boolean::booleanValue)
                .map(b -> EntityDtoUtil.toEntity(requestDto))
                .flatMap(this.transactionRepository::save)
                .map(ut -> EntityDtoUtil.toDto(requestDto, TransactionStatus.APPROVED))
                .defaultIfEmpty(EntityDtoUtil.toDto(requestDto, TransactionStatus.DECLINED));
    }

}
