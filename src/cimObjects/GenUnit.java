package cimObjects;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class GenUnit extends General {


	String g = "cim:GeneratingUnit.";
	String[] wanted = {g+"maxOperatingP", g+"minOperatingP", "cim:Equipment.EquipmentContainer"};

	public double maxP;
	public double minP;
	public String equipC;
	public String[] titles;

	public GenUnit(Node node) {

		super(node);
		this.node = node;
		Element en = (Element) node;
		this.maxP = Double.parseDouble(getEle(en,wanted[0]));
		this.minP = Double.parseDouble(getEle(en,wanted[1]));
		this.equipC = getAttRes(en,wanted[2]);


	}
}
