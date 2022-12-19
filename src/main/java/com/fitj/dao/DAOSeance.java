package com.fitj.dao;

import com.fitj.classes.Coach;
import com.fitj.classes.Exercice;
import com.fitj.classes.Seance;
import com.fitj.classes.Sport;

import java.util.List;

/**
 * Classe parente de tous les modèles séance qui permettent d'intéragir avec tout type de base de données
 * pour toutes modifications de cette dernière en rapport avec les séances
 *
 * Classe abstraite non instanciable
 *
 * @author Etienne Tillier
 */
public abstract class DAOSeance extends DAO {
    public DAOSeance() {
        super("seance");
    }


    /**
     * @param id int, l'id de la séance
     * @return la séance dans la base de donnée contenant l'id rentré en paramètre
     * @throws Exception
     */
    public abstract Seance getSeanceById(int id) throws Exception;

    /**
     * Créer une séance dans la base de donnée
     * @param nom String, le nom de la séance
     * @param description String, la description de la séance
     * @param prix double, le prix de la séance
     * @param coach Coach, le coach de la séance
     * @param sport Sport, le sport de la séance
     * @param exercices List<Exercice>, la liste des exercices de la séance
     * @throws Exception
     */
    public abstract void createSeance(String nom, String description, double prix, Coach coach, Sport sport, List<Exercice> exercices) throws Exception;


    /**
     * @param id int, l'id de la séance
     * @return la liste des exercices de la séance
     * @throws Exception
     */
    public abstract  List<Exercice> getExercices(int id) throws Exception;
}
