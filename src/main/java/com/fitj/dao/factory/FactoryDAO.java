package com.fitj.dao.factory;

import com.fitj.dao.*;

/**
 * Classe parente de toutes les factory de model qui permettent de créer des model en fonction du type de la base de donnée
 * Classe abstraite non instanciable (singleton)
 * @author Etienne Tillier, Romain Frezier
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
            if (databaseType.equals("postgreSQL")){
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
     * @return l'instance du DAOClient
     */
    public abstract DAOClient getDAOClient();

    /**
     * @return l'instance du DAOSeance
     *
     */
    public abstract DAOSeance getDAOSeance();

    /**
     * @return l'instance du DAOPaiement
     */
    public abstract DAOPaiement getDAOPaiement();

    /**
     * @return l'instance du DAOExercice
     */
    public abstract DAOExercice getDAOExercice();


    /**
     * @return l'instance du DAOAliment
     */
    public abstract DAOAliment getDAOAliment();

    /**
     * @return l'instance du DAORecette
     */
    public abstract DAORecette getDAORecette();

    /**
     * @return l'instance du DAOAvis
     */
    public abstract DAOAvis getDAOAvis();

    /**
     * @return l'instance du DAOCommande
     */
    public abstract DAOCommande getDAOCommande();

    /**
     * @return l'instance du DAOProgrammeNutrition
     */
    public abstract DAOProgrammeNutrition getDAOProgrammeNutrition();

    /**
     * @return l'instance du DAOProgrammeSportif
     */
    public abstract DAOProgrammeSportif getDAOProgrammeSportif();

    /**
     * @return l'instance du DAOProgrammePersonnalise
     */
    public abstract DAOProgrammePersonnalise getDAOProgrammePersonnalise();

    /**
     * @return l'instance du DAOPack
     */
    public abstract DAOPack getDAOPack();

    /**
     * @return l'instance du DAOMateriel
     */
    public abstract DAOMateriel getDAOMateriel();

    /**
     * @return l'instance du DAOSport
     */
    public abstract DAOSport getDAOSport();

    /**
     * @return l'instance du DAODemande
     */
    public abstract DAODemande getDAODemande();

    /**
     * @return l'instance du DAONotification
     */
    public abstract DAONotification getDAONotification();

}

