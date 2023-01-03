package com.fitj.dao;

import com.fitj.classes.*;
import com.fitj.enums.ProgrammeType;
import kotlin.Pair;

import java.util.ArrayList;
import java.util.List;

public abstract class DAOProgrammeSportif extends DAO{

    public DAOProgrammeSportif() {
        super("programmesportif");
    }

    /**
     * @param nom String, le nom du programme
     * @param description String, la description du programme
     * @param prix double, le prix du programme
     * @param type ProgrammeType, le type du programme
     * @param nbMois int, le nombre de mois du programme
     * @param coach Coach, le coach du programme
     * @param listeSeances ArrayList<Seance>, la liste des recettes du programme
     * @return le ProgrammeNutrition crée
     * @throws Exception
     */
    public abstract ProgrammeSportif createProgrammeSportif(String nom, String description, double prix, ProgrammeType type, int nbMois, Coach coach, ArrayList<Seance> listeSeances) throws Exception;

    /**
     * @param id int, l'id du programme
     * @return le ProgrammeSportif récupéré dans la base de donnée
     * @throws Exception
     */
    public abstract ProgrammeSportif getProgrammeSportifById(int id) throws Exception;

    /**
     * @param updateList List<Pair<String,Object>>, la liste des objet à modifié dans la table pour le programme sportif
     * @param id int, l'id du programme
     * @return le ProgrammeSportif modifié dans la base de donnée
     * @throws Exception
     */
    public abstract ProgrammeSportif updateProgrammeSportif(List<Pair<String,Object>> updateList, int id) throws Exception;

    /**
     * Supprimer dans la base de donnée le programme sportif
     * @param id int, l'id du programme
     * @throws Exception
     */
    public abstract void supprimerProgrammeSportif(int id) throws Exception;

    /**
     * @return la liste de tous les programmes sportif
     * @throws Exception
     */
    public abstract List<ProgrammeSportif> getAllProgrammeSportif() throws Exception;

    /**
     * @return la liste de tous les programmes sportif
     * @param whereList List<Pair<String,Object>>, la liste des conditions du where pour la requête
     * @throws Exception
     */
    public abstract List<ProgrammeSportif> getAllProgrammeSportifWhere(List<Pair<String,Object>> whereList) throws Exception;

    /**
     * @param id int, l'id du programme
     * @return la liste des séances du programme
     * @throws Exception
     */
    public abstract List<Seance> getSeances(int id) throws Exception;

    /**
     * Ajouter une séance au programme sportif
     * @param seance Seance, la séance à ajouter au programme sportif
     * @param id int, l'id du programme sportif
     * @throws Exception
     */
    public abstract void ajouterSeanceProgramme(Seance seance, int id) throws Exception;

    /**
     * Supprimer une séance du programme sportif
     * @param seance Seance, la séance à supprimer du programme sportif
     * @param id int, l'id du programme sportif
     * @throws Exception
     */
    public abstract void supprimerSeanceProgramme(Seance seance, int id) throws Exception;


}
