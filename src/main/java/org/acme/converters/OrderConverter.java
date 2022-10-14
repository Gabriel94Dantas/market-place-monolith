package org.acme.converters;

import com.google.gson.Gson;
import org.acme.dtos.OrderDto;

public class OrderConverter {

    public static String orderToJson(OrderDto orderDto){
        Gson gson = new Gson();
        String jsonProduct = gson.toJson(orderDto);
        return jsonProduct;
    }

    public static OrderDto jsonToOrder(String json){
        Gson gson = new Gson();
        OrderDto orderDto = gson.fromJson(json, OrderDto.class);
        return orderDto;
    }

}
