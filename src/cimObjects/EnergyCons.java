package cimObjects;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class EnergyCons extends General{

	String eC = "cim:EnergyConsumer.";
	String[] wanted = {eC+"p", eC+"q", "cim:Equipment.EquipmentContainer"};
	public double p;
	public double q;
	public String eequipC;
	public String ratedU;
	Node nodeSSH;
	
	public EnergyCons(Node node, Node nodeSSH) {
		
		super(node);
		this.node = node;
		this.nodeSSH = nodeSSH;
		Element en = (Element) node;
		Element ssh = (Element) nodeSSH;
		this.p = Double.parseDouble(getEle(ssh,wanted[0]));
		this.q = Double.parseDouble(getEle(ssh,wanted[1]));
		this.eequipC = getAttRes(en,wanted[2]);
		
		

		/**
		- rdf:ID 
		- name 
		-P 
		-Q
		- equipmentContainer_rdf:ID 
		- baseVoltage_ rdf:ID
		 **/
	}
}
