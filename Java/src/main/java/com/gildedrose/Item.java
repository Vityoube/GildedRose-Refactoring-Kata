package com.gildedrose;

import java.util.Objects;

public class Item {

    public final Type type;

    public final int sellIn;

    public final int quality;

    public Item(Type type, int sellIn, int quality) {
        this.type = type;
        this.sellIn = sellIn;
        this.quality = quality;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Item copy() {
        return new Item(this.type, this.sellIn, this.quality);
    }

    public Item withType(Type type) {
        return new Item(type, this.sellIn, this.quality);
    }

    public Item withSellIn(int sellIn) {
        return new Item(this.type, sellIn, this.quality);
    }

    public Item withQuality(int quality) {
        return new Item(this.type, this.sellIn, quality);
    }

   @Override
   public String toString() {
        return this.type + ", " + this.sellIn + ", " + this.quality;
    }

    // equals() and hashCode() must be overwrritten for correctly comparing 2 instances of Item class

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(type, item.type) && sellIn == item.sellIn && quality == item.quality;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, sellIn, quality);
    }

    public static class Builder {
        private Type type;
        private int sellIn;
        private int quality;

        public Builder type(Type type) {
            this.type = type;
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
            return new Item(type, sellIn, quality);
        }
    }
}
