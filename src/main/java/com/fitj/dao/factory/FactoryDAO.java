package com.fitj.dao.factory;

import com.fitj.dao.*;

/**
 * Classe parente de toutes les factory de model qui permettent de créer des model en fonction du type de la base de donnée
 *
 * Classe abstraite non instanciable (singleton)
 *
 * @author Etienne Tillier
 */
public abstract class FactoryDAO {

    /**
     * instance du singleton
     */
    private static FactoryDAO instance = null;

    /**
     * type de la base de donnée
     */
    public static final String databaseType = "postgreSQL";

    protected FactoryDAO(){

    }

    /**
     * @return l'instance du singleton
     */
    public static FactoryDAO getInstance(){
        if (instance == null){
            if (databaseType == "postgreSQL"){
                instance = new FactoryDAOPostgreSQL();
            }
            else {
                //mettre une erreur ici
                System.out.println("Le type de base de données n'existe pas");
            }
        }
        return instance;
    }

    /**
     * @return l'instance du modelClient
     */
    public abstract DAOClient getModelClient();

    /**
     * @return l'instance du modelSeance
     */
    public abstract DAOSeance getModelSeance();

    /**
     * @return l'instance du modelPaiement
     */
    public abstract DAOPaiement getModelPaiement();

    /**
     * @return l'instance du modelExercice
     */
    public abstract DAOExercice getModelExercice();


    /**
     * @return l'instance du modelAliment
     */
    public abstract DAOAliment getModelAliment();

    /**
     * @return l'instance du modelRecette
     */
    public abstract DAORecette getModelRecette();

    /**
     * @return l'instance du modelAvis
     */
    public abstract DAOAvis getModelAvis();

    /**
     * @return l'instance du modelCommande
     */
    public abstract DAOCommande getModelCommande();

    /**
     * @return l'instance du modelProgrammeNutrition
     */
    public abstract DAOProgrammeNutrition getModelProgrammeNutrition();

    /**
     * @return l'instance du modelProgrammeSportif
     */
    public abstract DAOProgrammeSportif getModelProgrammeSportif();

    /**
     * @return l'instance du modelProgrammePersonnalise
     */
    public abstract DAOProgrammePersonnalise getModelProgrammePersonnalise();

    /**
     * @return l'instance du modelPack
     */
    public abstract DAOPack getModelPack();

    /**
     * @return l'instance du modelMateriel
     */
    public abstract DAOMateriel getModelMateriel();

    /**
     * @return l'instance du modelSport
     */
    public abstract DAOSport getModelSport();

}

