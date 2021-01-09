package saveSystem;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class Save {
	public static void readSave() {
		try {
			// creating a constructor of file class and parsing an XML file
			File file = new File("files/Saves/XMLFile.xml");
			// an instance of factory that gives a document builder
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			// an instance of builder to parse the specified xml file
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
			NodeList nodeList = doc.getElementsByTagName("game");
			// nodeList is not iterable, so we are using for loop
			for (int itr = 0; itr < nodeList.getLength(); itr++) {
				Node node = nodeList.item(itr);
				System.out.println("\nNode Name :" + node.getNodeName());
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) node;
					System.out.println(
							"Student id: " + eElement.getElementsByTagName("playerName").item(0).getTextContent());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void writeSave() {
		/* need to save the states of all npcs
		 * memory can probably come into play after this is set up
		 * as of now the memory stuff is not important
		 */
		
		/* need to save the info attached to the player
		 * this includes the inventory, money, name, etc.
		 */
		
		/* need to save map states without relying on the base text version of each
		 * at this point the map has become an object rather than a sheet of paper
		 * so it needs to be treated and saved as such
		 */
		
	}
}