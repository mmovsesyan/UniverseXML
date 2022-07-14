package com.movsisyan.program;

import com.movsisyan.generator.Generator;
import com.movsisyan.model.Galaxy;
import com.movsisyan.model.Planet;
import com.movsisyan.model.Universe;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class Program {
    public static void main(String[] args) {
        try {
            Planet planet = new Planet("Earth", 12500, true, 120000, 5600);
//            planet.toXML("planets.xml");
//            Planet planet1 = Planet.fromXML("planets.xml");
//            System.out.println(planet1);
            Galaxy galaxy = new Galaxy("Milky Way");
            galaxy.addPlanet(planet);
            galaxy.addPlanet(new Planet("Mars", 6500, false, 20000, 4000));
            galaxy.toXML("galaxies.xml");


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
//        try {
//            Planet planet = new Planet();
//            planet.toXML("planets.xml");
//            Galaxy galaxy = new Galaxy();
//            galaxy.toXML("galaxies.xml");
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }

        try {
            Universe.toXML("Universe.xml", "newFileUniverse.xml");
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }

    }
}
