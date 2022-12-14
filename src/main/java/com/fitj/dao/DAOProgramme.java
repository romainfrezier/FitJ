package com.fitj.dao;

/**
 * Classe parente de tous les modèles programme qui permettent d'intéragir avec tout type de base de données
 * pour toutes modifications de cette dernière en rapport avec les programmes
 *
 * Classe abstraite non instanciable
 *
 * @author Etienne Tillier
 */
public abstract class DAOProgramme extends DAO {
    public DAOProgramme() {
        super("programme");
    }
}