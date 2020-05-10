package main;
import cimObjects.*;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory; 
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document; 
import org.w3c.dom.NodeList;
import org.w3c.dom.Node; 
import org.w3c.dom.Element;

/**
 * This class will go through all Elements to find the required data for each Node.
 * All data that has been found, will be added to an Arraylist for each class.
 * Each class represents an Node type of the wanted CIM-objects.
 * The Arraylists are returned the class Start and will be further processed.
 */

public class ReadXML {

	ArrayList<BaseVoltage> baseVoltList = new ArrayList<BaseVoltage>();
	ArrayList<Substation> subList= new ArrayList<Substation>();
	ArrayList<VoltageLevel> voltList = new ArrayList<VoltageLevel>();
	ArrayList<GenUnit> genUnitList = new ArrayList<GenUnit>();
	ArrayList<SyncMachine> syncList = new ArrayList<SyncMachine>();
	ArrayList<RegControl> regList = new ArrayList<RegControl>();
	ArrayList<PowerTrans> powerList = new ArrayList<PowerTrans>();
	ArrayList<EnergyCons> energyList = new ArrayList<EnergyCons>();
	ArrayList<PowerTransformerEnd> powerEndList = new ArrayList<PowerTransformerEnd>();
	ArrayList<Breaker> breakerList = new ArrayList<Breaker>();
	ArrayList<RatioTapChanger> ratioList = new ArrayList<RatioTapChanger>();
	ArrayList<ACLines> acList = new ArrayList<ACLines>();
	ArrayList<Busbar> busList = new ArrayList<Busbar>();
	ArrayList<ConnNode> connList = new ArrayList<ConnNode>();
	ArrayList<Terminal> termList = new ArrayList<Terminal>();

	static String[] objects = {"BaseVoltage", "Substation","VoltageLevel","GeneratingUnit","SynchronousMachine","RegulatingControl"
			,"PowerTransformer","EnergyConsumer","PowerTransformerEnd","Breaker","RatioTapChanger", "ACLineSegment", "BusbarSection", "ConnectivityNode","Terminal"};
	static String [] nodeNames = new String[objects.length];
	static String[] nodeListNames = new String[objects.length];

	public ReadXML(File eqXML, File sshXML) {

		for (int i =0; i< objects.length; i++) {
			nodeNames[i] = "cim:" + objects[i];
			nodeListNames[i] = "list" + objects[i]; 
		}

		extractNodes(eqXML, sshXML, nodeNames);
	}
	public void extractNodes(File eqXML, File sshXML, String[] nodeNames) {

		try {

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance(); 
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document eqDoc = dBuilder.parse(eqXML);
			Document sshDoc = dBuilder.parse(sshXML);

			eqDoc.getDocumentElement().normalize();
			sshDoc.getDocumentElement().normalize();

			NodeList eqList = eqDoc.getElementsByTagName("*");
			NodeList sshList = sshDoc.getElementsByTagName("*");

			for(int i = 0; i < eqList.getLength(); i++) {
				if(eqList != null) {
					
					// If not null, it goes through the objects that it encountered
					for(int q = 0; q < nodeNames.length; q++) {
						
						// Go through the entire length of the Node and go through all Elements in that node it to gather the proper ones.
						for(int j = 0; j < nodEle(eqList.item(i)).getElementsByTagName(nodeNames[q]).getLength(); j++) { 

							Node nodeEQ = nodNod(eqList.item(i),nodeNames[q],j);
							Node nodeSSH = nodNod(sshList.item(i),nodeNames[q],j);

							if(nodeSSH != null) {

								for(int k = 0; k < nodEle(sshList.item(i)).getElementsByTagName(nodeNames[q]).getLength(); k++) {
									Node nodeTestSSH = nodNod(sshList.item(i),nodeNames[q],k);

									if( nodEle(nodeTestSSH).getAttribute("rdf:about").contains(nodEle(nodeEQ).getAttribute("rdf:ID"))) {
										
										addToList(nodeEQ, nodeTestSSH, nodeNames, q);
									}
									else {
									}
								}								
							}
							else {
								addToList(nodeEQ, nodeSSH, nodeNames, q);
							}
						} 
					}
				}
			}
		}
		catch(Exception e) { 
			e.printStackTrace();
		}
	}

	public static Element nodEle (Node node) {
		Element ele = (Element) node;
		return ele;
	}

	public static Element nodSubEle (Node node, String nodeName, int number) {
		Element ele = nodEle(nodEle(node).getElementsByTagName(nodeName).item(number));
		return ele;
	}

	public static String nodEleAttr (Node node, String name) {
		Element ele = (Element) node;
		String att = ele.getAttribute(name);
		return att;
	}

	public static Node nodNod (Node node, String nodeName, int number) {

		Node nodeOut = (Node) nodEle(nodEle(node).getElementsByTagName(nodeName).item(number));
		return nodeOut;
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

	public void addToList(Node node, Node nodeSSH, String[] name, int nr) {
		AddList test = new AddList(name[nr], node, nodeSSH);

		switch (nr) {
		case 0:  baseVoltList.add(test.baseVolt());
		break;
		case 1:  subList.add(test.subs());
		break;
		case 2:  voltList.add(test.volt());
		break;
		case 3:  genUnitList.add(test.gen());
		break;
		case 4:  syncList.add(test.syncM());
		break;
		case 5:  regList.add(test.regC());
		break;
		case 6:  powerList.add(test.powerT());
		break;
		case 7:  energyList.add(test.energyCons());
		break;
		case 8:  powerEndList.add(test.powerTEnd());
		break;
		case 9:  breakerList.add(test.breaker());
		break;
		case 10: ratioList.add(test.ratio());
		break;
		case 11: acList.add(test.ac());
		break;
		case 12: busList.add(test.busbar());
		break;
		case 13: connList.add(test.conn());
		break;
		case 14: termList.add(test.terminal());
		break;
		default: System.out.println(" Unknown List ");
		break;
		}
	}
	public ArrayList<BaseVoltage> getBaseVoltList() {
		return baseVoltList;
	}
	public ArrayList<Substation> getSubList() {
		return subList;
	}
	public ArrayList<VoltageLevel> getVoltList() {
		return voltList;
	}
	public ArrayList<GenUnit> getGenUnitList() {
		return genUnitList;
	}
	public ArrayList<SyncMachine> getSyncList() {
		return syncList;
	}
	public ArrayList<RegControl> getRegList() {
		return regList;
	}
	public ArrayList<PowerTrans> getPowerList() {
		return powerList;
	}
	public ArrayList<EnergyCons> getenergyList() {
		return energyList;
	}
	public ArrayList<PowerTransformerEnd> getPowerEndList() {
		return powerEndList;
	}
	public ArrayList<Breaker> getBreakerList() {
		return breakerList;
	}
	public ArrayList<RatioTapChanger> getRatioList() {
		return ratioList;
	}
	public ArrayList<ACLines> getACLines() {
		return acList;
	}
	public ArrayList<Busbar> getBusbar() {
		return busList;
	}
	public ArrayList<ConnNode> getConnNode() {
		return connList;
	}
	public ArrayList<Terminal> getTerminal() {
		return termList;
	}
}




