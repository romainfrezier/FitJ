package com.fitj.dao;

import com.fitj.classes.Coach;
import com.fitj.classes.Exercice;
import com.fitj.classes.Seance;
import com.fitj.classes.Sport;
import kotlin.Pair;
import kotlin.Triple;

import java.util.List;

/**
 * Classe parente de tous les modèles séance qui permettent d'intéragir avec tout type de base de données
 * pour toutes modifications de cette dernière en rapport avec les séances
 *
 * Classe abstraite non instanciable
 *
 * @author Etienne Tillier
 */
public abstract class DAOSeance extends DAO {
    public DAOSeance() {
        super("seance");
    }


    /**
     * @param id int, l'id de la séance
     * @return la séance dans la base de donnée contenant l'id rentré en paramètre
     * @throws Exception si la séance n'existe pas
     */
    public abstract Seance getSeanceById(int id) throws Exception;

    /**
     * Créer une séance dans la base de donnée
     * @param nom String, le nom de la séance
     * @param description String, la description de la séance
     * @param prix double, le prix de la séance
     * @param coach Coach, le coach de la séance
     * @param sport Sport, le sport de la séance
     * @param exercices List<Triple<Exercice, Integer, Integer>>, la liste des exercices de la séance avec leur nombre de séries et de répétitions
     * @throws Exception si la séance existe déjà
     */
    public abstract Seance createSeance(String nom, String description, double prix, Coach coach, Sport sport, List<Triple<Exercice, Integer, Integer>> exercices) throws Exception;

    /**
     * @param idSport int, l'id du sport
     * @return une liste de séance qui font référence à ce sport
     * @throws Exception si le sport n'existe pas
     */
    public abstract List<Seance> getSeanceFromSport(int idSport) throws Exception;

    /**
     * @return une liste de séance contenant toutes les séances présentes dans la base de donnée
     * @throws Exception si la base de donnée n'est pas accessible
     */
    public abstract List<Seance> getAllSeances() throws Exception;

    /**
     * @return une liste de séance contenant toutes les séances présentes dans la base de donnée
     * @param whereList List<Pair<String,Object>>, la liste des conditions du where pour la requête
     * @throws Exception si la requête échoue
     */
    public abstract List<Seance> getAllSeancesWhere(List<Pair<String,Object>> whereList) throws Exception;

    /**
     * Supprimer de la base de donnée la séance avec l'id rentré en paramètre
     * @param id int, l'id de la séance
     * @throws Exception si la séance n'existe pas
     */
    public abstract void supprimerSeance(int id) throws Exception;

    /**
     * Met à jour la séance dans la base de donnée
     * @param updateList List<Pair<String,Object>>, la liste des objet à modifié dans la table pour la séance
     * @param id int, l'id de la séance
     * @return Seance, la séance modifiée
     * @throws Exception si la séance n'existe pas dans la base de donnée
     */
    public abstract Seance updateSeance(List<Pair<String,Object>> updateList, int id) throws Exception;

    /**
     * Ajoute un exercice à la séance
     * @param exercice Exercice, l'exercice à ajouter à la séance
     * @param nbrepetition int, le nombre de répétition de l'exercice
     * @param nbserie int, le nombre de série de l'exercice
     * @param id int, l'id de la séance
     * @throws Exception si la requête SQL échoue
     */
    public abstract void ajouterExercice(Exercice exercice, int nbrepetition, int nbserie, int id) throws Exception;

    /**
     * Supprime un exercice de la séance
     * @param exercice Exercice, l'exercice à supprimer de la séance
     * @param id int, l'id de la séance
     * @throws Exception si la requête SQL échoue
     */
    public abstract void supprimerExercice(Exercice exercice,int id) throws Exception;


    /**
     * @param id int, l'id de la séance
     * @return List<Triple<Exercice, Integer, Integer>>, la liste des exercices de la séance
     * @throws Exception si la séance n'existe pas
     */
    public abstract  List<Triple<Exercice, Integer, Integer>> getExercices(int id) throws Exception;
}
