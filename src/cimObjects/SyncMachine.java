package cimObjects;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class SyncMachine extends General {

	String rM = "cim:RotatingMachine.";
	String[] wanted = {rM+"ratedS",rM+"p", rM+"q", rM+"GeneratingUnit", "cim:RegulatingCondEq.RegulatingControl",
			"cim:Equipment.EquipmentContainer"};

	public double ratedS;
	public double p;
	public double q;
	public String genU;
	public String regC;
	public String eequipC;
	public String ratedU;
	public String[] titles;
	Node nodeSSH;

	public SyncMachine(Node node, Node nodeSSH) { 

		super(node);
		this.node = node;
		this.nodeSSH = nodeSSH;
		Element en = (Element) node;
		Element ssh = (Element) nodeSSH;
		this.ratedS = Double.parseDouble(getEle(en,wanted[0]));
		this.p = Double.parseDouble(getEle(ssh,wanted[1]));
		this.q = Double.parseDouble(getEle(ssh,wanted[2]));
		this.genU = getAttRes(en,wanted[3]);
		this.regC = getAttRes(en,wanted[4]);
		this.eequipC = getAttRes(en,wanted[5]); 

		/**
		- rdf:ID 
		- name 
		- ratedS 
		- P 
		- Q
		- genUnit_rdf:ID
		- regControl_rdf:ID
		- equipmentContainer_rdf:ID
		- baseVoltage_ rdf:ID
		**/

		
	}

}
