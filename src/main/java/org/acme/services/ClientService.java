package org.acme.services;

import com.google.gson.JsonObject;
import org.acme.converters.ClientConverter;
import org.acme.daos.ClientDao;
import org.acme.models.Client;

import java.sql.SQLException;
import java.util.List;

public class ClientService {

    private ClientDao clientDao;

    public ClientService(){
        this.clientDao = new ClientDao();
    }

    public void addClient(String clientString) throws SQLException {
        Client client = ClientConverter.jsonToClient(clientString);
        this.clientDao.insert(client);
    }

    public void editClient(String clientString) throws SQLException {
        Client client = ClientConverter.jsonToClient(clientString);
        this.clientDao.update(client);
    }

    public void removeClient(String clientString) throws SQLException {
        Client client = ClientConverter.jsonToClient(clientString);
        this.clientDao.delete(client);
    }

    public String returnByEmailPassword(String login) throws SQLException {
        JsonObject loginJsonObject = ClientConverter.stringToJsonObject(login);
        Client client = this.clientDao.selectByEmailPassword(loginJsonObject.get("email").getAsString(),
                loginJsonObject.get("password").getAsString());
        return ClientConverter.clientToJson(client);
    }

    public String returnById(String id) throws SQLException {
        Client client = this.clientDao.selectById(id);
        return ClientConverter.clientToJson(client);
    }

    public String returnAll() throws SQLException {
        List<Client> clients = this.clientDao.selectAll();
        return ClientConverter.listClientsToJson(clients);
    }

}
