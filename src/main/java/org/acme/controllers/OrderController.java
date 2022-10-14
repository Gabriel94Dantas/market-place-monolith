package org.acme.controllers;

import org.acme.constants.CheckoutConstant;
import org.acme.services.OrderService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("orders")
public class OrderController {

    private OrderService orderService;

    public OrderController(){
        this.orderService = new OrderService();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(String clientId){
        try{
            String orderReturned = this.orderService.checkout(clientId);
            if(orderReturned == CheckoutConstant.GONE){
                return Response.status(Response.Status.GONE).build();
            } else if (orderReturned == CheckoutConstant.FAIL_EVENT){
                return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
            }
            return Response.ok(orderReturned).build();
        }catch (Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}
