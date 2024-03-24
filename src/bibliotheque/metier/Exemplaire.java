package bibliotheque.metier;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

public class Exemplaire {

    private String matricule;
    private String descriptionEtat;

    private Ouvrage ouvrage;
    private Rayon rayon;

    private List<Location> lloc= new ArrayList<>();


    public Exemplaire(String matricule, String descriptionEtat,Ouvrage ouvrage) {
        this.matricule = matricule;
        this.descriptionEtat=descriptionEtat;
        this.ouvrage = ouvrage;
        this.ouvrage.getLex().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exemplaire that = (Exemplaire) o;
        return Objects.equals(matricule, that.matricule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matricule);
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getDescriptionEtat() {
        return descriptionEtat;
    }

    public void setDescriptionEtat(String descriptionEtat) {
        this.descriptionEtat = descriptionEtat;
    }

    public Ouvrage getOuvrage() {
        return ouvrage;
    }

    public void setOuvrage(Ouvrage ouvrage) {
        this.ouvrage = ouvrage;
    }

    public Rayon getRayon() {
        return rayon;
    }

    public void setRayon(Rayon rayon) {
        if(this.rayon!=null) this.rayon.getLex().remove(this);
        this.rayon=rayon;
        this.rayon.getLex().add(this);
    }

    public List<Location> getLloc() {
        return lloc;
    }

    public void setLloc(List<Location> lloc) {
        this.lloc = lloc;
    }

    @Override
    public String toString() {
        return "Exemplaire{" +
                "matricule='" + matricule + '\'' +
                ", descriptionEtat='" + descriptionEtat + '\'' +
                ", ouvrage=" + ouvrage +
                ", rayon=" + rayon +
                '}';
    }

    public void modifierEtat(String etat){
        //TODO modifier etat exemplaire
        System.out.println("l'état ancienne de l'exemplaire: "+this.descriptionEtat);
        this.descriptionEtat=etat;
        System.out.println("l'état actuelle de l'exemplaire: "+this.descriptionEtat);

    }

    public Lecteur lecteurActuel(){
        //TODO lecteur actuel exemplaire
       Lecteur lecteur;
       int size;
       size= lloc.size();
       size-=1;
       lecteur= lloc.get(size).getLoueur();
        return lecteur;
    }
    public List<Lecteur> lecteurs(){
        //TODO lecteurs exemplaire
        List<Lecteur> lecteursList = new ArrayList<>();
        for (Location loc : lloc){
            if(!lecteursList.contains(loc.getLoueur())){
                lecteursList.add(loc.getLoueur());
            }
        }
        return lecteursList;
    }

    public void envoiMailLecteurActuel(Mail mail){
        //TODO envoi mail lecteur exemplaire
        Lecteur lecteurActuel = lecteurActuel();
        if (lecteurActuel != null) {
            System.out.println("Envoi de mail à : " + lecteurActuel.getMail());
            System.out.println("Objet : " + mail.getObjet());
            System.out.println("Message : " + mail.getMessage());
            System.out.println("Date d'envoi : " + mail.getDateEnvoi());
        } else {
            System.out.println("Aucun lecteur actuel pour cet exemplaire.");
        }
    }
    public void envoiMailLecteurs(Mail mail){
        //TODO envoi mail lecteurs exemplaire
        //Liste pour éviter d'envoyer plusieurs mails au même lecteur
        Set<Lecteur> lecteursUniques = new HashSet<>();

        // Ajouter tous les lecteurs qui ont loué cet exemplaire à l'ensemble
        for (Location location : lloc) {
            lecteursUniques.add(location.getLoueur());
        }

        // Simuler l'envoi d'un mail à chaque lecteur unique
        for (Lecteur lecteur : lecteursUniques) {
            System.out.println("Envoi de mail à : " + lecteur.getMail());
            System.out.println("Objet : " + mail.getObjet());
            System.out.println("Message : " + mail.getMessage());
            System.out.println("Date d'envoi : " + mail.getDateEnvoi());
            System.out.println("----");
        }
    }

    public boolean enRetard(){
        //TODO enretard exemplaire
        if (lloc.isEmpty()) {
            // S'il n'y a pas de locations, l'exemplaire ne peut pas être en retard
            return false;
        }

        // Obtenir la dernière location de l'exemplaire
        Location derniereLocation = lloc.get(lloc.size() - 1);
        LocalDate dateRetourPrevue = null;

        // Calculer la date de retour prévue en fonction du type de l'ouvrage
        if (ouvrage instanceof Livre) {
            dateRetourPrevue = derniereLocation.getDateLocation().plusDays(15);
        } else if (ouvrage instanceof DVD) {
            dateRetourPrevue = derniereLocation.getDateLocation().plusDays(3);
        } else if (ouvrage instanceof CD) {
            dateRetourPrevue = derniereLocation.getDateLocation().plusDays(7);
        }

        // Vérifier si la date actuelle est après la date de retour prévue
        return dateRetourPrevue != null && LocalDate.now().isAfter(dateRetourPrevue);

    }

    public int joursRetard(){
        //TODO jours retard exemplaire
        if (lloc.isEmpty()) {
            // S'il n'y a pas de locations, il n'y a pas de retard
            return 0;
        }

        // Obtenir la dernière location
        Location derniereLocation = lloc.get(lloc.size() - 1);
        LocalDate dateRetourPrevue = null;

        // Calculer la date de retour prévue en fonction du type d'ouvrage
        if (ouvrage instanceof Livre) {
            dateRetourPrevue = derniereLocation.getDateLocation().plusDays(15);
        } else if (ouvrage instanceof DVD) {
            dateRetourPrevue = derniereLocation.getDateLocation().plusDays(3);
        } else if (ouvrage instanceof CD) {
            dateRetourPrevue = derniereLocation.getDateLocation().plusDays(7);
        }

        // Si la date de restitution est passée, calculer les jours de retard
        if (dateRetourPrevue != null && LocalDate.now().isAfter(dateRetourPrevue)) {
            return Period.between(dateRetourPrevue, LocalDate.now()).getDays();
        }

        // Aucun retard si la date de restitution n'est pas passée
        return 0;
    }


    public boolean enLocation(){
        //TODO en location exemplaires
        if (lloc.isEmpty()) {
            // Si la liste des locations est vide, l'exemplaire n'est pas en location
            return false;
        }

        // Obtenir la dernière location de l'exemplaire
        Location derniereLocation = lloc.get(lloc.size() - 1);

        // Vérifier si la date de restitution est nulle ou dans le futur
        return derniereLocation.getDateRestitution() == null || LocalDate.now().isBefore(derniereLocation.getDateRestitution());
    }


}
