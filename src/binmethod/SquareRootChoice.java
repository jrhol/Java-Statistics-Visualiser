/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binmethod;

import java.util.List;

/**
 *
 * @author jrhol
 */

//Extends the Abstract BinFormulae class
public class SquareRootChoice extends BinFormulae {
    //k = sqrt n
    //Where K is the number of bins 
    //N is the total number of observations/measurements 

    //constructor 
    public SquareRootChoice(List<Double> _inputData) { //Takes in File data when the class instance is created)
        super(_inputData);
    }

    //Calculate Bins Using the SquareRoot Formula
    @Override //Overrides the method declared earlier 
    public void calculateNumberOfBins() { //When called Calulates the number of bins using the SquareRoot formula.
        int N = numberOfMeasurements(inputData); //Calls the function to get the number of measurements for the input Data.
        K = (int) Math.ceil(Math.sqrt(N));
    }
}
