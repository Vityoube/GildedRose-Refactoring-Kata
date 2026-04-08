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
}
