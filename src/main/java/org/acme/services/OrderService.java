package org.acme.services;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.acme.constants.CheckoutConstant;
import org.acme.contexts.ConsumerContext;
import org.acme.contexts.ProducerContext;
import org.acme.converters.DateConverter;
import org.acme.converters.EventConverter;
import org.acme.converters.OrderConverter;
import org.acme.daos.ProductDao;
import org.acme.dtos.EventDto;
import org.acme.dtos.OrderDto;
import org.acme.models.Product;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class OrderService {

    private ConsumerContext consumerContext;
    private ProductDao productDao;
    private ProducerContext producerContext;

    public OrderService(){
        this.consumerContext = new ConsumerContext();
        this.productDao = new ProductDao();
        this.producerContext = new ProducerContext();
    }

    public String checkout (String clientId) throws SQLException {
        Gson gson = new Gson();
        JsonObject clientIdJsonObject = gson.fromJson(clientId, JsonObject.class);
        OrderDto orderReturned = checkoutRequest(clientIdJsonObject.get("clientId").getAsString());
        if(checkInventory(orderReturned.getProducts())){
            if(sendEvent(orderReturned) != 201){
                return CheckoutConstant.FAIL_EVENT;
            }
            return OrderConverter.orderToJson(orderReturned);
        }else {
            return CheckoutConstant.GONE;
        }
    }

    private boolean checkInventory(List<Product> products) throws SQLException {
        if(products != null && !products.isEmpty()){
            for (Product product : products){
                Product productReturned = this.productDao.selectById(product.getId());
                if(productReturned.getQuantity() < product.getQuantity()){
                    return false;
                }else{
                    productReturned.setQuantity(productReturned.getQuantity() - product.getQuantity());
                    this.productDao.update(productReturned);
                }
            }
        }
        return true;
    }

    private OrderDto checkoutRequest(String clientId){
        WebTarget target = this.consumerContext.getConnectionCheckout(clientId);
        Response response = target.request().get();
        return OrderConverter.jsonToOrder(response.readEntity(String.class));
    }

    private int sendEvent(OrderDto orderDto){
        WebTarget webTarget = this.producerContext.getConnectionEvent();
        String eventJson = EventConverter.eventToJson(createEvent(orderDto));
        Entity<String> entity = Entity.json(eventJson);
        Response response = webTarget.request(MediaType.APPLICATION_JSON).post(entity);
        return response.getStatus();
    }

    private EventDto createEvent(OrderDto orderDto){
        EventDto eventDto = new EventDto();
        Gson gson = new Gson();
        UUID guid = UUID.randomUUID();

        JsonObject jsonObject = gson.fromJson(OrderConverter.orderToJson(orderDto), JsonObject.class);
        eventDto.setId(guid.toString());
        eventDto.setSource("Monolith");
        eventDto.setTime(DateConverter.dateToString(new Date()));
        eventDto.setSubject("ORDER_CLOSED");
        eventDto.setType(this.producerContext.getTopic());
        eventDto.setCorrelationId(null);
        eventDto.setDataContentType("application/json");
        eventDto.setSpecVersion("1.0");
        eventDto.setData(jsonObject);

        return eventDto;
    }

}
