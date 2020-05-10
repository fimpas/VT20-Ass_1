package cimObjects;

import org.w3c.dom.Node; 
import org.w3c.dom.Element;

/**
 * This class is what all other CIM-objects classes are based on.
 * It includes the most commonly used methods in CIM-objects classes 
 * to return the corresponding values required for each object.
 * It converts Nodes -> Element
 * Element Attributes, such as rdf:IDs, TagNames, contexts, ...
 * 
 */

public class General {

	public String rdfID;
	public String rdfAbout;
	public String object;
	public String name;

	Node node;
	String str;


	public General(Node node) {
		this.node = node;
		Element en = (Element) node;
		this.rdfID = getAtt(node, "rdf:ID");
		this.rdfAbout = getAtt(node,"rdf:about");
		this.name = getEle(en, "cim:IdentifiedObject.name");


	}
	public String name() {
		return name;
	}
	public String rdfID() {
		return rdfID;
	}

	public String rdfAbout() {
		return rdfAbout;
	}

	public String getEle(Element el,String str) {
		String eleS = el.getElementsByTagName(str).item(0).getTextContent();
		return eleS;
	}
	public static String getAtt(Node node, String str) {
		Element ele = (Element) node;
		String att = ele.getAttribute(str);
		return att;
	}
	public static String getSubAtt(Node node, String str, String att) {
		Element ele = (Element) node;
		Element eleSub = (Element) ele.getElementsByTagName(str).item(0);
		String attS = eleSub.getAttribute(att);
		return attS;

	}
	public static String getAttRes(Node node, String str) {
		Element ele = (Element) node;
		String att = "rdf:resource";
		Element eleSub = (Element) ele.getElementsByTagName(str).item(0);
		String attRes = eleSub.getAttribute(att).substring(1);
		return attRes;
	}
	public static String getAttAbout(Node node, String str) {
		Element ele = (Element) node;
		String att = ele.getAttribute(str).substring(1);
		return att;
	}

}

