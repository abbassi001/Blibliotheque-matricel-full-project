package Align;

import Align.Matrice;


public class Vecteur extends Matrice {


    public Vecteur(int n) {
        super(n, 1);  }

    public Vecteur(double[] coefficients) {
        super(coefficients.length, 1); 
        for (int i = 0; i < coefficients.length; i++) {
            this.coefficient[i][0] = coefficients[i]; 
        }
    }

    public Vecteur(String fichier) {
        super(fichier);
    }

    public int taille() {
        return this.nbLigne();
    }

    public double getCoef(int ligne) {
        return super.getCoef(ligne, 0);
    }

    public void remplacecoef(int ligne, double value) {
        super.remplacecoef(ligne, 0, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.taille(); i++) {
            sb.append(this.getCoef(i)).append("\n");
        }
        return sb.toString();
    }

    public static double produitScalaire(Vecteur v1, Vecteur v2) {
        if (v1.taille() != v2.taille()) {
            throw new IllegalArgumentException("Les deux vecteurs doivent avoir la même taille.");
        }
        double resultat = 0.0;
        for (int i = 0; i < v1.taille(); i++) {
            resultat += v1.getCoef(i) * v2.getCoef(i);
        }
        return resultat;
    }

    public double normeL1() {
        double norme = 0.0;
        for (int i = 0; i < this.taille(); i++) {
            norme += Math.abs(this.getCoef(i));
        }
        return norme;
    }
    public double normeL2() {
        double norme = 0.0;
        for (int i = 0; i < this.taille(); i++) {
            norme += Math.pow(this.getCoef(i), 2);
        }
        return Math.sqrt(norme);
    }

    public double normeLinf() {
        double norme = 0.0;
        for (int i = 0; i < this.taille(); i++) {
            double coef = Math.abs(this.getCoef(i));
            if (coef > norme) {
                norme = coef;
            }
        }
        return norme;
    }

    public static void main(String[] args) {
        Vecteur v1 = new Vecteur(5);
        System.out.println("Vecteur v1 (taille 5) :");
        System.out.println(v1);

        double[] coefficients = {1.0, 2.0, 3.0, 4.0, 5.0};
        Vecteur v2 = new Vecteur(coefficients);
        System.out.println("Vecteur v2 (tableau de coefficients) :");
        System.out.println(v2);

        Vecteur v3 = new Vecteur("src/vecteur.txt");
        System.out.println("Vecteur v3 (à partir d'un fichier) :");
        System.out.println(v3);

        System.out.println("Coefficient de v2 à la ligne 2 : " + v2.getCoef(2));
        v2.remplacecoef(2, 10.0);
        System.out.println("Nouveau coefficient de v2 à la ligne 2 : " + v2.getCoef(2));

        double produit = Vecteur.produitScalaire(v1, v2);
        System.out.println("Produit scalaire de v1 et v2 : " + produit);
    }
}
