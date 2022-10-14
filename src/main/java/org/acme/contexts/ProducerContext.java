package org.acme.contexts;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class ProducerContext {

    private Client client;

    public ProducerContext(){
        this.client = ClientBuilder.newClient();
    }

    public WebTarget getConnectionEvent(){
        WebTarget webTarget = null;
        try{
            webTarget = this.client.target(getUrl() + checkoutPath());
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        return webTarget;
    }

    private String checkoutPath(){
        if(System.getenv("EVENT_PATH") != null
                && !System.getenv("EVENT_PATH").isEmpty()){
            return System.getenv("EVENT_PATH");
        }else{
            return "/event";
        }
    }

    private String getUrl(){
        if(System.getenv("PRODUCER_HOST") != null
                && !System.getenv("PRODUCER_HOST").isEmpty()){
            return System.getenv("PRODUCER_HOST");
        }else{
            return "http://localhost:8080";
        }
    }

    public String getTopic(){
        if(System.getenv("TOPIC_NAME") != null
                && !System.getenv("TOPIC_NAME").isEmpty()){
            return System.getenv("TOPIC_NAME");
        }else{
            return "br.com.example.chiefs";
        }
    }

}
