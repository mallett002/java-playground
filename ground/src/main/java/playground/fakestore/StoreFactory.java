package playground.fakestore;

import lombok.Data;
import net.datafaker.Faker;
import net.datafaker.providers.base.Name;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
                    random.nextFloat()
            ));
        }

        return itemList;
    }

    private List<Order> generateRandomOrders() {
        List<Order> orders = new ArrayList<>();

        for (int i = 0; i < 200; i++) {
            Customer customer = getRandomEntity(customers);

            orders.add(new Order(
                    java.util.UUID.randomUUID(),
                    customer,
                    customer.getStore(),
                    pickAFewItems()
            ));
        }

        return orders;
    }
























}
