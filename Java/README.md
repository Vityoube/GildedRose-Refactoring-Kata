# Gilded Rose Refactoring Kata – Java

## Project Description

The **Gilded Rose** is a classic refactoring kata. The starting codebase contains a single `GildedRose` class with a complex, hard-to-maintain `updateQuality()` method that governs how item quality and sell-in values change each day in an inn's inventory system.

### Items and Business Rules

Every item has two attributes:
- **`sellIn`** – the number of days remaining to sell the item.
- **`quality`** – a value representing how valuable the item is (0–50).

At the end of each day, the system updates both values according to these rules:

| Item | Behaviour |
|---|---|
| Normal item | `quality` decreases by 1 per day; degrades twice as fast once `sellIn` < 0 |
| **Aged Brie** | `quality` increases by 1 per day; increases twice as fast after sell date |
| **Backstage passes** (TAFKAL80ETC concert) | `quality` increases by 1; by 2 when ≤ 10 days left; by 3 when ≤ 5 days left; drops to 0 after the concert |
| **Sulfuras, Hand of Ragnaros** | Legendary item – never has to be sold and never decreases in quality |

### Source Layout

```
src/
  main/java/com/gildedrose/
    GildedRose.java   – core update logic
    Item.java         – item model
  test/java/com/gildedrose/
    GildedRoseTest.java – JUnit 5 tests (nested by item type)
```

---

## Prerequisites

| Tool | Minimum version | Notes |
|---|---|---|
| **Java JDK** | 8 (LTS or newer recommended) | `java -version` must work on PATH |
| **Gradle** *(optional)* | — | Gradle Wrapper (`gradlew`) is bundled – no installation required |
| **Maven** *(optional)* | — | Maven Wrapper (`mvnw`) is bundled – no installation required |

> You only need **one** of Gradle or Maven. Both wrappers are included in the repository, so no separate installation is necessary.

---

## Build

### Gradle

```bash
./gradlew build          # Linux / macOS
gradlew.bat build        # Windows
```

### Maven

```bash
./mvnw package           # Linux / macOS
mvnw.cmd package         # Windows
```

Both commands compile all sources and run the full test suite. Build artefacts are placed under `build/` (Gradle) or `target/` (Maven).

---

## Run Tests

### Gradle

```bash
./gradlew test           # Linux / macOS
gradlew.bat test         # Windows
```

An HTML test report is generated at:

```
build/reports/tests/test/index.html
```

### Maven

```bash
./mvnw test              # Linux / macOS
mvnw.cmd test            # Windows
```

An HTML test report is generated at:

```
target/surefire-reports/
```

---
