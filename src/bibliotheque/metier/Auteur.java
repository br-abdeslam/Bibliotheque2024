package bibliotheque.metier;

import bibliotheque.metier.Livre;
import bibliotheque.metier.Ouvrage;
import bibliotheque.metier.TypeLivre;
import bibliotheque.metier.TypeOuvrage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Auteur {
    private  String nom,prenom;
    private String nationalite;
    private List<Ouvrage> louvrage = new ArrayList<>();

    public Auteur(String nom, String prenom, String nationalite) {
        this.nom = nom;
        this.prenom = prenom;
        this.nationalite = nationalite;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public List<Ouvrage> getLouvrage() {
        return louvrage;
    }

    public void setLouvrage(List<Ouvrage> louvrage) {
        this.louvrage = louvrage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auteur auteur = (Auteur) o;
        return Objects.equals(nom, auteur.nom) && Objects.equals(prenom, auteur.prenom) && Objects.equals(nationalite, auteur.nationalite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom, prenom, nationalite);
    }

    @Override
    public String toString() {
        return "Auteur{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", nationalite='" + nationalite + '\'' +
                '}';
    }
    public void addOuvrage(Ouvrage o ){
        louvrage.add(o);
        o.getLauteurs().add(this);
    }

    public void remove(Ouvrage o){
        louvrage.remove(o);
        o.getLauteurs().remove(this);
    }

    public List<Ouvrage> listerOuvrages(){
        //TODO lister ouvrages
        List<Ouvrage> ouvragesListe = new ArrayList<>();
        int i=0;
        for (Ouvrage o : louvrage) {
            ouvragesListe.add(o);
        }
        return ouvragesListe;
    }

    public List<Ouvrage> listerOuvrages(TypeOuvrage to){
        //TODO lister ouvrages d'un type
        List<Ouvrage> ouvragesFiltres = new ArrayList<>();
        for (Ouvrage o : louvrage) {
            if(o.to.equals(to)) {
                ouvragesFiltres.add(o);
            }
        }
        return ouvragesFiltres;
    }
    public List<Livre> listerLivres(TypeLivre tl){
        //TODO lister livres d'un type
        List<Livre> livreList = new ArrayList<>();
        for (Ouvrage o : louvrage){
            if(o instanceof Livre){
                Livre livre = (Livre) o;
                if(livre.getTl().equals(tl)){
                    livreList.add(livre);
                }
            }
        }
        return livreList;
    }
    public List<Ouvrage> listerOuvrages(String genre){
        //TODO lister ouvrages d'un genre
        List<Ouvrage> ouvragesDuGenre = new ArrayList<>();
        for (Ouvrage o : louvrage) {
            if (o.getGenre().equalsIgnoreCase(genre)) {
                ouvragesDuGenre.add(o);
            }
        }

        return ouvragesDuGenre;
    }
}
