package com.fitj.dao;

import com.fitj.classes.*;
import kotlin.Pair;

import java.util.List;

public abstract class DAOPack extends DAO{

    public DAOPack() {
        super("pack");
    }


    /**
     * Crée un pack dans la base de données
     * @param nom String, le nom du pack
     * @param description String, la description du pack
     * @param prix double, le prix du pack
     * @param coach Coach, le coach du pack
     * @throws Exception si la requête échoue
     */
    public abstract Pack createPack(String nom, String description, double prix, Coach coach) throws Exception;

    /**
     * Met à jour le pack dans la base de donnée
     * @param updateList List<Pair<String,Object>>, la liste des champs à mettre à jour
     * @param id int, l'id du pack
     * @return le pack dans la base de donnée mis à jour
     * @throws Exception si la requête échoue
     */
    public abstract Pack updatePack(List<Pair<String,Object>> updateList, int id) throws Exception;

    /**
     * Supprime le pack dans la base de donnée
     * @param id int, l'id du pack
     * @throws Exception si la requête échoue
     */
    public abstract void deletePack(int id) throws Exception;

    /**
     * @param id int, l'id du pack
     * @return le pack dans la base de donnée
     * @throws Exception si la requête échoue
     */
    public abstract Pack getPackById(int id) throws Exception;

    /**
     * @return la liste de tous les packs
     * @throws Exception si la requête échoue
     */
    public abstract List<Pack> getAllPack() throws Exception;

    /**
     * @param whereList List<Pair<String,Object>>, la liste des conditions du where pour la requête
     * @return la liste de tous les packs
     * @throws Exception si la requête échoue
     */
    public abstract List<Pack> getAllPackWhere(List<Pair<String,Object>> whereList) throws Exception;

    /**
     * @param idPack int, l'id du pack
     * @return la liste des produits du pack
     * @throws Exception si la requête échoue
     */
    public abstract List<Produit> getAllProduitByPack(int idPack) throws Exception;

    /**
     * @param idPack int, l'id du pack
     * @return la liste des programmes nutrition du pack
     * @throws Exception si la requête échoue
     */
    public abstract List<ProgrammeNutrition> getAllProgrammeNutritionByPack(int idPack) throws Exception;

    /**
     * @param idPack int, l'id du pack
     * @return la liste des programmes personnalisés du pack
     * @throws Exception si la requête échoue
     */
    public abstract List<ProgrammePersonnalise> getAllProgrammePersonnaliseByPack(int idPack) throws Exception;

    /**
     * @param idPack int, l'id du pack
     * @return la liste des programmes sportifs du pack
     * @throws Exception si la requête échoue
     */
    public abstract List<ProgrammeSportif> getAllProgrammeSportifByPack(int idPack) throws Exception;

    /**
     * @param idPack int, l'id du pack
     * @return la liste des séances du pack
     * @throws Exception si la requête échoue
     */
    public abstract List<Seance> getAllSeanceByPack(int idPack) throws Exception;

    /**
     * @param idPack int, l'id du pack
     * @return la liste des packs dans le pack
     * @throws Exception si la requête échoue
     */
    public abstract List<Pack> getAllPackByPack(int idPack) throws Exception;


    /**
     * Ajouter un produit au pack
     * @param produit Produit, le produit à ajouter au pack
     * @param idPack int, l'id du pack
     * @throws Exception si la requête échoue
     */
    public abstract void ajouterProduit(Produit produit, int idPack) throws Exception;

    /**
     * Ajouter un programme nutrition au pack
     * @param programmeNutrition ProgrammeNutrition, le programme nutrition à ajouter au pack
     * @param idPack int, l'id du pack
     * @throws Exception si la requête échoue
     */
    public abstract void ajouterProgrammeNutrition(ProgrammeNutrition programmeNutrition, int idPack) throws Exception;

    /**
     * Ajouter un programme personnalisé au pack
     * @param programmePersonnalise ProgrammePersonnalise, le programme personnalisé à ajouter au pack
     * @param idPack int, l'id du pack
     * @throws Exception si la requête échoue
     */
    public abstract void ajouterProgrammePersonnalise(ProgrammePersonnalise programmePersonnalise, int idPack) throws Exception;

    /**
     * Ajouter un programme sportif au pack
     * @param programmeSportif ProgrammeSportif, le programme sportif à ajouter au pack
     * @param idPack int, l'id du pack
     * @throws Exception si la requête échoue
     */
    public abstract void ajouterProgrammeSportif(ProgrammeSportif programmeSportif, int idPack) throws Exception;

    /**
     * Ajouter une séance au pack
     * @param seance Seance, la séance à ajouter au pack
     * @param idPack int, l'id du pack
     * @throws Exception si la requête échoue
     */
    public abstract void ajouterSeance(Seance seance, int idPack) throws Exception;

    /**
     * Ajouter un pack au pack
     * @param pack Pack, le pack à ajouter au pack
     * @param idPack int, l'id du pack
     * @throws Exception si la requête échoue
     */
    public abstract void ajouterPack(Pack pack, int idPack) throws Exception;

    /**
     * Supprimer un produit du pack
     * @param produit Produit, le produit à supprimer du pack
     * @param idPack int, l'id du pack
     * @throws Exception si la requête échoue
     */
    public abstract void supprimerProduit(Produit produit, int idPack) throws Exception;

    /**
     * Supprimer un programme nutrition du pack
     * @param programmeNutrition ProgrammeNutrition, le programme nutrition à supprimer du pack
     * @param idPack int, l'id du pack
     * @throws Exception si la requête échoue
     */
    public abstract void supprimerProgrammeNutrition(ProgrammeNutrition programmeNutrition, int idPack) throws Exception;

    /**
     * Supprimer un programme personnalisé du pack
     * @param programmePersonnalise ProgrammePersonnalise, le programme personnalisé à supprimer du pack
     * @param idPack int, l'id du pack
     * @throws Exception si la requête échoue
     */
    public abstract void supprimerProgrammePersonnalise(ProgrammePersonnalise programmePersonnalise, int idPack) throws Exception;

    /**
     * Supprimer un programme sportif du pack
     * @param programmeSportif ProgrammeSportif, le programme sportif à supprimer du pack
     * @param idPack int, l'id du pack
     * @throws Exception si la requête échoue
     */
    public abstract void supprimerProgrammeSportif(ProgrammeSportif programmeSportif, int idPack) throws Exception;

    /**
     * Supprimer une séance du pack
     * @param seance Seance, la séance à supprimer du pack
     * @param idPack int, l'id du pack
     * @throws Exception si la requête échoue
     */
    public abstract void supprimerSeance(Seance seance, int idPack) throws Exception;

    /**
     * Supprimer un pack du pack
     * @param pack Pack, le pack à supprimer du pack
     * @param idPack int, l'id du pack
     * @throws Exception si la requête échoue
     */
    public abstract void supprimerPack(Pack pack, int idPack) throws Exception;

    /**
     * Récupérer les packs d'un coach
     * @param id int, l'id du coach
     * @throws Exception si la requête échoue
     */
    public abstract List<Pack> getAllPackByCoach(int id) throws Exception;
}
