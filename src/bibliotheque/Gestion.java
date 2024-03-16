package bibliotheque;

import bibliotheque.metier.*;
import static bibliotheque.metier.TypeOuvrage.*;
import static bibliotheque.metier.TypeLivre.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Gestion {
    Scanner sc = new Scanner(System.in);

    private static List<Auteur> laut = new ArrayList<>();
    private static List<Lecteur> llect = new ArrayList<>();
    private static List<Ouvrage> louv= new ArrayList<>();
    private static List<Exemplaire> lex = new ArrayList<>();
    private static List<Rayon> lrayon= new ArrayList<>();
    private static List<Location> lloc = new ArrayList<>();


    public void populate(){
        Auteur a = new Auteur("Verne","Jules","France");
        laut.add(a);

        Livre l = new Livre("Vingt mille lieues sous les mers",10, LocalDate.of(1880,1,1),1.50,"français","aventure","a125",350, ROMAN,"histoire de sous-marin");
        louv.add(l);

        a.addOuvrage(l);

        a = new Auteur("Spielberg","Steven","USA");
        laut.add(a);

        DVD d = new DVD("AI",12,LocalDate.of(2000,10,1),2.50,"anglais","SF",4578l,"120 min",(byte)2);
        d.getAutresLangues().add("français");
        d.getAutresLangues().add("italien");
        d.getSousTitres().add("néerlandais");
        louv.add(d);

        a.addOuvrage(d);

        a = new Auteur("Kubrick","Stanley","GB");
        laut.add(a);

        a.addOuvrage(d);


        CD c = new CD("The Compil 2023",0,LocalDate.of(2023,1,1),2,"English","POP",1245,(byte)20,"100 min");
        louv.add(c);

        Rayon r = new Rayon("r12","aventure");
        lrayon.add(r);

        Exemplaire e = new Exemplaire("m12","état neuf",l);
        lex.add(e);
        e.setRayon(r);


        r = new Rayon("r45","science fiction");
        lrayon.add(r);

        e = new Exemplaire("d12","griffé",d);
        lex.add(e);

        e.setRayon(r);


        Lecteur lec = new Lecteur("Dupont","Jean",LocalDate.of(2000,1,4),"Mons","jean.dupont@mail.com","0458774411");
        llect.add(lec);

        Location loc = new Location(LocalDate.of(2023,2,1),LocalDate.of(2023,3,1),lec,e);
        lloc.add(loc);
        loc.setDateRestitution(LocalDate.of(2023,2,4));

        lec = new Lecteur ("Durant","Aline",LocalDate.of(1980,10,10),"Binche","aline.durant@mail.com","045874444");
        llect.add(lec);

        loc = new Location(LocalDate.of(2023,2,5),LocalDate.of(2023,3,5),lec,e);
        lloc.add(loc);
    }

    private void menu() {
        List options = new ArrayList<>(Arrays.asList("auteurs","ouvrages","exemplaires","rayons","lecteurs","locations","fin"));

        do{
            for(int i=0;i<options.size();i++){
                System.out.println((i+1)+"."+options.get(i));
            }

            int choix;
            do {
                System.out.println("choix :");
                choix = sc.nextInt();
                sc.skip("\n");
            } while(choix <1 || choix > options.size());
            switch (choix){
                case 1 :gestAuteurs(); break;
                case 2 : gestOuvrages();break;
                case 3 : gestExemplaires();break;
                case 4 : gestRayons();break;
                case 5 : gestLecteurs();break;
                case 6 : gestLocations();break;
                default:System.exit(0);
            }
        }  while (true);
    }



    private void listerOuvrage(){
        int i=0;
        for (Ouvrage o : louv) {
            System.out.println((i+1)+" "+o);
            i++;
        }
    }

    private void gestLocations() {
    //TODO lister exemplaires,lister lecteurs,créer la location avec le constructeur à deux paramètres(loueur,exemplaire
        System.out.println("Exemplaires: ");
        listerExemplaire();
        System.out.println("Choisir un exemplaire : ");
        int choixExemplaire= sc.nextInt();
        choixExemplaire-=1;
        System.out.println("Lecteurs: ");
        listerLecteur();
        System.out.println("Choisir un lecteur ");
        int choixLecteur = sc.nextInt();
        choixLecteur-=1;

        Lecteur louer = llect.get(choixLecteur);

        Exemplaire exemplaire = lex.get(choixExemplaire);

        Location location = new Location(louer,exemplaire);

        lloc.add(location);

    }

    private void listerLecteur(){
        int i=0;
        for(Lecteur l : llect){
            System.out.println((i+1)+". "+l);
            i++;
        }
    }


    private void listerExemplaire(){
        int i=0;
        for(Exemplaire e : lex){
            System.out.println((i+1)+". "+e);
            i++;
        }
    }


    private void gestLecteurs() {

        sc.skip("\n");
        System.out.println("nom ");
        String nom=sc.nextLine();
        System.out.println("prénom ");
        String prenom=sc.nextLine();
        System.out.println("date de naissance");
        String[] jma = sc.nextLine().split(" ");
        int j = Integer.parseInt(jma[0]);
        int m = Integer.parseInt(jma[1]);
        int a = Integer.parseInt(jma[2]);
        LocalDate dn= LocalDate.of(a,m,j);
        System.out.println("adresse");
        String adr=sc.nextLine();
        System.out.println("mail");
        String mail=sc.nextLine();
        System.out.println("tel ");
        String tel=sc.nextLine();
        Lecteur lect = new Lecteur(nom,prenom,dn,adr,mail,tel);
        llect.add(lect);
        System.out.println("lecteur créé");
    }

    private void gestRayons() {
        //TODO gérer rayons

        System.out.println("Code rayon: ");
        String codeRayon = sc.nextLine();

        System.out.println("Genre : ");
        String genre = sc.nextLine();

        Rayon rayon = new Rayon(codeRayon,genre);

        lrayon.add(rayon);
    }

    private void gestExemplaires() {
        //TODO afficher les ouvrages et choisir par sa position dans la liste
        //TODO demander autres infos de l'exemplaire et le créer
        listerOuvrage();
        System.out.println("Choisir un ouvrage: ");
        int choix=sc.nextInt();
        choix-=1;
        Ouvrage ouvrage = louv.get(choix);


        System.out.println("Matricule: ");
        String marticule = sc.nextLine();

        System.out.println("Etat: ");
        String etat = sc.nextLine();


        Exemplaire exemplaire = new Exemplaire(marticule,etat,ouvrage);
        lex.add(exemplaire);
        louv.get(choix).addExemplaire(exemplaire);



    }

    private void afficherRayon(){
        int i=0;
        for(Rayon r : lrayon){
            System.out.println((i+1)+". "+r);
            i++;
        }
    }

    private void gestOuvrages() {
        //TODO créer ouvrages
        System.out.println("Titre: ");
        String titre= sc.nextLine();
        System.out.println("Age Min: ");
        int ageMin = sc.nextInt();
        sc.nextLine();

        System.out.println("Date de parution AAAA-MM-JJ:");
        String dateStr = sc.nextLine();
        LocalDate date = LocalDate.parse(dateStr);

        System.out.println("Type d'ouvrage (LIVRE, CD, DVD): ");
        String typOuv = sc.nextLine().toUpperCase();
        try {
            TypeOuvrage to = TypeOuvrage.valueOf(typOuv);
        } catch (IllegalArgumentException e) {
            System.out.println("Entrée invalide. Veuillez choisir parmi LIVRE, CD, DVD.");
        }

        System.out.println("Prix de location: ");
        double prixLoc = sc.nextDouble();
        sc.nextLine();

        System.out.println("Langue: ");
        String langue= sc.nextLine();

        System.out.println("Genre: ");
        String genre= sc.nextLine();
        switch (typOuv){
            case "LIVRE":
                System.out.println("isbn");
                String isbn=sc.nextLine();

                System.out.println("Nombre de pages: ");
                int nbrpages= sc.nextInt();
                sc.nextLine();

                System.out.println("Type de livre (ROMAN,NOUVELLE,ESSAI,DOCUMENTAIRE,BIOGRAPHIE): ");
                String typLivre = sc.nextLine().toUpperCase();
                try {
                    TypeLivre tl = TypeLivre.valueOf(typLivre);
                } catch (IllegalArgumentException e) {
                    System.out.println("Entrée invalide. Veuillez choisir parmi ROMAN,NOUVELLE,ESSAI,DOCUMENTAIRE,BIOGRAPHIE.");
                }

                System.out.println("Resume: ");
                String resume= sc.nextLine();

                Livre livre = new Livre(titre,ageMin,date,prixLoc,langue,genre,isbn,nbrpages,TypeLivre.valueOf(typLivre),resume);
                louv.add(livre);

                 ;break;
            case "CD":
                System.out.println("Code: ");
                Long code = sc.nextLong();
                System.out.println("Nombre de plages: ");
                byte nbrePlages = sc.nextByte();
                sc.nextLine();
                System.out.println("Duree total: ");
                String dureeTotal= sc.nextLine();

                CD cd = new CD(titre,ageMin,date,prixLoc,langue,genre,code,nbrePlages,dureeTotal);
                louv.add(cd);

                 ;break;
            case "DVD":
                System.out.println("Code: ");
                Long codeDvd = sc.nextLong();
                sc.nextLine();

                System.out.println("Duree totale: ");
                String dureeT = sc.nextLine();

                System.out.println("Nombre de bonus: ");
                byte nbrBonus = sc.nextByte();

                DVD dvd = new DVD(titre,ageMin,date,prixLoc,langue,genre,codeDvd,dureeT,nbrBonus);
                louv.add(dvd);
                 ;break;
        }

    }


    private void gestAuteurs() {
        System.out.println("Entrez les infos de l'auteur: ");
        System.out.println("Nom : ");
        String nom= sc.nextLine();
        System.out.println("Prenom : ");
        String prenom = sc.nextLine();
        System.out.println("Nationalité");
        String nationalite = sc.nextLine();
        Auteur auteur = new Auteur(nom,prenom,nationalite);
        laut.add(auteur);
        System.out.println("l'auteur créé");
    }

    public static void main(String[] args) {
        Gestion g = new Gestion();
        g.populate();
        g.menu();
    }

}

