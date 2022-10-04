package com.cristian.orderService.service;


import com.cristian.orderService.client.ProductClient;
import com.cristian.orderService.client.UserClient;
import com.cristian.orderService.dto.PurchaseOrderRequestDto;
import com.cristian.orderService.dto.PurchaseOrderResponseDto;
import com.cristian.orderService.dto.RequestContext;
import com.cristian.orderService.repository.PurchaseOrderRepository;
import com.cristian.orderService.util.EntityDtoUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Service
public class OrderFulfillmentService {

    private final ProductClient productClient;
    private final UserClient userClient;
    private final PurchaseOrderRepository orderRepository;

    public OrderFulfillmentService(
            ProductClient productClient,
            UserClient userClient,
            PurchaseOrderRepository orderRepository
    ){
        this.productClient = productClient;
        this.userClient = userClient;
        this.orderRepository = orderRepository;
    }


    public Mono<PurchaseOrderResponseDto> processOrder(Mono<PurchaseOrderRequestDto> requestDtoMono){
        return requestDtoMono
                .map(RequestContext::new)
                .flatMap(this::productRequestResponse)
                .doOnNext(EntityDtoUtil::setTransactionRequestDto)
                .flatMap(this::userRequestResponse)
                .map(EntityDtoUtil::getPurchaseOrder)
                .map(this.orderRepository::save) // this block the thread
                .map(EntityDtoUtil::getPurchaseOrderResponseDto)
                .subscribeOn(Schedulers.boundedElastic());
    }

    private Mono<RequestContext> productRequestResponse(RequestContext rc){
        return this.productClient.getProductById(rc.getPurchaseOrderRequestDto().getProductId())
                .doOnNext(rc::setProductDto)
                .thenReturn(rc);
    }

    private Mono<RequestContext> userRequestResponse(RequestContext rc){
        return this.userClient.authorizeTransaction(rc.getTransactionRequestDto())
                .doOnNext(rc::setTransactionResponseDto)
                .thenReturn(rc);
    }


}
