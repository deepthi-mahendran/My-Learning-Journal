# StockAnalysis - Java Program for Stock Price Analysis

This Java program demonstrates fundamental array and `ArrayList` processing techniques by performing common financial calculations on a set of stock prices. It includes methods to compute the **average price**, **maximum price**, **occurrence count** of a target price, and **cumulative sums** over a sequence of daily closing prices.

## Features

- Calculate the average price using a standard array (`double[]`) or an `ArrayList<Double>`.
- Find the maximum price from either data structure.
- Count how many times a specific price appears in the array.
- Compute the cumulative sum (running total) of prices from an `ArrayList`.
- Sample data for 10 trading days is provided in the `main` method.

## Prerequisites

- **Java Development Kit (JDK)** 8 or higher (the program uses standard Java features, no external libraries).

## Compilation and Execution

### Compile
Open a terminal/command prompt in the directory containing `StockAnalysis.java` and run:

```bash
javac StockAnalysis.java
```

### Run
```bash
java StockAnalysis
```

## Sample Output

When you run the program with the included sample stock prices, you will see output similar to:

```
Average price (array): 128.19
Average price (ArrayList): 128.19
Maximum price (array): 131.00
Maximum price (ArrayList): 131.00
Occurrences of price 127.75 in array: 3
Cumulative sums (ArrayList):
Day 1: 125.50
Day 2: 253.25
Day 3: 379.55
Day 4: 507.55
Day 5: 635.30
Day 6: 764.50
Day 7: 894.65
Day 8: 1023.15
Day 9: 1150.90
Day 10: 1281.90
```

## Program Structure

| Method | Description |
|--------|-------------|
| `calculateAveragePrice(double[] prices)` | Returns the average of an array of doubles. |
| `calculateAveragePrice(ArrayList<Double> prices)` | Returns the average of an `ArrayList` of `Double`. |
| `findMaximumPrice(double[] prices)` | Returns the largest value in the array. |
| `findMaximumPrice(ArrayList<Double> prices)` | Returns the largest value in the `ArrayList`. |
| `countOccurrences(double[] prices, double target)` | Counts exact matches of `target` in the array. |
| `computeCumulativeSum(ArrayList<Double> prices)` | Returns a new `ArrayList` where each element is the sum of all previous prices up to that index. |
| `main(String[] args)` | Demonstrates all methods using sample data and prints the results. |

## Customizing the Data

To analyze your own stock prices, modify the `stockArray` in the `main` method:

```java
double[] stockArray = {120.00, 121.50, 119.75, ...};
```

The `ArrayList` is automatically populated from the array. You can also directly create an `ArrayList<Double>` and pass it to the methods.

## Notes

- The occurrence count uses exact equality (`==`). This is suitable because the sample prices are given as exact literals. For floating‑point calculations from other sources, consider using a tolerance (e.g., `Math.abs(price - target) < 1e-9`).
- Cumulative sums are computed only for the `ArrayList` version, but the same logic works for arrays if needed.

## References

The code comments acknowledge the following sources:
- Eck, D. J. (2022). *Introduction to Programming Using Java, Version 9, JavaFX Edition*.
- Oracle Corporation. (2023). *The Java Tutorials: Collections*.
- Neso Academy. (2020). *ArrayLists in Java (Part 1)* .
---

**License**  
This program is provided for educational purposes under the terms of the GNU General Public License v3.0 or any later version.
