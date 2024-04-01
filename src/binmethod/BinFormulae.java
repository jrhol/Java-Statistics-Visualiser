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

//Abstract Class to represent Bin Rules
public abstract class BinFormulae {
    
    final public List<Double> inputData; //Once Data passed to a instance will not change (Class will be re instantiated in the event of new data)
    public int K; //Value to Hold the number of bins (needed in each extension of the class)
    
    //Constructor 
    public BinFormulae(List<Double> _inputData){ //Takes in File data when the class instance is created)
        inputData = _inputData;
    }
    
    //Static Method for all classes extended from BinFormulae
    //Will be needed in all classes
    public static int numberOfMeasurements(List<Double> inputList) //Function that when called calculates the number of measurements (i.e. the numer of lines in the files)
    {
        int N = inputList.size();
        return N; //Returns the number of measurements 
    }
    
    //Method
    //Declaring the method that will be overriden in each class
    public abstract void calculateNumberOfBins();
    
    //Gets the Number of bins (depending on class instantiated.)
    public int getNumberOfBins(){return K;}
    
    //No sets needed as the number of bins is entirelt dependant on the user input data
}
