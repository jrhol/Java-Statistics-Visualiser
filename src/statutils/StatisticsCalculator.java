/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statutils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author jrhol
 */
public class StatisticsCalculator {

    List<Double> samples; //List to store Data Samples
    double mean;
    double max;
    double min;
    double median;
    double varience;
    double stdDev;

    //Constructor 
    public StatisticsCalculator(List<Double> _inputData) { //Takes in the input data as a list
        samples = _inputData;
    }

    //This Member Function Calculates the mean
    public double calculateMean() {
        double sum = 0;
        for (double sample : samples) { //Adds up all the samples in the input list
            sum += sample;
        }
        mean = sum / samples.size();
        return mean; //Returns the mean of the samples
    }

    //This Member Function Calculates the Varience
    public double calculateVariance() {
        double temp_mean = calculateMean(); //First Calculates the mean
        double sumOfSquaredDifferences = 0;
        for (double sample : samples) { //Calculating sum Of Squared Differences
            double difference = sample - temp_mean;
            sumOfSquaredDifferences += difference * difference;
        }
        varience = sumOfSquaredDifferences / (samples.size() - 1);
        return varience; //Returns the Calculates Varience
    }

    //This Member Function Calculates the Maximum Value
    public double calculateMax() {
        max = Collections.max(samples);
        return max; //Returns Maxmimum Value from the list
    }

    //This Member Function Calculates the Minimum Value
    public double calculateMin() {
        min = Collections.min(samples);
        return min; //Returns Minimum Value from the list
    }

    //This Member Function Calculates the Median
    public double calculateMedian() {
        List<Double> sortedSamples = samples;
        Collections.sort(sortedSamples); //Sorts the list in numerical order

        if (sortedSamples.size() % 2 == 0) { //works out the middle sample number. If odd can simply select middle number, if even must take the average between the two middle values
            double lower = sortedSamples.get(sortedSamples.size() / 2 - 1);
            double upper = sortedSamples.get(sortedSamples.size() / 2);
            median = ((lower + upper) / 2.0);
            return median;
        } else {
            median = sortedSamples.get(((sortedSamples.size() + 1) / 2) - 1);
            return median;
        }
    }

    //This Member Function Calculates the Standard Deviation
    public double calculateStandardDeviation() {
        double temp_variance = calculateVariance(); //Calculates the Varience and returns the square root of this (The Standard Deviation).
        stdDev = Math.sqrt(temp_variance);
        return stdDev;
    }

    
    
    public double getMean() { //Get the array of the number of samples for each bin
        return mean;
    }

    public double getVariencen() { //Get the array of the number of samples for each bin
        return varience;
    }
    
    public double getMax() { //Get the array of the number of samples for each bin
        return max;
    }
    
    public double getMin() { //Get the array of the number of samples for each bin
        return min;
    }
    
    public double getMedian() { //Get the array of the number of samples for each bin
        return median;
    }
    
    public double getStandardDeviation() { //Get the array of the number of samples for each bin
        return stdDev;
    }
}
