package playground.fakestore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Customer {
    private UUID id;
    private String first;
    private String last;
    private Address address;
    private Store store;
}
