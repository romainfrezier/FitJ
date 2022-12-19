package com.fitj.dao;


import com.fitj.classes.Aliment;
import com.fitj.classes.Recette;
import kotlin.Pair;

import java.util.List;

/**
 * Classe parente de tous les modèles recette qui permettent d'intéragir avec tout type de base de données
 * pour toutes modifications de cette dernière en rapport avec les recettes
 *
 * Classe abstraite non instanciable
 *
 * @author Etienne Tillier
 */
public abstract class DAORecette extends DAO {
    public DAORecette() {
        super("recette");
    }


    /**
     * @param nom String, le nom de la recette
     * @param aliments List<Aliment>, la liste des aliments présents dans la recette
     * @return la recette créée
     * @throws Exception
     */
    public abstract Recette createRecette(String nom, List<Aliment> aliments) throws Exception;

    /**
     * @param id int, l'id de la recette
     * @return la recette cherchée en base de donnée en fonction de l'id rentrée en paramètre
     * @throws Exception
     */
    public abstract Recette getRecetteById(int id) throws Exception;

    /**
     * Supprimer la recette dans la base de donnée
     * @param id int, l'id de la recette
     * @throws Exception
     */
    public abstract void deleteRecette(int id) throws Exception;

    /**
     * Met à jour la recette dans la base de donnée
     * @param updateList List<Pair<String, Object>>, la liste des attributs à mettre à jour dans la base de donnée
     * @param id int, l'id de la recette
     * @return la recette mise à jour dans la base de donnée
     * @throws Exception
     */
    public abstract Recette updateRecette(List<Pair<String, Object>> updateList, int id) throws Exception;

}
