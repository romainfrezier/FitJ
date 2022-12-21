package com.fitj.dao.postgresql;

import com.fitj.classes.Coach;
import com.fitj.classes.ProgrammeSportif;
import com.fitj.classes.Recette;
import com.fitj.classes.Seance;
import com.fitj.dao.DAOProgrammeSportif;
import com.fitj.dao.methodesBD.MethodesPostgreSQL;
import com.fitj.enums.ProgrammeType;
import kotlin.Pair;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOProgrammeSportifPostgreSQL extends DAOProgrammeSportif {

    public DAOProgrammeSportifPostgreSQL(){
        super();
        this.methodesBD = new MethodesPostgreSQL();
    }

    @Override
    public ProgrammeSportif createProgrammeSportif(String nom, String description, double prix, ProgrammeType type, int nbMois, Coach coach, ArrayList<Seance> listeSeances) throws Exception {
        List<Pair<String,Object>> listeInsert = new ArrayList<>();
        listeInsert.add(new Pair<>("nom",nom));
        listeInsert.add(new Pair<>("description",description));
        listeInsert.add(new Pair<>("prix",prix));
        listeInsert.add(new Pair<>("type",ProgrammeType.getProgrammeType(type)));
        listeInsert.add(new Pair<>("nbMois",nbMois));
        listeInsert.add(new Pair<>("idcoach",coach.getId()));
        try {
            int idProgramme = ((MethodesPostgreSQL)this.methodesBD).insert(listeInsert, this.table);
            for (Seance seance : listeSeances){
                List<Pair<String,Object>> listeInsertSeance = new ArrayList<>();
                listeInsertSeance.add(new Pair<>("idprogramme",idProgramme));
                listeInsertSeance.add(new Pair<>("idseance",((Seance)seance).getId()));
                ((MethodesPostgreSQL)this.methodesBD).insert(listeInsertSeance, "programmesportseance");
            }
            return this.getProgrammeSportifId(idProgramme);
        }
        catch (Exception e){
            throw new SQLException("La création du programme sportif a échoué");
        }
    }

    @Override
    public ProgrammeSportif getProgrammeSportifId(int id) throws Exception {
        return null;
    }

    @Override
    public ProgrammeSportif updateProgrammeSportif(List<Pair<String, Object>> updateList, int id) throws Exception {
        return null;
    }

    @Override
    public void supprimerProgrammeSportif(int id) throws Exception {

    }

    @Override
    public List<ProgrammeSportif> getAllProgrammeSportif(List<Pair<String, Object>> whereList) throws Exception {
        return null;
    }

    @Override
    public List<Seance> getSeances(int id) throws Exception {
        return null;
    }
}
