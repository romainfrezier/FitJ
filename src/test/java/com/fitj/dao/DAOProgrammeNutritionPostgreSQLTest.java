package com.fitj.dao;

import com.fitj.classes.*;
import com.fitj.dao.factory.FactoryDAOPostgreSQL;
import com.fitj.dao.postgresql.DAOProgrammeNutritionPostgreSQL;
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

/**
 * Classe de test de la DAO ProgrammeNutritionPostgreSQL
 * @see DAOProgrammeNutritionPostgreSQL
 * @author Etienne Tillier, Romain Frezier
 */
public class DAOProgrammeNutritionPostgreSQLTest {

    /**
     * Objet utilisé pour les tests
     */
    private static ProgrammeNutrition programmeNutrition;

    /**
     * Objet utilisé pour les tests
     */
    private static ProgrammeNutrition programmeBD;

    /**
     * DAO utilisé pour les tests
     */
    private static DAOProgrammeNutritionPostgreSQL daoProgrammeNutritionPostgreSQL;

    /**
     * Objet utilisé pour les tests
     */
    private static Coach coach;

    /**
     * Objet utilisé pour les tests
     */
    private static ArrayList<Recette> listeRecettes;

    /**
     * Initialisation des objets utilisés pour les tests
     * @throws Exception si la requête SQL échoue
     */
    @BeforeAll
    public static void init() throws Exception {
        daoProgrammeNutritionPostgreSQL = new DAOProgrammeNutritionPostgreSQL();
        coach = new Coach("coach@gmail.com", "elcocho", 100, "dadada", 174, Sexe.HOMME, "test", 44);
        Recette recette = FactoryDAOPostgreSQL.getInstance().getDAORecette().getAllRecettes().get(0);
        listeRecettes = new ArrayList<>();
        listeRecettes.add(recette);
        programmeNutrition = new ProgrammeNutrition(1,"Programme healthy", "Super programme perdre du poids", 680, ProgrammeType.DIFFCILE, 6, coach, listeRecettes);
        programmeBD = daoProgrammeNutritionPostgreSQL.createProgrammeNutrition(programmeNutrition.getNom(),programmeNutrition.getDescription(),programmeNutrition.getPrix(),programmeNutrition.getType(),programmeNutrition.getNbMois(),programmeNutrition.getCoach(), (ArrayList<Recette>) programmeNutrition.getListeRecette());
    }

    /**
     * Méthode utilisée après tous les tests pour supprimer le programme créé
     * @throws Exception si la requête SQL échoue
     */
    @AfterAll
    public static void clean() throws Exception {
        daoProgrammeNutritionPostgreSQL.supprimerProgrammeNutrition(programmeBD.getId());
    }

    /**
     * Test de la méthode createProgrammeNutrition de la classe DAOProgrammeNutritionPostgreSQL
     */
    @Test
    public void testCreateProgrammeNutrition() {
        Assertions.assertEquals(programmeNutrition.getDescription(), programmeBD.getDescription());
    }

    /**
     * Test de la méthode updateProgrammeNutrition de la classe DAOProgrammeNutritionPostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testProgrammeNutritionUpdate() throws Exception {
        List<Pair<String, Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("nbmois", programmeBD.getNbMois()+1));
        programmeBD = daoProgrammeNutritionPostgreSQL.updateProgrammeNutrition(updateList, programmeBD.getId());
        Assertions.assertEquals(programmeBD.getNbMois(), programmeNutrition.getNbMois()+1);
    }

    /**
     * Test de la méthode supprimerProgrammeNutrition de la classe DAOProgrammeNutritionPostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testProgrammeNutritionDelete() throws Exception {
        ProgrammeNutrition programmeBD1 = daoProgrammeNutritionPostgreSQL.createProgrammeNutrition(programmeNutrition.getNom(),programmeNutrition.getDescription(),programmeNutrition.getPrix(),programmeNutrition.getType(),programmeNutrition.getNbMois(),programmeNutrition.getCoach(), (ArrayList<Recette>) programmeNutrition.getListeRecette());
        daoProgrammeNutritionPostgreSQL.supprimerProgrammeNutrition(programmeBD1.getId());
        Assertions.assertThrows(SQLException.class, () -> daoProgrammeNutritionPostgreSQL.getProgrammeNutritionId(programmeBD1.getId()));
    }

    /**
     * Test de la méthode getAllProgrammeNutrition de la classe DAOProgrammeNutritionPostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testGetAllProgrammeNutrition() throws Exception {
        ProgrammeNutrition programmeBD1 = daoProgrammeNutritionPostgreSQL.createProgrammeNutrition(programmeNutrition.getNom(),programmeNutrition.getDescription(),programmeNutrition.getPrix(),programmeNutrition.getType(),programmeNutrition.getNbMois(),programmeNutrition.getCoach(), (ArrayList<Recette>) programmeNutrition.getListeRecette());
        int nbSeanceBD = daoProgrammeNutritionPostgreSQL.getAllProgrammeNutrition().size();
        daoProgrammeNutritionPostgreSQL.supprimerProgrammeNutrition(programmeBD1.getId());
        Assertions.assertEquals(nbSeanceBD, daoProgrammeNutritionPostgreSQL.getAllProgrammeNutrition().size() + 1);
    }

    /**
     * Test de la méthode supprimerRecetteProgramme de la classe DAOProgrammeNutritionPostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testSupprimerRecetteProgrammeNutrition() throws Exception {
        ProgrammeNutrition programmeBD = daoProgrammeNutritionPostgreSQL.createProgrammeNutrition(programmeNutrition.getNom(),programmeNutrition.getDescription(),programmeNutrition.getPrix(),programmeNutrition.getType(),programmeNutrition.getNbMois(),programmeNutrition.getCoach(), new ArrayList<>());
        Recette recette = FactoryDAOPostgreSQL.getInstance().getDAORecette().getAllRecettes().get(0);
        daoProgrammeNutritionPostgreSQL.ajouterRecetteProgramme(recette,programmeBD.getId());
        daoProgrammeNutritionPostgreSQL.supprimerRecetteProgramme(recette,programmeBD.getId());
        ProgrammeNutrition programmeNutrition1 = daoProgrammeNutritionPostgreSQL.getProgrammeNutritionId(programmeBD.getId());
        daoProgrammeNutritionPostgreSQL.supprimerProgrammeNutrition(programmeBD.getId());
        Assertions.assertTrue(programmeNutrition1.getListeRecette().isEmpty());
    }

    /**
     * Test de la méthode ajouterRecetteProgramme de la classe DAOProgrammeNutritionPostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testAjouterRecetteProgrammeNutrition() throws Exception {
        ProgrammeNutrition programmeBD = daoProgrammeNutritionPostgreSQL.createProgrammeNutrition(programmeNutrition.getNom(),programmeNutrition.getDescription(),programmeNutrition.getPrix(),programmeNutrition.getType(),programmeNutrition.getNbMois(),programmeNutrition.getCoach(), new ArrayList<>());
        Recette recette = FactoryDAOPostgreSQL.getInstance().getDAORecette().getAllRecettes().get(0);
        daoProgrammeNutritionPostgreSQL.ajouterRecetteProgramme(recette,programmeBD.getId());
        ProgrammeNutrition programmeNutrition1 = daoProgrammeNutritionPostgreSQL.getProgrammeNutritionId(programmeBD.getId());
        daoProgrammeNutritionPostgreSQL.supprimerProgrammeNutrition(programmeBD.getId());
        Assertions.assertTrue(programmeBD.getListeRecette().isEmpty() && programmeNutrition1.getListeRecette().size() == 1);
    }

}
