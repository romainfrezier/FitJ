package com.fitj.dao;

import com.fitj.classes.*;
import com.fitj.dao.factory.FactoryDAOPostgreSQL;
import com.fitj.dao.postgresql.DAORecettePostgreSQL;
import com.fitj.enums.Sexe;
import com.fitj.interfaces.IsIngredient;
import kotlin.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestDAORecettePostgreSQL {

    private static Recette recette;

    private static DAORecettePostgreSQL daoRecettePostgreSQL;

    public static Aliment aliment;

    public static Coach coach;

    public static ArrayList<IsIngredient> ingredients;


    @BeforeAll
    public static void init() throws Exception {
        daoRecettePostgreSQL = new DAORecettePostgreSQL();
        coach = new Coach("coach@gmail.com", "elcocho", 100, "dadada", 174, Sexe.HOMME, "test", 44);
        aliment = FactoryDAOPostgreSQL.getInstance().getModelAliment().createAliment("Cacao");
        ingredients = new ArrayList<>();
        ingredients.add(aliment);
        recette = new Recette(1,"Poulet roti",coach);

    }

    @Test
    public void testCreateRecette() throws Exception {
        Recette recetteBD = daoRecettePostgreSQL.createRecette(recette.getNom(),coach,ingredients);
        daoRecettePostgreSQL.supprimerRecette(recetteBD.getId());
        Assertions.assertTrue(recetteBD.getNom().equals(recetteBD.getNom()));
    }


    @Test
    public void testRecetteUpdate() throws Exception {
        Recette recetteBD = daoRecettePostgreSQL.createRecette("Gateau au chocolat",coach,ingredients);
        List<Pair<String,Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("nom","Gateau à la fraise"));
        recetteBD = daoRecettePostgreSQL.updateRecette(updateList,recetteBD.getId());
        daoRecettePostgreSQL.supprimerRecette(recetteBD.getId());
        Assertions.assertTrue(recetteBD.getNom().equals("Gateau à la fraise"));
    }


    @Test
    public void testRecetteDelete() throws Exception {
        Recette recetteBD = daoRecettePostgreSQL.createRecette("Gateau au chocolat",coach,ingredients);
        daoRecettePostgreSQL.supprimerRecette(recetteBD.getId());
        Assertions.assertThrows(SQLException.class,
                () -> daoRecettePostgreSQL.getRecetteById(recetteBD.getId()));
    }

    @Test
    public void testGetAllRecette() throws Exception {
        Recette recetteBD = daoRecettePostgreSQL.createRecette("Gateau au chocolat",coach,ingredients);
        int nbRecetteBD = daoRecettePostgreSQL.getAllRecettes(new ArrayList<>()).size();
        daoRecettePostgreSQL.supprimerRecette(recetteBD.getId());
        Assertions.assertTrue(nbRecetteBD == daoRecettePostgreSQL.getAllRecettes(new ArrayList<>()).size() + 1);
    }

    @Test
    public void testRecetteDansRecette() throws Exception{
        Recette recetteBD = daoRecettePostgreSQL.createRecette("Gateau au chocolat",coach,ingredients);
        ArrayList<IsIngredient> newIngredients = new ArrayList<>();
        newIngredients.add(aliment);
        newIngredients.add(recetteBD);
        Recette newRecette = daoRecettePostgreSQL.createRecette("Gros gateau", coach, newIngredients);
        newRecette = daoRecettePostgreSQL.getRecetteById(newRecette.getId());
        daoRecettePostgreSQL.supprimerRecette(newRecette.getId());
        daoRecettePostgreSQL.supprimerRecette(recetteBD.getId());
        Assertions.assertTrue(((Recette)newRecette.getIngredients().get(1)).getNom().equals(recetteBD.getNom()));
    }

}