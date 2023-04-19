package autosarfilereorderer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class AutosarFileReorderer {
    public static void main(String[] args) {
        String inputFileName = args[0];
        String outputFileName = inputFileName.replace(".arxml", "_mod.arxml");

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(inputFileName));

            List<Element> containerElements = getContainerElements(document.getDocumentElement());

            Collections.sort(containerElements, (c1, c2) -> c1.getElementsByTagName("SHORT-NAME").item(0).getTextContent()
                    .compareTo(c2.getElementsByTagName("SHORT-NAME").item(0).getTextContent()));

            Document reorderedDocument = createReorderedDocument(containerElements);

            writeDocumentToFile(reorderedDocument, outputFileName);

            System.out.println("Reordering successful. Output written to " + outputFileName);
        } catch (IOException | SAXException | ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private static List<Element> getContainerElements(Element root) {
        List<Element> containerElements = new ArrayList<>();
        NodeList nodes = root.getElementsByTagName("CONTAINER");

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                containerElements.add((Element) node);
            }
        }

        return containerElements;
    }

    private static Document createReorderedDocument(List<Element> containerElements) throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();

        Element autosarElement = document.createElement("AUTOSAR");
        document.appendChild(autosarElement);

        for (Element containerElement : containerElements) {
            Node importedNode = document.importNode(containerElement, true);
            autosarElement.appendChild(importedNode);
        }

        return document;
    }

    private static void writeDocumentToFile(Document document, String fileName)
            throws TransformerConfigurationException, TransformerException, IOException {
        File outputFile = new File(fileName);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        DOMSource source = new DOMSource(document);
        FileWriter writer = new FileWriter(outputFile);
        StreamResult result = new StreamResult(writer);

        transformer.transform(source, result);

        writer.close();
    }
}