package com.fitj.classes;

import com.fitj.enums.Sexe;
import com.fitj.facades.FacadeClient;
import com.fitj.interfaces.NotifReceiver;
import com.fitj.interfaces.ProductReceiver;

import java.util.*;

/**
 * Une classe qui représente un client.
 * @author Paco Munarriz
 *
 */
public class Client implements ProductReceiver, NotifReceiver {

    /**
     * La facade du client pour exécuter des méthodes sur l'interface et sur la bd
     */
    private FacadeClient facadeClient;

    /**
     * Le sexe du client
     */
    private Sexe sexe;


    /**
     * L'id du client
     */
    private int id;


    /**
     * L'email du client
     */
    private String email;
    /**
     * Le pseudo du client
     */
    private String pseudo;

    /**
     * Le password du client
     */
    private String password;


    /**
     * Le poids du client
     */
    private double poids;

    /**
     * La photo du client
     */
    private String photo;

    /**
     * La taille du client
     */
    private int taille;

    /**
     * La liste des sports du client
     */
    private List<Sport> listeSport;

    /**
     * La liste des commandes du client
     */
    private List<Commande> listeCommande;

    /**
     * La liste de matériel du client
     */
    private List<Materiel> listeMateriel;

    /**
     * Default constructor
     */
    public Client(String email, String pseudo, double poids, String photo, int taille, Sexe sexe, String password, int id) {
        this.id = id;
        this.email = email;
        this.pseudo = pseudo;
        this.poids = poids;
        this.photo = photo;
        this.taille = taille;
        this.sexe = sexe;
        this.password = password;
        this.facadeClient = FacadeClient.getInstance();
        this.listeSport = new ArrayList<>();
        this.listeCommande = new ArrayList<>();
        this.listeMateriel = new ArrayList<>();
    }


    /**
     * @return int, l'id du client
     */
    public int getId() {
        return id;
    }

    /**
     * Set l'id du client
     * @param id l'id du client
     */
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Client{" +
                "facadeClient=" + facadeClient +
                ", sexe=" + sexe +
                ", email='" + email + '\'' +
                ", pseudo='" + pseudo + '\'' +
                ", password='" + password + '\'' +
                ", poids=" + poids +
                ", photo='" + photo + '\'' +
                ", taille=" + taille +
                ", listeSport=" + listeSport +
                ", listeCommande=" + listeCommande +
                ", listeMateriel=" + listeMateriel +
                '}';
    }

    public FacadeClient getFacadeClient() {
        return facadeClient;
    }

    /**
     * @return le sexe du client
     */
    public Sexe getSexe() {
        return sexe;
    }

    /**
     * @return le password du client
     */
    public String getPassword(){
        return password;
    }

    /**
     * Set le password du client
     * @param password String, le password du client
     */
    public void setPassword(String password){
        this.password = password;
    }

    /**
     * Set le sexe du client
     * @param sexe Sexe, le sexe du client
     */
    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    /**
     * @return l'email du client
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return le pseudo du client
     */
    public String getPseudo() {
        return pseudo;
    }

    /**
     * @return le poids du client
     */
    public double getPoids() {
        return poids;
    }

    /**
     * @return la photo du client
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * @return la taille du client
     */
    public int getTaille() {
        return taille;
    }

    public void setFacadeClient(FacadeClient facadeClient) {
        this.facadeClient = facadeClient;
    }

    /**
     * Set l'email du client
     * @param email String, l'email du client
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Set le pseudo du client
     * @param pseudo String, le pseudo du client
     */
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    /**
     * Set le poids du client
     * @param poids double, le poids du client
     */
    public void setPoids(double poids) {
        this.poids = poids;
    }

    /**
     * Set le lien vers la photo du client
     * @param photo String, le lien vers la photo du client
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * Set la taille du client
     * @param taille int, la taille du client
     */
    public void setTaille(int taille) {
        this.taille = taille;
    }

    /**
     * @return List<Sport>, la liste des sports du client
     */
    public List<Sport> getListeSport() {
        return listeSport;
    }

    /**
     * @return List<Commande>, La liste des commandes du client
     */
    public List<Commande> getListeCommande() {
        return listeCommande;
    }

    /**
     * @return List<Materiel>, La liste de matériel du client
     */
    public List<Materiel> getListeMateriel() {
        return listeMateriel;
    }

    /**
     * Set la liste des sports
     * @param listeSport List<Sport>, la liste des sports
     */
    public void setListeSport(List<Sport> listeSport) {
        this.listeSport = listeSport;
    }

    /**
     * Set la liste des commandes
     * @param listeCommande List<Commande>, la liste des commandes
     */
    public void setListeCommande(List<Commande> listeCommande) {
        this.listeCommande = listeCommande;
    }

    /**
     * Set la liste des matériels
     * @param listeMateriel List<Materiel>, la liste des matériel
     */
    public void setListeMateriel(List<Materiel> listeMateriel) {
        this.listeMateriel = listeMateriel;
    }

    /**
     * TODO test
     * Ajoute un sport au client
     * @param sport Sport, le sport à ajouter au client
     */
    public void ajouterSport(Sport sport){
        if (this.listeSport.contains(sport)) {
            this.listeSport.add(sport);
        }
        else {
            //erreur sport
        }
    }

    /**
     * TODO test
     * Ajoute une commande au client
     * @param commande Commande, la commande à ajouter au client
     */
    public void ajouterCommande(Commande commande){
        if (this.listeCommande.contains(commande)){
            this.listeCommande.add(commande);
        }
        else {
            //erreur commande
        }
    }

    /**
     * TODO test
     * Ajoute un matériel au client
     * @param materiel Materiel, le matériel à ajouter au client
     */
    public void ajouterMateriel(Materiel materiel){
        if (this.listeMateriel.contains(materiel)){
            this.listeMateriel.add(materiel);
        }
        else {
            //erreur materiel
        }
    }


    /**
     * @param content
     * Ajoute le produit à la liste de produit du client.
     */
    public void receive(Produit content) {
        // TODO implement here
    }

    /**
     * @param content
     * Ajoute la notification à la liste de notification du client.
     */
    public void receive(Notification content) {
        // TODO implement here
    }

}