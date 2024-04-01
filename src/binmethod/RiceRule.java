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
public class RiceRule extends BinFormulae {
    //Rice rule formula 
    //k = 2^3 * sqrt(n) 
    //Where K is the number of bins 
    //N is the total number of observations/measurements 

    //Constructor 
    public RiceRule(List<Double> _inputData) { //Takes in File data when the class instance is created)
        super(_inputData);
    }

    //Calculate Bins Using Rice Rule Formula
    @Override //Overrides the method declared earlier 
    public void calculateNumberOfBins() { //When called Calulates the number of bins using Rice Rule Formula.
        int N = numberOfMeasurements(inputData); //Calls the function to get the number of measurements for the input Data.
        K = (int) Math.ceil(2*Math.cbrt(N));
    }
}
