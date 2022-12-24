package com.fitj.dao;

import com.fitj.classes.Exercice;
import com.fitj.classes.Sport;
import com.fitj.enums.Sexe;
import kotlin.Pair;

import java.util.List;

/**
 * Classe parente de tous les modèles exercice qui permettent d'intéragir avec tout type de base de données
 * pour toutes modifications de cette dernière en rapport avec les exercices
 *
 * Classe abstraite non instanciable
 *
 * @author Etienne Tillier
 */
public abstract class DAOExercice extends DAO {
    public DAOExercice() {
        super("exercice");
    }

    /**
     * @param id int, l'id de l'exercice
     * @return l'exercice dans la base de donnée avec l'id rentré en paramètre
     * @throws Exception si une erreur SQL survient
     */
    public abstract Exercice getExerciceById(int id) throws Exception;

    /**
     * @return une liste contenant tous les exercices dans la base de donnée
     * @throws Exception si une erreur SQL survient
     */
    public abstract List<Exercice> getAllExercice() throws Exception;

    /**
     * @return une liste contenant tous les exercices dans la base de donnée
     * @param whereList List<Pair<String,Object>>, la liste des conditions du where pour la requête
     * @throws Exception si une erreur SQL survient
     */
    public abstract List<Exercice> getAllExerciceWhere(List<Pair<String,Object>> whereList) throws Exception;

    /**
     * Creer un exercice dans la base de donnée
     * @param nom String, le nom de l'exercice
     * @param description String, la description de l'exercice
     * @throws Exception en cas de problème lors de la création de l'exercice
     */
    public abstract Exercice createExercice(String nom, String description) throws Exception;

    /**
     * Supprimer l'exercice de la base de donnée
     * @param id int, l'id de l'exercice
     * @throws Exception si l'exercice n'existe pas
     */
    public abstract void supprimerExercice(int id) throws Exception;

    /**
     * Met à jour l'exercice dans la base de donnée
     * @param updateList List<Pair<String,Object>>, la liste des attributs à mettre à jour dans la base de donnée
     * @param id int, l'id de l'exercice
     * @return l'exercice mis à jour
     * @throws Exception si l'exercice n'existe pas dans la base de donnée
     */
    public abstract Exercice updateExercice(List<Pair<String,Object>> updateList, int id) throws Exception;

    /**
     * @param id int, l'id du coach
     * @return la liste des exercices du coach
     * @throws Exception en cas de problème lors de la requête SQL
     */
    public abstract List<Exercice> getAllExerciceByCoachId(int id) throws Exception;

}
