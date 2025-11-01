package com.app.ecom.services;

import com.app.ecom.dto.CartItemRequest;
import com.app.ecom.models.CartItem;
import com.app.ecom.models.Product;
import com.app.ecom.models.User;
import com.app.ecom.repository.CartItemRepository;
import com.app.ecom.repository.ProductRepository;
import com.app.ecom.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CartService {

   private final CartItemRepository cartItemRepository;
   private final ProductRepository productRepository;
   private final UserRepository userRepository;

   public boolean addToCart(String userId, CartItemRequest request){
     Optional<Product> optionalProduct   =productRepository.findById(request.getProductId());
     if(optionalProduct.isEmpty()){return false;}
     Product product =optionalProduct.get();
     if(product.getStockQuantity()<request.getQuantity()){return false;}
      Optional<User> userOpt=userRepository.findById(Long.valueOf(userId));
     if(userOpt.isEmpty()){return false;}
     User user=userOpt.get();
     CartItem existingCartItem =cartItemRepository.findByUserAndProduct(user,product);
     if(existingCartItem!=null){
         existingCartItem.setQuantity(existingCartItem.getQuantity()+request.getQuantity());
         existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
         cartItemRepository.save(existingCartItem);
         product.setStockQuantity(product.getStockQuantity()-request.getQuantity());
         productRepository.save(product);
     }
     else {
         CartItem cartItem=new CartItem();
         cartItem.setUser(user);
         cartItem.setProduct(product);
         cartItem.setQuantity(request.getQuantity());
         cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
         cartItemRepository.save(cartItem);
         }
     return true;
   }
    @Transactional
    public boolean deleteItemFromCart(String userId, Long productId) {
        Optional<Product> optionalProduct   =productRepository.findById(productId);
        if(optionalProduct.isEmpty()){return false;}

        Optional<User> userOpt=userRepository.findById(Long.valueOf(userId));
        if(userOpt.isEmpty()){return false;}

        userOpt.flatMap(user ->
            optionalProduct.map(product -> {
                cartItemRepository.deleteByUserAndProduct(user,product);
                return true;
            })
        );
        return false;
    }
}
