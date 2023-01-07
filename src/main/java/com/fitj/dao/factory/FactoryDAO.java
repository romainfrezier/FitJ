package com.fitj.dao.factory;

import com.fitj.dao.*;

/**
 * Classe parente de toutes les factory de model qui permettent de créer des model en fonction du type de la base de donnée
 * Classe abstraite non instanciable (singleton)
 * @author Etienne Tillier, Romain Frezier
 */
public abstract class FactoryDAO {

    /**
     * Instance du singleton
     */
    private static FactoryDAO instance = null;

    /**
     * Type de la base de donnée
     */
    public static final String databaseType = "postgreSQL";

    /**
     * Constructeur de la FactoryDAO
     */
    protected FactoryDAO(){

    }

    /**
     * Méthode permettant de récupérer l'instance de la FactoryDAO
     * @return FactoryDAO, l'instance de la FactoryDAO
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
     * Méthode permettant de récupérer une instance de la DAOClient
     * @return l'instance du DAOClient
     */
    public abstract DAOClient getDAOClient();

    /**
     * Méthode permettant de récupérer une instance de la DAOSeance
     * @return l'instance du DAOSeance
     *
     */
    public abstract DAOSeance getDAOSeance();

    /**
     * Méthode permettant de récupérer une instance de la DAOPaiement
     * @return l'instance du DAOPaiement
     */
    public abstract DAOPaiement getDAOPaiement();

    /**
     * Méthode permettant de récupérer une instance de la DAOExercice
     * @return l'instance du DAOExercice
     */
    public abstract DAOExercice getDAOExercice();


    /**
     * Méthode permettant de récupérer une instance de la DAOAliment
     * @return l'instance du DAOAliment
     */
    public abstract DAOAliment getDAOAliment();

    /**
     * Méthode permettant de récupérer une instance de la DAORecette
     * @return l'instance du DAORecette
     */
    public abstract DAORecette getDAORecette();

    /**
     * Méthode permettant de récupérer une instance de la DAOAvis
     * @return l'instance du DAOAvis
     */
    public abstract DAOAvis getDAOAvis();

    /**
     * Méthode permettant de récupérer une instance de la DAOCommande
     * @return l'instance du DAOCommande
     */
    public abstract DAOCommande getDAOCommande();

    /**
     * Méthode permettant de récupérer une instance de la DAOProgrammeNutrition
     * @return l'instance du DAOProgrammeNutrition
     */
    public abstract DAOProgrammeNutrition getDAOProgrammeNutrition();

    /**
     * Méthode permettant de récupérer une instance de la DAOProgrammeSportif
     * @return l'instance du DAOProgrammeSportif
     */
    public abstract DAOProgrammeSportif getDAOProgrammeSportif();

    /**
     * Méthode permettant de récupérer une instance de la DAOProgrammePersonnalise
     * @return l'instance du DAOProgrammePersonnalise
     */
    public abstract DAOProgrammePersonnalise getDAOProgrammePersonnalise();

    /**
     * Méthode permettant de récupérer une instance de la DAOPack
     * @return l'instance du DAOPack
     */
    public abstract DAOPack getDAOPack();

    /**
     * Méthode permettant de récupérer une instance de la DAOMateriel
     * @return l'instance du DAOMateriel
     */
    public abstract DAOMateriel getDAOMateriel();

    /**
     * Méthode permettant de récupérer une instance de la DAOTypeSport
     * @return l'instance du DAOSport
     */
    public abstract DAOSport getDAOSport();

    /**
     * Méthode permettant de récupérer une instance de la DAODemande
     * @return l'instance du DAODemande
     */
    public abstract DAODemande getDAODemande();

    /**
     * Méthode permettant de récupérer une instance de la DAONotification
     * @return l'instance du DAONotification
     */
    public abstract DAONotification getDAONotification();

}

