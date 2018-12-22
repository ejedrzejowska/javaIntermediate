package pl.sda.intermediate.products;

import lombok.Getter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDAO {
    public static final String PRODUCT_XML = "c:/projects/books.xml";
    private static ProductDAO instance;
    File xmlFile = new File(PRODUCT_XML);

    @Getter
    private List<Product> productList;
    @Getter
    private Map<String, Product> productMap;

    private ProductDAO() {
        productList = getProducts();
    }

    private List<Product> getProducts() {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;

        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("book");
            List<Product> prodList = new ArrayList<>();
            Map<String, Product> prodMap = new HashMap<>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Product p = parseProducts(nodeList.item(i));
                prodList.add(p);
                if (!prodMap.containsKey(p.getId())) {
                    prodMap.put(p.getId(), p);
                }
            }
            return prodList;
        } catch (SAXException | ParserConfigurationException | IOException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    private static Product parseProducts(Node node) {
        Product prod = new Product();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            prod.setId(element.getAttribute("id"));
            prod.setAuthor(getTagValue("author", element));
            prod.setTitle(getTagValue("title", element));
            prod.setGenre(getTagValue("genre", element));
            prod.setPrice(Double.parseDouble(getTagValue("price", element)));
            prod.setPublishDate(getTagValue("publish_date", element));
            prod.setDescription(getTagValue("description", element));
        }
        return prod;
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }

    public static ProductDAO getInstance() {
        if (instance == null) {
            synchronized (ProductDAO.class) {
                if (instance == null) {
                    instance = new ProductDAO();
                }
            }
        }
        return instance;
    }
}
