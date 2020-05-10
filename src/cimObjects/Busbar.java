package cimObjects;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Busbar extends General {

	String eequip;
	public Busbar(Node node) {
		
		super(node);
		Element en = (Element) node;
		this.eequip = getAttRes(en, "cim:Equipment.EquipmentContainer");

		/**
		- rdf:ID
		- name
		- EquipmentContainer
		**/

	}
}
