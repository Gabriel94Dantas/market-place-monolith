package org.acme.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Address {

    private String id;
    private String cep;
    private String address;
    private String number;
    private String complement;
    private String neighborhood;
    private String city;
    private String state;
    private String country;
    private String clientId;

    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Address)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Address a = (Address) o;

        // Compare the data members and return accordingly
        return id.equals(a.getId());
    }

}
