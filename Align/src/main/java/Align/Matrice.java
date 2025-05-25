package Align;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Matrice {

    public final static double EPSILON = 1.0E-06;

    /**
     * Définir ici les attributs de la classe *
     */
    protected double coefficient[][];

    /**
     * Définir ici les constructeur de la classe *
     */
    Matrice(int nbligne, int nbcolonne) {
        this.coefficient = new double[nbligne][nbcolonne];
    }

    Matrice(double[][] tableau) {
        coefficient = tableau;
    }

    Matrice(String fichier) {
        try {
            Scanner sc = new Scanner(new File(fichier));
            int ligne = sc.nextInt();
            int colonne = sc.nextInt();
            this.coefficient = new double[ligne][colonne];
            for (int i = 0; i < ligne; i++) {
                for (int j = 0; j < colonne; j++) {
                    this.coefficient[i][j] = sc.nextDouble();
                }
            }
            sc.close();

        } catch (FileNotFoundException e) {
            System.out.println("Fichier absent");
        }
    }

    /**
     * Definir ici les autres methodes
     */
    public void recopie(Matrice arecopier) {
        int ligne, colonne;
        ligne = arecopier.nbLigne();
        colonne = arecopier.nbColonne();
        this.coefficient = new double[ligne][colonne];
        for (int i = 0; i < ligne; i++) {
            for (int j = 0; j < colonne; j++) {
                this.coefficient[i][j] = arecopier.coefficient[i][j];
            }
        }
    }

    public int nbLigne() {
        return this.coefficient.length;
    }

    public int nbColonne() {
        return this.coefficient[0].length;
    }

    public double getCoef(int ligne, int colonne) {
        return this.coefficient[ligne][colonne];
    }

    public void remplacecoef(int ligne, int colonne, double value) {
        this.coefficient[ligne][colonne] = value;
    }

    public String toString() {
        int ligne = this.nbLigne();
        int colonne = this.nbColonne();
        String matr = "";
        for (int i = 0; i < ligne; i++) {
            for (int j = 0; j < colonne; j++) {
                if (j == 0) {
                    matr += this.getCoef(i, j);
                } else {
                    matr += " " + this.getCoef(i, j);
                }
            }
            matr += "\n";
        }
        return matr;
    }

    public Matrice produit(double scalaire) {
        int ligne = this.nbLigne();
        int colonne = this.nbColonne();
        for (int i = 0; i < ligne; i++) {
            for (int j = 0; j < colonne; j++) {
                this.coefficient[i][j] *= scalaire;
            }
        }
        return this;
    }

    static Matrice addition(Matrice a, Matrice b) {
        int ligne = a.nbLigne();
        int colonne = a.nbColonne();
        Matrice mat = new Matrice(ligne, colonne);
        for (int i = 0; i < ligne; i++) {
            for (int j = 0; j < colonne; j++) {
                mat.coefficient[i][j] = a.coefficient[i][j] + b.coefficient[i][j];
            }
        }
        return mat;
    }

    static Matrice verif_addition(Matrice a, Matrice b) throws Exception {
        if ((a.nbLigne() == b.nbLigne()) && (a.nbColonne() == b.nbColonne())) {
            int ligne = a.nbLigne();
            int colonne = a.nbColonne();
            Matrice mat = new Matrice(ligne, colonne);
            for (int i = 0; i < ligne; i++) {
                for (int j = 0; j < colonne; j++) {
                    mat.coefficient[i][j] = a.coefficient[i][j] + b.coefficient[i][j];
                }
            }
            return mat;
        } else {
            throw new Exception("Les deux matrices n'ont pas les mêmes dimensions !!!");
        }
    }

    static Matrice produit(Matrice a, Matrice b) {
        int ligne, colonne;
        ligne = a.nbLigne();
        colonne = b.nbColonne();
        Matrice mat = new Matrice(ligne, colonne);
        for (int i = 0; i < ligne; i++) {
            for (int j = 0; j < colonne; j++) {
                mat.coefficient[i][j] = 0;
                for (int k = 0; k < a.nbColonne(); k++) {
                    mat.coefficient[i][j] += a.coefficient[i][k] * b.coefficient[k][j];
                }
            }
        }
        return mat;
    }

    static Matrice verif_produit(Matrice a, Matrice b) throws Exception {
        int ligne = 0;
        int colonne = 0;
        if (a.nbColonne() == b.nbLigne()) {
            ligne = a.nbLigne();
            colonne = b.nbColonne();
        } else {
            throw new Exception("Dimensions des matrices à multiplier incorrectes");
        }

        Matrice mat = new Matrice(ligne, colonne);
        for (int i = 0; i < ligne; i++) {
            for (int j = 0; j < colonne; j++) {
                mat.coefficient[i][j] = 0;
                for (int k = 0; k < a.nbColonne(); k++) {
                    mat.coefficient[i][j] += a.coefficient[i][k] * b.coefficient[k][j];
                }
            }
        }
        return mat;
    }

    // public Matrice inverse() throws Exception{

    //     if(nbLigne() != nbColonne()){
    //         throw new IllegalArgumentException("La matrice n'est pas carrée");
    //     }else{
    //         int n = nbLigne();
    //         Matrice copie=new Matrice(n, n);
            
    //         copie.recopie(this);

    //         double tab_invmatrice[][] = new double[n][n];
    //         Vecteur b =new Vecteur(n);
    //         Helder h = new Helder(copie, b);
    //         h.factorLDR();
    //         for(int i = 0; i < n; i++){
    //             if(i>0)
    //                 b.remplacecoef(i-1, 0);
    //             b.remplacecoef(i, 1);
    //             Vecteur x = h.resolutionPartielle();
    //             for(int j = 0; j < n; j++){
    //                 tab_invmatrice[j][i] = x.getCoef(j);
    //             }
    //         }

    //     }

    //     return null;
    // }

    public Matrice inverse() throws Exception {
        if (this.nbLigne() != this.nbColonne()) {
            throw new IllegalArgumentException("La matrice n'est pas carrée");
        } else {
            int n = this.nbLigne();
            Vecteur b[] = new Vecteur[n];

            Matrice mat = new Matrice(n, n);
            mat.recopie(this);
            Matrice inverse = new Matrice(n, n);

            for (int nbligne = 0; nbligne < n; nbligne++) {
                Vecteur x = new Vecteur(n);
                x.remplacecoef(nbligne, 1);
                b[nbligne] = x;
            }

            Helder h = new Helder(mat, b[0]);
            h.factorLDR();
            for (int i = 0; i < n; i++) {

                h.setSecondMembre(b[i]);
                Vecteur x = h.resolutionPartielle();
                for(int j = 0; j < n; j++) {
                    inverse.coefficient[j][i] = x.getCoef(j);
                }
            }

            return inverse;
        }
    }
    // public Matrice inverse() throws IrregularSysLinException {
				
    //     // leve une exception si matrice non carree
    //     if(this.nbLigne() != this.nbColonne()) {
    //         throw new IrregularSysLinException("Matrice non carree");
            
    //     }
        
    //     int nombreLigne=nbLigne();
    //     Matrice inverse = new Matrice(nombreLigne,nombreLigne);
        
    //     // Appel de la classe Helder pour la factorisation LDR de la matrice A
    //     Helder helder= new Helder(this, new Vecteur(nombreLigne));
    //     helder.factorLDR();
        
    //     // Resolution des differents systemes lineaires
    //     for(int i=0;i<nombreLigne;i++) {
    //         // colonne de la matrice iodentite representqtnt le second membre de la matrice identite
    //         Vecteur secondMembre= new Vecteur(nombreLigne);
    //         for(int j=0;j<nombreLigne;j++) {
                
    //             if (j==i ) 
    //                 secondMembre.remplacecoef(j, 1.0);
    //             else
    //                 secondMembre.remplacecoef(j, 0.0);
    //         }
            
    //         // Ax=e(i) 
            
    //         helder.setSecondMembre(secondMembre);
    //         Vecteur solutionX=helder.resolutionPartielle();
    //         System.out.println("solutionX : "+solutionX);
            
    //         for(int j=0;j<nombreLigne;j++) {
                
    //             inverse.remplacecoef(j, i, solutionX.getCoef(j));
    //         }
    //     }
        
    //     return inverse;
    // }
       public double norme_1() {
        double max = 0;
            double somme = 0;
            for (int j = 0; j < this.nbColonne(); j++) {
                for (int i = 0; i < this.nbLigne(); i++) {
                    somme += Math.abs(this.coefficient[i][j]);
                }
                if (somme > max) {
                    max = somme;
                }
                somme = 0;
            }
            return max;
        }

    public double norme_inf() {
        double max = 0;
        double somme = 0;
        for (int i = 0; i < this.nbLigne(); i++) {
            for (int j = 0; j < this.nbColonne(); j++) {
                somme += Math.abs(this.coefficient[i][j]);
            }
            if (somme > max) {
                max = somme;
            }
            somme = 0;
        }
        return max;
    }
    public double cond_1() {
        Matrice mat = new Matrice(this.nbLigne(), this.nbColonne());
        try {
            mat = this.inverse();
        } catch (Exception e) {
            System.out.println(e);
        }
        return this.norme_1() * mat.norme_1();
    }

    public double cond_inf() {
        Matrice mat = new Matrice(this.nbLigne(), this.nbColonne());
        try {
            mat = this.inverse();
        } catch (Exception e) {
            System.out.println(e);
        }
        return this.norme_inf() * mat.norme_inf();
    }

    public static Matrice identite(int ordre) {

        Matrice mat = new Matrice(ordre, ordre);
        for (int i = 0; i < ordre; i++) {
            mat.coefficient[i][i] = 1;
        }
        return mat;
    }

    public Matrice transpose() {
        Matrice mat = new Matrice(this.nbColonne(), this.nbLigne());
        for (int i = 0; i < this.nbLigne(); i++) {
            for (int j = 0; j < this.nbColonne(); j++) {
                mat.coefficient[j][i] = this.coefficient[i][j];
            }
        }
        return mat;
    }
    

    public static void main(String[] args) throws Exception {
        Matrice mat = new Matrice("src/Matrice.txt");
        Matrice identite = new Matrice(new double[][] { { 1, 0, 0 }, { 0, 1, 0 },{ 0, 0, 1 } });
        System.out.println(mat);
        
        Matrice copie = new Matrice(mat.nbLigne(), mat.nbColonne());
        copie.recopie(mat);
        Matrice inverse = mat.inverse();

        Matrice prodMatrice = Matrice.produit(copie, inverse);
        System.out.println(prodMatrice);
        System.out.println("invrese : " + inverse);

        // System.out.println("Norme 1 : " + prodMatrice.norme_1());
        // System.out.println("Norme inf : " + prodMatrice.norme_inf());
        // System.out.println("Conditionnement 1 : " + prodMatrice.cond_1());
        // System.out.println("Conditionnement inf : " + prodMatrice.cond_inf());

        // System.out.println("Matrice identité : " + identite);
        // System.out.println();
        // System.out.println("NOrme 1 de l'identité : " + identite.norme_1());
        // System.out.println("Norme inf de l'identité : " + identite.norme_inf());

        // System.out.println("Conditionnement 1 de l'identité : " + identite.cond_1());
        // System.out.println("Conditionnement inf de l'identité : " + identite.cond_inf());


        System.out.println("diference de norme 1 : " + Math.abs(prodMatrice.norme_1() - identite.norme_1()));
        System.out.println("diference de norme inf : " + Math.abs(prodMatrice.norme_inf() - identite.norme_inf()));

        System.out.println("diference de conditionnement 1 : " + Math.abs(prodMatrice.cond_1() - identite.cond_1()));
        System.out.println("diference de conditionnement inf : " + Math.abs(prodMatrice.cond_inf() - identite.cond_inf()));

        System.out.println("comapraison de norme 1 : " + (Math.abs(prodMatrice.norme_1() - identite.norme_1()) < EPSILON));
        System.out.println("comapraison de norme inf : " + (Math.abs(prodMatrice.norme_inf() - identite.norme_inf()) < EPSILON));

        System.out.println("comapraison de conditionnement 1 : " + (Math.abs(prodMatrice.cond_1() - identite.cond_1()) < EPSILON));
        System.out.println("comapraison de conditionnement inf : " + (Math.abs(prodMatrice.cond_inf() - identite.cond_inf()) < EPSILON));
        
    }

  
}
