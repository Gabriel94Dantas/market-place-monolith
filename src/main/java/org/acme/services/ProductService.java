package org.acme.services;

import org.acme.converters.ProductConverter;
import org.acme.daos.ProductDao;
import org.acme.models.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductService {

    ProductDao productDao;

    public ProductService (){
        this.productDao = new ProductDao();
    }

    public void addProduct(String jsonProduct) throws SQLException {
        Product product = ProductConverter.jsonToProduct(jsonProduct);
        this.productDao.insert(product);
    }

    public void removeProduct(String jsonProduct) throws SQLException {
        Product product = ProductConverter.jsonToProduct(jsonProduct);
        this.productDao.delete(product);
    }

    public void editProduct(String jsonProduct) throws SQLException {
        Product product = ProductConverter.jsonToProduct(jsonProduct);
        this.productDao.update(product);
    }

    public String returnProductById(String id) throws SQLException {
        Product product = new Product();
        product = this.productDao.selectById(id);
        return ProductConverter.productToJson(product);
    }

    public String returnProductAll() throws SQLException {
        List<Product> products = new ArrayList<Product>();
        products = this.productDao.selectAll();
        return ProductConverter.listProductsToJson(products);
    }

    public String returnProductByCode(String code) throws SQLException {
        Product product = new Product();
        product = this.productDao.selectByCode(code);
        return ProductConverter.productToJson(product);
    }

}
