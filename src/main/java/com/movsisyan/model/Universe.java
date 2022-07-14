package com.movsisyan.model;

import com.movsisyan.generator.Generator;
import lombok.Data;
import org.w3c.dom.*;
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
import java.util.HashMap;
import java.util.Map;

@Data
public class Universe {
    private ArrayList<Galaxy> galaxies = new ArrayList<>();

    public void addGalaxy(Galaxy galaxy) {
        if (findGalaxy(galaxy) != -1) {
            throw new IllegalArgumentException("Galaxy exists " + galaxy);
        }
        this.galaxies.add(galaxy);
    }

    public int findPlanet(Planet planet) {
        for (Galaxy galaxy : this.galaxies) {
            for (int j = 0; j < galaxy.getPlanets().size(); j++) {
                if (galaxy.getPlanets().get(j).equals(planet)) {
                    return j;
                }
            }
        }
        return -1;
    }

    public Planet findPlanet(String name) {
        for (Galaxy galaxy : this.galaxies) {
            for (Planet planet : galaxy.getPlanets()) {
                if (planet.getName().equals(name)) {
                    return planet;
                }
            }
        }
        return null;
    }

    private int findGalaxy(Galaxy galaxy) {
        return this.galaxies.indexOf(galaxy);
    }

    public Galaxy findGalaxy(String name) {
        for (Galaxy galaxy : this.galaxies) {
            if (galaxy.getName().equalsIgnoreCase(name)) {
                return galaxy;
            }
        }
        return null;
    }

    public void behavior() {
        while (true) {
            try {
                ArrayList<Galaxy> galaxies = Generator.generateGalaxies(10);
                this.galaxies.addAll(galaxies);
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void fromXML(String fileName) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(fileName));
        Element element = (Element) document.getElementsByTagName("universe").item(0);

        NodeList galaxy = element.getElementsByTagName("galaxy");
        for (int i = 0; i < galaxy.getLength(); i++) {

        }
    }

    public static void toXML(String fileNAme, String newFileName) throws ParserConfigurationException,
            TransformerException, IOException, SAXException {
        HashMap<String, ArrayList<Element>> elements = new HashMap<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(fileNAme);

        NodeList planets = document.getElementsByTagName("Planet");
        for (int i = 0; i < planets.getLength(); i++) {
            Element element = (Element) planets.item(i);
            String name = element.getElementsByTagName("Name").item(0).getTextContent();
            ArrayList<Element> list = elements.getOrDefault(name, new ArrayList<>());
            list.add(element);
            elements.put(name, list);
        }

        Document documentRes = builder.newDocument();
        Element elementUniverse = documentRes.createElement("Universe");
        documentRes.appendChild(elementUniverse);
        int f = 1;
        for (var entry : elements.entrySet()) {
            Element elementEqlGroup = documentRes.createElement("EqualGroup");
            elementUniverse.appendChild(elementEqlGroup);
            Attr numberAttr = documentRes.createAttribute("number");
            numberAttr.setValue(String.valueOf(f));
            elementEqlGroup.setAttributeNode(numberAttr);
            f++;
            Attr nameAttr = documentRes.createAttribute("name");
            nameAttr.setValue(entry.getKey());
            elementEqlGroup.setAttributeNode(nameAttr);

            for (Element element : entry.getValue()) {
                element = (Element) documentRes.importNode(element, true);
                elementEqlGroup.appendChild(element);
            }
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(documentRes);
        File f1 = new File(newFileName);
        StreamResult streamResult = new StreamResult(f1);

        transformer.transform(domSource, streamResult);
    }
}
