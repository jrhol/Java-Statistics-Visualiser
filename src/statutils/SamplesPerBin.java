/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statutils;

import java.util.Collections;
import java.util.List;

/**
 *
 * @author jrhol
 */
public class SamplesPerBin {
    //This Function calculaes the width of each bin and then the numeber of samples per bin from this
    
    
    //Variable Declaration
    List<Double> inputData; //List to store input data
    int numberOfBins; 
    double width = 0; //Width of each bin
    public int[] ArraysamplesPerBin; //Array to store the number of samples per bin in a 1d array

    //Constructor 
    public SamplesPerBin(List<Double> _inputData, int _numberOfBins) { //Taking in the input data and the number of bins (depending on the rule used)
        inputData = _inputData;
        numberOfBins = _numberOfBins;
    }
    
    //Calculating Width
    public void calculateWidth() { //Calculates the width of each bin
        width = ((Collections.max(inputData) - Collections.min(inputData)) / numberOfBins); //Finds the minimum and maximum of the data and the number of bins to be used and works out the width from this.
    }

    public void calculateSamplesPerBin() { //Calculates the number of samples in each bin, depending on the width of each bin
        calculateWidth(); //First Calculates the width of each bin
        ArraysamplesPerBin = new int[numberOfBins]; //Array of the same size as number of bins to hold the new data
        for (int i = 0; i < numberOfBins; i++) { //Loops for Number of Bins
            double CurrentBinMin = Collections.min(inputData)+(width*i); //Current Bin minimum value
            double CurrentBinMax = Collections.min(inputData)+(width *(i+1)); //Current Bin Maximum Value

            for (int j = 0; j < inputData.size(); j++) { //Loops for Number of Samples
                if (inputData.get(j)>= CurrentBinMin &&  inputData.get(j) < CurrentBinMax ) // Checkes if the current sample being iterated is within the upper and lower bounds of the current bin
                        {
                            ArraysamplesPerBin[i] = ArraysamplesPerBin[i]+1; //Adds 1 to the number of samples per bin for the respective bin
                        }
                else if (i+1 == numberOfBins) //Checks to see if on final iteration
                {
                    if (inputData.get(j)>= CurrentBinMin &&  inputData.get(j) <= CurrentBinMax ) //If on final iteration, changed to include values that are on the upper edge of the last bin.
                    {
                        ArraysamplesPerBin[i] = ArraysamplesPerBin[i]+1; //adds 1 to the number of samples per bin for the respective bin for the special case
                    }
                }
            }
        }
    }

    public double getWidth() { //Get the width of each bin
        return width;
    }

    public int[] getSamplesPerBin() { //Get the array of the number of samples for each bin
        return ArraysamplesPerBin;
    }

}
