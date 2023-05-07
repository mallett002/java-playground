package playground.fakestore;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.Pair;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class StoreProcessor {

    @Getter
    StoreFactory storeFactory;
    ObjectMapper mapper;

    public StoreProcessor() {
        storeFactory = new StoreFactory();
        mapper = new ObjectMapper();
    }

    public Store getStoreWithHighestInventory() {
        List<Item> items = storeFactory.getItems();

        return storeFactory.getStores().stream()
                .sorted((store1, store2) -> {
                    Map<UUID, Integer> storeCount = items.stream()
                            .map(Item::getLocations)
                            .flatMap(List::stream)
                            .filter(storeId -> storeId.equals(store1.getId()) || storeId.equals(store2.getId()))
                            .reduce(
                                    new HashMap<UUID, Integer>(),
                                    (partial, storeId) -> {
                                        if (partial.containsKey(storeId)) {
                                            Integer countOne = partial.get(storeId);
                                            partial.put(storeId, countOne + 1);
                                        } else {
                                            partial.put(storeId, 1);
                                        }

                                        return partial;
                                    },
                                    (mapOne, mapTwo) -> {
                                        mapOne.putAll(mapTwo);
                                        return mapOne;
                                    }
                            );

                    return storeCount.get(store2.getId()).compareTo(storeCount.get(store1.getId()));
                })
                .findFirst()
                .orElse(null);


        // Alternative way:
//        TreeMap<String, Integer> inventoryCountsByStoreId = storeFactory.getItems().stream()
//                    .map(Item::getLocations)
//                    .flatMap(List::stream)
//                    .map((id) -> {
//                        Store foundStore = storeFactory.getStores().stream()
//                                .filter(store -> store.getId().equals(id)).findAny().orElse(null);
//
//                        return foundStore != null ? foundStore.getStoreName() : null;
//                    })
//                    .filter(Objects::nonNull)
//                    .reduce(
//                            new TreeMap<>(),
//                            (partial, storeName) -> {
//                                if (partial.containsKey(storeName)) {
//                                    Integer curr = partial.get(storeName);
//                                    partial.put(storeName, curr + 1);
//                                } else {
//                                    partial.put(storeName, 1);
//                                }
//
//                                return partial;
//                            },
//                            (first, second) -> {
//                                first.putAll(second);
//
//                                return first;
//                            }
//                    );
//
//        int largestInventoryCount = inventoryCountsByStoreId.values().stream().max(Integer::compare).get();
//        String expectedStoreWithLargestInventory = inventoryCountsByStoreId.keySet().stream().min((a, b) -> inventoryCountsByStoreId.get(b).compareTo(inventoryCountsByStoreId.get(a)))
//                .orElse(null);
    }

    public Item getMostCommonlyPurchasedItem() {
    /*
    * orders
    *   items flatmap
    *       item uuid
    *       hashmap<uuid, occuranceCount>
            return highest
    *
    * */

        Map<UUID, Pair<Item, Integer>> occurrencesByUuid = storeFactory.getOrders().stream()
                .flatMap(order -> order.getItems().stream())
                .reduce(
                        new HashMap<>(),
                        (accum, item) -> {
                            if (!accum.containsKey(item.getId())) {
                                accum.put(item.getId(), new Pair<>(item, 1));
                            } else {
                                Pair<Item, Integer> pair = accum.get(item.getId());
                                pair.setRight(pair.getRight() + 1);
                                accum.put(item.getId(), pair);
                            }

                            return accum;
                        },
                        (mapOne, mapTwo) -> {
                            mapOne.putAll(mapTwo);
                            return mapOne;
                        }
                );

        Pair<Item, Integer> maxPair = Collections.max(
                occurrencesByUuid.entrySet(),
                Comparator.comparingInt(entry -> entry.getValue().getRight())).getValue();

        return maxPair.getLeft();
    }

    public Customer getHighestPayingCustomer() {
        /*
            orders ->
                reduce to {customerId -> amount}
                get highestAmt
        */

        Map<UUID, Double> amountByCustomerId = getStoreFactory().getOrders().stream()
                .reduce(
                        new HashMap<>(),
                        (memo, order) -> {
                            Double orderTotal = order.getItems().stream()
                                    .reduce(0.00,
                                            (total, item) -> total + item.getPrice(),
                                            Double::sum
                                    );
                            UUID customerId = order.getCustomer().getId();

                            if (!memo.containsKey(customerId)) {
                                memo.put(customerId, orderTotal);
                            } else {
                                memo.put(customerId, memo.get(customerId) + orderTotal);
                            }

                            return memo;
                        },
                        (mapOne, mapTwo) -> {
                            mapOne.putAll(mapTwo);

                            return mapOne;
                        }
                );

        UUID customerID = Collections.max(
                amountByCustomerId.entrySet(),
                Comparator.comparingDouble(Map.Entry::getValue)).getKey();

        return getStoreFactory().getCustomers().stream()
                .filter(c -> c.getId().equals(customerID)).findAny().orElse(null);
    }

    public List<Customer> getCustomerThatShopAtMostStores() {
        Map<UUID, List<UUID>> storeCountByCustomerId = getStoreFactory().getOrders().stream()
                .reduce(
                        new HashMap<>(),
                        (map, order) -> {
                            UUID id = order.getCustomer().getId();

                            if (!map.containsKey(id)) {
                                List<UUID> storeIds = new ArrayList<>();
                                storeIds.add(order.getStore().getId());
                                map.put(id, storeIds);
                            } else {
                                List<UUID> storeIds = map.get(id);
                                UUID storeId = order.getStore().getId();

                                if (!storeIds.contains(storeId)) {
                                    storeIds.add(storeId);
                                    map.put(id, storeIds);
                                }
                            }

                            return map;
                        },
                        (mapOne, mapTwo) -> {
                            mapOne.putAll(mapTwo);
                            return mapOne;
                        }
                );

        int largestCount = Collections.max(
                storeCountByCustomerId.entrySet(),
                Comparator.comparingInt(entry -> entry.getValue().size())
        ).getValue().size();

        return storeCountByCustomerId.entrySet().stream()
                .filter(entry -> entry.getValue().size() == largestCount)
                .map(Map.Entry::getKey)
                .map(id ->
                        getStoreFactory().getCustomers().stream()
                                .filter(customer -> customer.getId().equals(id))
                                .findAny().orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public Store getStoresWithMostOrders() {
//        orders -> store -> {storeId: Pair<Store, count>}
//         return largest count
        Map<UUID, Pair<Store, Integer>> storeCountsByStoreId = getStoreFactory().getOrders().stream()
                .reduce(
                        new HashMap<>(),
                        (map, order) -> {
                            UUID storeId = order.getStore().getId();

                            if (!map.containsKey(storeId)) {
                                Pair<Store, Integer> storeToCount = new Pair<>(order.getStore(), 1);
                                map.put(storeId, storeToCount);

                                return map;
                            } else {
                                Pair<Store, Integer> storeToCount = map.get(storeId);
                                storeToCount.setRight(storeToCount.getRight() + 1);

                                map.put(storeId, storeToCount);

                                return map;
                            }
                        },
                        (mapOne, mapTwo) -> {
                            mapOne.putAll(mapTwo);

                            return mapOne;
                        });

        // Todo: turn into List<Store> with most in case there are more than one winner

        Store theStore = Collections.max(
                storeCountsByStoreId.entrySet(),
                Comparator.comparingInt(entry -> entry.getValue().getRight())
        ).getValue().getLeft();

        return theStore;
    }
}
