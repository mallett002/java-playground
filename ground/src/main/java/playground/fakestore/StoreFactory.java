package playground.fakestore;

import lombok.Data;
import net.datafaker.Faker;
import net.datafaker.providers.base.Name;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Data
public class StoreFactory {

    private Faker faker;
    private Random random;

    private List<Customer> customers;
    private List<Store> stores;
    private List<Item> items;
    private List<Order> orders;


    public StoreFactory() {
        faker = new Faker();
        random = new Random();
        stores = generateRandomStores();
        customers = generateRandomCustomers();
        items = generateRandomItems();
        orders = generateRandomOrders();
    }


    private List<Customer> generateRandomCustomers() {
        List<Customer> customers = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            Name name = faker.name();
            Store store = getRandomEntity(stores);

            Customer customer = new Customer(
                    java.util.UUID.randomUUID(),
                    name.firstName(),
                    name.lastName(),
                    getRandomAddress(),
                    store
            );

            customers.add(customer);
        }

        return customers;
    }

    private <T> T getRandomEntity(List<T> things) {
        int index = random.nextInt(things.size());

        return things.get(index);
    }

    private List<Item> pickAFewItems() {
        List<Item> cart = new ArrayList<>();
        int cartCount = random.nextInt(20);

        for (int i = 0; i < cartCount; i++) {
            cart.add(getRandomEntity(items));
        }

        return cart;
    }

    private List<UUID> pickStoreIdsForItem() {
        List<UUID> storeIds = new ArrayList<>();

        int maxStoreCount = random.nextInt(stores.size());

        for (int i = 0; i < maxStoreCount; i++) {
            storeIds.add(getRandomEntity(stores).getId());
        }

        return storeIds;
    }

    private Address getRandomAddress() {
        return new Address(
                faker.address().city(),
                faker.address().state(),
                faker.address().country(),
                faker.address().streetName());
    }

    private List<Store> generateRandomStores() {
        List<Store> stores = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            Store store = new Store(
                java.util.UUID.randomUUID(),
                faker.company().name(),
                getRandomAddress());

            stores.add(store);
        }

        return stores;
    }

    private List<Item> generateRandomItems() {
        List<Item> itemList = new ArrayList<>();

        for (int i = 0; i < 200; i++) {
            itemList.add(new Item(
                    java.util.UUID.randomUUID(),
                    faker.barcode().ean13(),
                    faker.commerce().productName(),
                    random.nextFloat(),
                    pickStoreIdsForItem()
            ));
        }

        return itemList;
    }

    private List<Order> generateRandomOrders() {
        List<Order> orders = new ArrayList<>();

        for (int i = 0; i < 200; i++) {
            Customer customer = getRandomEntity(customers);
            Store store = random.nextBoolean()
                    ? getRandomEntity(getStores())
                    : customer.getStore();

            orders.add(new Order(
                    java.util.UUID.randomUUID(),
                    customer,
                    store,
                    pickAFewItems()
            ));
        }

        return orders;
    }
























}
