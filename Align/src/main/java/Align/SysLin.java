package Align;

public abstract class SysLin {
    private final int ordre;
    protected Matrice matriceSystem;
    protected Vecteur secondMembre;

    public SysLin(Matrice matriceSystem, Vecteur secondMembre) throws IrregularSysLinException {
    //     if (matriceSystem.nbColonne() != matriceSystem.nbLigne()) {
    //         throw new IrregularSysLinException("La matrice doit être carrée.");
    //     }
    //     if (matriceSystem.nbLigne() != secondMembre.taille()) {
    //         throw new IrregularSysLinException("La taille de la matrice doit correspondre à la taille du second membre.");
    //     }
        this.matriceSystem = matriceSystem;
        this.secondMembre = secondMembre;
        this.ordre = matriceSystem.nbLigne();

    }

    public int getOrdre() {
        return ordre;
    }

    public Matrice getMatriceSystem() {
        return matriceSystem;
    }

    public Vecteur getSecondMembre() {
        return secondMembre;
    }

    public void setSecondMembre(Vecteur secondMembre) {
        this.secondMembre = secondMembre;
    }
    public abstract Vecteur resolution() throws IrregularSysLinException;
}