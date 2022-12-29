package com.fitj.facades;
import com.fitj.classes.Coach;
import com.fitj.classes.Recette;
import com.fitj.dao.DAORecette;
import com.fitj.dao.factory.FactoryDAO;
import com.fitj.interfaces.Ingredient;
import kotlin.Pair;

import java.util.ArrayList;
import java.util.List;

public class FacadeRecette extends Facade {

    protected DAORecette recetteDAO;
    private static FacadeRecette instance = null;
    protected FacadeRecette(){
        this.recetteDAO = FactoryDAO.getInstance().getDAORecette();
    }

    public static FacadeRecette getInstance(){
        if (instance == null){
            instance = new FacadeRecette();
        }
        return instance;
    }

    /**
     * @return la liste des recettes de la base de données
     * @throws Exception
     */
    public List<Recette> getListeRecettes() throws Exception{
        return this.recetteDAO.getAllRecettes();
    }

    /**
     * Supprime une recette de la base de données
     * @param idRecette l'id de la recette à récupérer
     * @throws Exception
     */

    public void deleteRecette(int idRecette) throws Exception{
        this.recetteDAO.supprimerRecette(idRecette);
    }

    public Recette getRecetteById(int idRecette) throws Exception{
        return this.recetteDAO.getRecetteById(idRecette);
    }

    public Recette updateRecette(int idRecette, String nomRecette) throws Exception{
        //ajout suppression
        List<Pair<String, Object>> updateValue = new ArrayList<>();
        updateValue.add(new Pair<>("nom", nomRecette));
        return this.recetteDAO.updateRecette(updateValue, idRecette);
    }

    public Recette createRecette(String nomRecette, List<Ingredient> ingredients, Coach coach) throws Exception{
        return this.recetteDAO.createRecette(nomRecette, coach, ingredients);
    }

    public void removeIngredientFromRecette(int idRecette, Ingredient ingredient) throws Exception{
        this.recetteDAO.supprimerIngredient(ingredient, idRecette);
    }

    public void addIngredientToRecette(int idRecette, Ingredient ingredient) throws Exception{
        this.recetteDAO.ajouterIngredient(ingredient, idRecette);
    }




}
