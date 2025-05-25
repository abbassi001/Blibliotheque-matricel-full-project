package Align;
import static java.lang.Math.abs;

public class Helder extends SysLin {

    public Helder(Matrice matriceSystem, Vecteur secondMembre) throws IrregularSysLinException {
        super(matriceSystem, secondMembre);

    }

    public void factorLDR() throws IrregularSysLinException {
        double sum = 0;

        for (int i = 0; i < getOrdre(); i++) {
            
            for (int j = 0; j < i; j++) {
                sum = 0;
                for (int k = 0; k < j; k++) {
                    sum += matriceSystem.getCoef(i, k) * matriceSystem.getCoef(k, k) * matriceSystem.getCoef(k, j);
                }
                
                matriceSystem.remplacecoef(i, j, (matriceSystem.getCoef(i, j) - sum) / matriceSystem.getCoef(j, j));
            }

            sum = 0;
            for (int k = 0; k < i; k++) {
                sum += matriceSystem.getCoef(i, k) * matriceSystem.getCoef(k, k) * matriceSystem.getCoef(k, i);
            }
          
            matriceSystem.remplacecoef(i, i, matriceSystem.getCoef(i, i) - sum);
            if (abs(matriceSystem.getCoef(i, i)) < Matrice.EPSILON) {
                throw new IrregularSysLinException("La matrice n'est pas régulière");
            }

            for (int j = i + 1; j < getOrdre(); j++) {
                sum = 0;
                for (int k = 0; k < i; k++) {
                    sum += matriceSystem.getCoef(i, k) * matriceSystem.getCoef(k, k) * matriceSystem.getCoef(k, j);
                }
                matriceSystem.remplacecoef(i, j, (matriceSystem.getCoef(i, j) - sum) / matriceSystem.getCoef(i, i));
            }

        }

    }

    public Vecteur resolutionPartielle() throws IrregularSysLinException {
        SysTriangInfUnite sysTriangInfUnite = new SysTriangInfUnite(matriceSystem, secondMembre);
        Vecteur z = sysTriangInfUnite.resolution();
        // System.out.println("z :");
        // System.out.println(z);
        SysDiagonal sysDiagonal = new SysDiagonal(matriceSystem, z);
        Vecteur y = sysDiagonal.resolution();
        // System.out.println("y :");
        // System.out.println(y);
        SysTriangSupUnite sysTriangSup = new SysTriangSupUnite(matriceSystem, y);
        Vecteur x = sysTriangSup.resolution();
        // System.out.println("x :");
        // System.out.println(x);

        return x;

    }

    @Override
    public Vecteur resolution() throws IrregularSysLinException {
        factorLDR();
        return resolutionPartielle();
    }

    public static void main(String[] args) throws IrregularSysLinException {

        System.out.println("-----------------------------------------------------------------------------");

        Matrice matrice = new Matrice("Align/src/Matrice.txt");
        Vecteur secondmembre = new Vecteur("Align/src/vecteur.txt");

        Matrice matrice2 = new Matrice("Align/src/Matrice.txt");
        Vecteur secondmembre2 = new Vecteur("Align/src/Matrice.txt");
        Helder helder2 = new Helder(matrice, secondmembre);
        System.out.println("Matrice Systeme :____________________________________________________");
        // System.out.println(helder2.resolution()); 
        Vecteur solution = helder2.resolution();

        for (int i = 0; i < solution.taille(); i++) {
            System.out.println("x" + i + " = " + solution.getCoef(i));

        }

        helder2.setSecondMembre(solution); // on remplace le second membre par la solution
        Vecteur x = helder2.resolutionPartielle(); // on resoud le systeme avec le second membre = solution

        Matrice aCarre = Matrice.produit(matrice2, matrice2);

        Matrice aCarreMoinsb = Matrice.addition(Matrice.produit(aCarre, x), secondmembre2.produit(-1));
        Vecteur aCarre_b = new Vecteur(aCarreMoinsb.nbLigne());

        // aCarre_b.recopie(aCarreMoinsb);

        // ax_b.recopie(axMoinsb);
        System.out.println("--------------------------Solution----------------------------------------------");

        // ---------------------------------------------------------------------------------------------------
        // System.out.println("Verification des Normes");
        // System.out.println("Norme 1 de Ax-b: " + (ax_b.normeL1() < Matrice.EPSILON));
        // System.out.println("Norme 2 de Ax-b: " + (ax_b.normeL2() < Matrice.EPSILON));
        // System.out.println("norme infinie de Ax-b: " + (ax_b.normeLinf() < Matrice.EPSILON));
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("Verification des Normes de A²x-b: ");
        System.out.println("Norme 1 de A²x-b: " + (aCarre_b.normeL1() < Matrice.EPSILON));
        System.out.println(aCarre_b.normeL1());
        System.out.println("Norme 2 de A²x-b: " + (aCarre_b.normeL2() < Matrice.EPSILON));
        System.out.println(aCarre_b.normeL2());
        System.out.println("norme infinie de A²x-b: " + (aCarre_b.normeLinf() < Matrice.EPSILON));
        System.out.println(aCarre_b.normeLinf());

    }
}
