package siit.sevices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import siit.ValidationException;
import siit.db.OrderDao;
import siit.db.ProductDao;
import siit.model.Customer;
import siit.model.Order;
import siit.model.OrderProduct;
import siit.model.Product;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    ProductDao productDao;

    public void deleteOrder(int orderId) {
        //delete pentru order product
        orderDao.deleteOrderProduct(orderId);
        orderDao.deleteOrder(orderId);
    }

    public List<OrderProduct> getOrderProductBy(int customerId, int orderId) {
       //orderDao.getOrderProductBy(orderId).stream().forEach(e-> System.out.println(e+"Produse"));
        List<OrderProduct> orderProducts = orderDao.getOrderProductBy(orderId);
//        for(OrderProduct op:orderProducts){
//            op.setValue(op.getQuantity().multiply(op.getProduct().getPrice()));
//        }
        return orderProducts;
    }

    public Order getOrderBy(int customerId, int orderId) {
        for(Order order : orderDao.getOrdersByCustomerId(customerId)){
            if(order.getId() == orderId){
                return order;
            }
        }
        return null;
    }

    private OrderProduct getOrderProduct2(List<OrderProduct>intermediateOpList, OrderProduct orderProduct,int orderId){

        for (OrderProduct op: intermediateOpList){
            if(op.getProduct().getId()==orderProduct.getProduct().getId()){
                return op;
            }
        }
        return null;
    }
    private OrderProduct prepareForInsertion(OrderProduct orderProduct,int orderId){
        OrderProduct orderToInsert = new OrderProduct();
        Product prod = productDao.getProductById(orderProduct);
       BigDecimal value = orderProduct.getQuantity().multiply(prod.getPrice());
        orderToInsert.setId(orderId);
        orderToInsert.setQuantity(orderProduct.getQuantity());
        orderToInsert.setValue(value);
        orderToInsert.setProduct(prod);
        return orderToInsert;
    }

    public OrderProduct getOrderProduct(OrderProduct orderProduct, int customerId,int orderId){
        List<OrderProduct> workingList= orderDao.getOrderProductBy(orderId);
        OrderProduct orderProductModificat = this.getOrderProduct2(workingList,orderProduct,orderId);
        if(workingList.contains(orderProductModificat)){
            for (OrderProduct op :workingList){
                if(op.getProduct().getId()==orderProduct.getProduct().getId()){
                    BigDecimal quantity = op.getQuantity().add(orderProduct.getQuantity());
                    op.setQuantity(quantity);
                    op.setValue(quantity.multiply(op.getProduct().getPrice()));
                    orderDao.updateOrder(orderId,op);
                    return op;
                }
            }
        }else {
            orderProductModificat= this.prepareForInsertion(orderProduct,orderId);
            orderDao.insertOrder(orderId, orderProductModificat);
            workingList.add(orderProductModificat);
            return orderProductModificat;
        }
        return null;
    }

    public void delete(int orderProductId){
        orderDao.deleteProduct(orderProductId);
    }

}
