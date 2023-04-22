package playground.fakestore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class Item {
    private UUID id;
    private long sku;
    private String name;
    private float price;
}
