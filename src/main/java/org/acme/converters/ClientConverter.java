package org.acme.converters;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.acme.models.Client;

import java.util.List;

public class ClientConverter {

    public static String clientToJson(Client client){
        Gson gson = new Gson();
        String jsonProduct = gson.toJson(client);
        return jsonProduct;
    }

    public static Client jsonToClient(String json){
        Gson gson = new Gson();
        Client client = gson.fromJson(json, Client.class);
        return client;
    }

    public static String listClientsToJson(List<Client> clients){
        Gson gson = new Gson();
        String json = gson.toJson(clients);
        return json;
    }

    public static JsonObject stringToJsonObject(String login){
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(login, JsonObject.class);
        return jsonObject;
    }
}
