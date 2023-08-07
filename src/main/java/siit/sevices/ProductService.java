package siit.sevices;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import siit.db.ProductDao;
import siit.model.OrderProduct;
import siit.model.Product;

@Service
public class ProductService {
    @Autowired
    private ProductDao productDao;

    public List<Product> getProductsBy(String term){  return productDao.getOrdersBy(term);}


}
