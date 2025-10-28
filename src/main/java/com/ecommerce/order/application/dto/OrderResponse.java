package com.ecommerce.order.application.dto;

import com.ecommerce.order.domain.Order;
import com.ecommerce.order.domain.OrderItem;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class OrderResponse {
    private String id;
    private String customerId;
    private String status;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private List<OrderItemResponse> items;

    public static class OrderItemResponse {
        private String productId;
        private String productName;
        private BigDecimal unitPrice;
        private int quantity;
        private BigDecimal totalPrice;

        public static OrderItemResponse from(OrderItem item) {
            OrderItemResponse response = new OrderItemResponse();
            response.productId = item.getProductId().getValue();
            response.productName = item.getProductName();
            response.unitPrice = item.getUnitPrice().getAmount();
            response.quantity = item.getQuantity();
            response.totalPrice = item.getTotalPrice().getAmount();
            return response;
        }

        // Getters
        public String getProductId() { return productId; }
        public String getProductName() { return productName; }
        public BigDecimal getUnitPrice() { return unitPrice; }
        public int getQuantity() { return quantity; }
        public BigDecimal getTotalPrice() { return totalPrice; }
    }

    public static OrderResponse from(Order order) {
        OrderResponse response = new OrderResponse();
        response.id = order.getId().getValue();
        response.customerId = order.getCustomerId().getValue();
        response.status = order.getStatus().name();
        response.orderDate = order.getOrderDate();
        response.totalAmount = order.getTotalAmount().getAmount();
        response.items = order.getItems().stream()
                              .map(OrderItemResponse::from)
                              .collect(Collectors.toList());
        return response;
    }

    // Getters
    public String getId() { return id; }
    public String getCustomerId() { return customerId; }
    public String getStatus() { return status; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public List<OrderItemResponse> getItems() { return items; }
}