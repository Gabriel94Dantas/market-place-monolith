package org.acme.converters;

import com.google.gson.Gson;
import org.acme.dtos.EventDto;

public class EventConverter {

    public static String eventToJson(EventDto eventDto){
        Gson gson = new Gson();
        String jsonProduct = gson.toJson(eventDto);
        return jsonProduct;
    }

    public static EventDto jsonToEvent(String json){
        Gson gson = new Gson();
        EventDto eventDto = gson.fromJson(json, EventDto.class);
        return eventDto;
    }

}
