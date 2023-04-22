package playground.fakestore;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order {
    private UUID id;
    private Customer customer;
    private Store store;
    private List<Item> items;
}
