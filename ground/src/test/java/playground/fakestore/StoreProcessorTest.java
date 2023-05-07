package playground.fakestore;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

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
    public void testGetHighestPayingCustomer() {
        Customer customer = processor.getHighestPayingCustomer();

        System.out.println(customer);
    }

    @Test
    public void testGetCustomersThatShopAtMostOtherStores() {
        List<Customer> customers = processor.getCustomerThatShopAtMostStores();

        System.out.println(customers);
    }

    @Test
    public void testGetStoresWithMostOrders() {
        Store store = processor.getStoresWithMostOrders();

        System.out.println(store);
    }
}
