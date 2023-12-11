import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.lang.*;

public class SudokuBase {

        //.........................................................................
        // Fonctions utiles
        //.........................................................................

        /**
         * pré-requis : min <= max
         * résultat :   un entier saisi compris entre min et max, avec re-saisie éventuelle jusqu'à ce qu'il le soit
         */
        public static int saisirEntierMinMax(int min, int max) {

            int nb;
            do {
                System.out.println("Veuillez saisir un nombre compris entre " + min + " et " + max + " : ");
                nb = Ut.saisirEntier();

                if (nb < min || nb > max) {
                    System.out.println("Veuillez saisir un nombre valide compris entre " + min + " et " + max + " : ");
                    nb = Ut.saisirEntier();
                }

            }
            while (nb < min || nb > max);
            return nb;
        }  // fin saisirEntierMinMax

        //.........................................................................

        /**
         * MODIFICI
         * pré-requis : mat1 et mat2 ont les mêmes dimensions
         * action : copie toutes les valeurs de mat1 dans mat2 de sorte que mat1 et mat2 soient identiques
         */
        public static void copieMatrice(int[][] mat1, int[][] mat2) {

            for (int i = 0; i < mat1.length; i++) {
                for (int j = 0; j < mat1[i].length; j++) {
                    mat2[i][j] = mat1[i][j];
                }
            }
        }  // fin copieMatrice

        //.........................................................................

        /**
         * pré-requis :  n >= 0
         * résultat : un tableau de booléens représentant le sous-ensemble de l'ensemble des entiers
         * de 1 à n égal à lui-même
         */
        public static boolean[] ensPlein(int n) {

            boolean[] ens = new boolean[n+1];

            for (int i = 1; i <= n; i++) {
                ens[i] = true;
            }
            //Ut.afficher(ens);
            return ens;
        }  // fin ensPlein

        //.........................................................................


        /**
         * pré-requis : 1 <= val < ens.length
         * action :     supprime la valeur val de l'ensemble représenté par ens, s'il y est
         * résultat :   vrai ssi val était dans cet ensemble
         */
        public static boolean supprime(boolean[] ens, int val) {

            boolean result = false;

            for (int i = 1; i < ens.length; i++) {
                if (i == val) {
                    ens[i] = false;
                    result = true;
                }
            }
            //Ut.afficher(ens);
            return result;
        }  // fin supprime

        //.........................................................................


        /**
         * pré-requis : l'ensemble représenté par ens n'est pas vide
         * résultat :   un élément de cet ensemble
         */

        public static int uneValeur(boolean[] ens) {

            for(int i = 1; i< ens.length; i++){
                if (ens[i] == true){
                    return i;
                }
            }
            return 0;
        }  // fin uneValeur

    //.........................................................................

        /**
         * 1 2 3 4 5 6 7 8 9
         * -------------------
         * 1 |6 2 9|7 8 1|3 4 5|
         * 2 |4 7 3|9 6 5|8 1 2|
         * 3 |8 1 5|2 4 3|6 9 7|
         * -------------------
         * 4 |9 5 8|3 1 2|4 7 6|
         * 5 |7 3 2|4 5 6|1 8 9|
         * 6| 1 6 4|8 7 9|2 5 3|
         * -------------------
         * 7 3 8 1|5 2 7|9 6 4
         * 8 |5 9 6|1 3 4|7 2 8|
         * 9 |2 4 7|6 9 8|5 3 1|
         * -------------------
         * <p>
         * <p>
         * 1 2 3 4 5 6 7 8 9
         * -------------------
         * 1 |6 0 0|0 0 1|0 4 0|
         * 2 |0 0 0|9 6 5|0 1 2|
         * 3 |8 1 0|0 4 0|0 0 0|
         * -------------------
         * 4 |0 5 0|3 0 2|0 7 0|
         * 5 |7 0 0|0 0 0|1 8 9|
         * 6||0 0 0|0 7 0|0 0 3|
         * -------------------
         * 7 |3 0 0|0 2 0|9 0 4|
         * 8 |0 9 0|0 0 0|7 2 0|
         * 9 |2 4 0|6 9 0|0 0 0|
         * -------------------
         * <p>
         * <p>
         * pré-requis : 0<=k<=3 et g est une grille k^2xk^2 dont les valeurs sont comprises
         * entre 0 et k^2 et qui est partitionnée en k sous-carrés kxk
         * action : affiche la  grille g avec ses sous-carrés et avec les numéros des lignes
         * et des colonnes de 1 à k^2.
         * Par exemple, pour k = 3, on obtient le dessin d'une grille de Sudoku
         */
        public static void afficheGrille(int k, int[][] g) {

            Ut.afficher("    ");
            for (int i = 1; i <= k * k; i++) {
                Ut.afficher(i + " ");
                if (i % k == 0) {
                    Ut.afficher("| ");
                }
                if (i == g.length) {
                    Ut.afficherSL("");
                    Ut.afficher("   ------------------------");
                }
            }
            Ut.afficherSL("");

            for (int i = 0; i < k * k; i++) {
                if (i % k == 0 && i != 0) {
                    //Ut.afficher("   ");
                    for (int j = 0; j < k * k * 2 + k - 1; j++) {
                        if (j == k * k) {
                            Ut.afficher("   ------------------------");
                        }
                    }
                    Ut.afficherSL("");
                }
                Ut.afficher((i + 1) + " | ");
                for (int j = 0; j < k * k; j++) {
                    Ut.afficher(g[i][j] + " ");
                    if ((j + 1) % k == 0) {
                        Ut.afficher("| ");
                    }
                }
                Ut.afficherSL("");
            }
            Ut.afficher("---------------------------");
        }// fin afficheGrille


        //.........................................................................

        /** pré-requis : k > 0, 0 <= i< k^2 et 0 <= j < k^2
         *  résultat : (i,j) étant les coordonnées d'une case d'une grille k^2xk^2 partitionnée
         *             en k sous-carrés kxk, retourne les coordonnées de la case du haut à gauche
         *             du sous-carré de la grille contenant cette case.
         *  Par exemple, si k=3, i=5 et j=7, la fonction retourne (3,6).
         */

        public static int[] debCarre(int k,int i,int j){
            int[] coordDebCarre = new int[2];
            coordDebCarre[0]=(i/k) *k;
            coordDebCarre[1]=(j/k) * k;
            //Ut.afficher(coordDebCarre);
            return coordDebCarre;
        }  // fin debCarre


        //.........................................................................

        // Initialisation
        //.........................................................................

        /** MODIFICI
         *  pré-requis : gComplete est une matrice 9X9
         *  action   :   remplit gComplete pour que la grille de Sudoku correspondante soit complète
         *  stratégie :  les valeurs sont données directement dans le code et on peut utiliser copieMatrice pour mettre à jour gComplete
         */
        public static void initGrilleComplete(int [][] gComplete){

            int [][] gComplete1 =   {{ 5, 3, 4, 6, 7, 8, 9, 1, 2 },
                    { 6, 7, 2, 1, 9, 5, 3, 4, 8 },
                    { 1, 9, 8, 3, 4, 2, 5, 6, 7 },
                    { 8, 5, 9, 7, 6, 1, 4, 2, 3 },
                    { 4, 2, 6, 8, 5, 3, 7, 9, 1 },
                    { 7, 1, 3, 9, 2, 4, 8, 5, 6 },
                    { 9, 6, 1, 5, 3, 7, 2, 8, 4 },
                    { 2, 8, 7, 4, 1, 9, 6, 3, 5 },
                    { 3, 4, 5, 2, 8, 6, 1, 7, 9 }};

            copieMatrice(gComplete1,gComplete);
        } // fin initGrilleComplete

        //.........................................................................

        /** MODIFICI
         *  pré-requis : gSecret est une grille de Sudoku complète de mêmes dimensions que gIncomplete et 0 <= nbTrous <= 81
         *  action :     modifie gIncomplete pour qu'elle corresponde à une version incomplète de la grille de Sudoku gSecret (gIncomplete peut être complétée en gSecret),
         *               avec nbTrous trous à des positions aléatoires
         */
        public static void initGrilleIncomplete(int nbTrous, int [][] gSecret, int[][] gIncomplete){

            copieMatrice(gSecret,gIncomplete);

            for(int i=0; i<nbTrous;i++){
                int ligne = Ut.randomMinMax(0,8);
                int colonne = Ut.randomMinMax(0,8);

                while(gIncomplete[ligne][colonne] == 0){
                    ligne = Ut.randomMinMax(0,8);
                    colonne = Ut.randomMinMax(0,8);
                }

                gIncomplete[ligne][colonne] = 0;
            }
            //afficheGrille(3,gIncomplete);
        }// fin initGrilleIncomplete

        //.........................................................................


        /** pré-requis : 0 <= nbTrous <= 81
         *  résultat :   une grille  9x9 saisie dont les valeurs sont comprises ente 0 et 9
         *               avec exactement  nbTrous valeurs nulles
         *               et avec re-saisie jusqu'à ce que ces conditions soient vérifiées.
         *               On suppose dans la version de base que la grille saisie est bien une grille de Sudoku incomplète.
         *  stratégie : utilise la fonction saisirEntierMinMax
         */

        public static int [][] saisirGrilleIncomplete(int nbTrous){

            int [][] grille = new int[2][2];
            int compteurDeTrous = 0;

            while (compteurDeTrous != nbTrous){
                System.out.println("Veuillez saisir une grille de sudoku avec " + nbTrous + " trous: ");
                for (int i = 0; i < grille.length; i++) {
                    for (int j = 0; j < grille[i].length; j++) {
                        int nb = saisirEntierMinMax(0, 9);
                        if (nb == 0) {
                            compteurDeTrous++;
                        }
                        grille[i][j] = nb;
                    }
                }
            }

            //Ut.afficher(grille);
            return grille;

            //demande a l'utilisateur de saisir 81 caractere et lui dis de recommencer si le nombre de trou qu'il a mis correspond pas a nbtrous
        }  // fin saisirGrilleIncomplete

        //.........................................................................

        /** MODIFICI
        *  pré-requis : 0 <= nbTrous <= 81 ; g est une grille 9x9 (vide a priori) ;
        *               fic est un nom de fichier de ce répertoire contenant des valeurs de Sudoku
        *  action :   remplit g avec les valeurs lues dans fic. Si la grille ne contient pas des valeurs
        *             entre 0 et 9 ou n'a pas exactement nbTrous valeurs nulles, la méthode doit signaler l'erreur,
        *             et l'utilisateur doit corriger le fichier jusqu'à ce que ces conditions soient vérifiées.
        *             On suppose dans la version de base que la grille saisie est bien une grille de Sudoku incomplète.
        */
        public static void saisirGrilleIncompleteFichier(int nbTrous, int [][] g, String fic){
            //_________________________________________________
            int nbZero = 0;

            try (BufferedReader lecteur = new BufferedReader(new FileReader(fic))) {
                for (int i = 0 ; i < 9 ; i++){
                    String ligne = lecteur.readLine();
                    String [] valeurs = ligne.split("\\s+");
                    for (int j = 0 ; j < 9 ; j++) {
                        g[i][j] = Integer.parseInt(valeurs[j]);

                        if (g[i][j] == 0){
                            nbZero++;
                        }

                        if (nbZero != nbTrous){
                            System.out.println("la grille ne contient pas assez ou trop de trou !");
                        }

                        if(g[i][j] < 0 | g[i][j] > 9){
                            System.out.println("La grille contient des valeurs absurdes !" );
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } // fin saisirGrilleIncompleteFichier


        /** pré-requis : gOrdi est une grille de Sudoku incomplète,
         *               valPossibles est une matrice 9x9 de tableaux de 10 booléens
         *               et nbValPoss est une matrice 9x9 d'entiers
         *  action : met dans valPossibles l'ensemble des entiers de 1 à 9 pour chaque trou de gOrdi
         *           et leur nombre dans nbValPoss
         */


        public static void initPleines(int [][] gOrdi, boolean[][][] valPossibles, int [][] nbValPoss){
            //intialiser l'ensemble de la ligne
            //Valpoissible[0][0] = ensplein[10]
            //mettre tous les tableaux de bool val possible a 9


            for(int i = 0;i<gOrdi.length;i++){
                for (int j = 0;j<gOrdi[i].length;j++){
                    if (gOrdi[i][j]==0){
                        valPossibles[i][j] = ensPlein(9);
                        nbValPoss[i][j]=9;
                    }
                }
            }

            //Ut.afficher(valPossibles);
            //Ut.afficher(nbValPoss);

        }  // fin initPleines

        //.........................................................................


        /** pré-requis : gOrdi est une grille de Sudoku incomplète,
         *               0<=i<9, 0<=j<9,g[i][j]>0,
         *               valPossibles est une matrice 9x9 de tableaux de 10 booléens
         *               et nbValPoss est une matrice 9x9 d'entiers
         *  action : supprime dans les matrices valPossibles et nbValPoss la valeur gOrdi[i][j] pour chaque case de la ligne,
         *           de la colonne et du carré contenant la case (i,j) correspondant à un trou de gOrdi.
         */

        public static void suppValPoss(int [][] gOrdi, int i, int j, boolean[][][] valPossibles, int [][]nbValPoss){

            /*for(int colonne = i;colonne< gOrdi.length;colonne++){
                if (gOrdi[i][colonne]==0){
                    supprime(valPossibles[i][j],gOrdi[i][j]);
                    nbValPoss[i][colonne]--;
                }
            }

            for(int ligne = j;ligne<gOrdi.length;ligne++){
                if(gOrdi[ligne][j]==0){
                    supprime(valPossibles[i][j],gOrdi[i][j]);
                    nbValPoss[ligne][j]--;
                }
            }

            int[] debutcarre = debCarre(3,i,j);
            for(int ligne = debutcarre[0]; ligne < debutcarre[0] + 3; ligne ++){
                for(int colonne = debutcarre[1]; colonne < debutcarre[1] + 3; colonne ++){
                    if(ligne != i || colonne != j){
                        supprime(valPossibles[ligne][colonne],gOrdi[i][j]);
                        nbValPoss[ligne][colonne]--;
                    }
                }
            }*/

            int valeur = gOrdi[i][j];

            // Supprimer la valeur de la ligne
            for (int colonne = 0; colonne < 9; colonne++) {
                if (gOrdi[i][colonne] == 0) {
                    supprime(valPossibles[i][colonne],gOrdi[i][j]);
                    nbValPoss[i][colonne]--;
                }
            }

            // Supprimer la valeur de la colonne
            for (int ligne = 0; ligne < 9; ligne++) {
                if (gOrdi[ligne][j] == 0) {
                    supprime(valPossibles[ligne][j],gOrdi[i][j]);
                    nbValPoss[ligne][j]--;
                }
            }

            // Supprimer la valeur du carré
            int carreLigne = 3 * (i / 3);
            int carreColonne = 3 * (j / 3);

            for (int ligne = carreLigne; ligne < carreLigne + 3; ligne++) {
                for (int colonne = carreColonne; colonne < carreColonne + 3; colonne++) {
                    if (gOrdi[ligne][colonne] == 0) {
                        valPossibles[ligne][colonne][valeur] = false;
                        nbValPoss[ligne][colonne]--;
                    }
                }
            }
        } // fin suppValPoss


        //.........................................................................

        /** pré-requis : gOrdi est une grille de Sudoju incomplète,
         *               valPossibles est une matrice 9x9 de tableaux de 10 booléens
         *               et nbValPoss est une matrice 9x9 d'entiers
         * action :      met dans valPossibles l'ensemble des valeurs possibles de chaque trou de gOrdi
         *               et leur nombre dans nbValPoss
         */

        public static void initPossibles(int [][] gOrdi, boolean[][][] valPossibles, int [][]nbValPoss){

            // parcourir la grille entiere et pour chaque trou mettre dans val possible leur valeur possible.

            initPleines(gOrdi, valPossibles, nbValPoss);

            for(int i=0;i<gOrdi.length;i++) {
                for (int j = 0; j < gOrdi[i].length; j++) {

                    if (gOrdi[i][j] == 0) {
                        suppValPoss(gOrdi, i, j, valPossibles, nbValPoss);
                    }
                }
            }
        } // fin initPossibles

        //.........................................................................


        /** pré-requis : gSecret, gHumain et gOrdi sont des grilles 9x9
         *  action :    OK - demande au joueur humain de saisir le nombre nbTrous compris entre 0 et 81,
         *             OK  - met dans gSecret une grille de Sudoku complète,
         *              OK  - met dans gHumain une grille de Sudoku incomplète, pouvant être complétée en gSecret
         *                  et ayant exactement nbTrous trous de positions aléatoires,
         *               - met dans gOrdi une grille de Sudoku incomplète saisie par le joueur humain
         *                  ayant  nbTrous trous,
         *               - met dans valPossibles l'ensemble des valeurs possibles de chaque trou de gOrdi
         *                  et leur nombre dans nbValPoss.
         * retour : la valeur de nbTrous
         */
        public static int initPartie(int [][] gSecret, int [][] gHumain, int [][] gOrdi, boolean[][][] valPossibles, int [][]nbValPoss){

            int [][] gComplete =   {{ 5, 3, 4, 6, 7, 8, 9, 1, 2 },
                    { 6, 7, 2, 1, 9, 5, 3, 4, 8 },
                    { 1, 9, 8, 3, 4, 2, 5, 6, 7 },
                    { 8, 5, 9, 7, 6, 1, 4, 2, 3 },
                    { 4, 2, 6, 8, 5, 3, 7, 9, 1 },
                    { 7, 1, 3, 9, 2, 4, 8, 5, 6 },
                    { 9, 6, 1, 5, 3, 7, 2, 8, 4 },
                    { 2, 8, 7, 4, 1, 9, 6, 3, 5 },
                    { 3, 4, 5, 2, 8, 6, 1, 7, 9 }};


            /* demande le nombre de trou */
            int nbTrou = saisirEntierMinMax(0,81);
            copieMatrice(gComplete,gSecret);


            initGrilleIncomplete(nbTrou, gSecret,gHumain);


            saisirGrilleIncompleteFichier(nbTrou,gOrdi, ".\\Sudoku2\\grille.txt" );

            initPossibles(gOrdi,valPossibles,nbValPoss);

			/*Ut.afficher(gHumain);
			System.out.println("");
			Ut.afficher(gSecret);
			System.out.println("");
			Ut.afficher(gOrdi);*/

            return nbTrou;
        }

        //...........................................................
        // Tour du joueur humain
        //...........................................................

        /** pré-requis : gHumain est une grille de Sudoju incomplète pouvant se compléter en
         *               la  grille de Sudoku complète gSecret
         *
         *  résultat :   le nombre de points de pénalité pris par le joueur humain pendant le tour de jeu
         *
         *  action :     effectue un tour du joueur humain
         */
        public static int tourHumain(int [][] gSecret, int [][] gHumain){

            int nbPenalitee = 0;
            int i,j;
            do {
                System.out.println("Veuillez entrée les coordonnées du point que vous voulez modifié en commencant par la ligne puis la colonne: ");
                System.out.println("");
                afficheGrille(3, gHumain);
                System.out.println("");
                i = saisirEntierMinMax(0, 9);
                j = saisirEntierMinMax(0, 9);
            }while(gHumain[i][j] == 0);

            System.out.println("Voulez vous un joker ? tapée '1' pour oui et '0' pour non ");
            int joker = saisirEntierMinMax(0, 1);
            if (joker == 1) {
                nbPenalitee++;
                gHumain[i - 1][j - 1] = gSecret[i - 1][j - 1];
                afficheGrille(3, gHumain);
                return nbPenalitee;
            }

            do {
                gHumain[i - 1][j - 1] = saisirEntierMinMax(1, 9);
                nbPenalitee++;
            }while (gHumain[i - 1][j - 1] != gSecret[i - 1][j - 1]);
            afficheGrille(3, gHumain);

            System.out.println(nbPenalitee);
            return nbPenalitee;
        }  // fin  tourHumain

        //.........................................................................

        // Tour de l'ordinateur
        //.........................................................................

        /** pré-requis : gOrdi et nbValPoss sont des matrices 9x9
         *  résultat :   le premier trou (i,j) de gOrdi (c'est-à-dire tel que gOrdi[i][j]==0)
         *               évident (c'est-à-dire tel que nbValPoss[i][j]==1) dans l'ordre des lignes,
         *                s'il y en a, sinon le premier trou de gOrdi dans l'ordre des lignes
         *
         */
        public static int[] chercheTrou(int[][] gOrdi,int [][]nbValPoss){

            int itemporaire = -10;
            int jtemporaire = -10;

            for(int i = 0;i<gOrdi.length;i++) {
                for (int j = 0; j < gOrdi[i].length; j++) {

                    if (gOrdi[i][j] == 0) {
                        if (nbValPoss[i][j] == 1) {
                            return new int[]{i, j};
                        }
                        if (itemporaire == -10) {
                            itemporaire = i;
                            jtemporaire = j;
                        }
                    }
                }
            }
            return new int[] {itemporaire,jtemporaire} ;
        }  // fin chercheTrou

        //.........................................................................

        /** pré-requis : gOrdi est une grille de Sudoju incomplète,
         *               valPossibles est une matrice 9x9 de tableaux de 10 booléens
         *               et nbValPoss est une matrice 9x9 d'entiers
         *  action :     effectue un tour de l'ordinateur
         */
		public static int tourOrdinateur(int [][] gOrdi, boolean[][][] valPossibles, int [][]nbValPoss){


			int nbPenalitee = 0;
			int[] trou = chercheTrou(gOrdi, nbValPoss);

			if( nbValPoss[trou[0]][trou[1]] == 1){
				gOrdi[trou[0]][trou[1]] = nbValPoss[trou[0]][trou[1]];
			}
            else{
                nbPenalitee ++;
                Ut.afficher("L'ordinateur a choisis de prendre un joker, veuillez lui renseigner la case: " + trou);
                saisirEntierMinMax(1,9);
            }



			return nbPenalitee;

		}  // fin tourOrdinateur

		//.........................................................................

		// Partie
		//.........................................................................

		/** pré-requis : aucun
		 *
		 *  action : crée et initialise les matrices utilisées dans une partie, et effectue une partie de Sudoku entre le joueur humain et l'ordinateur.
		 *
		 *  résultat :   0 s'il y a match nul, 1 si c'est le joueur humain qui gagne et 2 sinon
		 */

		public static int partie(){




            int [][] gSecret= {{ 5, 3, 4, 6, 7, 8, 9, 1, 2 },
                               { 6, 7, 2, 1, 9, 5, 3, 4, 8 },
                               { 1, 9, 8, 3, 4, 2, 5, 6, 7 },
                               { 8, 5, 9, 7, 6, 1, 4, 2, 3 },
                               { 4, 2, 6, 8, 5, 3, 7, 9, 1 },
                               { 7, 1, 3, 9, 2, 4, 8, 5, 6 },
                               { 9, 6, 1, 5, 3, 7, 2, 8, 4 },
                               { 2, 8, 7, 4, 1, 9, 6, 3, 5 },
                               { 3, 4, 5, 2, 8, 6, 1, 7, 9 }};

            int [][] gHumain = new int[gSecret.length][gSecret.length];
            copieMatrice(gSecret,gHumain);

            int[][] gOrdi = new int[gSecret.length][gSecret.length];

            boolean [][][] valPossible = new boolean [9][9][10];

            int [][] nbValPoss = new int[9][9];



            initPartie(gSecret, gHumain, gOrdi, valPossible, nbValPoss);

            while(true){
                tourHumain(gSecret,gHumain);
                tourOrdinateur(gOrdi,valPossible,nbValPoss);
            }

		}  // fin partie

		//.........................................................................


		/** pré-requis : aucun
		 *  action :     effectue une partie de Sudoku entre le joueur humain et l'ordinateur
		 *               et affiche qui a gagné
		 */

        public static void main(String[] args){


            //int [][] mat2 = new int[9][9];

            //boolean[] tableauplein = {false, true, true, true, true, true, true, true, true, true};

            //int[][] gComplete = new int[9][9];


            int[][] gOrdi       =  {{5, 3, 0, 0, 7, 0, 0, 0, 0},
                    {6, 0, 0, 1, 9, 5, 0, 0, 0},
                    {0, 9, 8, 0, 0, 0, 0, 6, 0},
                    {8, 0, 0, 0, 6, 0, 0, 0, 3},
                    {4, 0, 0, 8, 0, 3, 0, 0, 1},
                    {7, 0, 0, 0, 2, 0, 0, 0, 6},
                    {0, 6, 0, 0, 0, 0, 2, 8, 0},
                    {0, 0, 0, 4, 1, 9, 0, 0, 5},
                    {0, 0, 0, 0, 8, 0, 0, 7, 9}};








            //saisirEntierMinMax(2,90);
            //copieMatrice(mat1,mat2);
            //ensPlein(9);
            //supprime(ensPlein(9),5);
            //afficheGrille(3, grilleatrou);
            //debCarre(3,5,7);
            //initGrilleComplete(gComplete);
            //initGrilleIncomplete(20,mat1,gComplete);
            //saisirGrilleIncomplete(1);
            //initPleines(gOrdi, valPossible, mat2);
            //suppValPoss(gOrdi,1,1,valPossible,mat2);
            //initPossibles(gOrdi, valPossible,nbValPoss);
            //initPartie(gSecret, gHumain, gOrdi, valPossible, nbValPoss);
            //tourHumain(gSecret, gHumain);
            //afficheGrille(3, gOrdi);
            //System.out.println(" ");
            //Ut.afficher(chercheTrou(gOrdi,nbValPoss));
            partie();


        }  // fin main

    } // fin SudokuBase

