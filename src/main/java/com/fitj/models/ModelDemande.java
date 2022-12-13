package com.fitj.models;

/**
 * Classe parente de tous les modèles demande qui permettent d'intéragir avec tout type de base de données
 * pour toutes modifications de cette dernière en rapport avec les demandes
 *
 * Classe abstraite non instanciable
 *
 * @author Etienne Tillier
 */
public abstract class ModelDemande extends Model {
    public ModelDemande() {
        super("demande");
    }
}
