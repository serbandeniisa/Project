package siit.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import siit.model.OrderProduct;
import siit.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Product> getOrdersBy(String term){
        return jdbcTemplate.query("select * from products where lower(name) like ?", this::getOrderProduct, "%"+term.toLowerCase()+"%");
    }

    public Product getOrderProduct(ResultSet rs, int rowNum) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setPrice(rs.getBigDecimal("price"));
        product.setName(rs.getString("name"));
        product.setWeight(rs.getBigDecimal("weight"));
        product.setUrl(rs.getString("url"));
        return product;
    }

    public Product getProductById(OrderProduct orderProduct){
        return jdbcTemplate.queryForObject("select * from products where id=?",this::getOrderProduct,orderProduct.getProduct().getId());
    }


}
