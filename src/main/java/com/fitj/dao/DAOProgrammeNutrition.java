package com.fitj.dao;

import com.fitj.classes.*;
import com.fitj.enums.ProgrammeType;
import kotlin.Pair;

import java.util.ArrayList;
import java.util.List;

public abstract class DAOProgrammeNutrition extends DAO{

    public DAOProgrammeNutrition() {
        super("programmenutrition");
    }

    /**
     * @param nom String, le nom du programme
     * @param description String, la description du programme
     * @param prix double, le prix du programme
     * @param type ProgrammeType, le type du programme
     * @param nbMois int, le nombre de mois du programme
     * @param coach Coach, le coach du programme
     * @param listeRecettes ArrayList<Recette>, la liste des recettes du programme
     * @return le ProgrammeNutrition crée
     * @throws Exception
     */
    public abstract ProgrammeNutrition createProgrammeNutrition(String nom, String description, double prix, ProgrammeType type, int nbMois, Coach coach, ArrayList<Recette> listeRecettes) throws Exception;

    /**
     * @param id int, l'id du programme
     * @return le ProgrammeNutrition récupéré dans la base de donnée
     * @throws Exception
     */
    public abstract ProgrammeNutrition getProgrammeNutritionId(int id) throws Exception;

    /**
     * @param updateList List<Pair<String,Object>>, la liste des objet à modifié dans la table pour le programme nutrition
     * @param id int, l'id du programme
     * @return le ProgrammeNutrition modifié dans la base de donnée
     * @throws Exception
     */
    public abstract ProgrammeNutrition updateProgrammeNutrition(List<Pair<String,Object>> updateList, int id) throws Exception;

    /**
     * Supprimer dans la base de donnée le programme nutrition
     * @param id int, l'id du programme
     * @throws Exception
     */
    public abstract void supprimerProgrammeNutrition(int id) throws Exception;

    /**
     * @param whereList List<Pair<String,Object>>, La liste des conditions du where à respecter (peut être vide)
     * @return la liste de tous les programmes nutrition
     * @throws Exception
     */
    public abstract List<ProgrammeNutrition> getAllProgrammeNutrition(List<Pair<String,Object>> whereList) throws Exception;

    /**
     * @param id int, l'id du programme
     * @return la liste des recettes du programme
     * @throws Exception
     */
    public abstract  List<Recette> getRecettes(int id) throws Exception;



}
