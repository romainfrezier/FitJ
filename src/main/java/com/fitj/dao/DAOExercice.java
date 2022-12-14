package com.fitj.dao;

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
}
