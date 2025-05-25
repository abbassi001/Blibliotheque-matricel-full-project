package Align;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.lines.SeriesLines;
import org.knowm.xchart.style.markers.SeriesMarkers;

public class MainTest {
    public static void main(String[] args) throws IrregularSysLinException {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Entrez le nom du fichier CSV (ex: data.csv) : ");
        String fileName = scanner.nextLine();

        System.out.println("Entrez le degré du polynome : ");
        int degre= scanner.nextInt();

        
        String filePath = Paths.get("Align", "src", "Resources", fileName).toString();

        try {
            double[][] data = CSVLoader.loadCSV(filePath);
            double[] x = data[0];
            double[] y = data[1];

            ModPoly modPoly = new ModPoly(degre);
            modPoly.identify(x, y);

            double xmin = Arrays.stream(x).min().getAsDouble();
            double xmax = Arrays.stream(x).max().getAsDouble();

            List<Double> xInterp = new ArrayList<>();
            List<Double> yInterp = new ArrayList<>();

            for (int i = 0; i < 100; i++) {
                double xValue = xmin + i * (xmax - xmin) / 99; 
                xInterp.add(xValue);
                yInterp.add(modPoly.eval(xValue));
                System.out.println("x = " + xValue + " y = " + modPoly.eval(xValue));
            }

            List<Double> xList = new ArrayList<>();
            List<Double> yList = new ArrayList<>();
            for (double value : x) xList.add(value);
            for (double value : y) yList.add(value);

            XYChart chart = new XYChartBuilder().width(1200).height(800).title("Méthode des moindres carrées").xAxisTitle("X").yAxisTitle("Y").build();


            

            XYSeries splineSeries = chart.addSeries("Méthode des moindres carrées", xInterp, yInterp);
            splineSeries.setMarker(SeriesMarkers.PLUS);
            splineSeries.setLineColor(java.awt.Color.ORANGE);
            splineSeries.setMarkerColor(Color.BLUE);


            XYSeries originalSeries = chart.addSeries("Points de support", xList, yList);
            
            originalSeries.setLineStyle(SeriesLines.NONE);
            originalSeries.setMarker(SeriesMarkers.DIAMOND);
            originalSeries.setMarkerColor(java.awt.Color.RED);

            new SwingWrapper<>(chart).displayChart();

        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }

        scanner.close();
    }
}
