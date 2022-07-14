package com.movsisyan.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
import java.util.Objects;

public class Planet {
    private String name;
    private int diameter;
    private boolean forHuman;
    private int percentAreaForLive;
    private int percentWater;

    public Planet() {
    }

    public Planet(String name, int diameter, boolean forHuman, int percentAreaForLive, int percentWater) {
        this.name = name;
        this.diameter = diameter;
        this.forHuman = forHuman;
        this.percentAreaForLive = percentAreaForLive;
        this.percentWater = percentWater;
    }

    public String behavior() {
        return this.name + " " + this.diameter + " " + this.forHuman + " " + this.percentAreaForLive + " " + this.percentWater;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDiameter() {
        return diameter;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }

    public boolean isForHuman() {
        return forHuman;
    }

    public void setForHuman(boolean forHuman) {
        this.forHuman = forHuman;
    }

    public int getPercentAreaForLive() {
        return percentAreaForLive;
    }

    public void setPercentAreaForLive(int percentAreaForLive) {
        this.percentAreaForLive = percentAreaForLive;
    }

    public int getPercentWater() {
        return percentWater;
    }

    public void setPercentWater(int percentWater) {
        this.percentWater = percentWater;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Planet planet = (Planet) o;
        return diameter == planet.diameter && forHuman == planet.forHuman && percentAreaForLive == planet.percentAreaForLive && percentWater == planet.percentWater && Objects.equals(name, planet.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, diameter, forHuman, percentAreaForLive, percentWater);
    }

    @Override
    public String toString() {
        return "Planet{" +
                "name='" + name + '\'' +
                ", diameter=" + diameter +
                ", forHuman=" + forHuman +
                ", percentAreaForLive=" + percentAreaForLive +
                ", percentWater=" + percentWater +
                '}';
    }

    public static Planet fromXML(String filename) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        Document document = documentBuilder.parse(new File(filename));

        Element element = (Element) document.getElementsByTagName("Planet").item(0);
        String name = element.getElementsByTagName("name").item(0).getTextContent();
        int diameter = Integer.parseInt(element.getElementsByTagName("diameter").item(0).getTextContent());
        boolean forHuman = Boolean.parseBoolean(element.getElementsByTagName("forHuman").item(0).getTextContent());
        int percentAreaForLive = Integer.parseInt(element.getElementsByTagName("percentAreaForLive").item(0).getTextContent());
        int percentWater = Integer.parseInt(element.getElementsByTagName("percentWater").item(0).getTextContent());
        return  new Planet(name, diameter, forHuman, percentAreaForLive, percentWater);
    }

    public void toXML(String fileNAme) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();
        Element planet = document.createElement("Planet");
        document.appendChild(planet);

        Element name = document.createElement("name");
        name.appendChild(document.createTextNode(String.valueOf(this.name)));
        planet.appendChild(name);
        Element diameter = document.createElement("diameter");
        diameter.appendChild(document.createTextNode(String.valueOf(this.diameter)));
        planet.appendChild(diameter);
        Element forHuman = document.createElement("forHuman");
        forHuman.appendChild(document.createTextNode(String.valueOf(this.forHuman)));
        planet.appendChild(forHuman);
        Element percentAreaForLive = document.createElement("percentAreaForLive");
        percentAreaForLive.appendChild(document.createTextNode(String.valueOf(this.percentAreaForLive)));
        planet.appendChild(percentAreaForLive);
        Element percentWater = document.createElement("percentWater");
        percentWater.appendChild(document.createTextNode(String.valueOf(this.percentWater)));
        planet.appendChild(percentWater);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(fileNAme));

        transformer.transform(domSource, streamResult);
    }
}