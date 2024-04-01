/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aircoachroughness;

/**
 *
 * @author jrhol
 */
/*
Client code to test the binmethod package
 */
import java.util.List;
import java.util.Arrays;
// the bin rule formulae must be implemented in binmethod package
import binmethod.*;

public class Unit_test_binmethod {

    public static void main(String[] args) {
// Create array of data
        List<Double> exampleData = Arrays.asList(1., 2., 3., 4., 5., 6., 7., 8., 9., 10., 11.);

// test SturgesFormula class
        SturgesFormula SturgesInstance = new SturgesFormula(exampleData);
        SturgesInstance.calculateNumberOfBins();
        System.out.printf("By Sturges Formula: %d \n", SturgesInstance.getNumberOfBins());

// test SquareRootChoice class
        SquareRootChoice SquareRootChoiceInstance = new SquareRootChoice(exampleData);
        SquareRootChoiceInstance.calculateNumberOfBins();
        System.out.printf("By Square Root Formula: %d \n", SquareRootChoiceInstance.getNumberOfBins());

// test RiceRule class
        RiceRule RiceRuleInstance = new RiceRule(exampleData);
        RiceRuleInstance.calculateNumberOfBins();
        System.out.printf("By Rice Rule Formula: %d \n", RiceRuleInstance.getNumberOfBins());
    }
}
