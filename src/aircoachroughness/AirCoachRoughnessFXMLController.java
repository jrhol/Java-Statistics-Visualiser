package aircoachroughness;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

import binmethod.*;
import static binmethod.BinFormulae.numberOfMeasurements;
import statutils.*;
import static mathutils.dataFitting.calculateDataFitting;
import static mathutils.dataFitting.getPDFParam;

public class AirCoachRoughnessFXMLController implements Initializable {

    //*********************************//
    //Variables needed to be seen accross all functions
    public List<Double> fileInputData = new ArrayList<>();
    public boolean BarChartOn = false;
    public boolean LineChartOn = false;

    //*********************************//
    //This is the Scenebuilder Generated Scenebuilder elements
    @FXML
    private StackPane WindowPane;

    @FXML
    private BarChart<String, Number> BarChartData;

    @FXML
    private LineChart<String, Number> lineChartData;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private ToggleGroup BinMethod;

    @FXML
    private TextField numSamplesText;

    @FXML
    private TextField minValueText;

    @FXML
    private TextField maxValueText;

    @FXML
    private TextField MeanText;

    @FXML
    private TextField VarienceText;

    @FXML
    private TextField MedianText;

    @FXML
    private TextField StdDevText;

    @FXML
    private TextField AText;

    @FXML
    private TextField uText;

    @FXML
    private TextField sigmaText;

    @FXML
    private TextField Title;

    //End of Scenebuilder Generated Elements
    //*********************************//
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //Setting up Bar Graph
        BarChartData.setLegendVisible(false);
        BarChartData.setAnimated(false);

        //END TEST
        lineChartData.setAnimated(false);

        //Setting up StackedGraphs
        lineChartData.setLegendVisible(false);
        lineChartData.setCreateSymbols(true);
        lineChartData.setAlternativeRowFillVisible(false);
        lineChartData.setAlternativeColumnFillVisible(false);
        lineChartData.setHorizontalGridLinesVisible(false);
        lineChartData.setVerticalGridLinesVisible(false);
        lineChartData.getXAxis().setVisible(true); //not sure why these two don't work Java Fx version issue?
        lineChartData.getYAxis().setVisible(true);
        lineChartData.getYAxis().setTickLabelsVisible(false);
        lineChartData.getYAxis().setOpacity(0);
        lineChartData.getXAxis().setOpacity(0);

        BarChartData.setVisible(false);
        BarChartData.setManaged(false);
        lineChartData.setVisible(false);
        lineChartData.setManaged(false);
    }

    //TODO - Last
    @FXML
    void PlotGraph(ActionEvent event) {
        //Put Data on Graph
        if (BarChartOn == false) {
            BarChartData.setVisible(true);
            BarChartData.setManaged(true);
            BarChartOn = true;
        } else if (BarChartOn == true) {
            BarChartData.setVisible(false);
            BarChartData.setManaged(false);
            BarChartOn = false;
        }

    }

    //TODO
    @FXML
    void PlotProbDen(ActionEvent event) {
        //Add on Probability density function
        //Put Data on Graph
        if (LineChartOn == false && BarChartOn == true) {
            lineChartData.setVisible(true);
            lineChartData.setManaged(true);
            LineChartOn = true;
        } else if (LineChartOn == true) {
            lineChartData.setVisible(false);
            lineChartData.setManaged(false);
            LineChartOn = false;
        }
    }

    //On This action, the Rice Rule formula is used to Calculate the Normalised Histogram
    @FXML
    void RiceAction(ActionEvent event) {

        //Clears The Previous Graph
        BarChartData.getData().clear();
        lineChartData.getData().clear();

        //Clear the Title box
        Title.clear();

        //Perform Calculations with Rice method
        //Creates an instance of the RiceRule class to calculated the number of bins
        RiceRule riceRuleInstance = new RiceRule(fileInputData);
        riceRuleInstance.calculateNumberOfBins();

        //Creates an instance of the Samples per Bin class, instantiated with rice rules number of bins
        SamplesPerBin samplesPerBinInstance = new SamplesPerBin(fileInputData, 
                                                                riceRuleInstance.getNumberOfBins());
        samplesPerBinInstance.calculateWidth(); //Calls the calculate width function
        samplesPerBinInstance.calculateSamplesPerBin(); //Calls the Calculate samples per bin function

        //Creates an instance of the Samples per Bin class, instantiated with rice rules number of bins, the width and the array of samples per bin as calculated above.
        HistogramNormalisation histogramNormalisationInstance = new HistogramNormalisation(riceRuleInstance.getNumberOfBins(),
                samplesPerBinInstance.getWidth(),
                samplesPerBinInstance.getSamplesPerBin());
        //Calls the normalise histogramn function and holds the returned array
        double[] normaliseHistogram = histogramNormalisationInstance.normaliseHistogram();

        //Creates an XYseries for the Histogram
        XYChart.Series RiceRuleSeries = new XYChart.Series();

        //Populates the Barchart with the X and Y data. 
        //X data is the Top end of each Bin (Based on the width)
        //Y data is the normalised Histogram
        for (int i = 0; i < riceRuleInstance.getNumberOfBins(); i++) {
            RiceRuleSeries.getData().add(new XYChart.Data(Double.toString(Math.round((Collections.min(fileInputData) + (samplesPerBinInstance.getWidth() * i)) * 1000000)), normaliseHistogram[i]));
        }

        //Adds the XY data to the Bar Chart
        BarChartData.getData().add(RiceRuleSeries);

        //Creates an XYseries for the PDF
        XYChart.Series riceRuleSeriesPDF = new XYChart.Series();
        //Populates an Array with the Y coords of the PDF function via calling the calculateDataFitting function as part of the Data Fitting class.
        double[] PDFArray = calculateDataFitting(fileInputData, riceRuleInstance.getNumberOfBins(), 
                                                                Collections.min(fileInputData), 
                                                                samplesPerBinInstance.getWidth(), 
                                                                normaliseHistogram);

        //Populates the PDF XY serues with the X and Y coords
        //X data is the Top end of each Bin (Based on the width)
        //Y data is the Gaussian Fitted Value 
        for (int i = 0; i < riceRuleInstance.getNumberOfBins(); i++) {
            riceRuleSeriesPDF.getData().add(new XYChart.Data(Double.toString(Math.round((Collections.min(fileInputData) + (samplesPerBinInstance.getWidth() * i)) * 1000000)), PDFArray[i]));
        }

        //Adds the XY data to the Line Chart
        lineChartData.getData().add(riceRuleSeriesPDF);

        //Calculates the Data values for the PDF Function
        double[] DataFittingArray = getPDFParam(fileInputData, riceRuleInstance.getNumberOfBins(), 
                                                               Collections.min(fileInputData), 
                                                               samplesPerBinInstance.getWidth(), 
                                                               normaliseHistogram);

        //Sets the Text in the Scene
        AText.setText(Double.toString(DataFittingArray[0])); //Normalisation Factor of the fitted curve (if fitted)
        uText.setText(Double.toString(DataFittingArray[1] * 1000000)); //Mean of of the fittec curve
        sigmaText.setText(Double.toString(DataFittingArray[2] * 1000000)); //Sigma of of the input data
    }

    //Done
    @FXML
    void SelectFilePath(ActionEvent event) {
        //Clears list of all data
        fileInputData.clear();
        //creates instance of file chooser
        FileChooser fileChooser = new FileChooser();
        //only allow text files to be selected using chooser
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt"));
        try {
            //let user select file
            File fileToLoad = fileChooser.showOpenDialog(null);
            //set initial directory somewhere user will recognise
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            try {
                Scanner myReader = new Scanner(fileToLoad);
                while (myReader.hasNextDouble()) {
                    fileInputData.add(myReader.nextDouble());
                }
                myReader.close();
                Title.setText("");
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
            }
        } catch (Exception ex) {
            System.out.println("Error Overloop");
            Title.setText("No Text File Selected");
        }

        //Putting Stastics data into the Text boxes on the LHS 
        StatisticsCalculator statisticsCalculatorInstance = new StatisticsCalculator(fileInputData);
        numSamplesText.setText(Double.toString(numberOfMeasurements(fileInputData))); //Number of samples
        minValueText.setText(Double.toString(statisticsCalculatorInstance.calculateMin() * 1000000)); //minimum value in the input data
        maxValueText.setText(Double.toString(statisticsCalculatorInstance.calculateMax() * 1000000)); //maximum value in the input data
        MeanText.setText(Double.toString(statisticsCalculatorInstance.calculateMean() * 1000000)); //mean value of the input data
        VarienceText.setText(Double.toString(statisticsCalculatorInstance.calculateVariance() * 1000000)); //Varience of the input data
        MedianText.setText(Double.toString(statisticsCalculatorInstance.calculateMedian() * 1000000)); //median of the input data
        StdDevText.setText(Double.toString(statisticsCalculatorInstance.calculateStandardDeviation() * 1000000));//STD Dev of of the input data

    }

    //TODO
    @FXML
    void SquareAction(ActionEvent event) {
        //Perform Calculations with Squareroot method
        //Find number of bins using Squareroot 
        //Calculate stats analysis 
        //Calculate histogram data

        //Clears The Previous Graph
        BarChartData.getData().clear();
        lineChartData.getData().clear();

        //Clear the Title box
        Title.clear();

        //Perform Calculations with Square Root method
        //Creates an instance of the SquareRootChoice class to calculated the number of bins
        SquareRootChoice SquareRootChoiceInstance = new SquareRootChoice(fileInputData);
        SquareRootChoiceInstance.calculateNumberOfBins();

        //Creates an instance of the Samples per Bin class, instantiated with Square Root rule number of bins
        SamplesPerBin samplesPerBinInstance = new SamplesPerBin(fileInputData, 
                                                                SquareRootChoiceInstance.getNumberOfBins());
        samplesPerBinInstance.calculateWidth(); //Calls the calculate width function
        samplesPerBinInstance.calculateSamplesPerBin(); //Calls the Calculate samples per bin function

        //Creates an instance of the Samples per Bin class, instantiated with Square Root rule number of bins, the width and the array of samples per bin as calculated above.
        HistogramNormalisation histogramNormalisationInstance = new HistogramNormalisation(SquareRootChoiceInstance.getNumberOfBins(),
                                                                                           samplesPerBinInstance.getWidth(),
                                                                                           samplesPerBinInstance.getSamplesPerBin());
        //Calls the normalise histogramn function and holds the returned array
        double[] normaliseHistogram = histogramNormalisationInstance.normaliseHistogram();

        //Creates an XYseries for the Histogram
        XYChart.Series SquareRootChoiceSeries = new XYChart.Series();

        //Populates the Barchart with the X and Y data. 
        //X data is the Top end of each Bin (Based on the width)
        //Y data is the normalised Histogram
        for (int i = 0; i < SquareRootChoiceInstance.getNumberOfBins(); i++) {
            SquareRootChoiceSeries.getData().add(new XYChart.Data(Double.toString(Math.round((Collections.min(fileInputData) + (samplesPerBinInstance.getWidth() * i)) * 1000000)), normaliseHistogram[i]));
        }

        //Adds the XY data to the Bar Chart
        BarChartData.getData().add(SquareRootChoiceSeries);

        //Creates an XYseries for the PDF
        XYChart.Series SquareRootChoiceSeriesPDF = new XYChart.Series();
        //Populates an Array with the Y coords of the PDF function via calling the calculateDataFitting function as part of the Data Fitting class.
        double[] PDFArray = calculateDataFitting(fileInputData, SquareRootChoiceInstance.getNumberOfBins(), 
                                                                Collections.min(fileInputData), 
                                                                samplesPerBinInstance.getWidth(), 
                                                                normaliseHistogram);

        //Populates the PDF XY serues with the X and Y coords
        //X data is the Top end of each Bin (Based on the width)
        //Y data is the Gaussian Fitted Value 
        for (int i = 0; i < SquareRootChoiceInstance.getNumberOfBins(); i++) {
            SquareRootChoiceSeriesPDF.getData().add(new XYChart.Data(Double.toString(Math.round((Collections.min(fileInputData) + (samplesPerBinInstance.getWidth() * i)) * 1000000)), PDFArray[i]));
        }

        //Adds the XY data to the Line Chart
        lineChartData.getData().add(SquareRootChoiceSeriesPDF);

        //Calculates the Data values for the PDF Function
        double[] DataFittingArray = getPDFParam(fileInputData, SquareRootChoiceInstance.getNumberOfBins(), 
                                                               Collections.min(fileInputData), 
                                                               samplesPerBinInstance.getWidth(), 
                                                               normaliseHistogram);

        //Sets the Text in the Scene
        AText.setText(Double.toString(DataFittingArray[0])); //Normalisation Factor of the fitted curve (if fitted)
        uText.setText(Double.toString(DataFittingArray[1] * 1000000)); //Mean of of the fittec curve
        sigmaText.setText(Double.toString(DataFittingArray[2] * 1000000)); //Sigma of of the input data
    }

    //TODO
    @FXML
    void SturgesAction(ActionEvent event) {
        //Perform Calculations with SturgesAction method
        //Find number of bins using sturges 
        //Calculate stats analysis 
        //Calculate histogram data

        //Clears The Previous Graph
        BarChartData.getData().clear();
        lineChartData.getData().clear();

        //Clear the Title box
        Title.clear();

        //Perform Calculations with Sturges Formula Root method
        //Creates an instance of the Sturges Formula class to calculated the number of bins
        SturgesFormula SturgesFormulaInstance = new SturgesFormula(fileInputData);
        SturgesFormulaInstance.calculateNumberOfBins();

        //Creates an instance of the Samples per Bin class, instantiated with Sturges Formula rule number of bins
        SamplesPerBin samplesPerBinInstance = new SamplesPerBin(fileInputData, 
                                                                SturgesFormulaInstance.getNumberOfBins());
        samplesPerBinInstance.calculateWidth(); //Calls the calculate width function
        samplesPerBinInstance.calculateSamplesPerBin(); //Calls the Calculate samples per bin function

        //Creates an instance of the Samples per Bin class, instantiated with Sturges Formula rule number of bins, the width and the array of samples per bin as calculated above.
        HistogramNormalisation histogramNormalisationInstance = new HistogramNormalisation(SturgesFormulaInstance.getNumberOfBins(),
                                                                                           samplesPerBinInstance.getWidth(),
                                                                                           samplesPerBinInstance.getSamplesPerBin());
        //Calls the normalise histogramn function and holds the returned array
        double[] normaliseHistogram = histogramNormalisationInstance.normaliseHistogram();

        //Creates an XYseries for the Histogram
        XYChart.Series SturgesFormulaeSeries = new XYChart.Series();

        //Populates the Barchart with the X and Y data. 
        //X data is the Top end of each Bin (Based on the width)
        //Y data is the normalised Histogram
        for (int i = 0; i < SturgesFormulaInstance.getNumberOfBins(); i++) {
            SturgesFormulaeSeries.getData().add(new XYChart.Data(Double.toString(Math.round((Collections.min(fileInputData) + (samplesPerBinInstance.getWidth() * i)) * 1000000)), normaliseHistogram[i]));
        }

        //Adds the XY data to the Bar Chart
        BarChartData.getData().add(SturgesFormulaeSeries);

        //Creates an XYseries for the PDF
        XYChart.Series SturgesFormulaeSeriesPDF = new XYChart.Series();
        //Populates an Array with the Y coords of the PDF function via calling the calculateDataFitting function as part of the Data Fitting class.
        double[] PDFArray = calculateDataFitting(fileInputData, SturgesFormulaInstance.getNumberOfBins(), 
                                                                Collections.min(fileInputData), 
                                                                samplesPerBinInstance.getWidth(), 
                                                                normaliseHistogram);

        //Populates the PDF XY serues with the X and Y coords
        //X data is the Top end of each Bin (Based on the width)
        //Y data is the Gaussian Fitted Value 
        for (int i = 0; i < SturgesFormulaInstance.getNumberOfBins(); i++) {
            SturgesFormulaeSeriesPDF.getData().add(new XYChart.Data(Double.toString(Math.round((Collections.min(fileInputData) + (samplesPerBinInstance.getWidth() * i)) * 1000000)), PDFArray[i]));
        }

        //Adds the XY data to the Line Chart
        lineChartData.getData().add(SturgesFormulaeSeriesPDF);

        //Calculates the Data values for the PDF Function
        double[] DataFittingArray = getPDFParam(fileInputData, SturgesFormulaInstance.getNumberOfBins(), 
                                                               Collections.min(fileInputData), 
                                                               samplesPerBinInstance.getWidth(), 
                                                               normaliseHistogram);

        //Sets the Text in the Scene
        AText.setText(Double.toString(DataFittingArray[0])); //Normalisation Factor of the fitted curve (if fitted)
        uText.setText(Double.toString(DataFittingArray[1] * 1000000)); //Mean of of the fittec curve
        sigmaText.setText(Double.toString(DataFittingArray[2] * 1000000)); //Sigma of of the input data
    }

    //Done
    @FXML
    void clearGraph(ActionEvent event) {
        BarChartData.getData().clear();
        lineChartData.getData().clear();
        BarChartOn = false;
        LineChartOn = false;
    }

    //Done
    @FXML
    void saveToPNG(ActionEvent event) {
        //Getting image from imageview
        WritableImage imageToBeSaved = WindowPane.snapshot(new SnapshotParameters(), null);

        FileChooser fileChooser = new FileChooser();
        //Setting the File Chooser Title
        fileChooser.setTitle("Save");
        //only allow PNG files to be selected using chooser
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG"));
        //set initial directory somewhere user will recognise
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        //let user select file
        File file = fileChooser.showSaveDialog(null);
        System.out.println(file);
        if (file != null) {
            try {
                // save to file
                ImageIO.write(SwingFXUtils.fromFXImage(imageToBeSaved, null), "png", file);
            } catch (IOException ex) {
            }
        }
    }
}
