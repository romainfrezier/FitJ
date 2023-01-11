package com.fitj.dao;

import com.fitj.classes.*;
import com.fitj.dao.factory.FactoryDAOPostgreSQL;
import com.fitj.dao.postgresql.DAORecettePostgreSQL;
import com.fitj.enums.Sexe;
import com.fitj.interfaces.Ingredient;
import kotlin.Pair;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe de test de la DAO RecettePostgreSQL
 * @see DAORecettePostgreSQL
 * @author Etienne Tillier, Romain Frezier
 */
public class DAORecettePostgreSQLTest {

    /**
     * Objet utilisé pour les tests
     */
    private static Recette recette;

    /**
     * Objet utilisé pour les tests
     */
    private static Recette recetteBD;

    /**
     * DAO utilisé pour les tests
     */
    private static DAORecettePostgreSQL daoRecettePostgreSQL;

    /**
     * Objet utilisé pour les tests
     */
    public static Aliment aliment;

    /**
     * Objet utilisé pour les tests
     */
    public static Coach coach;

    /**
     * Objet utilisé pour les tests
     */
    public static ArrayList<Ingredient> ingredients;

    /**
     * Initialisation des objets utilisés pour les tests
     * @throws Exception si la requête SQL échoue
     */
    @BeforeAll
    public static void init() throws Exception {
        daoRecettePostgreSQL = new DAORecettePostgreSQL();
        coach = new Coach("coach@gmail.com", "elcocho", 100, "dadada", 174, Sexe.HOMME, "test", 44,false);
        aliment = FactoryDAOPostgreSQL.getInstance().getDAOAliment().createAliment("Cacao");
        ingredients = new ArrayList<>();
        ingredients.add(aliment);
        recette = new Recette(1,"Poulet roti",coach);
        recetteBD = daoRecettePostgreSQL.createRecette(recette.getNom(), coach, ingredients);
    }

    /**
     * Méthode appelée après tous les tests
     * @throws Exception si la requête SQL échoue
     */
    @AfterAll
    public static void clean() throws Exception{
        FactoryDAOPostgreSQL.getInstance().getDAOAliment().supprimerAliment(aliment.getId());
        daoRecettePostgreSQL.supprimerRecette(recetteBD.getId());
    }

    /**
     * Test de la méthode createRecette
     */
    @Test
    public void testCreateRecette() {
        Assertions.assertEquals(recetteBD.getNom(), recette.getNom());
    }

    /**
     * Test de la méthode updateRecette
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testRecetteUpdate() throws Exception {
        List<Pair<String,Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("nom","Gateau à la fraise"));
        recetteBD = daoRecettePostgreSQL.updateRecette(updateList,recetteBD.getId());
        Assertions.assertEquals("Gateau à la fraise", recetteBD.getNom());
    }

    /**
     * Test de la méthode supprimerRecette
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testRecetteDelete() throws Exception {
        Recette recetteBD1 = daoRecettePostgreSQL.createRecette("Gateau au chocolat",coach,ingredients);
        daoRecettePostgreSQL.supprimerRecette(recetteBD1.getId());
        Assertions.assertThrows(Exception.class, () -> daoRecettePostgreSQL.getRecetteById(recetteBD1.getId()));
    }

    /**
     * Test de la méthode getAllRecette
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testGetAllRecette() throws Exception {
        int nbRecetteBD = daoRecettePostgreSQL.getAllRecettes().size();
        Assertions.assertTrue(nbRecetteBD > 0);
    }

    /**
     * Test de l'ajout d'une recette dans une autre
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testRecetteDansRecette() throws Exception{
        ArrayList<Ingredient> newIngredients = new ArrayList<>();
        newIngredients.add(aliment);
        newIngredients.add(recetteBD);
        Recette newRecette = daoRecettePostgreSQL.createRecette("Gros gateau", coach, newIngredients);
//        newRecette = daoRecettePostgreSQL.getRecetteById(newRecette.getId());
        daoRecettePostgreSQL.supprimerRecette(newRecette.getId());
        Assertions.assertEquals(((Recette) newRecette.getIngredients().get(1)).getNom(), recetteBD.getNom());
    }

    /**
     * Test de la méthode supprimer ingredient
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testSupprimerIngredientSeance() throws Exception {
        Recette recette1 = daoRecettePostgreSQL.createRecette("Gros gateau", coach, new ArrayList<>());
        Aliment aliment1 = FactoryDAOPostgreSQL.getInstance().getDAOAliment().createAliment("Saucisson");

        daoRecettePostgreSQL.ajouterIngredient(recette1, recetteBD.getId());
        daoRecettePostgreSQL.ajouterIngredient(aliment1, recetteBD.getId());

        int size = daoRecettePostgreSQL.getRecetteById(recetteBD.getId()).getIngredients().size();

        daoRecettePostgreSQL.supprimerIngredient(recette1, recetteBD.getId());
        daoRecettePostgreSQL.supprimerIngredient(aliment1, recetteBD.getId());

        daoRecettePostgreSQL.supprimerRecette(recette1.getId());
        FactoryDAOPostgreSQL.getInstance().getDAOAliment().supprimerAliment(aliment1.getId());

        Assertions.assertEquals(size - 2, recetteBD.getIngredients().size());
    }

    /**
     * Test de la méthode ajouter ingredient
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testAjouterIngredientSeance() throws Exception {
        Recette recette1 = daoRecettePostgreSQL.createRecette("Recette1", coach, new ArrayList<>());
        daoRecettePostgreSQL.ajouterRecette(recette1, recetteBD.getId());

        int size = daoRecettePostgreSQL.getRecetteById(recetteBD.getId()).getIngredients().size();

        daoRecettePostgreSQL.supprimerIngredient(recette1, recetteBD.getId());
        daoRecettePostgreSQL.supprimerRecette(recette1.getId());

        Assertions.assertEquals(size, recetteBD.getIngredients().size()+1);
    }

}
