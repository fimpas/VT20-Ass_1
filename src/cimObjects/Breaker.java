package cimObjects;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Breaker extends General{
	
	String[] wanted = {"cim:Switch.open", "cim:Equipment.EquipmentContainer"};

	public Boolean state;
	public String eequipC;
	public String ratedU;
	Node nodeSSH;

	public Breaker(Node node, Node nodeSSH) {
		super(node);
		this.node = node;
		this.nodeSSH = nodeSSH;
		Element en = (Element) node;
		Element ssh = (Element) nodeSSH;
		this.state = Boolean.parseBoolean(getEle(ssh,wanted[0]));
		this.eequipC = getAttRes(en,wanted[1]);


	
	
		/**
		- rdf:ID
		- name
		- state
		- equipmentContainer_rdf:ID
		- baseVoltage_ rdf:ID
		**/
		
	}

}
