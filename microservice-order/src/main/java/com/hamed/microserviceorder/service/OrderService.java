package com.hamed.microserviceorder.service;

import com.hamed.microserviceorder.config.WebClientConfig;
import com.hamed.microserviceorder.dto.InventoryResponse;
import com.hamed.microserviceorder.dto.OrderLineItemsDto;
import com.hamed.microserviceorder.dto.OrderRequest;
import com.hamed.microserviceorder.model.Order;
import com.hamed.microserviceorder.model.OrderLineItems;
import com.hamed.microserviceorder.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final WebClient.Builder webClientBuilder;
    private final OrderRepository orderRepository;
    public void  placeOrder(OrderRequest orderRequest){
        Order order= new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::eMapToDto)
                .toList();
        order.setOrderLineItemList(orderLineItems);

        List<String> skuCodes = orderLineItems.stream().map(OrderLineItems::getSkuCode).toList();

        InventoryResponse [] inventoryResponseArray = webClientBuilder.build().get()
                .uri("http://inventory/api/inventory",uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse [].class)
                .block();
        boolean result = Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse::getIsInStock);

        if(result){
            orderRepository.save(order);
        }
        else {
            throw new IllegalArgumentException("Product is not in stock,Please Try again letter!");
        }
    }

    private OrderLineItems eMapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems =new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
