package org.acme.dtos;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EventDto {

    private String id;
    private String specVersion;
    private String source;
    private String type;
    private String subject;
    private String time;
    private String correlationId;
    private String dataContentType;
    private JsonObject data;

}
