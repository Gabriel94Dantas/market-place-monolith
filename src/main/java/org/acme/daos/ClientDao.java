package org.acme.daos;

import org.acme.contexts.MysqlContext;
import org.acme.models.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClientDao {

    private MysqlContext mysqlContext;

    public ClientDao(){
        this.mysqlContext = new MysqlContext();
    }

    public void insert(Client client) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try{

            conn = this.mysqlContext.getConnection();
            conn.setAutoCommit(false);

            StringBuilder insert = new StringBuilder();
            insert.append(" INSERT INTO clients (id, name, lastname, email, password) ");
            insert.append(" VALUES (?, ?, ?, ?, ?) ");

            preparedStatement = conn.prepareStatement(insert.toString());

            UUID guid = UUID.randomUUID();
            preparedStatement.setString(1, guid.toString());
            preparedStatement.setString(2, client.getName());
            preparedStatement.setString(3, client.getLastname());
            preparedStatement.setString(4, client.getEmail());
            preparedStatement.setString(5, client.getPassword());

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

    public void update(Client client) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try{

            conn = this.mysqlContext.getConnection();
            conn.setAutoCommit(false);

            StringBuilder update = new StringBuilder();
            update.append(" UPDATE clients SET id = ?, name = ?, lastname = ?, email = ?, password = ? ");
            update.append(" WHERE id = ? ");

            preparedStatement = conn.prepareStatement(update.toString());

            preparedStatement.setString(1, client.getId());
            preparedStatement.setString(2, client.getName());
            preparedStatement.setString(3, client.getLastname());
            preparedStatement.setString(4, client.getEmail());
            preparedStatement.setString(5, client.getPassword());
            preparedStatement.setString(6, client.getId());

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

    public void delete(Client client) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try{

            conn = this.mysqlContext.getConnection();
            conn.setAutoCommit(false);

            StringBuilder delete = new StringBuilder();
            delete.append(" DELETE FROM clients ");
            delete.append(" WHERE id = ? ");

            preparedStatement = conn.prepareStatement(delete.toString());

            preparedStatement.setString(1, client.getId());

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

    public List<Client> selectAll() throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        List<Client> clients = new ArrayList<Client>();
        try{
            conn = this.mysqlContext.getConnection();

            StringBuilder select = new StringBuilder();
            select.append(" SELECT id, name, lastname, email ");
            select.append(" FROM clients ");
            select.append(" WHERE 1 = 1 ");

            preparedStatement = conn.prepareStatement(select.toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Client client = selectReturn(resultSet);
                clients.add(client);
            }


        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }finally {
            preparedStatement.close();
            conn.close();
        }
        return clients;
    }

    public Client selectByEmailPassword(String email, String password) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        Client client = new Client();
        try{
            conn = this.mysqlContext.getConnection();

            StringBuilder select = new StringBuilder();
            select.append(" SELECT id, name, lastname, email ");
            select.append(" FROM clients ");
            select.append(" WHERE email = ? AND password = ? ");

            preparedStatement = conn.prepareStatement(select.toString());
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                client = selectReturn(resultSet);
            }


        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }finally {
            preparedStatement.close();
            conn.close();
        }
        return client;
    }

    public Client selectById(String id) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        Client client = new Client();
        try{
            conn = this.mysqlContext.getConnection();

            StringBuilder select = new StringBuilder();
            select.append(" SELECT id, name, lastname, email ");
            select.append(" FROM clients ");
            select.append(" WHERE id = ? ");

            preparedStatement = conn.prepareStatement(select.toString());
            preparedStatement.setString(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                client = selectReturn(resultSet);
            }


        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }finally {
            preparedStatement.close();
            conn.close();
        }
        return client;
    }

    private Client selectReturn(ResultSet resultSet) throws SQLException {
        Client client = new Client();

        if(resultSet.getString("id") != null){
            client.setId(resultSet.getString("id"));
        }

        if(resultSet.getString("name") != null){
            client.setName(resultSet.getString("name"));
        }

        if(resultSet.getString("lastname") != null){
            client.setLastname(resultSet.getString("lastname"));
        }

        if(resultSet.getString("email") != null){
            client.setEmail(resultSet.getString("email"));
        }

        return client;
    }

}
