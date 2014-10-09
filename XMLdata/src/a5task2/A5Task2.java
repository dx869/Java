/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a5task2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author rebecca
 */
public class A5Task2 {

    Document orig;      // Input XML as DOM tree
    Document mod;       // DOM tree constructed by applications
    DocumentBuilderFactory factory = null;
    DocumentBuilder parser = null;
    static HashMap<String, Element> map;

    private void makeSelectedDOM() {
        try {
            factory = DocumentBuilderFactory.newInstance();
            parser = factory.newDocumentBuilder();
            mod = parser.newDocument();
            // Create root for new tree
            Element root = mod.createElement("world_data");

            //get all the countries.
            DocumentBuilderFactory factory2 = DocumentBuilderFactory.newInstance();
            DocumentBuilder parser2 = factory2.newDocumentBuilder();
            orig = parser2.parse("mondial-3.0.xml");

            NodeList countries = orig.getElementsByTagName("country");
            //for each country
            for (int i = 0; i < countries.getLength(); i++) {
                Element country = (Element) countries.item(i);
                Element aCountry = mod.createElement("country");
                String id = country.getAttribute("id");

                //add country name to the node
                Element countryname = mod.createElement("countryname");
                countryname.appendChild(mod.createTextNode(country.getAttribute("name")));

                //add population to the node;
                Element population = mod.createElement("population");
                population.appendChild(mod.createTextNode(country.getAttribute("population")));

                //add area to node;
                Element area = mod.createElement("area");
                area.appendChild(mod.createTextNode(country.getAttribute("total_area")));

                //add province to node;
                Element provinces = mod.createElement("provinces");
                NodeList provincesnode = country.getElementsByTagName("provinces");
                for (int j = 0; j < provincesnode.getLength(); j++) {
                    Element provincess = (Element) provincesnode.item(i);
                    Element provincename = mod.createElement("provincename");
                    provincename.appendChild(mod.createTextNode(provincess.getAttribute("name")));
                    provinces.appendChild(provincename);
                }

                //add city to node;
                Element cities = mod.createElement("cities");
                NodeList cityList = country.getElementsByTagName("city");
                for (int num = 0; num < cityList.getLength(); num++) {
                    Element city = (Element) cityList.item(num);
                    Element cityname = mod.createElement("cityname");

                    NodeList cityli = city.getElementsByTagName("name");
                    String aCity =cityli.item(0).getFirstChild().getNodeValue().trim();
   
                    cityname.appendChild(mod.createTextNode(aCity));
                    cities.appendChild(cityname);
                }
                //add river
                Element riverss = mod.createElement("rivers");
                Element mountains = mod.createElement("mountains");

                //add those to aCountry node;
                aCountry.appendChild(countryname);
                aCountry.appendChild(population);
                aCountry.appendChild(area);
                aCountry.appendChild(provinces);
                aCountry.appendChild(cities);
                aCountry.appendChild(riverss);
                aCountry.appendChild(mountains);
                map.put(id, aCountry);

            }

            //find the rivers
            NodeList rivers = orig.getElementsByTagName("river");
            for (int riversNum = 0; riversNum < rivers.getLength(); riversNum++) {
                Element river = (Element) rivers.item(riversNum);
                String riverName = river.getAttribute("name");

                NodeList location = river.getElementsByTagName("located");
                if (location.getLength() != 0) {
                    Element l = (Element) location.item(0);
                    String countryId = l.getAttribute("country");

                    Element e = (Element) map.get(countryId).getElementsByTagName("rivers").item(0);
                    Element temp = mod.createElement("rivers");

                    temp.appendChild(mod.createTextNode(riverName));
                    e.appendChild(temp);
                }
            }
            //find the mountains
            NodeList mountains = orig.getElementsByTagName("mountain");
            for (int mountNum = 0; mountNum < mountains.getLength(); mountNum++) {
                Element mountain = (Element) mountains.item(mountNum);
                String mountainName = mountain.getAttribute("name");

                NodeList mLocation = mountain.getElementsByTagName("located");
                if (mLocation.getLength() != 0) {
                    Element m = (Element) mLocation.item(0);
                    String countryId = m.getAttribute("country");

                    Element e = (Element) map.get(countryId).getElementsByTagName("rivers").item(0);
                    Element temp = mod.createElement("mountain");

                    temp.appendChild(mod.createTextNode(mountainName));
                    e.appendChild(temp);

                }
            }

            Iterator iter = map.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Element val = (Element) entry.getValue();
                root.appendChild(val);
            }
            mod.appendChild(root);

            try {
                TransformerFactory tFactory = TransformerFactory.newInstance();
                Transformer transformer = tFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                DOMSource source = new DOMSource(mod);
                StreamResult result = new StreamResult(System.out);
                transformer.transform(source, result);
            } catch (Exception e) {
                System.out.println("Exception when trying to generate output");
                System.out.println(e.toString());
            }
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(A5Task2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(A5Task2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(A5Task2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void outputSelectedDOM() {
        try {
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(mod);
            StreamResult result = new StreamResult(System.out);
            transformer.transform(source, result);
        } catch (Exception e) {
            System.out.println("Exception when trying to generate output");
            System.out.println(e.toString());
        }
    }

    public static void main(String[] args) {
        map = new HashMap<>();
        A5Task2 dom = new A5Task2();
        dom.makeSelectedDOM();
        //  dom.outputSelectedDOM();
    }

}
