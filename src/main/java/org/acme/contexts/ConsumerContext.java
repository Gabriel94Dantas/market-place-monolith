package org.acme.contexts;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class ConsumerContext {

    private Client client;

    public ConsumerContext (){
        this.client = ClientBuilder.newClient();
    }

    public WebTarget getConnectionCheckout(String clientId){
        WebTarget webTarget = null;
        try{
            webTarget = this.client.target(getUrl() + checkoutPath()+ clientId);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        return webTarget;
    }

    private String checkoutPath(){
        if(System.getenv("CHECKOUT_PATH") != null
                && !System.getenv("CHECKOUT_PATH").isEmpty()){
            return System.getenv("CHECKOUT_PATH");
        }else{
            return "/orders/clientId/";
        }
    }

    private String getUrl(){
        if(System.getenv("CONSUMER_HOST") != null
                && !System.getenv("CONSUMER_HOST").isEmpty()){
            return System.getenv("CONSUMER_HOST");
        }else{
            return "http://localhost:8082";
        }
    }

}
