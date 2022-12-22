package com.fitj.dao;

import com.fitj.classes.*;
import com.fitj.dao.factory.FactoryDAOPostgreSQL;
import com.fitj.dao.postgresql.DAOProgrammePersonnalisePostgreSQL;
import com.fitj.dao.postgresql.DAOProgrammeSportifPostgreSQL;
import com.fitj.enums.ProgrammeType;
import com.fitj.enums.Sexe;
import kotlin.Pair;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestDAOProgrammePersonnalisePostgreSQL {


    private static ProgrammePersonnalise programmePersonnalise;

    private static ProgrammePersonnalise programmePersonnaliseDemande;
    private static DAOProgrammePersonnalisePostgreSQL daoProgrammePersonnalisePostgreSQL;

    private static Coach coach;

    private static ArrayList<Programme> listeProgramme;

    private static Demande demande;

    private static Sport sport;

    private static ProgrammeSportif programmeSportif;

    private static ProgrammeNutrition programmeNutrition;


    @BeforeAll
    public static void init() throws Exception {
        try {
            daoProgrammePersonnalisePostgreSQL = new DAOProgrammePersonnalisePostgreSQL();
            sport = FactoryDAOPostgreSQL.getInstance().getDAOSport().getAllSport().get(0);
            coach = new Coach("coach@gmail.com", "elcocho", 100, "dadada", 174, Sexe.HOMME, "test", 44);
            programmePersonnalise = daoProgrammePersonnalisePostgreSQL.createProgrammePersonnalise("ProgrammeMaster", "Je veux devenir superman", 1000, coach);
            demande = FactoryDAOPostgreSQL.getInstance().getDAODemande().createDemande(12,"Je suis faible je veux devenir fort", true, true, 5, 12, sport, programmePersonnalise);
            programmePersonnaliseDemande = daoProgrammePersonnalisePostgreSQL.getProgrammePersonnaliseId(demande.getProgrammePersonnalise().getId());
            listeProgramme = new ArrayList<>();
            programmeSportif = FactoryDAOPostgreSQL.getInstance().getDAOProgrammeSportif().getAllProgrammeSportif().get(0);
            programmeNutrition = FactoryDAOPostgreSQL.getInstance().getDAOProgrammeNutrition().getAllProgrammeNutrition().get(0);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @AfterAll
    public static void fin() throws Exception{
        try {
            daoProgrammePersonnalisePostgreSQL.supprimerProgrammePersonnalise(programmePersonnalise.getId());
            daoProgrammePersonnalisePostgreSQL.supprimerProgrammePersonnalise(programmePersonnaliseDemande.getId());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateProgrammePersonnalise() throws Exception {
        Assertions.assertTrue(programmePersonnalise.getDescription().equals("Je veux devenir superman"));
    }

    @Test
    public void testCreateDemandeProgrammePersonnalise() throws Exception {
        Assertions.assertTrue(programmePersonnaliseDemande.getDescription().equals("Je veux devenir superman"));
    }


    @Test
    public void testProgrammePersonnaliseUpdate() throws Exception {
        List<Pair<String,Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("prix",600));
        programmePersonnaliseDemande = daoProgrammePersonnalisePostgreSQL.updateProgrammePersonnalise(updateList,programmePersonnaliseDemande.getId());
        Assertions.assertTrue(programmePersonnaliseDemande.getPrix() == 600);
    }


    @Test
    public void testProgrammePersonnaliseDelete() throws Exception {
        daoProgrammePersonnalisePostgreSQL.supprimerProgrammePersonnalise(programmePersonnaliseDemande.getId());
        Assertions.assertThrows(SQLException.class,
                () -> daoProgrammePersonnalisePostgreSQL.getProgrammePersonnaliseId(programmePersonnaliseDemande.getId()));
    }

    @Test
    public void testGetAllProgrammePersonnalise() throws Exception {
        int nbSeanceBD = daoProgrammePersonnalisePostgreSQL.getAllProgrammePersonnalise().size();
        daoProgrammePersonnalisePostgreSQL.supprimerProgrammePersonnalise(programmePersonnaliseDemande.getId());
        Assertions.assertTrue(nbSeanceBD == daoProgrammePersonnalisePostgreSQL.getAllProgrammePersonnalise().size() + 1);
    }

    @Test
    public void testSupprimerProgrammeProgrammePersonnalise() throws Exception {
        daoProgrammePersonnalisePostgreSQL.ajouterProgrammeProgrammePersonnalise(programmeNutrition,programmePersonnaliseDemande.getId());
        ProgrammePersonnalise programmePersonnalise1 = daoProgrammePersonnalisePostgreSQL.getProgrammePersonnaliseId(programmePersonnaliseDemande.getId());
        daoProgrammePersonnalisePostgreSQL.supprimerProgrammeProgrammePersonnalise(programmeNutrition,programmePersonnaliseDemande.getId());
        ProgrammePersonnalise programmePersonnalise2 = daoProgrammePersonnalisePostgreSQL.getProgrammePersonnaliseId(programmePersonnaliseDemande.getId());
        Assertions.assertTrue(programmePersonnalise1.getListeProgrammes().size() == 1 && programmePersonnalise2.getListeProgrammes().isEmpty());
    }

    @Test
    public void testAjouterProgrammeProgrammePersonnalise() throws Exception {
        daoProgrammePersonnalisePostgreSQL.ajouterProgrammeProgrammePersonnalise(programmeNutrition,programmePersonnaliseDemande.getId());
        daoProgrammePersonnalisePostgreSQL.ajouterProgrammeProgrammePersonnalise(programmeSportif,programmePersonnaliseDemande.getId());
        ProgrammePersonnalise programmePersonnalise1 = daoProgrammePersonnalisePostgreSQL.getProgrammePersonnaliseId(programmePersonnaliseDemande.getId());
        daoProgrammePersonnalisePostgreSQL.supprimerProgrammeProgrammePersonnalise(programmeNutrition,programmePersonnaliseDemande.getId());
        daoProgrammePersonnalisePostgreSQL.supprimerProgrammeProgrammePersonnalise(programmeSportif,programmePersonnaliseDemande.getId());
        Assertions.assertTrue(programmePersonnaliseDemande.getListeProgrammes().isEmpty() && programmePersonnalise1.getListeProgrammes().size() == 2);
    }

}
