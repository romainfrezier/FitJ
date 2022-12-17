package com.fitj.dao;

import com.fitj.classes.Exercice;
import com.fitj.enums.Sexe;

/**
 * Classe parente de tous les modèles exercice qui permettent d'intéragir avec tout type de base de données
 * pour toutes modifications de cette dernière en rapport avec les exercices
 *
 * Classe abstraite non instanciable
 *
 * @author Etienne Tillier
 */
public abstract class DAOExercice extends DAO {
    public DAOExercice() {
        super("exercice");
    }

    /**
     * @param id int, l'id de l'exercice
     * @return l'exercice dans la base de donnée avec l'id rentré en paramètre
     * @throws Exception
     */
    public abstract Exercice getExerciceById(int id) throws Exception;

    /**
     * Creer un exercice dans la base de donnée
     * @param nom String, le nom de l'exercice
     * @param description String, la description de l'exercice
     * @throws Exception
     */
    public abstract void createExercice(String nom, String description) throws Exception;



}
