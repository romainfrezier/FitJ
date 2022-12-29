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

    /**
     * @param idRecette l'id de la recette à récupérer
     * @return la recette correspondant à l'id
     * @throws Exception
     */
    public Recette getRecetteById(int idRecette) throws Exception{
        return this.recetteDAO.getRecetteById(idRecette);
    }

    /**
     * @param coach le coach qui a créé la recette
     * @return la liste des recettes créées par le coach
     * @throws Exception
     */
    public List<Recette> getRecetteByCoach(Coach coach) throws Exception{
        return this.recetteDAO.getAllRecettesByCoach(coach.getId());
    }

    /**
     * Met à jour la recette dans la base de donnée via le DAO
     * @param idRecette l'id de la recette à récupérer
     * @param nomRecette le nom de la recette à récupérer
     * @return la recette correspondant à l'id mise à jour dans la base de données
     * @throws Exception
     */
    public Recette updateRecette(int idRecette, String nomRecette) throws Exception{
        //ajout suppression
        List<Pair<String, Object>> updateValue = new ArrayList<>();
        updateValue.add(new Pair<>("nom", nomRecette));
        return this.recetteDAO.updateRecette(updateValue, idRecette);
    }

    /**
     * Créer la recette dans la base de donnée via le DAO
     * @param nomRecette le nom de la recette à créer
     * @param ingredients la liste des ingrédients de la recette
     * @param coach le coach qui a créé la recette
     * @return la recette créée
     * @throws Exception
     */
    public Recette createRecette(String nomRecette, List<Ingredient> ingredients, Coach coach) throws Exception{
        return this.recetteDAO.createRecette(nomRecette, coach, ingredients);
    }

    /**
     * Supprimer un ingrédient de la recette dans la base de donnée via le DAO
     * @param idRecette l'id de la recette à récupérer
     * @param ingredient l'ingrédient à ajouter à la recette
     * @throws Exception si l'ingrédient est déjà présent dans la recette
     */
    public void removeIngredientFromRecette(int idRecette, Ingredient ingredient) throws Exception{
        this.recetteDAO.supprimerIngredient(ingredient, idRecette);
    }

    /**
     * Ajouter un ingrédient à la recette dans la base de donnée via le DAO
     * @param idRecette l'id de la recette à récupérer
     * @param ingredient l'ingrédient à ajouter à la recette
     * @throws Exception si l'ingrédient est déjà présent dans la recette
     */
    public void addIngredientToRecette(int idRecette, Ingredient ingredient) throws Exception{
        this.recetteDAO.ajouterIngredient(ingredient, idRecette);
    }




}
