package com.gildedrose;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    private Item updateItem(Type type, int sellIn, int quality) {
        return GildedRose.transformItems(Collections.singletonList(new Item(type, sellIn, quality))).get(0);
    }

    // ── Normal items ────────────────────────────────────────────────

    @Nested
    class NormalItem {

        @Test
        void qualityDecreasesByOneBeforeSellDate() {
            Item result = updateItem(Type.NORMAL, 10, 20);
            assertEquals(9, result.sellIn);
            assertEquals(19, result.quality);
        }

        @Test
        void qualityDecreasesTwiceAsFastAfterSellDate() {
            Item result = updateItem(Type.NORMAL, 0, 20);
            assertEquals(-1, result.sellIn);
            assertEquals(18, result.quality);
        }

        @Test
        void qualityNeverGoesNegative() {
            Item result = updateItem(Type.NORMAL, 10, 0);
            assertEquals(9, result.sellIn);
            assertEquals(0, result.quality);
        }

        @Test
        void qualityNeverGoesNegativeWhenExpired() {
            Item result = updateItem(Type.NORMAL, 0, 0);
            assertEquals(-1, result.sellIn);
            assertEquals(0, result.quality);
        }

        @Test
        void qualityDoesNotDropBelowZeroWhenExpiredWithQualityOne() {
            Item result = updateItem(Type.NORMAL, 0, 1);
            assertEquals(-1, result.sellIn);
            assertEquals(0, result.quality);
        }

        @Test
        void alreadyExpiredItemContinuesToDegrade() {
            Item result = updateItem(Type.NORMAL, -5, 10);
            assertEquals(-6, result.sellIn);
            assertEquals(8, result.quality);
        }
    }

    // ── Aged Brie ───────────────────────────────────────────────────

    @Nested
    class AgedBrie {

        @Test
        void qualityIncreasesByOneBeforeSellDate() {
            Item result = updateItem(Type.BRIE, 10, 20);
            assertEquals(9, result.sellIn);
            assertEquals(21, result.quality);
        }

        @Test
        void qualityIncreasesByTwoAfterSellDate() {
            Item result = updateItem(Type.BRIE, 0, 20);
            assertEquals(-1, result.sellIn);
            assertEquals(22, result.quality);
        }

        @Test
        void qualityNeverExceedsFifty() {
            Item result = updateItem(Type.BRIE, 10, 50);
            assertEquals(9, result.sellIn);
            assertEquals(50, result.quality);
        }

        @Test
        void qualityNeverExceedsFiftyWhenExpired() {
            Item result = updateItem(Type.BRIE, 0, 50);
            assertEquals(-1, result.sellIn);
            assertEquals(50, result.quality);
        }

        @Test
        void qualityCapsAtFiftyWhenExpiredAt49() {
            Item result = updateItem(Type.BRIE, 0, 49);
            assertEquals(-1, result.sellIn);
            assertEquals(50, result.quality);
        }

        @Test
        void qualityIncreasesFromZero() {
            Item result = updateItem(Type.BRIE, 5, 0);
            assertEquals(4, result.sellIn);
            assertEquals(1, result.quality);
        }
    }

    // ── Sulfuras ────────────────────────────────────────────────────

    @Nested
    class Sulfuras {

        @Test
        void neverChangesBeforeSellDate() {
            Item result = updateItem(Type.SULFURAS, 10, 80);
            assertEquals(10, result.sellIn);
            assertEquals(80, result.quality);
        }

        @Test
        void neverChangesOnSellDate() {
            Item result = updateItem(Type.SULFURAS, 0, 80);
            assertEquals(0, result.sellIn);
            assertEquals(80, result.quality);
        }

        @Test
        void neverChangesAfterSellDate() {
            Item result = updateItem(Type.SULFURAS, -1, 80);
            assertEquals(-1, result.sellIn);
            assertEquals(80, result.quality);
        }
    }

    // ── Backstage passes ────────────────────────────────────────────

    @Nested
    class BackstagePasses {

        @Test
        void qualityIncreasesByOneWhenMoreThanTenDays() {
            Item result = updateItem(Type.BACKSTAGE, 15, 20);
            assertEquals(14, result.sellIn);
            assertEquals(21, result.quality);
        }

        @Test
        void qualityIncreasesByOneAtExactlyElevenDays() {
            Item result = updateItem(Type.BACKSTAGE, 11, 20);
            assertEquals(10, result.sellIn);
            assertEquals(21, result.quality);
        }

        @Test
        void qualityIncreasesByTwoAtTenDays() {
            Item result = updateItem(Type.BACKSTAGE, 10, 20);
            assertEquals(9, result.sellIn);
            assertEquals(22, result.quality);
        }

        @Test
        void qualityIncreasesByTwoAtSixDays() {
            Item result = updateItem(Type.BACKSTAGE, 6, 20);
            assertEquals(5, result.sellIn);
            assertEquals(22, result.quality);
        }

        @Test
        void qualityIncreasesByThreeAtFiveDays() {
            Item result = updateItem(Type.BACKSTAGE, 5, 20);
            assertEquals(4, result.sellIn);
            assertEquals(23, result.quality);
        }

        @Test
        void qualityIncreasesByThreeAtOneDay() {
            Item result = updateItem(Type.BACKSTAGE, 1, 20);
            assertEquals(0, result.sellIn);
            assertEquals(23, result.quality);
        }

        @Test
        void qualityDropsToZeroAfterConcert() {
            Item result = updateItem(Type.BACKSTAGE, 0, 20);
            assertEquals(-1, result.sellIn);
            assertEquals(0, result.quality);
        }

        @Test
        void qualityDropsToZeroWhenAlreadyPastConcert() {
            Item result = updateItem(Type.BACKSTAGE, -1, 20);
            assertEquals(-2, result.sellIn);
            assertEquals(0, result.quality);
        }

        @Test
        void qualityCapsAtFiftyWithTenDaysLeft() {
            Item result = updateItem(Type.BACKSTAGE, 10, 49);
            assertEquals(9, result.sellIn);
            assertEquals(50, result.quality);
        }

        @Test
        void qualityCapsAtFiftyWithFiveDaysLeft() {
            Item result = updateItem(Type.BACKSTAGE, 5, 49);
            assertEquals(4, result.sellIn);
            assertEquals(50, result.quality);
        }

        @Test
        void qualityCapsAtFiftyWithFiveDaysLeftAt48() {
            Item result = updateItem(Type.BACKSTAGE, 5, 48);
            assertEquals(4, result.sellIn);
            assertEquals(50, result.quality);
        }

        @Test
        void qualityDoesNotExceedFiftyWithMoreThanTenDays() {
            Item result = updateItem(Type.BACKSTAGE, 15, 50);
            assertEquals(14, result.sellIn);
            assertEquals(50, result.quality);
        }
    }

    // ── Multiple items ──────────────────────────────────────────────

    @Nested
    class MultipleItems {

        @Test
        void updatesAllItemsInList() {
            List<Item> items = List.of(
                new Item(Type.NORMAL, 5, 10),
                new Item(Type.BRIE, 3, 6)
            );
            List<Item> result = GildedRose.transformItems(items);

            assertEquals(new Item(Type.NORMAL, 4, 9), result.get(0));
            assertEquals(new Item(Type.BRIE, 2, 7), result.get(1));
        }

        @Test
        void handlesEmptyList() {
            List<Item> result = GildedRose.transformItems(Collections.emptyList());
            assertEquals(0, result.size());
        }
    }
}
