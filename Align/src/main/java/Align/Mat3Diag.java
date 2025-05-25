package Align;
public class Mat3Diag extends Matrice {

    public Mat3Diag(int dim1 ,int dim2) {
        super(dim1, dim2);
        if (dim1 != 3) {
            throw new IllegalArgumentException("Erreur : la matrice doit avoir exactement 3 lignes");
        }
    }

    public Mat3Diag(double[][] matriceData) {
        super(matriceData);
        if (matriceData.length != 3) {
            throw new IllegalArgumentException("Erreur : la matrice doit avoir exactement 3 lignes");
        }
    }

    public Mat3Diag(int dim) {
        super(3, dim);
    }

    public static Vecteur produitMat3Diag(Mat3Diag mat3Diag, Vecteur b) {
        int n = b.taille();
        if (mat3Diag.nbColonne() != n) {
            throw new IllegalArgumentException("Erreur : la matrice et le vecteur doivent avoir la même taille");
        }
        Vecteur result = new Vecteur(n);

        double [] bData = new double[n]; // diagonal
        double [] aData = new double[n]; // sous diagonal
        double [] cData = new double[n]; // sur diagonal

        for (int i = 0; i < n; i++) {
            aData[i] = mat3Diag.getCoef(0, i);
            bData[i] = mat3Diag.getCoef(1, i);
            cData[i] = mat3Diag.getCoef(2, i);            
        }
        
        for (int i = 0; i < n; i++) {
            double sum = bData[i] * b.getCoef(i); // diagonal
            if (i > 0) {
                sum += aData[i] * b.getCoef(i - 1); // sous diagonal
            }
            if (i < n - 1) {
                sum += cData[i] * b.getCoef(i + 1); // sur diagonal
            }
            result.remplacecoef(i, sum);
        }

        return result;
    }

    public static void main(String[] args) {
        double[][] matriceData = { 
            { 0, -1, -1, -1 }, //sous diagonale
            { 2,  2,  2,  2 }, // diagonale
            { -1, -1, -1, 0 }, //sur diagonale
        };

        Mat3Diag mat3DiagA = new Mat3Diag(matriceData);
        System.out.println("Matrice A :\n" + mat3DiagA);

        Vecteur vect = new Vecteur(new double[] { -2, -2, -2, 23 });

        Vecteur result = produitMat3Diag(mat3DiagA, vect);
        System.out.println("Resultat :\n" + result);
        
    }
}