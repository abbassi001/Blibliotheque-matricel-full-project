package Align;
public class SysTriangSup extends SysLin {

    public SysTriangSup(Matrice matriceSystem, Vecteur secondMembre) throws IrregularSysLinException {
        super(matriceSystem, secondMembre);

    }

    @Override
    public Vecteur resolution() throws IrregularSysLinException {
        int ordre = getOrdre();
        Vecteur solution = new Vecteur(ordre);

        for (int i = ordre - 1; i >= 0; i--) {
            double sum = 0;
            // System.out.println("Calculating x" + i + ":");
            for (int j = i + 1; j < ordre; j++) {
                double term = getMatriceSystem().getCoef(i, j) * solution.getCoef(j);
                sum += term;
                // System.out.println("  Adding " + getMatriceSystem().getCoef(i, j) + " * " + solution.getCoef(j) + " = " + term);
            }
            double coef = getMatriceSystem().getCoef(i, i);
            if (coef == 0) {
                throw new IrregularSysLinException("La matrice triangulaire supérieure contient un zéro sur la diagonale.");
            }
            double value = (getSecondMembre().getCoef(i) - sum) / coef;
            // System.out.println("  Subtracting sum from b" + i + ": " + getSecondMembre().getCoef(i) + " - " + sum + " = " + (getSecondMembre().getCoef(i) - sum));
            // System.out.println("  Dividing by " + coef + ": " + (getSecondMembre().getCoef(i) - sum) + " / " + coef + " = " + value);
            solution.remplacecoef(i, value);
        }

        return solution;
    }

    public static void main(String[] args) {
        try {
            // Create an upper triangular matrix
            double[][] matrixData = {
                {1, 1, -2},
                {0, 1, -1.5},
                {0, 0, 1}
            };

            double [] vectorData={3,5,8}; 

            Matrice matrice = new Matrice(matrixData);
            Vecteur secondMembre = new Vecteur(vectorData);

            // Create a SysTriangSup object
            SysTriangSup sysTriangSup = new SysTriangSup(matrice, secondMembre);

            // Solve the system
            Vecteur solution = sysTriangSup.resolution();
            Matrice axMoinsb = Matrice.addition(Matrice.produit(matrice, solution),secondMembre.produit(-1));
            Vecteur ax_b= new Vecteur(axMoinsb.nbLigne());
            ax_b.recopie(axMoinsb);


            // Print the solution
            System.out.println("Solution:");
            for (int i = 0; i < solution.taille(); i++) {
                System.out.println("x" + i + " = " + solution.getCoef(i));
            }

            System.out.println("Verification des Normes");

            System.out.println("Norme 1 de Ax-b: " +(ax_b.normeL1()< Matrice.EPSILON));
            System.out.println("Norme 2 de Ax-b: " +(ax_b.normeL2()< Matrice.EPSILON));
            System.out.println("Norme infinie de Ax-b: " +(ax_b.normeLinf()< Matrice.EPSILON));


            
        } catch (IrregularSysLinException e) {
            System.err.println(e);
        }
    }
}
