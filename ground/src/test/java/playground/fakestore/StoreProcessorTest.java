package playground.fakestore;

import junit.framework.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StoreProcessorTest {
    private StoreProcessor processor;

    @BeforeAll
    void beforeAllTests() {
        processor = new StoreProcessor();
    }

    @Test
    public void getStoreWithHighestInventory() {
        Store store = processor.storeWithHighestInventory();

        System.out.println(store);
    }

    // most expensive store
    // most loyal customer
}
