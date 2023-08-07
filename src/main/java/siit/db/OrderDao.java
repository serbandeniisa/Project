package siit.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import siit.model.Order;
import siit.model.OrderProduct;
import siit.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Repository
public class OrderDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Order> getOrdersByCustomerId(int customerId){
//        Order order = new Order();
//        order.setId(1);
//        order.setNumber("TestNumer");
//        order.setValue(200);
//        order.setPlaced(LocalDateTime.now());
        return jdbcTemplate.query("select * from orders where customer_id = ? ", this::getOrder, customerId);
    }

    public Order getOrder (ResultSet rs, int rowNum) throws SQLException {
        Order order = new Order();
        order.setId(rs.getInt("id"));
        order.setNumber(rs.getString("number"));
        order.setPlaced(rs.getTimestamp("placed").toLocalDateTime());
        return order;
    }

    public void deleteOrder (int orderID){
        String deleteQuery = "delete from orders where id = ?";
        jdbcTemplate.update(deleteQuery,orderID);
    }

    public void deleteOrderProduct (int orderID){
        String deleteQuery = "delete from orders_products where order_id = ?";
        jdbcTemplate.update(deleteQuery,orderID);
    }


    public List<OrderProduct> getOrderProductBy(int orderId){
        return jdbcTemplate.query("SELECT  op.order_id, op.id,  op.quantity, p.name, op.quantity * p.price as value, p.id as product_id, p.weight, p.price, p.url\n" +
                "FROM ORDERS_PRODUCTS op\n" +
                "join products p on p.id = op.product_id\n" +
                "where op.order_id = ?\n", this::getOrderProduct, orderId);
    }

    public OrderProduct getOrderProduct (ResultSet rs, int rowNum) throws SQLException {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setOrderId(rs.getInt("order_id"));
        orderProduct.setId(rs.getInt("id"));
        orderProduct.setName(rs.getString("name"));
        orderProduct.setQuantity(rs.getBigDecimal("quantity"));
        orderProduct.setValue(rs.getBigDecimal("value"));
        Product product = new Product();

        product.setId(rs.getInt("product_id"));
        product.setPrice(rs.getBigDecimal("price"));
        product.setName(rs.getString("name"));
        product.setWeight(rs.getBigDecimal("weight"));
        product.setUrl(rs.getString("url"));

        orderProduct.setProduct(product);

        return orderProduct;
    }

    public void updateOrder(int orderId, OrderProduct orderProduct){
        String query = "Update ORDERS_PRODUCTS set quantity = ? where order_id=? and product_id=?";
        jdbcTemplate.update(query,orderProduct.getQuantity(),orderId,orderProduct.getProduct().getId());
    }

    public void insertOrder(int orderId, OrderProduct orderProduct){
        String query = "Insert into ORDERS_PRODUCTS (order_id, product_id, quantity) values (?,?,?)";
        jdbcTemplate.update(query,orderId,orderProduct.getProduct().getId(),orderProduct.getQuantity());
    }

    public void deleteProduct(int orderProductId){
        String query = "delete from Orders_products where id=?";
        jdbcTemplate.update(query,orderProductId);
    }


}
