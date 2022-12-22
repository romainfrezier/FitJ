package com.fitj.dao;

import com.fitj.classes.*;
import com.fitj.dao.factory.FactoryDAOPostgreSQL;
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

/**
 * Classe de test de la DAO ProgrammeSportifPostgreSQL
 * @see DAOProgrammeSportifPostgreSQL
 * @author Etienne Tillier, Romain Frezier
 */
public class TestDAOProgrammeSportifPostgreSQL {

    /**
     * Objet utilisé pour les tests
     */
    private static ProgrammeSportif programmeSportif;

    /**
     * Objet utilisé pour les tests
     */
    private static ProgrammeSportif programmeBD;

    /**
     * DAO utilisé pour les tests
     */
    private static DAOProgrammeSportifPostgreSQL daoProgrammeSportifPostgreSQL;

    /**
     * Objet utilisé pour les tests
     */
    private static Coach coach;

    /**
     * Objet utilisé pour les tests
     */
    private static ArrayList<Seance> listeSeances;

    /**
     * Initialisation des objets utilisés pour les tests
     * @throws Exception si la requête SQL échoue
     */
    @BeforeAll
    public static void init() throws Exception {
        daoProgrammeSportifPostgreSQL = new DAOProgrammeSportifPostgreSQL();
        coach = new Coach("coach@gmail.com", "elcocho", 100, "dadada", 174, Sexe.HOMME, "test", 44);
        Seance seance = FactoryDAOPostgreSQL.getInstance().getDAOSeance().getAllSeances().get(0);
        listeSeances = new ArrayList<>();
        listeSeances.add(seance);
        programmeSportif = new ProgrammeSportif(1,"Programme prise de masse", "Super programme pour prendre de la masse", 540, ProgrammeType.MOYEN, 4, coach, listeSeances);
        programmeBD = daoProgrammeSportifPostgreSQL.createProgrammeSportif(programmeSportif.getNom(),programmeSportif.getDescription(),programmeSportif.getPrix(),programmeSportif.getType(),programmeSportif.getNbMois(),programmeSportif.getCoach(), (ArrayList<Seance>) programmeSportif.getListeSeance());
    }

    /**
     * Méthode appelée après tous les tests pour supprimer le programme créé
     * @throws Exception si la requête SQL échoue
     */
    @AfterAll
    public static void clean() throws Exception {
        daoProgrammeSportifPostgreSQL.supprimerProgrammeSportif(programmeBD.getId());
    }

    /**
     * Test de la méthode createProgrammeSportif de la classe DAOProgrammeSportifPostgreSQL
     */
    @Test
    public void testCreateProgrammeSportif() {
        Assertions.assertEquals(programmeBD.getDescription(), programmeSportif.getDescription());
    }

    /**
     * Test de la méthode updateProgrammeSportif de la classe DAOProgrammeSportifPostgreSQL
     */
    @Test
    public void testProgrammeSportifUpdate() throws Exception {
        List<Pair<String,Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("type",ProgrammeType.getProgrammeType(ProgrammeType.DIFFCILE)));
        programmeBD = daoProgrammeSportifPostgreSQL.updateProgrammeSportif(updateList,programmeBD.getId());
        Assertions.assertEquals(programmeBD.getType(), ProgrammeType.DIFFCILE);
    }

    /**
     * Test de la méthode supprimerProgrammeSportif de la classe DAOProgrammeSportifPostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testProgrammeSportifDelete() throws Exception {
        ProgrammeSportif programmeBD1 = daoProgrammeSportifPostgreSQL.createProgrammeSportif(programmeSportif.getNom(),programmeSportif.getDescription(),programmeSportif.getPrix(),programmeSportif.getType(),programmeSportif.getNbMois(),programmeSportif.getCoach(), (ArrayList<Seance>) programmeSportif.getListeSeance());
        daoProgrammeSportifPostgreSQL.supprimerProgrammeSportif(programmeBD1.getId());
        Assertions.assertThrows(SQLException.class, () -> daoProgrammeSportifPostgreSQL.getProgrammeSportifId(programmeBD1.getId()));
    }

    /**
     * Test de la méthode getAllProgrammeSportif de la classe DAOProgrammeSportifPostgreSQL
     */
    @Test
    public void testGetAllProgrammeSportif() throws Exception {
        ProgrammeSportif programmeBD1 = daoProgrammeSportifPostgreSQL.createProgrammeSportif(programmeSportif.getNom(),programmeSportif.getDescription(),programmeSportif.getPrix(),programmeSportif.getType(),programmeSportif.getNbMois(),programmeSportif.getCoach(), (ArrayList<Seance>) programmeSportif.getListeSeance());
        int nbSeanceBD = daoProgrammeSportifPostgreSQL.getAllProgrammeSportif().size();
        daoProgrammeSportifPostgreSQL.supprimerProgrammeSportif(programmeBD1.getId());
        Assertions.assertEquals(nbSeanceBD, daoProgrammeSportifPostgreSQL.getAllProgrammeSportif().size() + 1);
    }

    /**
     * Test de la méthode supprimerSeanceProgramme de la classe DAOProgrammeSportifPostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testSupprimerSeanceProgrammeSportif() throws Exception {
        Seance seance = FactoryDAOPostgreSQL.getInstance().getDAOSeance().getAllSeances().get(0);
        daoProgrammeSportifPostgreSQL.ajouterSeanceProgramme(seance,programmeBD.getId());
        int size = daoProgrammeSportifPostgreSQL.getProgrammeSportifId(programmeBD.getId()).getListeSeance().size();
        daoProgrammeSportifPostgreSQL.supprimerSeanceProgramme(seance,programmeBD.getId());
        ProgrammeSportif programmeSportif1 = daoProgrammeSportifPostgreSQL.getProgrammeSportifId(programmeBD.getId());
        Assertions.assertEquals(0, size-1);
    }

    /**
     * Test de la méthode ajouterSeanceProgramme de la classe DAOProgrammeSportifPostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testAjouterSeanceProgrammeSportif() throws Exception {
        Seance seance = FactoryDAOPostgreSQL.getInstance().getModelSeance().getAllSeances().get(0);
        daoProgrammeSportifPostgreSQL.ajouterSeanceProgramme(seance,programmeBD.getId());
        int size = daoProgrammeSportifPostgreSQL.getProgrammeSportifId(programmeBD.getId()).getListeSeance().size();
        daoProgrammeSportifPostgreSQL.supprimerSeanceProgramme(seance,programmeBD.getId());
        Assertions.assertEquals(size, programmeBD.getListeSeance().size() + 1);
    }

}
