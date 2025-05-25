package Align;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVLoader {
    public static double[][] loadCSV(String filePath) throws IOException {
        List<Double> xList = new ArrayList<>();
        List<Double> yList = new ArrayList<>();
        
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = br.readLine()) != null) {
            String[] values = line.trim().split(",");
            if (values.length == 2) {
                xList.add(Double.parseDouble(values[0])); // Colonne X
                yList.add(Double.parseDouble(values[1])); // Colonne Y
            }
        }
        br.close();
        
        double[] x = xList.stream().mapToDouble(Double::doubleValue).toArray();
        double[] y = yList.stream().mapToDouble(Double::doubleValue).toArray();
        
        return new double[][]{x, y};
    }
}
