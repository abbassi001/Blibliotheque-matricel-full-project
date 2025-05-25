package Align;

public class ModPoly {
    private  double[] coefficient_ai;

    public ModPoly(int degre){
        this.coefficient_ai = new double[degre +1];
    }

    public double  function(int i ,double  x){
        return Math.pow(x, i);
    }

    public void identify(double[] x, double[] y) throws IrregularSysLinException
    {
        Matrice matrice_F=new Matrice(x.length , coefficient_ai.length);
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < coefficient_ai.length; j++) {
                matrice_F.remplacecoef(i, j, function(j, x[i]));
            }
            
        }

        Vecteur vecteur_Y=new Vecteur(y);

        Matrice matrice_F_transpose = matrice_F.transpose();

        Matrice matrice_F_x_Ft =Matrice.produit(matrice_F_transpose,matrice_F);

        Matrice matrice_Ft_x_vecteur = Matrice.produit(matrice_F_transpose,vecteur_Y);

        Vecteur secondmembre = new Vecteur(matrice_Ft_x_vecteur.nbLigne());
        
        for (int i = 0; i < matrice_Ft_x_vecteur.nbLigne(); i++) {
            secondmembre.remplacecoef(i, matrice_Ft_x_vecteur.getCoef(i, 0));
        }

        Helder solutionHelder = new Helder(matrice_F_x_Ft, secondmembre);
        Vecteur solutionVecteur = solutionHelder.resolution();

        

        for (int i = 0; i < coefficient_ai.length; i++) {
            coefficient_ai[i] = solutionVecteur.getCoef(i);            
        } 
        
    }

    public double eval(double x){
        double result = 0;
        for (int i = 0; i < coefficient_ai.length; i++) {
            result += coefficient_ai[i] * function(i, x);
        }
        return result;
    }

}
