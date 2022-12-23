package com.fitj.dao.postgresql;

import com.fitj.dao.DAOPaiement;
import com.fitj.dao.methodesBD.MethodesPostgreSQL;
import com.fitj.enums.PaiementType;
import kotlin.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui permet d'intéragir avec la base de données PostgreSQL pour ce qui fait référence aux paiements
 *
 * @author Etienne Tillier
 */
public class DAOPaiementPostgreSQL extends DAOPaiement {

    public DAOPaiementPostgreSQL(){
        super();
        this.methodesBD = new MethodesPostgreSQL();
    }

    @Override
    public void createPaiement(int idcommande, double prix, PaiementType paiementType) throws Exception {
        List<Pair<String, Object>> listeInsertPaiement = new ArrayList<>();
        listeInsertPaiement.add(new Pair<>("idcommande", idcommande));
        listeInsertPaiement.add(new Pair<>("montant", prix));
        listeInsertPaiement.add(new Pair<>("type", PaiementType.getPaiementType(paiementType)));
        ((MethodesPostgreSQL) this.methodesBD).insert(listeInsertPaiement, "paiement");
    }

}
