package com.fitj.models;

/**
 * Classe parente de tous les modèles aliment qui permettent d'intéragir avec tout type de base de données
 * pour toutes modifications de cette dernière en rapport avec les aliments
 *
 * Classe abstraite non instanciable
 *
 * @author Etienne Tillier
 */
public abstract class ModelAliment extends Model {
    public ModelAliment() {
        super("aliment");
    }
}
