package com.fitj.facades;

import com.fitj.classes.*;
import com.fitj.dao.DAOClient;
import com.fitj.dao.factory.FactoryDAO;

import java.util.List;

public class FacadeCoach extends Facade {

    protected DAOClient daoClient;
    private static FacadeCoach instance = null;
    protected FacadeCoach(){
        this.daoClient = FactoryDAO.getInstance().getDAOClient();
    }

    public static FacadeCoach getInstance(){
        if (instance == null){
            instance = new FacadeCoach();
        }
        return instance;
    }

    public List<Coach> getAllCoachs() throws Exception {
        return this.daoClient.getAllCoach();
    }

    public Admin clientBecomeAdmin(int idObjectSelected) throws Exception {
        return this.daoClient.clientBecomeAdmin(idObjectSelected);
    }

    public List<Client> getAllClientsForACoach(int coachId) throws Exception {
        return this.daoClient.getAllClientForACoach(coachId);
    }

    public Coach getCoachById(int id) throws Exception {
        return (Coach)this.daoClient.getClientById(id);
    }

    public List<Seance> getSeanceByCoach(Coach coach) throws Exception {
        return FactoryDAO.getInstance().getDAOSeance().getAllSeancesFromCoach(coach.getId());
    }

    public List<ProgrammeSportif> getCoachProgrammeSportifs(Coach coach) throws Exception {
        return FactoryDAO.getInstance().getDAOProgrammeSportif().getAllProgrammeSportifByCoach(coach.getId());
    }

    public List<ProgrammeNutrition> getCoachProgrammeNutrition(Coach coach) throws Exception {
        return FactoryDAO.getInstance().getDAOProgrammeNutrition().getProgrammeNutritionByCoach(coach.getId());
    }

    public List<Pack> getAllPackByCoach(Coach coach) throws Exception {
        return FactoryDAO.getInstance().getDAOPack().getAllPackByCoach(coach.getId());
    }
}
