package Align;
public class SysTriangInf extends SysLin {

    public SysTriangInf(Matrice matriceSystem, Vecteur secondMembre) throws IrregularSysLinException {
        super(matriceSystem, secondMembre);

    }

    @Override
    public Vecteur resolution() throws IrregularSysLinException {
        int ordre = getOrdre();
        Vecteur solution = new Vecteur(ordre);

        for (int i = 0; i < ordre; i++) {
            double sum = 0;
            for (int j = 0; j < i; j++) {
                double term = getMatriceSystem().getCoef(i, j) * solution.getCoef(j);
                sum += term;
            }
            double coef = getMatriceSystem().getCoef(i, i);
            if (coef == 0) {
                throw new IrregularSysLinException("La matrice triangulaire inférieure contient un zéro sur la diagonale.");
            }
            double value = (getSecondMembre().getCoef(i) - sum) / coef;
            solution.remplacecoef(i, value);
        }

        return solution;
    }

    public static void main(String[] args) {
        try {

            double[][] matrixData = {
                {1, 0, 0},
                {4, 1, 0},
                {3, 0.666666666666667, 1}
            };

            double [] vectorData={3,5,8};
            
            Matrice matrice = new Matrice(matrixData);

            Vecteur secondMembre = new Vecteur(vectorData);

            // Create a SysTriangInf object
            SysTriangInf sysTriangInf = new SysTriangInf(matrice, secondMembre);

            // Solve the system
            Vecteur solution = sysTriangInf.resolution();

            Matrice axMoinsb = Matrice.addition(Matrice.produit(matrice, solution),secondMembre.produit(-1));

            Vecteur ax_b= new Vecteur(axMoinsb.nbLigne());

            ax_b.recopie(axMoinsb);

            // Print the solution
            System.out.println("Solution: Systeme  triangulaire inferieure ");
            for (int i = 0; i < solution.taille(); i++) {
                System.out.println("x" + i + " = " + solution.getCoef(i));
            }

            System.out.println(" verification Norme L1 " + (ax_b.normeL1() < Matrice.EPSILON));
            System.out.println(" verification Norme L2 " + (ax_b.normeL2() < Matrice.EPSILON));
            System.out.println(" verification Norme L infini  " + (ax_b.normeLinf() < Matrice.EPSILON));



        } catch (IrregularSysLinException e) {
            System.err.println(e);
        }
    }
}
