package Tests.DomTests;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Created by rbtlong on 5/16/15.
 */
public class DomTest {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuild = fact.newDocumentBuilder();

        String exampleString =
                "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"" +
                        " \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">" +
                        "<html xmlns=\"http://www.w3.org/1999/xhtml\">" +
                        "<head><title>asdfadsfasdfasdf</title>" +
                        "<meta>jhjhjhjh</meta>" +
                        "<meta name=\"description\" content=\"Free Web tutorials\">\n" +
                        "<meta name=\"keywords\" content=\"HTML,CSS,XML,JavaScript\">\n" +
                        "<meta name=\"author\" content=\"Hege Refsnes\">" +
                        "</head>" +
                        "<body>" +
                        "body" +
                        "<ul><li/><li/><li/><li/><li/><li/></ul>" +
                        "<table>" +
                        "<tbody>" +
                        "<td><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr></td>" +
                        "<td><tr></tr></td>" +
                        "</tbody>" +
                        "</table>" +
                        "</body>" +
                        "</html>";
        InputStream stream = new ByteArrayInputStream(
                exampleString.getBytes(StandardCharsets.UTF_8));
        Document doc = docBuild.parse(stream);

        Node head = doc.getElementsByTagName("head").item(0);

        //NodeList metas= ((org.w3c.dom.Element)head).getElementsByTagName("meta");
/*        NodeList metas= head.getChildNodes();
        for(int i=0; i<metas.getLength(); ++i){
            System.out.println(metas.item(i).getNodeName());
        }*/
        //traverseNodes(doc);
    }

/*    static void traverseNodes(Node node) {
        NodeList nodes = node.getChildNodes();
        System.out.println(node.getNodeName());
        for (int i = 0; i < node.getChildNodes().getLength(); ++i) {
            traverseNodes(nodes.item(i));
        }

    }*/
}
