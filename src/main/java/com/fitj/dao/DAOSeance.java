package com.fitj.dao;

import com.fitj.classes.Seance;

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

    //public abstract void createSeance()
}
