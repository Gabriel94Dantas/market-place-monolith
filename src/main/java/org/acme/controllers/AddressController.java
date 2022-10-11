package org.acme.controllers;

import org.acme.services.AddressService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/addresses")
public class AddressController {

    private AddressService addressService;

    public AddressController(){
        this.addressService = new AddressService();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(String address){
        try{
            this.addressService.addAddress(address);
            return Response.status(Response.Status.CREATED).build();
        }catch (Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response put(String address){
        try{
            this.addressService.editAddress(address);
            return Response.status(Response.Status.OK).build();
        }catch (Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(String address){
        try{
            this.addressService.removeAddress(address);
            return Response.status(Response.Status.NO_CONTENT).build();
        }catch (Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("clientId/{clientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByClientId(@PathParam("clientId") String clientId){
        try {
            return Response.ok(this.addressService.returnByClientId(clientId)).build();
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") String id){
        try{
            return Response.ok(this.addressService.returnById(id)).build();
        }catch (Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}
