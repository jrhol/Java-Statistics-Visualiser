/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statutils;

/**
 *
 * @author jrhol
 */
public class HistogramNormalisation {
    //Normalises the Histogram

    int numberOfBins;
    double width = 0;
    double sumAllFrequencys = 0;
    int[] samplesPerBin;
    public double[] normalisedSamplesPerBin;

    double sumOfFreq = 0;

    //Constructor  //Takes in the number of bins, the width of the bins, and the array of samples per bin.
    public HistogramNormalisation(int _numberOfBins, 
                                  double _Binwidth, 
                                  int[] _samplesPerBin) {
        numberOfBins = _numberOfBins;
        width = _Binwidth;
        this.samplesPerBin = _samplesPerBin;
    }

    //Normalises the Histogram
    public double[] normaliseHistogram() {

        for (int i = 0; i < numberOfBins; i++) { // Loops for the number of bins
            sumOfFreq += samplesPerBin[i] * width; //Summing the freq*width for the Whole dataset
        }

        //Creating an array to store the normalised data that is the same size as the number of bins.
        normalisedSamplesPerBin = new double[numberOfBins]; 
        
        for (int i = 0; i < numberOfBins; i++) { //Loops for the number of bins
            normalisedSamplesPerBin[i] = (samplesPerBin[i]) / (sumOfFreq); //Normalises each unnormalised frequency in the data set
        }

        return normalisedSamplesPerBin; //Returns the normalised data as a 1d array
    }

    public double getSumOfFreq() { //Get the width of each bin
        return sumOfFreq;
    }

    public double[] getNormalisedSamplesPerBin() { //Get the width of each bin
        return normalisedSamplesPerBin;
    }

}
