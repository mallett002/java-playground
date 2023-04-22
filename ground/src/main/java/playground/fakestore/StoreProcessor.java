package playground.fakestore;

import lombok.Data;
import lombok.Getter;

public class StoreProcessor {

    @Getter
    StoreFactory storeFactory;

    public StoreProcessor() {
        storeFactory = new StoreFactory();
    }

    public Store storeWithHighestInventory() {
        Store store = storeFactory.getStores().stream().findAny().orElse(null);

        return store;
    }
}
