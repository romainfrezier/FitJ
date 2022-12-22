package com.fitj.classes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Classe de test de la classe Materiel
 * @see Materiel
 * @author Romain Frezier
 */
public class TestMateriel {

    /**
     * Objet Materiel
     */
    private static Materiel materiel;

    /**
     * Méthode d'initialisation réalisée avant chaque test
     */
    @BeforeAll
    public static void init(){
        materiel = new Materiel(1,"Tapis de course");
    }

    /**
     * Test du constructeur de la classe Materiel
     */
    @Test
    public void testMaterielConstructeur(){
        Assertions.assertEquals(1, materiel.getId());
        Assertions.assertEquals("Tapis de course", materiel.getNom());
    }

    /**
     * Test de la méthode getNom de la classe Materiel
     */
    @Test
    public void testMaterielGetNom(){
        Assertions.assertEquals("Tapis de course", materiel.getNom());
    }

    /**
     * Test de la méthode setNom de la classe Materiel
     */
    @Test
    public void testMaterielSetNom(){
        materiel.setNom("Tapis de course motorisé");
        Assertions.assertEquals("Tapis de course motorisé", materiel.getNom());
    }

    /**
     * Test de la méthode getId de la classe Materiel
     */
    @Test
    public void testMaterielGetId(){
        Assertions.assertEquals(1, materiel.getId());
    }

    /**
     * Test de la méthode setId de la classe Materiel
     */
    @Test
    public void testMaterielSetId(){
        materiel.setId(2);
        Assertions.assertEquals(2, materiel.getId());
    }
}
