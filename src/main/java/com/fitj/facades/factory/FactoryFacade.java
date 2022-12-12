package com.fitj.facades.factory;

import com.fitj.facades.*;

public abstract class FactoryFacade {

    private static FactoryFacade instance = null;

    private static final String databaseType = "postgreSQL";

    protected FactoryFacade(){

    }

    public static FactoryFacade getInstance(){
        if (instance == null){
            if (databaseType.equals("postgreSQL")){
                instance = new FactoryFacadePostgreSQL();
            }
            else {
                //exception database type
                System.out.println("Le type de base de données n'existe pas");
            }
        }
        return instance;
    }

    //vérifier à la fin du développement si on a besoins de toutes les façades

    public abstract FacadeClient getFacadeClient();

    public abstract FacadeAdmin getFacadeAdmin();

    public abstract FacadeUser getFacadeUser();

    public abstract FacadeSport getFacadeSport();

    public abstract FacadeSeance getFacadeSeance();

    public abstract FacadeRecette getFacadeRecette();

    public abstract FacadePaiement getFacadePaiement();

    public abstract FacadePack getFacadePack();

    public abstract FacadeNotification getFacadeNotification();

    public abstract FacadeMateriel getFacadeMateriel();

    public abstract FacadeExercice getFacadeExercice();

    public abstract FacadeDemande getFacadeDemande();

    public abstract FacadeCommande getFacadeCommande();

    public abstract FacadeCoach getFacadeCoach();

    public abstract FacadeAvis getFacadeAvis();

    public abstract FacadeProgramme getFacadeProgramme();


}
