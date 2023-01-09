package com.fitj.dao;

import com.fitj.classes.Aliment;
import com.fitj.classes.Aliment;
import kotlin.Pair;

import java.util.List;

/**
 * Classe parente de tous les modèles aliment qui permettent d'intéragir avec tout type de base de données
 * pour toutes modifications de cette dernière en rapport avec les aliments
 * Classe abstraite non instanciable
 * @see DAO
 * @author Etienne Tillier
 */
public abstract class DAOAliment extends DAO {
    public DAOAliment() {
        super("aliment");
    }

    /**
     * Créer un aliment dans la base de donnée
     * @param nom String, le nom de l'aliment
     * @return l'aliment créer
     * @throws Exception si une erreur SQL survient
     */
    public abstract Aliment createAliment(String nom) throws Exception;

    /**
     * @param id int, l'id de l'aliment
     * @return l'aliment récupéré dans la base de donné
     * @throws Exception si une erreur SQL survient
     */
    public abstract Aliment getAlimentById(int id) throws Exception;

    /**
     * @return une liste d'aliments contenant tous les aliments présents dans la base de donnée
     * @throws Exception si une erreur SQL survient
     */
    public abstract List<Aliment> getAllAliments() throws Exception;

    /**
     * Supprime l'aliment de la base de donnée
     * @param id int, l'id de l'aliment
     * @throws Exception si une erreur SQL survient
     */
    public abstract void supprimerAliment(int id) throws Exception;

    /**
     * Met à jour l'aliment dans la base de donnée
     * @param updateList List<Pair<String, Object>>, la liste des attributs à mettre à jour dans la table avec leur valeur
     * @param id int, l'id de l'aliment
     * @return l'aliment mis à jour
     * @throws Exception si une erreur SQL survient
     */
    public abstract Aliment updateAliment(List<Pair<String, Object>> updateList, int id) throws Exception;
}
