package com.fitj.facades.factory;

import com.fitj.facades.*;
import com.fitj.facades.factory.FactoryFacade;
import com.fitj.facades.postgresql.*;

public class FactoryFacadePostgreSQL extends FactoryFacade {



    @Override
    public FacadeClientPostgreSQL getFacadeClient() {
        return FacadeClientPostgreSQL.getInstance();
    }

    @Override
    public FacadeAdminPostgreSQL getFacadeAdmin() {
        return FacadeAdminPostgreSQL.getInstance();
    }

    @Override
    public FacadeUserPostgreSQL getFacadeUser() {
        return FacadeUserPostgreSQL.getInstance();
    }

    @Override
    public FacadeSportPostgreSQL getFacadeSport() {
        return FacadeSportPostgreSQL.getInstance();
    }

    @Override
    public FacadeSeancePostgreSQL getFacadeSeance() {
        return FacadeSeancePostgreSQL.getInstance();
    }

    @Override
    public FacadeRecettePostgreSQL getFacadeRecette() {
        return FacadeRecettePostgreSQL.getInstance();
    }

    @Override
    public FacadePaiementPostgreSQL getFacadePaiement() {
        return FacadePaiementPostgreSQL.getInstance();
    }

    @Override
    public FacadePackPostgreSQL getFacadePack() {
        return FacadePackPostgreSQL.getInstance();
    }

    @Override
    public FacadeNotificationPostgreSQL getFacadeNotification() {
        return FacadeNotificationPostgreSQL.getInstance();
    }

    @Override
    public FacadeMaterielPostgreSQL getFacadeMateriel() {
        return FacadeMaterielPostgreSQL.getInstance();
    }

    @Override
    public FacadeExercicePostgreSQL getFacadeExercice() {
        return FacadeExercicePostgreSQL.getInstance();
    }

    @Override
    public FacadeDemandePostgreSQL getFacadeDemande() {
        return FacadeDemandePostgreSQL.getInstance();
    }

    @Override
    public FacadeCommandePostgreSQL getFacadeCommande() {
        return FacadeCommandePostgreSQL.getInstance();
    }

    @Override
    public FacadeCoach getFacadeCoach() {
        return FacadeCoachPostgreSQL.getInstance();
    }

    @Override
    public FacadeAvis getFacadeAvis() {
        return FacadeAvisPostgreSQL.getInstance();
    }

    @Override
    public FacadeProgramme getFacadeProgramme() {
        return FacadeProgrammePostgreSQL.getInstance();
    }
}
