package com.fitj.dao;

import com.fitj.classes.Demande;
import com.fitj.classes.Exercice;
import com.fitj.classes.ProgrammePersonnalise;
import com.fitj.classes.Sport;
import kotlin.Pair;

import java.util.List;

/**
 * Classe parente de tous les modèles demande qui permettent d'intéragir avec tout type de base de données
 * pour toutes modifications de cette dernière en rapport avec les demandes
 *
 * Classe abstraite non instanciable
 *
 * @author Etienne Tillier
 */
public abstract class DAODemande extends DAO {
    public DAODemande() {
        super("demande");
    }

    /**
     * @param id int, l'id de la demande
     * @return la demande dans la base de donnée avec l'id rentré en paramètre
     * @throws Exception
     */
    public abstract Demande getDemandeById(int id) throws Exception;

    /**
     * @return une liste contenant toutes les demandes dans la base de donnée
     * @throws Exception
     */
    public abstract List<Demande> getAllDemande() throws Exception;

    /**
     * @return une liste contenant tous les demandes dans la base de donnée
     * @param whereList List<Pair<String,Object>>, la liste des conditions du where pour la requête
     * @throws Exception
     */
    public abstract List<Demande> getAllDemandeWhere(List<Pair<String,Object>> whereList) throws Exception;


    /**
     * Créer une demande sur un programme personnalisé dans la base de donnée
     * @param nbMois int, le nombre de mois de la demande
     * @param description String, la description de la demande
     * @param sport Sport, la sport de la demande
     * @param programmeSportif boolean, si l'on veut un programme sportif dans le programme personnalise
     * @param programmeNutrition boolean, si l'on veut un programme nutrition dans le programme personnalise
     * @param nbSeanceSemaine int, le nombre de séances par semaine
     * @param nbRecetteSemaine int, le nombre de recettes par semaine
     * @param programmePersonnalise ProgrammePersonnalise, le programme personnalisé sur lequel on fait la demande
     * @return la demande créée dans la base de donnée
     * @throws Exception
     */
    public abstract Demande createDemande(int nbMois, String description, boolean programmeSportif, boolean programmeNutrition, int nbSeanceSemaine, int nbRecetteSemaine, Sport sport, ProgrammePersonnalise programmePersonnalise) throws Exception;

    /**
     * Supprimer la demande de la base de donnée
     * @param id int, l'id de la demande
     * @throws Exception
     */
    public abstract void supprimerDemande(int id) throws Exception;

    /**
     * Met à jour la demande dans la base de donnée
     * @param updateList List<Pair<String,Object>>, la liste des attributs à mettre à jour dans la base de donnée
     * @param id int, l'id de la demande
     * @return la demande mise à jour
     * @throws Exception
     */
    public abstract Demande updateDemande(List<Pair<String,Object>> updateList, int id) throws Exception;
}
