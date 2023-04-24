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
    public void testGetStoreWithHighestInventory() {
        Store store = processor.getStoreWithHighestInventory();

        System.out.println(store);
    }

    @Test
    public void testGetMostCommonlyPurchasedItem() {
        Item item = processor.getMostCommonlyPurchasedItem();

        System.out.println(item);
    }

    @Test
    public void getMostExpensiveStore() {

    }

    @Test
    public void getMostLoyalCustomer() {

    }
}
