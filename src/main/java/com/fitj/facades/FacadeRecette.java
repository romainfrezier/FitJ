package com.fitj.facades;
import com.fitj.classes.Coach;
import com.fitj.classes.Recette;
import com.fitj.dao.DAORecette;
import com.fitj.dao.factory.FactoryDAO;
import com.fitj.interfaces.Ingredient;
import kotlin.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Facade utilisée pour les opérations sur les recettes
 * @see Facade
 * @author Paul Merceur
 */
public class FacadeRecette extends Facade {

    /**
     * Instance du DAO
     */
    protected DAORecette recetteDAO;

    /**
     * Instance de la Facade, utilisée pour le pattern Singleton
     */
    private static FacadeRecette instance = null;

    /**
     * Constructeur de la FacadeRecette
     */
    protected FacadeRecette(){
        this.recetteDAO = FactoryDAO.getInstance().getDAORecette();
    }

    /**
     * Méthode permettant de récupérer l'instance de la FacadeRecette
     * @return FacadeRecette, l'instance de la FacadeRecette
     */
    public static FacadeRecette getInstance(){
        if (instance == null){
            instance = new FacadeRecette();
        }
        return instance;
    }

    /**
     * Méthode permettant de récupérer toutes les recettes
     * @return List<Recette>, la liste des recettes
     * @throws Exception en cas d'erreur
     */
    public List<Recette> getListeRecettes() throws Exception{
        return this.recetteDAO.getAllRecettes();
    }

    /**
     * Supprime une recette de la base de données
     * @param idRecette int, la recette à supprimer
     * @throws Exception en cas d'erreur
     */
    public void deleteRecette(int idRecette) throws Exception{
        this.recetteDAO.supprimerRecette(idRecette);
    }

    /**
     * Méthode permettant de récupérer une recette, à partir de son id
     * @param idRecette int, l'id de la recette à récupérer
     * @return Recette, la recette récupérée
     * @throws Exception en cas d'erreur
     */
    public Recette getRecetteById(int idRecette) throws Exception{
        return this.recetteDAO.getRecetteById(idRecette);
    }

    /**
     * Méthode permettant de récupérer les recettes d'un coach
     * @param coach Coach, le coach dont on veut récupérer les recettes
     * @return List<Recette>, la liste des recettes du coach
     * @throws Exception en cas d'erreur
     */
    public List<Recette> getRecetteByCoach(Coach coach) throws Exception{
        return this.recetteDAO.getAllRecettesByCoach(coach.getId());
    }

    /**
     * Met à jour la recette dans la base de donnée via le DAO
     * @param idRecette int, l'id de la recette à mettre à jour
     * @param nomRecette String, le nom de la recette
     * @return Recette, la recette mise à jour
     * @throws Exception en cas d'erreur
     */
    public Recette updateRecette(int idRecette, String nomRecette) throws Exception{
        //ajout suppression
        List<Pair<String, Object>> updateValue = new ArrayList<>();
        updateValue.add(new Pair<>("nom", nomRecette));
        return this.recetteDAO.updateRecette(updateValue, idRecette);
    }

    /**
     * Créer la recette dans la base de donnée via le DAO
     * @param nomRecette String, le nom de la recette à créer
     * @param ingredients List<Ingredient>, la liste des ingrédients de la recette
     * @param coach Coach, le coach qui a créé la recette
     * @return Recette, la recette créée
     * @throws Exception en cas d'erreur
     */
    public Recette createRecette(String nomRecette, List<Ingredient> ingredients, Coach coach) throws Exception{
        return this.recetteDAO.createRecette(nomRecette, coach, ingredients);
    }

    /**
     * Supprimer un ingrédient de la recette dans la base de donnée via le DAO
     * @param idRecette int, l'id de la recette à récupérer
     * @param ingredient Ingredient, l'ingrédient à supprimer de la recette
     * @throws Exception si l'ingrédient n'est pas dans la recette
     */
    public void removeIngredientFromRecette(int idRecette, Ingredient ingredient) throws Exception{
        this.recetteDAO.supprimerIngredient(ingredient, idRecette);
    }

    /**
     * Ajouter un ingrédient à la recette dans la base de donnée via le DAO
     * @param idRecette int, l'id de la recette à récupérer
     * @param ingredient Ingredient, l'ingrédient à ajouter à la recette
     * @throws Exception si l'ingrédient est déjà présent dans la recette
     */
    public void addIngredientToRecette(int idRecette, Ingredient ingredient) throws Exception{
        this.recetteDAO.ajouterIngredient(ingredient, idRecette);
    }

}
