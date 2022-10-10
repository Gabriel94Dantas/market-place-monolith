package org.acme.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.acme.models.Client;
import org.acme.models.Product;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderDto {

    private String id;
    private List<Product> products;
    private String clientId;
    private Double totalPrice;

    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof OrderDto)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        OrderDto orderDto = (OrderDto) o;

        // Compare the data members and return accordingly
        return id.equals(orderDto.getId());
    }


}
