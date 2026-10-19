package com.rimworld;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import javax.swing.text.AbstractDocument.ElementEdit;
import javax.xml.parsers.DocumentBuilder;


public class Main {
    public static void main(String[] args) {
        Path steamMods = Path.of("C:/Program Files (x86)/Steam/steamapps/workshop/content/294100");
        Path rimMod = Path.of("2194472657","About", "About.xml");
        Path fullPath = steamMods.resolve(rimMod);

        if (Files.exists(fullPath)){
            System.out.println("Located About.xml at "+ fullPath);
        } else {
            System.out.println("Unable to find file at " + fullPath);
            System.exit(0);
        }

        try {
            readXML(fullPath);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    // Pass a path to the XML file, then reads the mod name at the top
    public static void readXML(Path modPath) throws Exception{
        try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            Document doc = dBuilder.parse(modPath.toFile());
            doc.getDocumentElement().normalize();

            // Get the name of the mod
            NodeList nameList = doc.getElementsByTagName("name");
            Node nNode = nameList.item(0);
            System.out.println(nNode.getTextContent());

            NodeList modDependencies = doc.getElementsByTagName("modDependencies");
            System.out.println("-----------DEPENDENCIES-----------");

            if (modDependencies.getLength() > 0){
                Element modContainer = (Element) modDependencies.item(0);
                NodeList liList = modContainer.getElementsByTagName("li");

                for(int i=0; i < liList.getLength(); i++){
                    Element liElement = (Element) liList.item(i);
                    NodeList packageId = liElement.getElementsByTagName("packageId");

                    if (packageId.getLength() > 0){
                        String modDependency = packageId.item(0).getTextContent().trim();
                        System.out.println(i+1 + ". " + modDependency);
                    }
                }

            } else {
                System.out.println("No dependencies detected");
                System.exit(0);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}