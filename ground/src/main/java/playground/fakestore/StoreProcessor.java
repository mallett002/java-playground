package playground.fakestore;

import lombok.Data;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

public class StoreProcessor {

    @Getter
    StoreFactory storeFactory;

    public StoreProcessor() {
        storeFactory = new StoreFactory();
    }

    public Store storeWithHighestInventory() {
        List<Item> items = storeFactory.getItems();
        TreeMap<Integer, String> inventoryCountMap = new TreeMap<>();

        storeFactory.getStores()
                .forEach((store) -> {
                    Integer sumA = 0;
                    Integer sumB = 0;

                    for (Item item : items) {
                        for (UUID storeId : item.getLocations()) {
                            if (storeId.equals(store.getId())) {
                                sumA++;
                            }
                            if (storeId.equals(store.getId())) {
                                sumB++;
                            }
                        }
                    }
                    inventoryCountMap.put(sumA, store.getStoreName());
                    inventoryCountMap.put(sumB, store.getStoreName());
                });


//        List<UUID> it =
        TreeMap<String, Integer> it = storeFactory.getItems().stream()
                    .map(Item::getLocations)
                    .flatMap(List::stream)
                    .map((id) -> {
                        Store foundStore = storeFactory.getStores().stream()
                                .filter(store -> store.getId().equals(id)).findAny().orElse(null);

                        return foundStore != null ? foundStore.getStoreName() : null;
                    })
                    .filter(Objects::nonNull)
                    .reduce(
                            new TreeMap<>(),
                            (partial, storeName) -> {
                                if (partial.containsKey(storeName)) {
                                    Integer curr = partial.get(storeName);
                                    partial.put(storeName, curr + 1);
                                } else {
                                    partial.put(storeName, 1);
                                }

                                return partial;
                            },
                            (first, second) -> {
                                first.putAll(second);

                                return first;
                            }
                    );

        int max = it.values().stream().max(Integer::compare).get();
        String maxKey = it.keySet().stream().min((a, b) -> it.get(b).compareTo(it.get(a)))
                .orElse(null);

//                        .collect(Collectors.toMap(Animal::getId, Function.identity()));


        return null;
    }
}
