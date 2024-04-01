/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aircoachroughness;

import binmethod.RiceRule;
import binmethod.SquareRootChoice;
import binmethod.SturgesFormula;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import mathutils.dataFitting;
import statutils.HistogramNormalisation;
import statutils.SamplesPerBin;
import static mathutils.dataFitting.calculateDataFitting;
import static mathutils.dataFitting.getPDFParam;

/**
 *
 * @author jrhol
 */
public class Unit_test_mathutils {

    public static void main(String[] args) {

        // List<Double> exampleData = Arrays.asList(0.000060, -0.000047, -0.000108, -0.000189);
        List<Double> exampleData = Arrays.asList(1., 2., 3., 4., 5., 6., 7., 8., 9., 10., 11.);

        //**********************************************//
        //Testing With RiceRule
        System.out.printf("\nRice Rule");
        //Create an instance of Rice Rule
        RiceRule riceRuleInstance = new RiceRule(exampleData);
        //Calculates the Number of bins using Rice's Formula
        riceRuleInstance.calculateNumberOfBins();
        System.out.printf(" :Number of Bins %d ", riceRuleInstance.getNumberOfBins());

        //Creates an instance of samples per bin class which uses Rice Rule for the number of Bins
        SamplesPerBin samplesPerBinInstance = new SamplesPerBin(exampleData, riceRuleInstance.getNumberOfBins());
        //Calculates the Samples per Bin
        samplesPerBinInstance.calculateSamplesPerBin();

        //Creates an instance of Histogram normalisation (For Rice Rule)
        HistogramNormalisation histogramNormalisationInstance = new HistogramNormalisation(riceRuleInstance.getNumberOfBins(),
                samplesPerBinInstance.getWidth(),
                samplesPerBinInstance.getSamplesPerBin());
        //Performs the Normalisation and stores the output inside a double array
        double[] normalisedHistogram = histogramNormalisationInstance.normaliseHistogram();

        //Prints the Samples Per Bin Array (Rice Rule)
        System.out.printf("\nSamples Per Bin ");
        System.out.printf(Arrays.toString(samplesPerBinInstance.getSamplesPerBin()));

        //Prints the Normalised Histogram
        System.out.printf("\nNormalised Histogram Y Values ");
        System.out.printf(Arrays.toString(histogramNormalisationInstance.normaliseHistogram()));

        //Perfoms the Data Fitting
        double[] PDFArray = calculateDataFitting(exampleData, riceRuleInstance.getNumberOfBins(), Collections.min(exampleData), samplesPerBinInstance.getWidth(), normalisedHistogram);
        System.out.printf("\nProbability Density Function Y Values");
        System.out.printf(Arrays.toString(PDFArray));

        //Gets the Output array containing the Value of the Y coords of the PDF function
        double[] DataFittingArray = getPDFParam(exampleData, riceRuleInstance.getNumberOfBins(), Collections.min(exampleData), samplesPerBinInstance.getWidth(), normalisedHistogram);
        //Prints out the PDF parameters
        System.out.println("\nNormalisation Factor " + Double.toString(DataFittingArray[0])); //Normalisation Factor of the fitted curve (if fitted)
        System.out.println("Mean " + Double.toString(DataFittingArray[1] * 1000000)); //Mean of of the fittec curve
        System.out.println("Sigma " + Double.toString(DataFittingArray[2] * 1000000)); //Sigma of of the input data
        //**********************************************//

        //**********************************************//
        //Testing With Sturges Formula
        System.out.printf("\nSturges Rule");
        //Create an instance of Sturges Formula
        SturgesFormula SturgesRuleInstance = new SturgesFormula(exampleData);
        //Calculates the Number of bins using Sturges Formula
        SturgesRuleInstance.calculateNumberOfBins();
        System.out.printf(" :Number of Bins %d ", SturgesRuleInstance.getNumberOfBins());

        //Creates an instance of samples per bin class which uses Sturges Formula for the number of Bins
        SamplesPerBin samplesPerBinInstance2 = new SamplesPerBin(exampleData, SturgesRuleInstance.getNumberOfBins());
        //Calculates the Samples per Bin
        samplesPerBinInstance2.calculateSamplesPerBin();

        //Creates an instance of Histogram normalisation (For Sturges Formula)
        HistogramNormalisation histogramNormalisationInstance2 = new HistogramNormalisation(SturgesRuleInstance.getNumberOfBins(),
                samplesPerBinInstance2.getWidth(),
                samplesPerBinInstance2.getSamplesPerBin());
        //Performs the Normalisation and stores the output inside a double array
        double[] normalisedHistogram2 = histogramNormalisationInstance2.normaliseHistogram();

        //Prints the Samples Per Bin Array (Sturges Formula)
        System.out.printf("\nSamples Per Bin ");
        System.out.printf(Arrays.toString(samplesPerBinInstance2.getSamplesPerBin()));

        //Prints the Normalised Histogram
        System.out.printf("\nNormalised Histogram Y Values ");
        System.out.printf(Arrays.toString(histogramNormalisationInstance2.normaliseHistogram()));

        //Perfoms the Data Fitting
        double[] PDFArray2 = calculateDataFitting(exampleData, SturgesRuleInstance.getNumberOfBins(), Collections.min(exampleData), samplesPerBinInstance2.getWidth(), normalisedHistogram2);
        System.out.printf("\nProbability Density Function Y Values");
        System.out.printf(Arrays.toString(PDFArray2));

        //Gets the Output array containing the Value of the Y coords of the PDF function
        double[] DataFittingArray2 = dataFitting.getPDFParam(exampleData, riceRuleInstance.getNumberOfBins(), Collections.min(exampleData), samplesPerBinInstance.getWidth(), normalisedHistogram);
        //Prints out the PDF parameters
        System.out.println("\nNormalisation Factor " + Double.toString(DataFittingArray2[0])); //Normalisation Factor of the fitted curve (if fitted)
        System.out.println("Mean " + Double.toString(DataFittingArray2[1] * 1000000)); //Mean of of the fittec curve
        System.out.println("Sigma " + Double.toString(DataFittingArray2[2] * 1000000)); //Sigma of of the input data
        //**********************************************//

        //**********************************************//
        //Testing With SquareRoot Choice
        System.out.printf("\nSquareRoot Rule");
        //Create an instance of SquareRoot Rule
        SquareRootChoice SquareRootInstance = new SquareRootChoice(exampleData);
        //Calculates the Number of bins using SquareRoot Formula
        SquareRootInstance.calculateNumberOfBins();
        System.out.printf(" :Number of Bins %d ", SquareRootInstance.getNumberOfBins());

        //Creates an instance of samples per bin class which uses SquareRoot Rule for the number of Bins
        SamplesPerBin samplesPerBinInstance3 = new SamplesPerBin(exampleData, SquareRootInstance.getNumberOfBins());
        //Calculates the Samples per Bin
        samplesPerBinInstance3.calculateSamplesPerBin();

        //Creates an instance of Histogram normalisation (For SquareRoot Rule)
        HistogramNormalisation histogramNormalisationInstance3 = new HistogramNormalisation(SquareRootInstance.getNumberOfBins(),
                samplesPerBinInstance3.getWidth(),
                samplesPerBinInstance3.getSamplesPerBin());
        //Performs the Normalisation and stores the output inside a double array
        double[] normalisedHistogram3 = histogramNormalisationInstance3.normaliseHistogram();

        //Prints the Samples Per Bin Array (SquareRoot Choice)
        System.out.printf("\nSamples Per Bin ");
        System.out.printf(Arrays.toString(samplesPerBinInstance3.getSamplesPerBin()));

        //Prints the Normalised Histogram
        System.out.printf("\nNormalised Histogram Y Values ");
        System.out.printf(Arrays.toString(histogramNormalisationInstance3.normaliseHistogram()));

        //Perfoms the Data Fitting
        double[] PDFArray3 = calculateDataFitting(exampleData, SquareRootInstance.getNumberOfBins(), Collections.min(exampleData), samplesPerBinInstance3.getWidth(), normalisedHistogram3);
        System.out.printf("\nProbability Density Function Y Values");
        System.out.printf(Arrays.toString(PDFArray3));

        //Gets the Output array containing the Value of the Y coords of the PDF function
        double[] DataFittingArray3 = dataFitting.getPDFParam(exampleData, SquareRootInstance.getNumberOfBins(), Collections.min(exampleData), samplesPerBinInstance3.getWidth(), normalisedHistogram3);
        //Prints out the PDF parameters
        System.out.println("\nNormalisation Factor " + Double.toString(DataFittingArray3[0])); //Normalisation Factor of the fitted curve (if fitted)
        System.out.println("Mean " + Double.toString(DataFittingArray3[1] * 1000000)); //Mean of of the fittec curve
        System.out.println("Sigma " + Double.toString(DataFittingArray3[2] * 1000000)); //Sigma of of the input data
        //**********************************************//

    }
}
