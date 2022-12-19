package com.fitj.dao.postgresql;

import com.fitj.classes.*;
import com.fitj.dao.DAORecette;
import com.fitj.dao.factory.FactoryDAOPostgreSQL;
import com.fitj.dao.methodesBD.MethodesPostgreSQL;
import com.fitj.interfaces.IsIngredient;
import kotlin.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Classe qui permet d'intéragir avec la base de données PostgreSQL pour ce qui fait référence aux recettes
 *
 * @author Etienne Tillier
 */
public class DAORecettePostgreSQL extends DAORecette {

    public DAORecettePostgreSQL(){
        super();
        this.methodesBD = new MethodesPostgreSQL();
    }

    @Override
    public Recette createRecette(String nom, List<Aliment> aliments) throws Exception {
        return null;
    }

    @Override
    public Recette getRecetteById(int id) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id", id));
        ResultSet recetteBD = ((MethodesPostgreSQL)this.methodesBD).selectWhere(whereList, this.table);
        try{
            if (recetteBD.next()){
                Coach coach = (Coach) FactoryDAOPostgreSQL.getInstance().getModelClient().getClientAccount(recetteBD.getInt("idcoach"));
                List<IsIngredient> listeIngredients = getIngredientsFromRecette(id);
                return new Recette(recetteBD.getInt("id"),recetteBD.getString("nom"),coach, (ArrayList)listeIngredients);
            }
            else {
                throw new SQLException("Aucune recette avec cet id n'existe");
            }
        }
        catch(Exception e){
            throw new SQLException("La sélection de la recette a échoué");
        }
    }

    public List<IsIngredient> getIngredientsFromRecette(int id) throws Exception{
        List<IsIngredient> listeAliment = new ArrayList<>();
        List<Pair<String, Object>> whereListAliment = new ArrayList<>();
        whereListAliment.add(new Pair<>("idrecette", id));
        List<Pair<String, Object>> whereListRecette1 = new ArrayList<>();
        whereListRecette1.add(new Pair<>("idrecette1", id));
        List<Pair<String, Object>> whereListRecette2 = new ArrayList<>();
        whereListRecette2.add(new Pair<>("idrecette2", id));
        try {
            ResultSet alimentsBD = ((MethodesPostgreSQL)this.methodesBD).selectWhere(whereListAliment, "recettealiment");
            ResultSet recettesBD1 = ((MethodesPostgreSQL)this.methodesBD).selectWhere(whereListRecette1, "recetterecette");
            ResultSet recettesBD2 = ((MethodesPostgreSQL)this.methodesBD).selectWhere(whereListRecette2, "recetterecette");
            while (alimentsBD.next()){
                listeAliment.add(FactoryDAOPostgreSQL.getInstance().getModelAliment().getAlimentById(alimentsBD.getInt("idaliment")));
            }
            while (recettesBD1.next()){
                listeAliment.add(this.getRecetteById(recettesBD1.getInt("idrecette2")));
            }
            while (recettesBD2.next()){
                listeAliment.add(this.getRecetteById(recettesBD1.getInt("idrecette1")));
            }
            return listeAliment;
        }
        catch (Exception e){
            throw new SQLException("Les ingrédients de la recette n'ont pas pu être trouvés");
        }
    }

    @Override
    public void deleteRecette(int id) throws Exception {

    }

    @Override
    public Recette updateRecette(List<Pair<String, Object>> updateList, int id) throws Exception {
        return null;
    }

}
