package com.main.ecommerce.controller;

import org.springframework.web.bind.annotation.RestController;

import com.main.ecommerce.ResponseEntity.AuthResponse;
import com.main.ecommerce.ResponseEntity.DataResponse;
import com.main.ecommerce.entities.OrderItem;
import com.main.ecommerce.entities.OrderPay;
import com.main.ecommerce.entities.OrderStack;
import com.main.ecommerce.entities.Product;
import com.main.ecommerce.entities.User;
import com.main.ecommerce.entities.UserOrder;
import com.main.ecommerce.payment.PaymentMethod;
import com.main.ecommerce.services.impl.OrderItemServiceImpl;
import com.main.ecommerce.services.impl.OrderPayServiceImpl;
import com.main.ecommerce.services.impl.OrderStackServiceImpl;
import com.main.ecommerce.services.impl.ProductServiceImpl;
import com.main.ecommerce.services.impl.UserOrderServiceImpl;
import com.main.ecommerce.services.impl.UserServiceImpl;
import com.main.ecommerce.status.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = { "http://localhost:5173/", "http://localhost:5174/" })
public class UserController {

    @Autowired
    private ProductServiceImpl productServiceImpl;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private OrderItemServiceImpl orderItemServiceImpl;

    @Autowired
    private OrderStackServiceImpl orderStackServiceImpl;

    @Autowired
    private UserOrderServiceImpl userOrderServiceImpl;

    @Autowired
    private OrderPayServiceImpl orderPayServiceImpl;

    @PostMapping("/updateUser")
    public ResponseEntity<DataResponse> updateUser(@RequestHeader("Authorization") String jwt,
            @RequestBody User updatedUser) {

        DataResponse response = new DataResponse();

        try {
            // Retrieve the existing user using JWT
            User existingUser = this.userServiceImpl.getUserByJwt(jwt);
            if (existingUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            // Update basic user fields
            existingUser.setName(updatedUser.getName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setMobileNo(updatedUser.getMobileNo());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setUpdateDate(LocalDateTime.now());

            // Save updated user and new address
            User savedUser = this.userServiceImpl.updateUser(existingUser);
            response.setStatus(HttpStatus.OK);
            response.setMessage("User Updated Successfully");
            response.setData(savedUser);

            return ResponseEntity.of(Optional.of(response));

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("User Updated failed");

            return ResponseEntity.of(Optional.of(response));
        }
    }

    @GetMapping("/getUser")
    public ResponseEntity<DataResponse> getUserById(@RequestHeader("Authorization") String jwt) {

        DataResponse response = new DataResponse();

        User user = this.userServiceImpl.getUserByJwt(jwt);

        try {
            response.setStatus(HttpStatus.OK);
            response.setMessage("User Get succesfully ! ");
            response.setData(user);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("User Get failed ! ");
            response.setData(user);

            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/addCart/{id}/{quantity}")
    public ResponseEntity<DataResponse> addCartItem(@RequestHeader("Authorization") String jwt,
            @PathVariable("id") long id, @PathVariable("quantity") int quantity) {
        User user = userServiceImpl.getUserByJwt(jwt);
        Product product = productServiceImpl.getProductbyId(id);
        DataResponse response = new DataResponse();
        OrderStack stack = new OrderStack();
        OrderItem item = new OrderItem();
        try {
            item = orderItemServiceImpl.addOrderiItem(product, quantity);
            stack = orderStackServiceImpl.addOrderItem(user, item);
            response.setData(stack);
            response.setStatus(HttpStatus.OK);
            response.setMessage("success");

            return ResponseEntity.of(Optional.of(response));
        } catch (Exception e) {
            response.setData(null);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("failed");
            return ResponseEntity.of(Optional.of(response));
        }
    }

    @GetMapping("/getCart")
    public ResponseEntity<DataResponse> getStack(@RequestHeader("Authorization") String jwt) {
        User user = userServiceImpl.getUserByJwt(jwt);
        DataResponse response = new DataResponse();
        try {
            OrderStack orderStack = orderStackServiceImpl.getOrderStackByUserId(user.getId());
            response.setData(orderStack);
            response.setStatus(HttpStatus.OK);
            response.setMessage("success");
            return ResponseEntity.of(Optional.of(response));
        } catch (Exception e) {
            OrderStack orderStack = new OrderStack();
            orderStack.setUser(user);
            response.setData(orderStack);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("failed");
            return ResponseEntity.of(Optional.of(response));
        }
    }

    @PostMapping("/removeCart/{stackId}/{itemId}")
    public ResponseEntity<DataResponse> removeCartItem(@RequestHeader("Authorization") String jwt,
            @PathVariable("stackId") long stackId, @PathVariable("itemId") long itemId) {
        OrderStack orderStack = new OrderStack();
        DataResponse response = new DataResponse();
        try {
            orderStack = this.orderStackServiceImpl.removeOrderItemFromCart(itemId, stackId);
            response.setData(orderStack);
            response.setStatus(HttpStatus.OK);
            response.setMessage("Success");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            response.setData(null);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("failed");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/myCart/placeOrder")
    public ResponseEntity<DataResponse> placeOrder(@RequestHeader("Authorization") String jwt,
            @RequestBody UserOrder userOrder) {

        User user = this.userServiceImpl.getUserByJwt(jwt);
        DataResponse response = new DataResponse();

        try {
            OrderStack orderStack = this.orderStackServiceImpl.getOrderStackByUserId(user.getId());

            // userOrder.setPaymentMethod(PaymentMethod.PAYPAL);
            userOrder.setTotalPaid(orderStack.getTotalPrice());
            userOrder.setTransitionId(UUID.randomUUID().toString());

            UserOrder createdOrder = this.userOrderServiceImpl.creatOrder(userOrder, orderStack.getStackId());

            response.setStatus(HttpStatus.CREATED);
            response.setMessage("Order Placed Success");
            response.setData(createdOrder);

            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Order Placed failed or may be your cart not exists !");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/payment/{orderId}")
    public ResponseEntity<DataResponse> proceedToPay(@RequestHeader("Authorization") String jwt,
            @PathVariable long orderId) {

        User user = this.userServiceImpl.getUserByJwt(jwt);
        DataResponse response = new DataResponse();

        try {

            UserOrder userOrder = this.userOrderServiceImpl.getUserOrderById(orderId);

            if (userOrder.getOrderStatus().equals(OrderStatus.COMPLETED)) {

                response.setStatus(HttpStatus.BAD_REQUEST);
                response.setMessage("Payment already done for this order !!");

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

            }

            OrderPay orderPay = this.orderPayServiceImpl.proceedToPay(userOrder);

            response.setStatus(HttpStatus.CREATED);
            response.setMessage("Payment Completed Successfull");
            response.setData(orderPay);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Payment Failed");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    @GetMapping("/orders")
    public ResponseEntity<DataResponse> getUserOrders(@RequestHeader("Authorization") String jwt) {

        User user = this.userServiceImpl.getUserByJwt(jwt);
        DataResponse response = new DataResponse();

        try {
            List<UserOrder> userOrders = this.userOrderServiceImpl.getUserOrderByUserid(user.getId());

            response.setStatus(HttpStatus.OK);
            response.setMessage("success");
            response.setData(userOrders);

            return ResponseEntity.ok().body(response);

        } catch (Exception e) {

            response.setStatus(HttpStatus.OK);
            response.setMessage("success");
            UserOrder userOrder = new UserOrder();
            userOrder.setUser(user);
            response.setData(userOrder);

            return ResponseEntity.ok().body(response);
        }
    }

    @GetMapping("/orderDetail/{orderId}")
    public ResponseEntity<DataResponse> getOrderById(@RequestHeader("Authorization") String jwt,
            @PathVariable long orderId) {

        User user = this.userServiceImpl.getUserByJwt(jwt);
        DataResponse response = new DataResponse();

        try {

            UserOrder userOrder = this.userOrderServiceImpl.getUserOrderById(orderId);
            response.setStatus(HttpStatus.OK);
            response.setMessage("success");
            response.setData(userOrder);

            return ResponseEntity.ok().body(response);

        } catch (Exception e) {

            response.setStatus(HttpStatus.NOT_FOUND);

            response.setMessage("Not found");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/orderStack/{orderId}")
    public ResponseEntity<DataResponse> getOrderStackByOrderId(@RequestHeader("Authorization") String jwt,
            @PathVariable long orderId) {

        User user = this.userServiceImpl.getUserByJwt(jwt);
        DataResponse response = new DataResponse();

        try {

            UserOrder userOrder = this.userOrderServiceImpl.getUserOrderById(orderId);
            OrderStack orderStack = this.orderStackServiceImpl.getOrderStackByUserOrder(userOrder);
            response.setStatus(HttpStatus.OK);
            response.setMessage("success");
            response.setData(orderStack);

            return ResponseEntity.ok().body(response);

        } catch (Exception e) {

            response.setStatus(HttpStatus.NOT_FOUND);

            response.setMessage("Not found");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
