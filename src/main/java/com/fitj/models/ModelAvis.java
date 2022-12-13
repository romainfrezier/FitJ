package com.fitj.models;

/**
 * Classe parente de tous les modèles avis qui permettent d'intéragir avec tout type de base de données
 * pour toutes modifications de cette dernière en rapport avec les avis
 *
 * Classe abstraite non instanciable
 *
 * @author Etienne Tillier
 */
public abstract class ModelAvis extends Model {
    public ModelAvis() {
        super("avis");
    }
}
