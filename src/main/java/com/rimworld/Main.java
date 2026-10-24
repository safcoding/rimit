package com.rimworld;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;




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
            System.out.println(getMod(readXML(fullPath)));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    // Pass a path to the XML file, then reads the mod name at the top
    public static Document readXML(Path modPath) throws Exception{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            Document doc = dBuilder.parse(modPath.toFile());
            doc.getDocumentElement().normalize();
            
            return doc;
    }

    public static Mod getMod (Document doc){
        Node modName = doc.getElementsByTagName("name").item(0);
        String name = modName.getTextContent();

        Node modPackageIdNode = doc.getElementsByTagName("packageId").item(0);
        String modPacakgeId = modPackageIdNode.getTextContent();

        List<String> dependencies = new ArrayList<>();
        NodeList modDependencyList = doc.getElementsByTagName("modDependencies");
        if (modDependencyList.getLength() > 0){
            Element mod = (Element) modDependencyList.item(0);
            NodeList liList = mod.getElementsByTagName("li");

            for(int i=0; i < liList.getLength(); i++){
                Element liElement = (Element) liList.item(i);
                NodeList dependencyIdNodeList = liElement.getElementsByTagName("packageId");
                String packageId = dependencyIdNodeList.item(0).getTextContent().trim();
                dependencies.add(packageId);
            }
        } else {
            System.out.println("No dependencies detected");
        }
        return new Mod(name, modPacakgeId, dependencies);
    }
}


/*
1. store the fetched thingies into an object
    a. name
    b. description (optional)
    c. dependencies to load before

2. put the objects in an array? is an array suitable?
3. sort?
    a. i have no idea how they sort the mods rn
*/ 
