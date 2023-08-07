package siit.web;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import siit.model.Order;
import siit.model.OrderProduct;
import siit.model.Product;
import siit.sevices.OrderService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/customers/{customerId}/orders/{orderId}")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping
    public Order getOrderBy(@PathVariable int customerId, @PathVariable int orderId) {
        return orderService.getOrderBy(customerId, orderId);
    }

    @GetMapping("/products")
    public List<OrderProduct> getOrderProducts(@PathVariable int customerId, @PathVariable int orderId) {
        return orderService.getOrderProductBy(customerId, orderId);
    }

    @PostMapping("/products")
    public OrderProduct addProduct(@RequestBody OrderProduct orderProduct, @PathVariable int customerId, @PathVariable int orderId) {
//        OrderProduct mockedOrderProduct1 = new OrderProduct();
//        mockedOrderProduct1.setId(100);
//        mockedOrderProduct1.setName("Mocked");
//        mockedOrderProduct1.setQuantity(BigDecimal.ONE);
//        mockedOrderProduct1.setPrice(BigDecimal.ONE);
//        Product product = new Product();
//        product.setId(1000);
//        product.setPrice(BigDecimal.TEN);
//        product.setName("MockedProductName");
//        product.setWeight(BigDecimal.TEN);
//
//        mockedOrderProduct1.setProduct(product);
        return orderService.getOrderProduct(orderProduct, customerId, orderId);
    }

    @DeleteMapping("/products/{orderProductId}")
    public void delete(@PathVariable int orderProductId){
       orderService.delete(orderProductId);
    }

}
