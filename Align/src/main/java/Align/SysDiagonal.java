package Align;
public class SysDiagonal extends SysLin {

    public SysDiagonal(Matrice matriceSystem, Vecteur secondMembre) throws IrregularSysLinException {
        super(matriceSystem, secondMembre);
    }



    @Override
    public Vecteur resolution() throws IrregularSysLinException {
        int ordre = getOrdre();
        Vecteur solution = new Vecteur(ordre);

        for (int i = 0; i < ordre; i++) {
            double coef = getMatriceSystem().getCoef(i, i);
            if (coef == 0) {
                throw new IrregularSysLinException("La matrice diagonale contient un zéro sur la diagonale.");
            }
            double value = getSecondMembre().getCoef(i) / coef;
            solution.remplacecoef(i, value);
        }

        return solution;
    }

    public static void main(String[] args) {
        try {
            // Create a diagonal matrix
     
            Matrice matrice = new Matrice("src/Matrice.txt");

            // Create a vector
            Vecteur secondMembre = new Vecteur("src/vecteur.txt");

            // Create a SysDiagonal object
            SysDiagonal sysDiagonal = new SysDiagonal(matrice, secondMembre);

            // Solve the system
            Vecteur solution = sysDiagonal.resolution();

            // Print the solution
            System.out.println("Solution:");
            for (int i = 0; i < solution.taille(); i++) {
                System.out.println("x" + i + " = " + solution.getCoef(i));
            }
        } catch (IrregularSysLinException e) {
            System.err.println(e);
        }
    }
}