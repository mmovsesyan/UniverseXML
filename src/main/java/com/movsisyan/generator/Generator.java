package com.movsisyan.generator;

import com.movsisyan.model.Galaxy;
import com.movsisyan.model.Planet;

import java.util.ArrayList;
import java.util.Random;

public class Generator {
    public static int randomNumber(int a, int b) {
        return (int) (a + (Math.random() * (b - a) + 1));
    }

    public static Planet randomPlanet(int a, int b) {
        boolean randomBool = new Random().nextBoolean();
        return new Planet("P" + " " + randomNumber(a, b), randomNumber(a, b), randomBool, randomNumber(a, b), randomNumber(a, b));
    }

    public static Galaxy randomGalaxy(int a, int b) {
        Galaxy galaxy = new Galaxy("G " + randomNumber(a, b));
        for (int i = 0; i < (int) (Math.random() * 10); i++) {
            galaxy.addPlanet(randomPlanet((int) (Math.random() * i), (int) (Math.random() * (i + 1))));
        }
        return galaxy;
    }

    public static ArrayList<Galaxy> generateGalaxies(int n) {
        ArrayList<Galaxy> galaxyList = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            galaxyList.add(Generator.randomGalaxy((int) (Math.random() * n), (int) (Math.random() * n + 1)));
        }
        return galaxyList;
    }

}
