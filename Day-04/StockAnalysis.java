/**
 * StockAnalysis.java
 * 
 * This program demonstrates processing of stock prices using arrays and ArrayLists.
 * It calculates average price, maximum price, occurrence count of a target price,
 * and cumulative sum of prices.
 * 
 * References:
 * - Eck, D. J. (2022). Introduction to Programming Using Java, Version 9, JavaFX Edition.
 * - Oracle Corporation. (2023). The Java Tutorials: Collections.
 * - Neso Academy. (2020). ArrayLists in Java (Part 1) [Video].
 * 
 * @author Student Name
 * @version 1.0
 * @since [Date]
 */

import java.util.ArrayList;

public class StockAnalysis {

    /**
     * Calculates the average of stock prices stored in a standard array.
     *
     * @param prices array of stock prices (double)
     * @return average price as double
     */
    public static double calculateAveragePrice(double[] prices) {
        double sum = 0.0;
        for (double price : prices) {
            sum += price;
        }
        return sum / prices.length;
    }

    /**
     * Calculates the average of stock prices stored in an ArrayList.
     *
     * @param prices ArrayList of stock prices (Double)
     * @return average price as double
     */
    public static double calculateAveragePrice(ArrayList<Double> prices) {
        double sum = 0.0;
        for (double price : prices) {
            sum += price;
        }
        return sum / prices.size();
    }

    /**
     * Finds the maximum stock price in a standard array.
     *
     * @param prices array of stock prices (double)
     * @return maximum price
     */
    public static double findMaximumPrice(double[] prices) {
        double max = prices[0];
        for (double price : prices) {
            if (price > max) {
                max = price;
            }
        }
        return max;
    }

    /**
     * Finds the maximum stock price in an ArrayList.
     *
     * @param prices ArrayList of stock prices (Double)
     * @return maximum price
     */
    public static double findMaximumPrice(ArrayList<Double> prices) {
        double max = prices.get(0);
        for (double price : prices) {
            if (price > max) {
                max = price;
            }
        }
        return max;
    }

    /**
     * Counts how many times a target price appears in the array.
     *
     * @param prices array of stock prices (double)
     * @param target price to search for
     * @return number of occurrences
     */
    public static int countOccurrences(double[] prices, double target) {
        int count = 0;
        for (double price : prices) {
            // Using exact comparison because stock prices are given as exact values
            if (price == target) {
                count++;
            }
        }
        return count;
    }

    /**
     * Computes the cumulative sum of stock prices from an ArrayList.
     * For each index i, the cumulative sum is the sum of prices[0] through prices[i].
     *
     * @param prices ArrayList of stock prices (Double)
     * @return new ArrayList containing cumulative sums at each position
     */
    public static ArrayList<Double> computeCumulativeSum(ArrayList<Double> prices) {
        ArrayList<Double> cumulative = new ArrayList<>();
        double runningSum = 0.0;
        for (double price : prices) {
            runningSum += price;
            cumulative.add(runningSum);
        }
        return cumulative;
    }

    /**
     * Main method – demonstrates all functionality with sample stock data.
     */
    public static void main(String[] args) {
        // Sample stock prices for 10 days (as double values)
        double[] stockArray = {125.50, 127.75, 126.30, 128.00, 127.75,
                                129.20, 130.15, 128.50, 127.75, 131.00};

        // Create an ArrayList with the same values
        ArrayList<Double> stockList = new ArrayList<>();
        for (double price : stockArray) {
            stockList.add(price);
        }

        // 1. Average price (array and ArrayList)
        double avgArray = calculateAveragePrice(stockArray);
        double avgList = calculateAveragePrice(stockList);
        System.out.printf("Average price (array): %.2f%n", avgArray);
        System.out.printf("Average price (ArrayList): %.2f%n", avgList);

        // 2. Maximum price (array and ArrayList)
        double maxArray = findMaximumPrice(stockArray);
        double maxList = findMaximumPrice(stockList);
        System.out.printf("Maximum price (array): %.2f%n", maxArray);
        System.out.printf("Maximum price (ArrayList): %.2f%n", maxList);

        // 3. Occurrence count of a specific price (using array)
        double target = 127.75;
        int occurrences = countOccurrences(stockArray, target);
        System.out.printf("Occurrences of price %.2f in array: %d%n", target, occurrences);

        // 4. Cumulative sum (from ArrayList)
        ArrayList<Double> cumulativeSums = computeCumulativeSum(stockList);
        System.out.println("Cumulative sums (ArrayList):");
        for (int i = 0; i < cumulativeSums.size(); i++) {
            System.out.printf("Day %d: %.2f%n", i + 1, cumulativeSums.get(i));
        }
    }
}