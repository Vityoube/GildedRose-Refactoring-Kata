package com.gildedrose;

import java.util.Objects;

public class Item {

    public String name;

    public int sellIn;

    public int quality;

    public Item(String name, int sellIn, int quality) {
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Item copy() {
        return new Item(this.name, this.sellIn, this.quality);
    }

    public Item withName(String name) {
        return new Item(name, this.sellIn, this.quality);
    }

    public Item withSellIn(int sellIn) {
        return new Item(this.name, sellIn, this.quality);
    }

    public Item withQuality(int quality) {
        return new Item(this.name, this.sellIn, quality);
    }

   @Override
   public String toString() {
        return this.name + ", " + this.sellIn + ", " + this.quality;
    }

    // equals() and hashCode() must be overwrritten for correctly comparing 2 instances of Item class

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(name, item.name) && sellIn == item.sellIn && quality == item.quality;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, sellIn, quality);
    }

    public static class Builder {
        private String name;
        private int sellIn;
        private int quality;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder sellIn(int sellIn) {
            this.sellIn = sellIn;
            return this;
        }

        public Builder quality(int quality) {
            this.quality = quality;
            return this;
        }

        public Item build() {
            return new Item(name, sellIn, quality);
        }
    }
}
