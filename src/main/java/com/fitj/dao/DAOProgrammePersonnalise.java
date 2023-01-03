package com.fitj.dao;

import com.fitj.classes.*;
import kotlin.Pair;

import java.util.List;

public abstract class DAOProgrammePersonnalise extends DAO{
    public DAOProgrammePersonnalise() {
        super("programmepersonnalise");
    }


    /**
     * Créer dans la base de donnée le programme personnalise
     * @param nom String, le nom du programme
     * @param description String, la description du programme
     * @param prix double, le prix du programme
     * @param coach Coach, le coach du programme
     * @return le programme personnalisé créé dans la base de donnée
     * @throws Exception
     */
    public abstract ProgrammePersonnalise createProgrammePersonnalise(String nom, String description, double prix, Coach coach) throws Exception;


    /**
     * Créer dans la base de donnée le programme personnalise relié à la demande
     * @param programmePersonnalise ProgrammePersonnalise, le programme personnalise à copier
     * @param demande Demande, la demande à ajouter au programme personnalise
     * @return le programme personnalise relié à la demande
     * @throws Exception
     */
    public abstract ProgrammePersonnalise createProgrammePersonnaliseDemande(ProgrammePersonnalise programmePersonnalise, Demande demande) throws Exception;

    /**
     * @param id int, l'id du programme
     * @return le ProgrammePersonnalise récupéré dans la base de donnée
     * @throws Exception
     */
    public abstract ProgrammePersonnalise getProgrammePersonnaliseById(int id) throws Exception;

    /**
     * @param updateList List<Pair<String,Object>>, la liste des objet à modifié dans la table pour le programme personnalise
     * @param id int, l'id du programme personnalise
     * @return le ProgrammePersonnalise modifié dans la base de donnée
     * @throws Exception
     */
    public abstract ProgrammePersonnalise updateProgrammePersonnalise(List<Pair<String,Object>> updateList, int id) throws Exception;

    /**
     * Supprimer dans la base de donnée le programme personnalise
     * @param id int, l'id du programme personnalise
     * @throws Exception
     */
    public abstract void supprimerProgrammePersonnalise(int id) throws Exception;

    /**
     * @return la liste de tous les programmes personnalise
     * @throws Exception
     */
    public abstract List<ProgrammePersonnalise> getAllProgrammePersonnalise() throws Exception;

    /**
     * @return la liste de tous les programmes personnalise
     * @param whereList List<Pair<String,Object>>, la liste des conditions du where pour la requête
     * @throws Exception
     */
    public abstract List<ProgrammePersonnalise> getAllProgrammePersonnaliseWhere(List<Pair<String,Object>> whereList) throws Exception;

    /**
     * @param id int, l'id du programme personnalise
     * @return la liste des programmes du programme personnalise
     * @throws Exception
     */
    public abstract List<Programme> getProgrammes(int id) throws Exception;

    /**
     * Ajouter un programme au programme personnalise
     * @param programme programme, le programme à ajouter au programme personnalise
     * @param id int, l'id du programme personnalise
     * @throws Exception
     */
    public abstract void ajouterProgrammeProgrammePersonnalise(Programme programme, int id) throws Exception;

    /**
     * Supprimer un programme du programme personnalise
     * @param programme Programme, le programme à supprimer du programme personnalise
     * @param id int, l'id du programme personnalise
     * @throws Exception
     */
    public abstract void supprimerProgrammeProgrammePersonnalise(Programme programme, int id) throws Exception;

}
