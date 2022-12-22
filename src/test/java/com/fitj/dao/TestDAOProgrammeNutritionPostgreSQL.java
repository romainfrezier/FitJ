package com.fitj.dao;

import com.fitj.classes.*;
import com.fitj.dao.factory.FactoryDAOPostgreSQL;
import com.fitj.dao.postgresql.DAOProgrammeNutritionPostgreSQL;
import com.fitj.dao.postgresql.DAOProgrammeSportifPostgreSQL;
import com.fitj.enums.ProgrammeType;
import com.fitj.enums.Sexe;
import kotlin.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestDAOProgrammeNutritionPostgreSQL {

    private static ProgrammeNutrition programmeNutrition;

    private static DAOProgrammeNutritionPostgreSQL daoProgrammeNutritionPostgreSQL;

    private static Coach coach;

    private static ArrayList<Recette> listeRecettes;


    @BeforeAll
    public static void init() throws Exception {
        daoProgrammeNutritionPostgreSQL = new DAOProgrammeNutritionPostgreSQL();
        coach = new Coach("coach@gmail.com", "elcocho", 100, "dadada", 174, Sexe.HOMME, "test", 44);
        Recette recette = FactoryDAOPostgreSQL.getInstance().getDAORecette().getAllRecettes().get(0);
        listeRecettes = new ArrayList<>();
        listeRecettes.add(recette);
        programmeNutrition = new ProgrammeNutrition(1,"Programme healthy", "Super programme perdre du poids", 680, ProgrammeType.DIFFCILE, 6, coach, listeRecettes);
        ProgrammeNutrition programmeBD = daoProgrammeNutritionPostgreSQL.createProgrammeNutrition(programmeNutrition.getNom(),programmeNutrition.getDescription(),programmeNutrition.getPrix(),programmeNutrition.getType(),programmeNutrition.getNbMois(),programmeNutrition.getCoach(), (ArrayList<Recette>) programmeNutrition.getListeRecette());
    }

    @Test
    public void testCreateProgrammeSportif() throws Exception {
        ProgrammeNutrition programmeBD = daoProgrammeNutritionPostgreSQL.createProgrammeNutrition(programmeNutrition.getNom(),programmeNutrition.getDescription(),programmeNutrition.getPrix(),programmeNutrition.getType(),programmeNutrition.getNbMois(),programmeNutrition.getCoach(), (ArrayList<Recette>) programmeNutrition.getListeRecette());
        daoProgrammeNutritionPostgreSQL.supprimerProgrammeNutrition(programmeBD.getId());
        Assertions.assertTrue(programmeBD.getDescription().equals(programmeBD.getDescription()));
    }


    @Test
    public void testProgrammeSportifUpdate() throws Exception {
        ProgrammeNutrition programmeBD = daoProgrammeNutritionPostgreSQL.createProgrammeNutrition(programmeNutrition.getNom(),programmeNutrition.getDescription(),programmeNutrition.getPrix(),programmeNutrition.getType(),programmeNutrition.getNbMois(),programmeNutrition.getCoach(), (ArrayList<Recette>) programmeNutrition.getListeRecette());
        List<Pair<String,Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("nbmois",7));
        programmeBD = daoProgrammeNutritionPostgreSQL.updateProgrammeNutrition(updateList,programmeBD.getId());
        daoProgrammeNutritionPostgreSQL.supprimerProgrammeNutrition(programmeBD.getId());
        Assertions.assertTrue(programmeBD.getNbMois() == 7);
    }


    @Test
    public void testProgrammeSportifDelete() throws Exception {
        ProgrammeNutrition programmeBD = daoProgrammeNutritionPostgreSQL.createProgrammeNutrition(programmeNutrition.getNom(),programmeNutrition.getDescription(),programmeNutrition.getPrix(),programmeNutrition.getType(),programmeNutrition.getNbMois(),programmeNutrition.getCoach(), (ArrayList<Recette>) programmeNutrition.getListeRecette());
        daoProgrammeNutritionPostgreSQL.supprimerProgrammeNutrition(programmeBD.getId());
        Assertions.assertThrows(SQLException.class,
                () -> daoProgrammeNutritionPostgreSQL.getProgrammeNutritionId(programmeBD.getId()));
    }

    @Test
    public void testGetAllProgrammeSportif() throws Exception {
        ProgrammeNutrition programmeBD = daoProgrammeNutritionPostgreSQL.createProgrammeNutrition(programmeNutrition.getNom(),programmeNutrition.getDescription(),programmeNutrition.getPrix(),programmeNutrition.getType(),programmeNutrition.getNbMois(),programmeNutrition.getCoach(), (ArrayList<Recette>) programmeNutrition.getListeRecette());
        int nbSeanceBD = daoProgrammeNutritionPostgreSQL.getAllProgrammeNutrition().size();
        daoProgrammeNutritionPostgreSQL.supprimerProgrammeNutrition(programmeBD.getId());
        Assertions.assertTrue(nbSeanceBD == daoProgrammeNutritionPostgreSQL.getAllProgrammeNutrition().size() + 1);
    }

    @Test
    public void testSupprimerRecetteProgrammeNutrition() throws Exception {
        try {
            ProgrammeNutrition programmeBD = daoProgrammeNutritionPostgreSQL.createProgrammeNutrition(programmeNutrition.getNom(),programmeNutrition.getDescription(),programmeNutrition.getPrix(),programmeNutrition.getType(),programmeNutrition.getNbMois(),programmeNutrition.getCoach(), new ArrayList<>());
            Recette recette = FactoryDAOPostgreSQL.getInstance().getDAORecette().getAllRecettes().get(0);
            daoProgrammeNutritionPostgreSQL.ajouterRecetteProgramme(recette,programmeBD.getId());
            daoProgrammeNutritionPostgreSQL.supprimerRecetteProgramme(recette,programmeBD.getId());
            ProgrammeNutrition programmeNutrition1 = daoProgrammeNutritionPostgreSQL.getProgrammeNutritionId(programmeBD.getId());
            daoProgrammeNutritionPostgreSQL.supprimerProgrammeNutrition(programmeBD.getId());
            Assertions.assertTrue(programmeNutrition1.getListeRecette().isEmpty());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void testAjouterRecetteProgrammeNutrition() throws Exception {
        try {
            ProgrammeNutrition programmeBD = daoProgrammeNutritionPostgreSQL.createProgrammeNutrition(programmeNutrition.getNom(),programmeNutrition.getDescription(),programmeNutrition.getPrix(),programmeNutrition.getType(),programmeNutrition.getNbMois(),programmeNutrition.getCoach(), new ArrayList<>());
            Recette recette = FactoryDAOPostgreSQL.getInstance().getDAORecette().getAllRecettes().get(0);
            daoProgrammeNutritionPostgreSQL.ajouterRecetteProgramme(recette,programmeBD.getId());
            ProgrammeNutrition programmeNutrition1 = daoProgrammeNutritionPostgreSQL.getProgrammeNutritionId(programmeBD.getId());
            daoProgrammeNutritionPostgreSQL.supprimerProgrammeNutrition(programmeBD.getId());
            Assertions.assertTrue(programmeBD.getListeRecette().isEmpty() && programmeNutrition1.getListeRecette().size() == 1);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
