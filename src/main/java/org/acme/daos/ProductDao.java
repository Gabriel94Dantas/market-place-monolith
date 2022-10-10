package org.acme.daos;

import org.acme.contexts.MysqlContext;
import org.acme.models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductDao {
    private MysqlContext mysqlContext;

    public ProductDao() {
        this.mysqlContext = new MysqlContext();
    }

    public void insert(Product product) throws SQLException {
        Connection conn = null;
        try{
            conn = this.mysqlContext.getConnection();
            conn.setAutoCommit(false);


            StringBuilder insert = new StringBuilder();
            insert.append(" INSERT INTO products(id, name, quantity, price, code) ");
            insert.append(" values (?, ?, ?, ?, ?); ");

            PreparedStatement preparedStatement = conn.prepareStatement(insert.toString());

            UUID guid = UUID.randomUUID();
            product.setId(guid.toString());
            preparedStatement.setString(1, product.getId());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setInt(3, product.getQuantity());
            preparedStatement.setDouble(4, product.getPrice());
            preparedStatement.setString(5, product.getCode());

            preparedStatement.execute();
            conn.commit();
        }catch (Exception e){
            throw e;
        }finally {
            conn.close();
        }
    }

    public void update(Product product) throws SQLException {
        Connection conn = null;
        try{
            conn = this.mysqlContext.getConnection();
            conn.setAutoCommit(false);


            StringBuilder update = new StringBuilder();
            update.append(" UPDATE products SET id = ?, name = ?, quantity = ?, price = ?, code = ? ");
            update.append(" WHERE id = ? ; ");

            PreparedStatement preparedStatement = conn.prepareStatement(update.toString());


            preparedStatement.setString(1, product.getId());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setInt(3, product.getQuantity());
            preparedStatement.setDouble(4, product.getPrice());
            preparedStatement.setString(5, product.getCode());

            preparedStatement.setString(6, product.getId());

            preparedStatement.execute();
            conn.commit();
        }catch (Exception e){
            throw e;
        }finally {
            conn.close();
        }
    }

    public void delete(Product product) throws SQLException {
        Connection conn = null;
        try{
            conn = this.mysqlContext.getConnection();
            conn.setAutoCommit(false);


            StringBuilder delete = new StringBuilder();
            delete.append(" DELETE FROM products ");
            delete.append(" WHERE id = ? ; ");

            PreparedStatement preparedStatement = conn.prepareStatement(delete.toString());


            preparedStatement.setString(1, product.getId());

            preparedStatement.execute();
            conn.commit();
        }catch (Exception e){
            throw e;
        }finally {
            conn.close();
        }
    }

    public List<Product> selectAll() throws SQLException {
        Connection conn = null;
        List<Product> products = new ArrayList<Product>();
        try{
            conn = this.mysqlContext.getConnection();


            StringBuilder select = new StringBuilder();
            select.append(" SELECT id, name, quantity, price, code FROM products ");
            select.append(" WHERE 1 = 1 ");

            PreparedStatement preparedStatement = conn.prepareStatement(select.toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Product product = setProductReturn(resultSet);
                products.add(product);
            }

        }catch (Exception e){
            throw e;
        }finally {
            conn.close();
        }
        return products;
    }


    public Product selectById(String id) throws SQLException {
        Connection conn = null;
        Product product = new Product();
        try{
            conn = this.mysqlContext.getConnection();


            StringBuilder select = new StringBuilder();
            select.append(" SELECT id, name, quantity, price, code FROM products ");
            select.append(" WHERE id = ? ");

            PreparedStatement preparedStatement = conn.prepareStatement(select.toString());

            preparedStatement.setString(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                 product = setProductReturn(resultSet);
            }

        }catch (Exception e){
            throw e;
        }finally {
            conn.close();
        }
        return product;
    }

    public Product selectByCode(String code) throws SQLException {
        Connection conn = null;
        Product product = new Product();
        try{
            conn = this.mysqlContext.getConnection();


            StringBuilder select = new StringBuilder();
            select.append(" SELECT id, name, quantity, price, code FROM products ");
            select.append(" WHERE code = ? ");

            PreparedStatement preparedStatement = conn.prepareStatement(select.toString());

            preparedStatement.setString(1, code);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                product = setProductReturn(resultSet);
            }

        }catch (Exception e){
            throw e;
        }finally {
            conn.close();
        }
        return product;
    }

    private Product setProductReturn(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        if(resultSet.getString("id") != null){
            product.setId(resultSet.getString("id"));
        }
        if(resultSet.getString("name") != null){
            product.setName(resultSet.getString("name"));
        }
        if(resultSet.getInt("quantity") != 0){
            product.setQuantity(resultSet.getInt("quantity"));
        }
        if(resultSet.getDouble("price") != 0.0){
            product.setPrice(resultSet.getDouble("price"));
        }
        if(resultSet.getString("code") != null){
            product.setCode(resultSet.getString("code"));
        }

        return product;
    }
}
