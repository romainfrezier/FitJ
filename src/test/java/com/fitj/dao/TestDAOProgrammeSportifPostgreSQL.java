package com.fitj.dao;

import com.fitj.classes.*;
import com.fitj.dao.factory.FactoryDAOPostgreSQL;
import com.fitj.dao.postgresql.DAOProgrammeSportifPostgreSQL;
import com.fitj.dao.postgresql.DAOSeancePostgreSQL;
import com.fitj.enums.ProgrammeType;
import com.fitj.enums.Sexe;
import kotlin.Pair;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestDAOProgrammeSportifPostgreSQL {

    private static ProgrammeSportif programmeSportif;

    private static DAOProgrammeSportifPostgreSQL daoProgrammeSportifPostgreSQL;

    private static Coach coach;

    private static ArrayList<Seance> listeSeances;


    @BeforeAll
    public static void init() throws Exception {
        daoProgrammeSportifPostgreSQL = new DAOProgrammeSportifPostgreSQL();
        coach = new Coach("coach@gmail.com", "elcocho", 100, "dadada", 174, Sexe.HOMME, "test", 44);
        Seance seance = FactoryDAOPostgreSQL.getInstance().getDAOSeance().getAllSeances().get(0);
        listeSeances = new ArrayList<>();
        listeSeances.add(seance);
        programmeSportif = new ProgrammeSportif(1,"Programme prise de masse", "Super programme pour prendre de la masse", 540, ProgrammeType.MOYEN, 4, coach, listeSeances);
    }

    @Test
    public void testCreateProgrammeSportif() throws Exception {
        ProgrammeSportif programmeBD = daoProgrammeSportifPostgreSQL.createProgrammeSportif(programmeSportif.getNom(),programmeSportif.getDescription(),programmeSportif.getPrix(),programmeSportif.getType(),programmeSportif.getNbMois(),programmeSportif.getCoach(), (ArrayList<Seance>) programmeSportif.getListeSeance());
        daoProgrammeSportifPostgreSQL.supprimerProgrammeSportif(programmeBD.getId());
        Assertions.assertTrue(programmeBD.getDescription().equals(programmeBD.getDescription()));
    }


    @Test
    public void testProgrammeSportifUpdate() throws Exception {
        ProgrammeSportif programmeBD = daoProgrammeSportifPostgreSQL.createProgrammeSportif(programmeSportif.getNom(),programmeSportif.getDescription(),programmeSportif.getPrix(),programmeSportif.getType(),programmeSportif.getNbMois(),programmeSportif.getCoach(), (ArrayList<Seance>) programmeSportif.getListeSeance());
        List<Pair<String,Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("type","difficile"));
        programmeBD = daoProgrammeSportifPostgreSQL.updateProgrammeSportif(updateList,programmeBD.getId());
        daoProgrammeSportifPostgreSQL.supprimerProgrammeSportif(programmeBD.getId());
        Assertions.assertTrue(programmeBD.getType().equals(ProgrammeType.DIFFCILE));
    }


    @Test
    public void testProgrammeSportifDelete() throws Exception {
        ProgrammeSportif programmeBD = daoProgrammeSportifPostgreSQL.createProgrammeSportif(programmeSportif.getNom(),programmeSportif.getDescription(),programmeSportif.getPrix(),programmeSportif.getType(),programmeSportif.getNbMois(),programmeSportif.getCoach(), (ArrayList<Seance>) programmeSportif.getListeSeance());
        daoProgrammeSportifPostgreSQL.supprimerProgrammeSportif(programmeBD.getId());
        Assertions.assertThrows(SQLException.class,
                () -> daoProgrammeSportifPostgreSQL.getProgrammeSportifId(programmeBD.getId()));
    }

    @Test
    public void testGetAllProgrammeSportif() throws Exception {
        ProgrammeSportif programmeBD = daoProgrammeSportifPostgreSQL.createProgrammeSportif(programmeSportif.getNom(),programmeSportif.getDescription(),programmeSportif.getPrix(),programmeSportif.getType(),programmeSportif.getNbMois(),programmeSportif.getCoach(), (ArrayList<Seance>) programmeSportif.getListeSeance());
        int nbSeanceBD = daoProgrammeSportifPostgreSQL.getAllProgrammeSportif().size();
        daoProgrammeSportifPostgreSQL.supprimerProgrammeSportif(programmeBD.getId());
        Assertions.assertTrue(nbSeanceBD == daoProgrammeSportifPostgreSQL.getAllProgrammeSportif().size() + 1);
    }

    @Test
    public void testSupprimerSeanceProgrammeSportif() throws Exception {
        try {
            ProgrammeSportif programmeBD = daoProgrammeSportifPostgreSQL.createProgrammeSportif(programmeSportif.getNom(),programmeSportif.getDescription(),programmeSportif.getPrix(),programmeSportif.getType(),programmeSportif.getNbMois(),programmeSportif.getCoach(), new ArrayList<>());
            Seance seance = FactoryDAOPostgreSQL.getInstance().getDAOSeance().getAllSeances().get(0);
            daoProgrammeSportifPostgreSQL.ajouterSeanceProgramme(seance,programmeBD.getId());
            daoProgrammeSportifPostgreSQL.supprimerSeanceProgramme(seance,programmeBD.getId());
            ProgrammeSportif programmeSportif1 = daoProgrammeSportifPostgreSQL.getProgrammeSportifId(programmeBD.getId());
            daoProgrammeSportifPostgreSQL.supprimerProgrammeSportif(programmeBD.getId());
            Assertions.assertTrue(programmeSportif1.getListeSeance().size() == 0);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void testAjouterSeanceProgrammeSportif() throws Exception {
        try {
            ProgrammeSportif programmeBD = daoProgrammeSportifPostgreSQL.createProgrammeSportif(programmeSportif.getNom(),programmeSportif.getDescription(),programmeSportif.getPrix(),programmeSportif.getType(),programmeSportif.getNbMois(),programmeSportif.getCoach(), new ArrayList<>());
            Seance seance = FactoryDAOPostgreSQL.getInstance().getDAOSeance().getAllSeances().get(0);
            daoProgrammeSportifPostgreSQL.ajouterSeanceProgramme(seance,programmeBD.getId());
            ProgrammeSportif programmeSportif1 = daoProgrammeSportifPostgreSQL.getProgrammeSportifId(programmeBD.getId());
            daoProgrammeSportifPostgreSQL.supprimerProgrammeSportif(programmeBD.getId());
            Assertions.assertTrue(programmeBD.getListeSeance().isEmpty() && programmeSportif1.getListeSeance().size() == 1);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
