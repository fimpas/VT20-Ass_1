package cimObjects;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class PowerTrans extends General {
	
	public String[] wanted = {"cim:Equipment.EquipmentContainer"}; 
	public String equipC;

	public PowerTrans(Node node) {
		
		super(node);
		this.node = node;
		Element en = (Element) node;
		this.equipC = getAttRes(en, wanted[0]);

		/**
		- rdf:ID
		- name
		- equipmentContainer_rdf:ID
		**/
		
	}
}
