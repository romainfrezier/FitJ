package com.fitj.models;

/**
 * Classe parente de tous les modèles sport qui permettent d'intéragir avec tout type de base de données
 * pour toutes modifications de cette dernière en rapport avec les sports
 *
 * Classe abstraite non instanciable
 *
 * @author Etienne Tillier
 */
public abstract class ModelSport extends Model {
    public ModelSport() {
        super("sport");
    }
}
