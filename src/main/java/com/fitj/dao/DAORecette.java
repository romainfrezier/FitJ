package com.fitj.dao;


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

}
