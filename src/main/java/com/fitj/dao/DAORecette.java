package com.fitj.dao;


import com.fitj.classes.Coach;
import com.fitj.classes.Recette;
import com.fitj.interfaces.Ingredient;
import kotlin.Pair;

import java.util.List;

/**
 * Classe parente de tous les modèles recette qui permettent d'intéragir avec tout type de base de données
 * pour toutes modifications de cette dernière en rapport avec les recettes
 * Classe abstraite non instanciable
 * @see DAO
 * @author Etienne Tillier, Romain Frezier
 */
public abstract class DAORecette extends DAO {
    public DAORecette() {
        super("recette");
    }


    /**
     * @param nom String, le nom de la recette
     * @param aliments List<IsIngredient>, la liste des aliments présents dans la recette
     * @return la recette créée
     * @throws Exception si la requête échoue
     */
    public abstract Recette createRecette(String nom, Coach coach, List<Ingredient> aliments) throws Exception;

    /**
     * @param id int, l'id de la recette
     * @return la recette cherchée en base de donnée en fonction de l'id rentrée en paramètre
     * @throws Exception si la requête échoue
     */
    public abstract Recette getRecetteById(int id) throws Exception;

    /**
     * @param id int, l'id de la recette
     * @return la recette cherchée en base de donnée en fonction de l'id rentrée en paramètre
     * @throws Exception si la requête échoue
     */
    public abstract Recette getRecetteByIdWithoutIngredients(int id) throws Exception;

    /**
     * Supprimer la recette dans la base de donnée
     * @param id int, l'id de la recette
     * @throws Exception si la requête échoue
     */
    public abstract void supprimerRecette(int id) throws Exception;

    /**
     * @return la liste de toutes les recettes présente dans la base de donnée
     * @throws Exception si la requête échoue
     */
    public abstract List<Recette> getAllRecettes() throws Exception;

    /**
     * @return la liste de toutes les recettes présente dans la base de donnée
     * @param whereList List<Pair<String,Object>>, la liste des conditions du where pour la requête
     * @throws Exception si la requête échoue
     */
    public abstract List<Recette> getAllRecettesWhere(List<Pair<String,Object>> whereList) throws Exception;

    /**
     * Met à jour la recette dans la base de donnée
     * @param updateList List<Pair<String, Object>>, la liste des attributs à mettre à jour dans la base de donnée
     * @param id int, l'id de la recette
     * @return la recette mise à jour dans la base de donnée
     * @throws Exception si la requête échoue
     */
    public abstract Recette updateRecette(List<Pair<String, Object>> updateList, int id) throws Exception;

    /**
     * @param idCoach int, l'id du coach
     * @return la liste de toutes les recettes présente dans la base de donnée pour le coach
     * @throws Exception si la requête échoue
     */
    public abstract List<Recette> getAllRecettesByCoach(int idCoach) throws Exception;

    /**
     * Ajoute un ingrédient à la recette
     * @param ingredient IsIngredient, l'ingrédient à ajouter à la recette
     * @param id int, l'id de la recette
     * @throws Exception si la requête échoue
     */
    public abstract void ajouterIngredient(Ingredient ingredient, int id) throws Exception;

    /**
     * Supprimer un ingrédient de la recette
     * @param ingredient IsIngredient, l'ingrédient à supprimer de la recette
     * @param id int, l'id de la recette
     * @throws Exception si la requête échoue
     */
    public abstract void supprimerIngredient(Ingredient ingredient, int id) throws Exception;

}
