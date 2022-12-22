package com.fitj.dao;

import com.fitj.classes.*;
import com.fitj.dao.factory.FactoryDAOPostgreSQL;
import com.fitj.dao.postgresql.DAODemandePostgreSQL;
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

public class TestDAODemandePostgreSQL {


    private static ProgrammePersonnalise programmePersonnaliseBD;

    private static DAODemandePostgreSQL daoDemandePostgreSQL;

    private static Demande demande;

    private static Sport sport;

    private static Coach coach;



    @BeforeAll
    public static void init() throws Exception {
        daoDemandePostgreSQL = new DAODemandePostgreSQL();
        sport = FactoryDAOPostgreSQL.getInstance().getDAOSport().getAllSport().get(0);
        coach = new Coach("coach@gmail.com", "elcocho", 100, "dadada", 174, Sexe.HOMME, "test", 44);
        programmePersonnaliseBD = FactoryDAOPostgreSQL.getInstance().getDAOProgrammePersonnalise().createProgrammePersonnalise("Super programme", "Je personnalise votre programme", 680, coach);
        demande = daoDemandePostgreSQL.createDemande(6,"Je suis Francis jaimerai manger plus et rester au meme poids", true, true,4, 9, sport, programmePersonnaliseBD);
    }

    @AfterAll
    public static void fin() throws Exception {
        FactoryDAOPostgreSQL.getInstance().getDAOProgrammePersonnalise().supprimerProgrammePersonnalise(programmePersonnaliseBD.getId());
        daoDemandePostgreSQL.supprimerDemande(demande.getId());
    }

    @Test
    public void testCreateDemande() throws Exception {
        //pb string ''
        Assertions.assertTrue(demande.getDescription().equals("Je suis Francis jaimerai manger plus et rester au meme poids"));
    }


    @Test
    public void testDemandeUpdate() throws Exception {
        List<Pair<String,Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("nbmois",9));
        Demande demande1 = daoDemandePostgreSQL.updateDemande(updateList,demande.getId());
        Assertions.assertTrue(demande1.getNbMois() == 9);
    }


    @Test
    public void testDemandeDelete() throws Exception {
        daoDemandePostgreSQL.supprimerDemande(demande.getId());
        Assertions.assertThrows(SQLException.class,
                () -> daoDemandePostgreSQL.getDemandeById(demande.getId()));
    }

    @Test
    public void testGetAllDemande() throws Exception {
        int nbSeanceBD = daoDemandePostgreSQL.getAllDemande().size();
        daoDemandePostgreSQL.supprimerDemande(demande.getId());
        Assertions.assertTrue(nbSeanceBD == daoDemandePostgreSQL.getAllDemande().size() + 1);
    }


}
