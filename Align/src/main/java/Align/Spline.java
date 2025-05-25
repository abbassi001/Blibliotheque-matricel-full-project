package Align;

import java.util.Arrays;

public class Spline {

    double x[], y[];
    int n;
    double g[];

    public Spline(double x[], double y[]) {
        this.x = x;
        this.y = y;
        this.n = x.length;
        this.g = new double[n];

        sortData();
        
        try {
            derivesecond();
        } catch (IrregularSysLinException e) {
            e.printStackTrace();
        }

    }

    private void derivesecond() throws IrregularSysLinException {
        double a[] = new double[n - 2];
        double b[] = new double[n - 2];
        double c[] = new double[n - 2];
        double d[] = new double[n - 2];

        g[0] = 0;
        g[n - 1] = 0;
        b[0] = 2 * (x[2] - x[0]);
        c[0] = x[2] - x[1];
        d[0] = 6 * ((y[2] - y[1]) / (x[2] - x[1]) - (y[1] - y[0]) / (x[1] - x[0]));

        for (int j = 2; j <= n - 3; j++) {
            a[j-1] = c[j - 2];
            b[j-1] = 2 * (x[j + 1] - x[j - 1]);
            c[j-1] = x[j + 1] - x[j];
            d[j-1] = 6 * ((y[j + 1] - y[j]) / c[j-1] - (y[j] - y[j - 1]) / a[j-1]);
        }
        a[n - 3] = c[n - 4];
        b[n - 3] = 2 * (x[n - 1] - x[n - 3]);
        d[n - 3] = 6 * ((y[n - 1] - y[n - 2]) / (x[n - 1] - x[n - 2]) - (y[n - 2] - y[n - 3]) / a[n - 3]);

        double[][] matrixData = new double[3][n-2];
        for (int i = 0; i < n - 2; i++) {
            matrixData[0][i] = a[i];
            matrixData[1][i] = b[i];
            matrixData[2][i] = c[i];
        }


        Mat3Diag mat3Diag = new Mat3Diag(matrixData);
        System.out.println("===============================Matrice 3 diagonale : =========================================");
        System.out.println(mat3Diag.toString());
        Vecteur vect = new Vecteur(d);
        System.out.println("=====================================Vecteur d : =========================================");
        System.out.println(vect.toString());

        Thomas thomas = new Thomas(mat3Diag, vect);

        Vecteur result = thomas.resolution();
        System.out.println("=====================================Resultat : =========================================");
        System.out.println(result.toString());
        for (int i = 1; i < n - 1 ; i++) {
            g[i] = result.getCoef(i - 1);
        }

    }

    public double interpolate(double xValue) throws DataOutOfRangeException {

        if (xValue < x[0] || xValue > x[n - 1]) {
            throw new DataOutOfRangeException("Valeur " + xValue + " hors des bornes [" + x[0] + ", " + x[n - 1] + "]");
        }
        int j = 0;

        while (j < n - 1 && xValue > x[j + 1]) {
            j++;
        }
        double alpha = x[j + 1] - xValue;
        double beta = xValue - x[j];
        double gamma = x[j + 1] - x[j];

        double t1 = g[j] / 6 * (alpha * alpha * alpha / gamma - gamma * alpha);
        double t2 = g[j + 1] / 6 * (beta * beta * beta / gamma - gamma * beta);
        double t3 = y[j] * alpha / gamma + y[j + 1] * beta / gamma;

        return t1 + t2 + t3;
    }
    

    private void sortData() {
        Integer[] indices = new Integer[n];
        for (int i = 0; i < n; i++) {
            indices[i] = i;
        }

        Arrays.sort(indices, (i, j) -> Double.compare(x[i], x[j]));

        double[] sortedX = new double[n];
        double[] sortedY = new double[n];

        for (int i = 0; i < n; i++) {
            sortedX[i] = x[indices[i]];
            sortedY[i] = y[indices[i]];
        }

        this.x = sortedX;
        this.y = sortedY;
    }

}
