package cimObjects;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Terminal extends General{
	
	String t = "cim:Terminal.";
	String[] wanted = {t+"ConductingEquipment", t+"ConnectivityNode", "cim:ACDCTerminal.sequenceNumber", "cim:ACDCTerminal.connected"};
	 
	public String cequip; 
	public String connectNode;
	public double acdc;
	public Boolean acdcState;
	public String about;
	Node nodeSSH;
	public int end;


	public Terminal(Node node, Node nodeSSH) {

		super(node);
		this.nodeSSH = nodeSSH;
		Element en = (Element) node;
		Element ssh = (Element) nodeSSH;
		this.cequip = getAttRes(node, wanted[0]);
		this.connectNode = getAttRes(node, wanted[1]);
		this.acdc = Double.parseDouble(getEle(en,wanted[2]));
		this.acdcState = Boolean.parseBoolean(getEle(ssh,wanted[3]));
		this.about = getAttAbout(nodeSSH, "rdf:about");
		this.end = Integer.parseInt(getEle(en,wanted[2]));
		/**
		- rdf:ID
		- name
		- ConductingEquipment_rdf:ID
		- ConnectivityNode rdf:ID
		- ACDCTerminal.sequenceNumber
		**/
		

	}

}
