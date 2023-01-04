package com.fitj.dao.factory;

import com.fitj.dao.*;
import com.fitj.dao.postgresql.*;

/**
 * Classe de la factory de model PostgreSQL qui permettent de créer des model PostgreSQL
 * Classe (singleton)
 *
 * @author Etienne Tillier, Romain Frezier
 */
public class FactoryDAOPostgreSQL extends FactoryDAO {

    /**
     * constructeur de la factory
     */
    public FactoryDAOPostgreSQL(){
        super();

    }

    /**
     * Méthode permettant de récupérer une instance de la DAOClient
     * @return l'instance du modelClientPostgreSQL
     */
    @Override
    public DAOClient getDAOClient() {
        return new DAOClientPostgreSQL();
    }

    /**
     * Méthode permettant de récupérer une instance de la DAOSeance
     * @return l'instance du modelSeancePostgreSQL
     */
    @Override
    public DAOSeance getDAOSeance() {
        return new DAOSeancePostgreSQL();
    }

    /**
     * Méthode permettant de récupérer une instance de la DAOPaiement
     * @return l'instance du modelPaiementPostgreSQL
     */
    @Override
    public DAOPaiement getDAOPaiement() {
        return new DAOPaiementPostgreSQL();
    }

    /**
     * Méthode permettant de récupérer une instance de la DAOExercice
     * @return l'instance du modelExercicePostgreSQL
     */
    @Override
    public DAOExercice getDAOExercice() {
        return new DAOExercicePostgreSQL();
    }

    /**
     * Méthode permettant de récupérer une instance de la DAOAliment
     * @return l'instance du modelAlimentPostgreSQL
     */
    @Override
    public DAOAliment getDAOAliment() {
        return new DAOAlimentPostgreSQL();
    }

    /**
     * Méthode permettant de récupérer une instance de la DAORecette
     * @return l'instance du modelRecettePostgreSQL
     */
    @Override
    public DAORecette getDAORecette() {
        return new DAORecettePostgreSQL();
    }

    /**
     * Méthode permettant de récupérer une instance de la DAOAvis
     * @return l'instance du modelAvisPostgreSQL
     */
    @Override
    public DAOAvis getDAOAvis() {
        return new DAOAvisPostgreSQL();
    }

    /**
     * Méthode permettant de récupérer une instance de la DAOCommande
     * @return l'instance du modelCommandePostgreSQL
     */
    @Override
    public DAOCommande getDAOCommande() {
        return new DAOCommandePostgreSQL();
    }

    /**
     * Méthode permettant de récupérer une instance de la DAOProgrammeNutrition
     * @return l'instance du modelProgrammeNutritionPostgreSQL
     */
    @Override
    public DAOProgrammeNutrition getDAOProgrammeNutrition() {
        return new DAOProgrammeNutritionPostgreSQL();
    }

    /**
     * Méthode permettant de récupérer une instance de la DAOProgrammeSportif
     * @return l'instance du modelProgrammeSportifPostgreSQL
     */
    @Override
    public DAOProgrammeSportif getDAOProgrammeSportif() {
        return new DAOProgrammeSportifPostgreSQL();
    }

    /**
     * Méthode permettant de récupérer une instance de la DAOProgrammePersonnalise
     * @return l'instance du modelProgrammePersonnalisePostgreSQL
     */
    @Override
    public DAOProgrammePersonnalise getDAOProgrammePersonnalise() {
        return new DAOProgrammePersonnalisePostgreSQL();
    }

    /**
     * Méthode permettant de récupérer une instance de la DAOPack
     * @return l'instance du modelPackPostgreSQL
     */
    @Override
    public DAOPack getDAOPack() {
        return new DAOPackPostgreSQL();
    }


    /**
     * Méthode permettant de récupérer une instance de la DAOMateriel
     * @return l'instance du modelMaterielPostgreSQL
     */
    @Override
    public DAOMateriel getDAOMateriel() {
        return new DAOMaterielPostgreSQL();
    }

    /**
     * Méthode permettant de récupérer une instance de la DAOSport
     * @return l'instance du modelSportPostgreSQL
     */
    @Override
    public DAOSport getDAOSport() {
        return new DAOSportPostgreSQL();
    }

    /**
     * Méthode permettant de récupérer une instance de la DAODemande
     * @return l'instance du modelDemandePostgreSQL
     */
    @Override
    public DAODemande getDAODemande() {
        return new DAODemandePostgreSQL();
    }

    /**
     * Méthode permettant de récupérer une instance de la DAONotification
     * @return l'instance du modelNotificationPostgreSQL
     */
    @Override
    public DAONotification getDAONotification() {
        return new DAONotificationPostgreSQL();
    }

}
