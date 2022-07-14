package com.movsisyan.model;


import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.StringJoiner;

public class Galaxy {
    private String name;
    private ArrayList<Planet> planets = new ArrayList<>();

    public Galaxy() {
    }

    public Galaxy(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Planet> getPlanets() {
        return planets;
    }

    public void setPlanets(ArrayList<Planet> planets) {
        this.planets = planets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Galaxy galaxy = (Galaxy) o;
        return Objects.equals(name, galaxy.name) && Objects.equals(planets, galaxy.planets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, planets);
    }

    @Override
    public String toString() {
        return "Galaxy{" +
                "name='" + name + '\'' +
                ", planets=" + planets +
                '}';
    }


    /**
     * behavior
     *
     * @return
     */
    public String behavior() {
        StringJoiner sj = new StringJoiner(", ");
        for (Planet planet : this.planets) {
            sj.add(planet.behavior());
        }
        return sj.toString();
    }

    /**
     * addPlanet
     */
    public void addPlanet(Planet p) {
        if (findPlanet(p) != -1) {
            throw new IllegalArgumentException("Planet exists " + p);
        }
        this.planets.add(p);
    }

    private int findPlanet(Planet p) {
        return this.planets.indexOf(p);
    }

    public Planet findPlanet(String name) {
        for (Planet p : this.planets) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }

    public void removePlanet(String name) {
        Planet planet = findPlanet(name);
        if (planet != null) {
            this.planets.remove(planet);
        }
    }

    public Galaxy fromXML(String fileName) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(new File(fileName));

        Element element = (Element) document.getElementsByTagName("galaxy").item(0);
        String name = element.getAttribute("name");
        Galaxy galaxy = new Galaxy(name);

        NodeList planets = element.getElementsByTagName("planet");
        for (int i = 0; i < planets.getLength(); i++) {
            Element element1 = (Element) planets.item(i);
            String name1 = element1.getElementsByTagName("name").item(i).getTextContent();
            int diameter = Integer.parseInt(element1.getElementsByTagName("diameter").item(i).getTextContent());
            boolean forHuman = Boolean.parseBoolean(element1.getElementsByTagName("forHuman").item(i).getTextContent());
            int percentAreaForLive = Integer.parseInt(element1.getElementsByTagName("percentAreaForLive").item(i).getTextContent());
            int percentWater = Integer.parseInt(element1.getElementsByTagName("percentWater").item(i).getTextContent());
            Planet planet = new Planet(name1, diameter, forHuman, percentAreaForLive, percentWater);
            galaxy.addPlanet(planet);
        }
        return galaxy;
    }

    public void toXML(String fileNAme) throws TransformerException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();
        Element galaxyElement = document.createElement("Galaxy");
        document.appendChild(galaxyElement);

        Element element = document.createElement("Name");
        element.setTextContent(this.name);
        galaxyElement.appendChild(element);


        for (Planet planet : this.planets) {
            Element planetElement = document.createElement("Planet");
            galaxyElement.appendChild(planetElement);
            Element name = document.createElement("name");
            name.appendChild(document.createTextNode(String.valueOf(planet.getName())));
            planetElement.appendChild(name);
            Element diameter = document.createElement("diameter");
            diameter.appendChild(document.createTextNode(String.valueOf(planet.getDiameter())));
            planetElement.appendChild(diameter);
            Element forHuman = document.createElement("forHuman");
            forHuman.appendChild(document.createTextNode(String.valueOf(planet.isForHuman())));
            planetElement.appendChild(forHuman);
            Element percentAreaForLive = document.createElement("percentAreaForLive");
            percentAreaForLive.appendChild(document.createTextNode(String.valueOf(planet.getPercentAreaForLive())));
            planetElement.appendChild(percentAreaForLive);
            Element percentWater = document.createElement("percentWater");
            percentWater.appendChild(document.createTextNode(String.valueOf(planet.getPercentWater())));
            planetElement.appendChild(percentWater);
        }


        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(fileNAme));

        transformer.transform(domSource, streamResult);
    }
}
