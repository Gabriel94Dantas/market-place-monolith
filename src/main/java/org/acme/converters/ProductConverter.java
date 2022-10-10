package org.acme.converters;

import com.google.gson.Gson;
import org.acme.models.Product;

import java.util.List;

public class ProductConverter {

    public static String productToJson(Product product){
        Gson gson = new Gson();
        String jsonProduct = gson.toJson(product);
        return jsonProduct;
    }

    public static Product jsonToProduct(String json){
        Gson gson = new Gson();
        Product product = gson.fromJson(json, Product.class);
        return product;
    }

    public static String listProductsToJson(List<Product> products){
        Gson gson = new Gson();
        String json = gson.toJson(products);
        return json;
    }

}
