/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mathutils;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.math3.fitting.GaussianCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

/**
 *
 * @author jrhol
 */
public class dataFitting {

//    //Constructor 
    public dataFitting() {
    }

    // Declaration of a static method.
    public static double[] calculateDataFitting(List<Double> _inputData, 
                                                int _numberOfBins, 
                                                double _min, 
                                                double _width, 
                                                double[] _normalisedSamplesPerBin) {
        //Take in array/list of data points after normalisation has been performed on the data
        //perform fitting below
        double  minimumBinCenter = _min + (_width / 2);

        WeightedObservedPoints data = new WeightedObservedPoints();

        for (int i = 0; i < _numberOfBins; i++) {
            data.add((minimumBinCenter + ((_width * i))), _normalisedSamplesPerBin[i]);
        }

        // Fit a Gaussian curve to the data points.
        GaussianCurveFitter fitter = GaussianCurveFitter.create();
        double[] dataFittingParameters = fitter.fit(data.toList());

        //return the following 
        //The data points x and y for the normalisaed curve
        //The Values for the normalisation factor, Mean and Sigma
        double[] PDF = new double[_numberOfBins];
        for (int i = 0; i < _numberOfBins; i++) {
            PDF[i] = dataFittingParameters[0] * (Math.exp(-0.5 * (Math.pow(((minimumBinCenter + (_width * i)) - dataFittingParameters[1]) / (dataFittingParameters[2]), 2))));
        }
        return PDF;
    }

    //Calculates Just the Data Fitting Parameters.
    public static double[] getPDFParam(List<Double> _inputData, 
                                       int _numberOfBins, 
                                       double _min, 
                                       double _width, 
                                       double[] _normalisedSamplesPerBin) { 
        //Take in array/list of data points after normalisation has been performed on the data
        //perform fitting below
        double  minimumBinCenter = _min + (_width / 2);

        WeightedObservedPoints data = new WeightedObservedPoints();

        for (int i = 0; i < _numberOfBins; i++) {
            data.add((minimumBinCenter + ((_width * i))), _normalisedSamplesPerBin[i]);
        }

        // Fit a Gaussian curve to the data points.
        GaussianCurveFitter fitter = GaussianCurveFitter.create();
        double[] dataFittingParameters = fitter.fit(data.toList());
        return dataFittingParameters;
    }


}
