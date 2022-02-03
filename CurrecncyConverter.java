import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.net.URL;

public class CurrecncyConverter {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException,
            XPathExpressionException {
        String url = "http://www.cbr.ru/scripts/XML_daily.asp";
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new URL(url).openStream());

        System.out.println("1 норвежская крона = "
                + parseCurrencyXml(doc, "R01535") / parseCurrencyXml(doc, "R01135") + " венгерских форитов");
    }

    private static double parseCurrencyXml(Document document, String id) throws DOMException, XPathExpressionException {
        XPathFactory pathFactory = XPathFactory.newInstance();
        XPath xpath = pathFactory.newXPath();
        XPathExpression expr = xpath.compile("//Valute[@ID='" + id + "']");
        Node node = (Node) expr.evaluate(document, XPathConstants.NODE);
        double nominal = Double.parseDouble(node.getChildNodes().item(2).getTextContent());
        double valuteRubles = Double.parseDouble(node.getChildNodes().item(4).getTextContent()
                .replace(',','.'));
        return valuteRubles / nominal;

    }


}
