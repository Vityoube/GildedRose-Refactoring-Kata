package com.gildedrose;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    private Item updateItem(String name, int sellIn, int quality) {
        Item[] items = new Item[] { new Item(name, sellIn, quality) };
        new GildedRose(items).updateQuality();
        return items[0];
    }

    // ── Normal items ────────────────────────────────────────────────

    @Nested
    class NormalItem {

        @Test
        void qualityDecreasesByOneBeforeSellDate() {
            Item result = updateItem("Normal Item", 10, 20);
            assertEquals(9, result.sellIn);
            assertEquals(19, result.quality);
        }

        @Test
        void qualityDecreasesTwiceAsFastAfterSellDate() {
            Item result = updateItem("Normal Item", 0, 20);
            assertEquals(-1, result.sellIn);
            assertEquals(18, result.quality);
        }

        @Test
        void qualityNeverGoesNegative() {
            Item result = updateItem("Normal Item", 10, 0);
            assertEquals(9, result.sellIn);
            assertEquals(0, result.quality);
        }

        @Test
        void qualityNeverGoesNegativeWhenExpired() {
            Item result = updateItem("Normal Item", 0, 0);
            assertEquals(-1, result.sellIn);
            assertEquals(0, result.quality);
        }

        @Test
        void qualityDoesNotDropBelowZeroWhenExpiredWithQualityOne() {
            Item result = updateItem("Normal Item", 0, 1);
            assertEquals(-1, result.sellIn);
            assertEquals(0, result.quality);
        }

        @Test
        void alreadyExpiredItemContinuesToDegrade() {
            Item result = updateItem("Normal Item", -5, 10);
            assertEquals(-6, result.sellIn);
            assertEquals(8, result.quality);
        }
    }

    // ── Aged Brie ───────────────────────────────────────────────────

    @Nested
    class AgedBrie {

        @Test
        void qualityIncreasesByOneBeforeSellDate() {
            Item result = updateItem("Aged Brie", 10, 20);
            assertEquals(9, result.sellIn);
            assertEquals(21, result.quality);
        }

        @Test
        void qualityIncreasesByTwoAfterSellDate() {
            Item result = updateItem("Aged Brie", 0, 20);
            assertEquals(-1, result.sellIn);
            assertEquals(22, result.quality);
        }

        @Test
        void qualityNeverExceedsFifty() {
            Item result = updateItem("Aged Brie", 10, 50);
            assertEquals(9, result.sellIn);
            assertEquals(50, result.quality);
        }

        @Test
        void qualityNeverExceedsFiftyWhenExpired() {
            Item result = updateItem("Aged Brie", 0, 50);
            assertEquals(-1, result.sellIn);
            assertEquals(50, result.quality);
        }

        @Test
        void qualityCapsAtFiftyWhenExpiredAt49() {
            Item result = updateItem("Aged Brie", 0, 49);
            assertEquals(-1, result.sellIn);
            assertEquals(50, result.quality);
        }

        @Test
        void qualityIncreasesFromZero() {
            Item result = updateItem("Aged Brie", 5, 0);
            assertEquals(4, result.sellIn);
            assertEquals(1, result.quality);
        }
    }

    // ── Sulfuras ────────────────────────────────────────────────────

    @Nested
    class Sulfuras {

        @Test
        void neverChangesBeforeSellDate() {
            Item result = updateItem("Sulfuras, Hand of Ragnaros", 10, 80);
            assertEquals(10, result.sellIn);
            assertEquals(80, result.quality);
        }

        @Test
        void neverChangesOnSellDate() {
            Item result = updateItem("Sulfuras, Hand of Ragnaros", 0, 80);
            assertEquals(0, result.sellIn);
            assertEquals(80, result.quality);
        }

        @Test
        void neverChangesAfterSellDate() {
            Item result = updateItem("Sulfuras, Hand of Ragnaros", -1, 80);
            assertEquals(-1, result.sellIn);
            assertEquals(80, result.quality);
        }
    }

    // ── Backstage passes ────────────────────────────────────────────

    @Nested
    class BackstagePasses {

        private static final String NAME = "Backstage passes to a TAFKAL80ETC concert";

        @Test
        void qualityIncreasesByOneWhenMoreThanTenDays() {
            Item result = updateItem(NAME, 15, 20);
            assertEquals(14, result.sellIn);
            assertEquals(21, result.quality);
        }

        @Test
        void qualityIncreasesByOneAtExactlyElevenDays() {
            Item result = updateItem(NAME, 11, 20);
            assertEquals(10, result.sellIn);
            assertEquals(21, result.quality);
        }

        @Test
        void qualityIncreasesByTwoAtTenDays() {
            Item result = updateItem(NAME, 10, 20);
            assertEquals(9, result.sellIn);
            assertEquals(22, result.quality);
        }

        @Test
        void qualityIncreasesByTwoAtSixDays() {
            Item result = updateItem(NAME, 6, 20);
            assertEquals(5, result.sellIn);
            assertEquals(22, result.quality);
        }

        @Test
        void qualityIncreasesByThreeAtFiveDays() {
            Item result = updateItem(NAME, 5, 20);
            assertEquals(4, result.sellIn);
            assertEquals(23, result.quality);
        }

        @Test
        void qualityIncreasesByThreeAtOneDay() {
            Item result = updateItem(NAME, 1, 20);
            assertEquals(0, result.sellIn);
            assertEquals(23, result.quality);
        }

        @Test
        void qualityDropsToZeroAfterConcert() {
            Item result = updateItem(NAME, 0, 20);
            assertEquals(-1, result.sellIn);
            assertEquals(0, result.quality);
        }

        @Test
        void qualityDropsToZeroWhenAlreadyPastConcert() {
            Item result = updateItem(NAME, -1, 20);
            assertEquals(-2, result.sellIn);
            assertEquals(0, result.quality);
        }

        @Test
        void qualityCapsAtFiftyWithTenDaysLeft() {
            Item result = updateItem(NAME, 10, 49);
            assertEquals(9, result.sellIn);
            assertEquals(50, result.quality);
        }

        @Test
        void qualityCapsAtFiftyWithFiveDaysLeft() {
            Item result = updateItem(NAME, 5, 49);
            assertEquals(4, result.sellIn);
            assertEquals(50, result.quality);
        }

        @Test
        void qualityCapsAtFiftyWithFiveDaysLeftAt48() {
            Item result = updateItem(NAME, 5, 48);
            assertEquals(4, result.sellIn);
            assertEquals(50, result.quality);
        }

        @Test
        void qualityDoesNotExceedFiftyWithMoreThanTenDays() {
            Item result = updateItem(NAME, 15, 50);
            assertEquals(14, result.sellIn);
            assertEquals(50, result.quality);
        }
    }

    // ── Multiple items ──────────────────────────────────────────────

    @Nested
    class MultipleItems {

        @Test
        void updatesAllItemsInArray() {
            Item[] items = new Item[] {
                new Item("Normal Item", 5, 10),
                new Item("Aged Brie", 3, 6)
            };
            new GildedRose(items).updateQuality();

            assertEquals(new Item("Normal Item", 4, 9), items[0]);
            assertEquals(new Item("Aged Brie", 2, 7), items[1]);
        }

        @Test
        void handlesEmptyItemArray() {
            Item[] items = new Item[] {};
            new GildedRose(items).updateQuality();
            assertEquals(0, items.length);
        }
    }
}
