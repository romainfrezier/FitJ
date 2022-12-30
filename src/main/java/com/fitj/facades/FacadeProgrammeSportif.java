package com.fitj.facades;

import com.fitj.classes.Coach;
import com.fitj.classes.Recette;
import com.fitj.interfaces.Ingredient;
import kotlin.Pair;

import java.util.ArrayList;
import java.util.List;

public class FacadeProgrammeSportif extends Facade{


    private static FacadeProgrammeSportif instance = null;
    protected FacadeProgrammeSportif(){

    }

    public static FacadeProgrammeSportif getInstance(){
        if (instance == null){
            instance = new FacadeProgrammeSportif();
        }
        return instance;
    }




}
