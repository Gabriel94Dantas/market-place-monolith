package org.acme.converters;

import com.google.gson.Gson;
import org.acme.models.Address;
import org.acme.models.Client;

import java.util.List;

public class AddressConverter {

    public static String addressToJson(Address address){
        Gson gson = new Gson();
        String jsonProduct = gson.toJson(address);
        return jsonProduct;
    }

    public static Address jsonToAddress(String json){
        Gson gson = new Gson();
        Address address = gson.fromJson(json, Address.class);
        return address;
    }

    public static String listAddressesToJson(List<Address> addresses){
        Gson gson = new Gson();
        String json = gson.toJson(addresses);
        return json;
    }
}
