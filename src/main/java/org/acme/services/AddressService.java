package org.acme.services;

import org.acme.converters.AddressConverter;
import org.acme.daos.AddressDao;
import org.acme.models.Address;

import java.sql.SQLException;
import java.util.List;

public class AddressService {

    private AddressDao addressDao;

    public AddressService(){
        this.addressDao = new AddressDao();
    }

    public void addAddress(String addressString) throws SQLException {
        Address address = AddressConverter.jsonToAddress(addressString);
        this.addressDao.insert(address);
    }

    public void editAddress(String addressString) throws SQLException {
        Address address = AddressConverter.jsonToAddress(addressString);
        this.addressDao.update(address);
    }

    public void removeAddress(String addressString) throws SQLException {
        Address address = AddressConverter.jsonToAddress(addressString);
        this.addressDao.delete(address);
    }

    public String returnByClientId(String clientId) throws SQLException {
        List<Address> addresses = this.addressDao.selectByClientId(clientId);
        return AddressConverter.listAddressesToJson(addresses);
    }

    public String returnById(String id) throws SQLException {
        Address address = this.addressDao.selectById(id);
        return AddressConverter.addressToJson(address);
    }
}
