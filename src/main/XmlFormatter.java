package main;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import java.io.File;
import java.io.StringWriter;

public class XmlFormatter {
    public static void prettyPrintToFile(Document doc, File outputFile) throws Exception {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();

        // Imposta le proprietà per la formattazione
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        // Applica la trasformazione direttamente in un file
        transformer.transform(new DOMSource(doc), new StreamResult(outputFile));
    }

    public static String prettyPrint(Document doc) throws Exception {
        // Crea una TransformerFactory
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();

        // Imposta le proprietà per la formattazione
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        // L'indent-amount è un'estensione supportata da molte implementazioni standard
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        // Applica la trasformazione su uno StringWriter
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));

        return writer.toString();
    }
}

