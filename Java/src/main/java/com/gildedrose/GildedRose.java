package com.gildedrose;

import java.util.List;
import java.util.stream.Collectors;

class GildedRose {

    private GildedRose() {}

    /** Returns a new list of updated items, leaving the originals unchanged. */
    public static List<Item> transformItems(final List<Item> items) {
        return items.stream()
                .map(GildedRose::transformItem)
                .collect(Collectors.toList());
    }

    private static Item transformItem(Item item) {
        switch (item.type) {
            case SULFURAS:
                return item;

            case BRIE:
                return updateBrie(item);

            case BACKSTAGE:
                return updateBackstage(item);

            default: // NORMAL
                return updateNormal(item);
        }
    }

    private static Item updateNormal(Item item) {
        int newSellIn = item.sellIn - 1;
        int degradation = item.sellIn <= 0 ? 2 : 1;
        int newQuality = Math.max(0, item.quality - degradation);
        return item.withSellIn(newSellIn).withQuality(newQuality);
    }

    private static Item updateBrie(Item item) {
        int newSellIn = item.sellIn - 1;
        int increase = item.sellIn <= 0 ? 2 : 1;
        int newQuality = Math.min(50, item.quality + increase);
        return item.withSellIn(newSellIn).withQuality(newQuality);
    }

    private static Item updateBackstage(Item item) {
        int newSellIn = item.sellIn - 1;
        if (item.sellIn <= 0) {
            return item.withSellIn(newSellIn).withQuality(0);
        }
        int increase = item.sellIn <= 5 ? 3 : item.sellIn <= 10 ? 2 : 1;
        int newQuality = Math.min(50, item.quality + increase);
        return item.withSellIn(newSellIn).withQuality(newQuality);
    }
}
