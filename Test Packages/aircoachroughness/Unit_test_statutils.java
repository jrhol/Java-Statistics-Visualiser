/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aircoachroughness;

import binmethod.*;
import java.util.Arrays;
import java.util.List;
import statutils.HistogramNormalisation;
import statutils.SamplesPerBin;
import statutils.StatisticsCalculator;

/**
 *
 * @author jrhol
 */
public class Unit_test_statutils {

    public static void main(String[] args) {

        //List<Double> exampleData = Arrays.asList(0.000060,-0.000047 ,-0.000108 ,-0.000189);
        List<Double> exampleData = Arrays.asList(1., 2., 3., 4., 5., 6., 7., 8., 9., 10., 11.);
        
        //**********************************************//
        //Creatingh an instance of Statistics Calculator 
        StatisticsCalculator statisticsCalculatorInstance = new StatisticsCalculator(exampleData);
        
        //Calling the various functions within the statistics calculator class
        System.out.printf("STATISTICAL ANALYSIS \n");
        System.out.printf("Mean: %s \n", statisticsCalculatorInstance.calculateMean());
        System.out.printf("Variance: %s \n", statisticsCalculatorInstance.calculateVariance());
        System.out.printf("Max: %s \n", statisticsCalculatorInstance.calculateMax());
        System.out.printf("Min: %s \n", statisticsCalculatorInstance.calculateMin());
        System.out.printf("Median: %s \n", statisticsCalculatorInstance.calculateMedian());
        System.out.printf("Standard Deviation: %s \n", statisticsCalculatorInstance.calculateStandardDeviation());
        // Prints the output
        //**********************************************//        
        
        //**********************************************//
        //Testing with Sturges Formula
        System.out.printf("\nSturges Formula ");
        //Creates and instance of Sturges formula
        SturgesFormula SturgesInstance = new SturgesFormula(exampleData);
        //Calculates the Number of bins using sturges Formula
        SturgesInstance.calculateNumberOfBins();

        //Creates an instance of samples per bin class which uses Sturges Rule for the number of Bins
        SamplesPerBin samplesPerBinInstance = new SamplesPerBin(exampleData, SturgesInstance.getNumberOfBins());
        System.out.printf(" :Number of Bins %d ", SturgesInstance.getNumberOfBins());
        //Calculates the Samples per Bin
        samplesPerBinInstance.calculateSamplesPerBin();
        
        //Prints the Samples Per Bin Array (Sturges Rule)
        System.out.printf("\nSamples Per Bin ");
        System.out.printf(Arrays.toString(samplesPerBinInstance.getSamplesPerBin()));

        //Creates an instance of Histogram normalisation (For sturges Rule)
        HistogramNormalisation histogramNormalisationInstance = new HistogramNormalisation(SturgesInstance.getNumberOfBins(),
                samplesPerBinInstance.getWidth(),
                samplesPerBinInstance.getSamplesPerBin());
        //Performs the Normalisation
        histogramNormalisationInstance.normaliseHistogram();
        //Prints the Normalised Histogram based on Sturges Rule
        System.out.printf("\nNormalised Histogram Y Values ");
        System.out.printf(Arrays.toString(histogramNormalisationInstance.normaliseHistogram()));
        //**********************************************//
        
        
        //**********************************************//
        //Testing with Rice's Formula
        System.out.printf("\n\nRice Formula ");
        //Creates and instance of Rice's formula
        RiceRule RiceRuleInstance = new RiceRule(exampleData);
        //Calculates the Number of bins using Rice's Formula
        RiceRuleInstance.calculateNumberOfBins();

        //Creates an instance of samples per bin class which uses Rice Rule for the number of Bins
        SamplesPerBin samplesPerBinInstance2 = new SamplesPerBin(exampleData, RiceRuleInstance.getNumberOfBins());
        System.out.printf(" :Number of Bins %d ",RiceRuleInstance.getNumberOfBins());
        //Calculates the Samples per Bin
        samplesPerBinInstance2.calculateSamplesPerBin();
        
        //Prints the Samples Per Bin Array (Rice Rule)
        System.out.printf("\nSamples Per Bin ");
        System.out.printf(Arrays.toString(samplesPerBinInstance2.getSamplesPerBin()));

        //Creates an instance of Histogram normalisation (For Rice Rule)
        HistogramNormalisation histogramNormalisationInstance2 = new HistogramNormalisation(RiceRuleInstance.getNumberOfBins(),
                samplesPerBinInstance2.getWidth(),
                samplesPerBinInstance2.getSamplesPerBin());
        //Performs the Normalisation
        histogramNormalisationInstance2.normaliseHistogram();
        //Prints the Normalised Histogram based on Rice Rule
        System.out.printf("\nNormalised Histogram Y Values ");
        System.out.printf(Arrays.toString(histogramNormalisationInstance2.normaliseHistogram()));
        //**********************************************//
        
        //**********************************************//
        //Testing with Square Root Formula
        System.out.printf("\n\nSquare Root Formula ");
        //Creates and instance of Square Root formula
        SquareRootChoice SquareRootChoiceInstance = new SquareRootChoice(exampleData);
        //Calculates the Number of bins using Square Root Formula
        SquareRootChoiceInstance.calculateNumberOfBins();

        //Creates an instance of samples per bin class which uses Square Root Rule for the number of Bins
        SamplesPerBin samplesPerBinInstance3 = new SamplesPerBin(exampleData, SquareRootChoiceInstance.getNumberOfBins());
        System.out.printf(" :Number of Bins %d ",SquareRootChoiceInstance.getNumberOfBins());
        //Calculates the Samples per Bin
        samplesPerBinInstance3.calculateSamplesPerBin();
        
        //Prints the Samples Per Bin Array (Square Root Rule)
        System.out.printf("\nSamples Per Bin ");
        System.out.printf(Arrays.toString(samplesPerBinInstance3.getSamplesPerBin()));

        //Creates an instance of Histogram normalisation (For Square Root Rule)
        HistogramNormalisation histogramNormalisationInstance3 = new HistogramNormalisation(SquareRootChoiceInstance.getNumberOfBins(),
                samplesPerBinInstance3.getWidth(),
                samplesPerBinInstance3.getSamplesPerBin());
        //Performs the Normalisation
        histogramNormalisationInstance3.normaliseHistogram();
        //Prints the Normalised Histogram based on Square Root Rule
        System.out.printf("\nNormalised Histogram Y Values ");
        System.out.printf(Arrays.toString(histogramNormalisationInstance3.normaliseHistogram()));
        //**********************************************//
    }
}
