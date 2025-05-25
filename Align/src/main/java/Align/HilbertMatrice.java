package Align;
import java.util.Scanner;

public class HilbertMatrice extends Matrice {

    public HilbertMatrice(int n) {
        super(n, n); 
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.coefficient[i][j] = 1.0 / (i + j + 1);
            }
        }
    }


    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int ordre;

        do {
            System.out.print("Veuillez entrer l'ordre de la matrice (entre 3 et 15) : ");
            ordre = sc.nextInt();
        } while (ordre < 3 || ordre > 15);

        HilbertMatrice hilbertMatrice = new HilbertMatrice(ordre);
        System.out.println("\n Matrice de Hilbert d'ordre " + ordre + " :");
        System.out.print(hilbertMatrice);
        Matrice identite = Matrice.identite(ordre);
        System.out.println("\n Matrice identité d'ordre " + ordre + " :");
        System.out.print(identite);

        Matrice copie= new Matrice(ordre,ordre);
        copie.recopie(hilbertMatrice);

        Matrice inverse = hilbertMatrice.inverse();
        System.out.println("\nMatrice inverse :");
        System.out.print(inverse);

        Matrice produit = Matrice.produit(copie, inverse);
        System.out.println("\n Produit de la matrice initiale et de son inverse :");
        System.out.print(produit);

        Matrice diff = Matrice.addition(produit,identite.produit(-1));


        System.out.println("Conditionnement 1 de la matrice : " + hilbertMatrice.cond_1());
        System.out.println("Conditionnement infini de de la matrice : " + hilbertMatrice.cond_inf());

        System.out.println("difference entre valeur absolue de  produit - identite :" + Math.abs(diff.norme_1()));
        System.out.println("difference entre valeur absolue de  produit - identite :" + Math.abs(diff.norme_inf()));

        if (Math.abs(diff.norme_1()) < Matrice.EPSILON) {
            System.out.println("***  La norme 1 est inférieur à epsilon ***");
        } else {
            System.out.println("*** La norme 1 est supérieur à epsilon ***");
        }

        if (Math.abs(diff.norme_inf()) < Matrice.EPSILON) {
            System.out.println("*** La norme infinie est inférieur à epsilon ***");
        } else {
            System.out.println("*** La norme infinie est supérieur à epsilon ***");
        }

        sc.close();
    }
}

    
        

