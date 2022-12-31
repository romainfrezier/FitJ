package com.fitj.dao.tool;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe fournissant une méthode statique pour récupérer les noms des paramètres d'une méthode.
 * @author Etienne Tillier
 */
public final class GetParams {

    /**
     * Récupère la liste des noms des paramètres d'une méthode
     *
     * @param method la méthode pour laquelle on veut récupérer les noms des paramètres
     * @return List<String>, la liste des noms des paramètres de la méthode
     * @throws IllegalArgumentException si les noms des paramètres ne sont pas présents
     */
    public static List<String> getParameterNames(Method method) {
        Parameter[] parameters = method.getParameters();
        List<String> parameterNames = new ArrayList<>();

        for (Parameter parameter : parameters) {
            if(!parameter.isNamePresent()) {
                throw new IllegalArgumentException("Parameter names are not present!");
            }
            String parameterName = parameter.getName();
            parameterNames.add(parameterName);
        }

        return parameterNames;
    }

}
