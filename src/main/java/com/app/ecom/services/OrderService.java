package com.app.ecom.services;

import com.app.ecom.ModelMapperConfig;
import com.app.ecom.dto.OrderItemDTO;
import com.app.ecom.dto.OrderResponse;
import com.app.ecom.dto.UserDTO;
import com.app.ecom.models.*;
import com.app.ecom.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartService cartService;
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final ModelMapperConfig modelMapperConfig;
    @Transactional
    public Optional<OrderResponse> createOrder(String userId) {
        //validate for CartItems
        List<CartItem> cartItems=cartService.getCart(userId);
        if(cartItems.isEmpty()){
            return Optional.empty();
        }
        //validate for User
        Optional<User> userOpt =userService.findById(Long.valueOf(userId));
        if(userOpt.isEmpty()){
            return Optional.empty();
        }
        User user=userOpt.get();
        //calculate total cost
        BigDecimal totalPrice =cartItems.stream().map(CartItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        //create Order
        Order order=new Order();
        order.setUser(user);
        order.setOrderStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);
        List<OrderItem>  orderItems=cartItems.stream().map(item -> new OrderItem(null,order,item.getProduct(),item.getPrice(),item.getQuantity())).toList();
        order.setItems(orderItems);
        Order saveOrder=orderRepository.save(order);
        //clear the Cart
        cartService.clearCart(userId);
        return  Optional.of(mapToOrderResponse(saveOrder));

    }

    private OrderResponse mapToOrderResponse(Order saveOrder) {
        return new  OrderResponse(saveOrder.getId(),
                saveOrder.getTotalAmount(),
                saveOrder.getOrderStatus(),
                saveOrder.getItems().stream().map(orderItem -> new OrderItemDTO(
                        orderItem.getId(),
                        orderItem.getProduct().getId(),
                        orderItem.getQuantity(),
                        orderItem.getPrice()
                )).toList(),
                saveOrder.getCreationDate()
        );
    }
}
