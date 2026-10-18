package com.rimworld;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;


public class Main {
    public static void main(String[] args) {
        Path steamMods = Path.of("C:/Program Files (x86)/Steam/steamapps/workshop/content/294100");
        Path rimMod = Path.of("3425601715","About", "About.xml");
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

            NodeList nameList = doc.getElementsByTagName("name");
            Node nNode = nameList.item(0);
            System.out.println(nNode.getTextContent());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}