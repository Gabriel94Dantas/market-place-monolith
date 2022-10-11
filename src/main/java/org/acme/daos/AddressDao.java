package org.acme.daos;

import org.acme.contexts.MysqlContext;
import org.acme.converters.AddressConverter;
import org.acme.models.Address;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AddressDao {

    private MysqlContext mysqlContext;

    public AddressDao(){
        this.mysqlContext = new MysqlContext();
    }

    public void insert(Address address) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try{
            conn = this.mysqlContext.getConnection();
            conn.setAutoCommit(false);

            StringBuilder insert = new StringBuilder();
            insert.append(" INSERT INTO addresses (id, cep, address, number, " +
                    "complement, neighborhood, city, state, country, clientId) ");
            insert.append(" VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");

            preparedStatement = conn.prepareStatement(insert.toString());

            UUID guid = UUID.randomUUID();
            preparedStatement.setString(1, guid.toString());
            preparedStatement.setString(2, address.getCep());
            preparedStatement.setString(3, address.getAddress());
            preparedStatement.setString(4, address.getNumber());
            preparedStatement.setString(5, address.getComplement());
            preparedStatement.setString(6, address.getNeighborhood());
            preparedStatement.setString(7, address.getCity());
            preparedStatement.setString(8, address.getState());
            preparedStatement.setString(9, address.getCountry());
            preparedStatement.setString(10, address.getClientId());

            preparedStatement.execute();
            conn.commit();

        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }finally {
            preparedStatement.close();
            conn.close();
        }
    }

    public void update(Address address) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try{
            conn = this.mysqlContext.getConnection();
            conn.setAutoCommit(false);

            StringBuilder update = new StringBuilder();
            update.append(" UPDATE addresses SET id = ?, cep = ?, address = ?, number = ?, " +
                    "complement = ?, neighborhood = ?, city = ?, state = ?, country = ?, clientId = ? ");
            update.append(" WHERE id = ? ");

            preparedStatement = conn.prepareStatement(update.toString());

            preparedStatement.setString(1, address.getId());
            preparedStatement.setString(2, address.getCep());
            preparedStatement.setString(3, address.getAddress());
            preparedStatement.setString(4, address.getNumber());
            preparedStatement.setString(5, address.getComplement());
            preparedStatement.setString(6, address.getNeighborhood());
            preparedStatement.setString(7, address.getCity());
            preparedStatement.setString(8, address.getState());
            preparedStatement.setString(9, address.getCountry());
            preparedStatement.setString(10, address.getClientId());
            preparedStatement.setString(11, address.getId());

            preparedStatement.execute();
            conn.commit();

        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }finally {
            preparedStatement.close();
            conn.close();
        }
    }

    public void delete(Address address) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try{
            conn = this.mysqlContext.getConnection();
            conn.setAutoCommit(false);

            StringBuilder delete = new StringBuilder();
            delete.append(" DELETE FROM addresses ");
            delete.append(" WHERE id = ? ");

            preparedStatement = conn.prepareStatement(delete.toString());

            preparedStatement.setString(1, address.getId());

            preparedStatement.execute();
            conn.commit();

        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }finally {
            preparedStatement.close();
            conn.close();
        }
    }

    public List<Address> selectByClientId(String clientId) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        List<Address> addresses = new ArrayList<Address>();

        try {

            conn = this.mysqlContext.getConnection();

            StringBuilder select = new StringBuilder();
            select.append(" SELECT id, cep, address, number, " +
                    " complement, neighborhood, city, state, country, clientId ");
            select.append(" FROM addresses ");
            select.append(" WHERE clientId = ? ");

            preparedStatement = conn.prepareStatement(select.toString());

            preparedStatement.setString(1, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Address address = selectReturn(resultSet);
                addresses.add(address);
            }

        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }finally {
            preparedStatement.close();
            conn.close();
        }
        return addresses;
    }

    public Address selectById(String id) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        Address address = new Address();

        try {

            conn = this.mysqlContext.getConnection();

            StringBuilder select = new StringBuilder();
            select.append(" SELECT id, cep, address, number, " +
                    " complement, neighborhood, city, state, country, clientId ");
            select.append(" FROM addresses ");
            select.append(" WHERE id = ? ");

            preparedStatement = conn.prepareStatement(select.toString());

            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                address = selectReturn(resultSet);
            }

        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }finally {
            preparedStatement.close();
            conn.close();
        }
        return address;
    }

    private Address selectReturn(ResultSet resultSet) throws SQLException {
        Address address = new Address();

        if(resultSet.getString("id") != null){
            address.setId(resultSet.getString("id"));
        }
        if(resultSet.getString("cep") != null){
            address.setCep(resultSet.getString("cep"));
        }

        if(resultSet.getString("address") != null){
            address.setAddress(resultSet.getString("address"));
        }

        if(resultSet.getString("number") != null){
            address.setNumber(resultSet.getString("number"));
        }

        if(resultSet.getString("complement") != null){
            address.setComplement(resultSet.getString("complement"));
        }

        if(resultSet.getString("neighborhood") != null){
            address.setNeighborhood(resultSet.getString("neighborhood"));
        }

        if(resultSet.getString("city") != null){
            address.setCity(resultSet.getString("city"));
        }

        if(resultSet.getString("state") != null){
            address.setState(resultSet.getString("state"));
        }

        if(resultSet.getString("country") != null){
            address.setCountry(resultSet.getString("country"));
        }

        if(resultSet.getString("clientId") != null){
            address.setClientId(resultSet.getString("clientId"));
        }

        return address;
    }

}
