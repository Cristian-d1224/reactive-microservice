package com.cristian.orderService.service;

import com.cristian.orderService.dto.PurchaseOrderResponseDto;
import com.cristian.orderService.entity.PurchaseOrder;
import com.cristian.orderService.repository.PurchaseOrderRepository;
import com.cristian.orderService.util.EntityDtoUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
public class OrderQueryService {

    public final PurchaseOrderRepository orderRepository;

    public OrderQueryService(PurchaseOrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }


    public Flux<PurchaseOrderResponseDto> getProductsByUserId(int userId){
        return Flux.fromStream(() -> this.orderRepository.findByUserId(userId).stream()) // block
                .map(EntityDtoUtil::getPurchaseOrderResponseDto)
                .subscribeOn(Schedulers.boundedElastic());
    }

}
