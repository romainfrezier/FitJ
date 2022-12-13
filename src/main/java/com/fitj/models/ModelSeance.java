package com.fitj.models;

/**
 * Classe parente de tous les modèles séance qui permettent d'intéragir avec tout type de base de données
 * pour toutes modifications de cette dernière en rapport avec les séances
 *
 * Classe abstraite non instanciable
 *
 * @author Etienne Tillier
 */
public abstract class ModelSeance extends Model {
    public ModelSeance() {
        super("seance");
    }
}
