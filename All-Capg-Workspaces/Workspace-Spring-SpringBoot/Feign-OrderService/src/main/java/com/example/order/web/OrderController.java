package com.example.order.web;



import com.example.order.client.ProductClient;
import com.example.order.model.Product;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final ProductClient productClient;

    public OrderController(ProductClient productClient) {
        this.productClient = productClient;
    }
   //http://localhost:8092/orders/101
    
    /*  Oput put
     
      {
"orderId": "ORD-101",
       "product": {
        "id": "101",
       "name": "Laptop-101",
       "price": 65000
    },
      "status": "PLACED"
}
     */
    
    
    @GetMapping("/{productId}")
    public OrderResponse placeOrder(@PathVariable String productId) {
        Product product = productClient.getById(productId);
        return new OrderResponse("ORD-" + productId, product, "PLACED");
    }

    public static class OrderResponse {
        private String orderId;
        private Product product;
        private String status;

        public OrderResponse() { }

        public OrderResponse(String orderId, Product product, String status) {
            this.orderId = orderId; this.product = product; this.status = status;
        }

        public String getOrderId() { return orderId; }
        public void setOrderId(String orderId) { this.orderId = orderId; }

        public Product getProduct() { return product; }
        public void setProduct(Product product) { this.product = product; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }
}
