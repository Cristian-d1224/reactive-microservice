package com.cristian.productService.service;

import com.cristian.productService.dto.ProductDto;
import com.cristian.productService.repository.ProductRepository;
import com.cristian.productService.util.EntityDtoUtil;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository){
        this.repository = repository;
    }

    public Flux<ProductDto> getAll(){
        return this.repository
                .findAll()
                .map(EntityDtoUtil::toDto);
    }

    public Flux<ProductDto> getProductByPriceRange(int min, int max){
        return this.repository.findByPriceBetween(Range.closed(min, max))
                .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> getProductById(String id){
        return this.repository
                .findById(id)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> insertProduct(Mono<ProductDto> productDtoMono){
        return productDtoMono
                .map(EntityDtoUtil::toEntity)
                .flatMap(this.repository::insert)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> updateProduct(String id, Mono<ProductDto> productDtoMono){
        return this.repository.findById(id)
                .flatMap(p -> productDtoMono
                        .map(EntityDtoUtil::toEntity)
                        .doOnNext(e ->e.setId(id)))
                .flatMap(this.repository::save)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<Void> deleteProduct(String id){
        return this.repository.deleteById(id);
    }

}
