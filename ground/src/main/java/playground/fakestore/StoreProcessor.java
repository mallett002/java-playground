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
}
