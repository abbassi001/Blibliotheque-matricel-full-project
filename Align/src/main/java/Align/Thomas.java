package Align;


public class Thomas extends SysLin {
    private final Mat3Diag matrice;  
    private final Vecteur secondMembre; 

    public Thomas(Mat3Diag matrice, Vecteur secondMembre) throws IrregularSysLinException {
        super(matrice, secondMembre);
        this.matrice = matrice;
        this.secondMembre = secondMembre;
    }

    @Override
    public Vecteur resolution() {
        int n = secondMembre.nbLigne();
        if (matrice.nbColonne() != n || secondMembre.taille() != n || matrice.nbLigne() != 3) {
            throw new IllegalArgumentException("Erreur : la matrice et le vecteur doivent avoir la même taille");
        }

        Vecteur p= new Vecteur(n);
        Vecteur q= new Vecteur(n);

        Vecteur x = new Vecteur(n);

        p.remplacecoef(0, -matrice.getCoef(2, 0) / matrice.getCoef(1, 0));
        q.remplacecoef(0, secondMembre.getCoef(0) / matrice.getCoef(1, 0));

        for (int k = 1; k <= n - 2; k++) {
            double beta = matrice.getCoef(0, k) * p.getCoef(k - 1) + matrice.getCoef(1, k);
            p.remplacecoef(k, -matrice.getCoef(2, k) / beta);
            q.remplacecoef(k, (secondMembre.getCoef(k) - matrice.getCoef(0, k) * q.getCoef(k - 1)) / beta);
        }

        double an = matrice.getCoef(0, n - 1); 
        double bn = matrice.getCoef(1, n - 1); 
        double dn = secondMembre.getCoef(n - 1); 
        x.remplacecoef(n - 1, (dn - an * q.getCoef(n - 2)) / (an * p.getCoef(n - 2) + bn));

        for (int k = n - 2; k >= 0; k--) {
            x.remplacecoef(k, p.getCoef(k) * x.getCoef(k + 1) + q.getCoef(k));
        }
        return x;
    }

    public static void main(String[] args) throws IrregularSysLinException {
        System.out.println("Test de la méthode de Thomas");


        double[][] matriceData = { 
            { 0, -1, -1, -1 }, //sous diagonale
            { 2,  2,  2,  2 }, // diagonale
            { -1, -1, -1, 0 }, //sur diagonale
        };

        Mat3Diag mat3DiagA = new Mat3Diag(matriceData);
        System.out.println("Matrice A :\n" + mat3DiagA);

        Vecteur vect = new Vecteur(new double[] { -2, -2, -2, 23 });
        System.out.println("Vecteur b :\n" + vect);

        Thomas thomas = new Thomas(mat3DiagA, vect);

        Vecteur resultatX = thomas.resolution();
        System.out.println("Vecteur X :\n" + resultatX);

        System.out.println("=== Fin du test ===");

        System.out.println("Verfications :");

        System.out.println("Produit matrice vecteur :");
        Vecteur Ax = Mat3Diag.produitMat3Diag(mat3DiagA, resultatX);

        System.out.println("Matrice A * X :\n" + Ax);

        Vecteur b = new Vecteur(new double[] { -2, -2, -2, 23 });

        System.out.println("Vecteur b :\n" + b);

        Vecteur negB = new Vecteur(b.taille());
        for (int i = 0; i < b.taille(); i++) {
            negB.remplacecoef(i, b.getCoef(i) * -1);
        } // chaque coeficient est multiplié par -1 pour obtenir -b

        Matrice AxMoinsBMatrice = Matrice.addition(Ax, negB);
        Vecteur AxMoinsB = new Vecteur(AxMoinsBMatrice.nbLigne());
        AxMoinsB.recopie(AxMoinsBMatrice);
        System.out.println("Vecteur -b :\n" + negB);

        System.out.println("Matrice A * X - b :\n" + Matrice.addition(Ax, negB));
        
        double normeL1 = AxMoinsB.normeL1();
        double normeL2 = AxMoinsB.normeL2();
        double normeLinf = AxMoinsB.normeLinf();

        // verification de la solution
        System.out.println("Verification des Normes");
        System.out.println("Norme 1 de Ax-b: " + (normeL1 < Matrice.EPSILON));
        System.out.println("Norme 2 de Ax-b: " + (normeL2 < Matrice.EPSILON));
        System.out.println("norme infinie de Ax-b: " + (normeLinf < Matrice.EPSILON));

    }


}
