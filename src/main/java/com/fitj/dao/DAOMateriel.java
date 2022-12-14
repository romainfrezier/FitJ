package com.fitj.dao;

/**
 * Classe parente de tous les modèles matériel qui permettent d'intéragir avec tout type de base de données
 * pour toutes modifications de cette dernière en rapport avec les matériels
 *
 * Classe abstraite non instanciable
 *
 * @author Etienne Tillier
 */
public abstract class DAOMateriel extends DAO {
    public DAOMateriel() {
        super("materiel");
    }
}