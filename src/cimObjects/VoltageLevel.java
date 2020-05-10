package cimObjects;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class VoltageLevel extends General {

	String v = "cim:VoltageLevel.";
	public String substation;
	public String baseV;
	String[] wanted = {v+"Substation", v+"BaseVoltage"};
	public String[] titles;

	public VoltageLevel(Node node) {

		super(node);
		this.node = node;
		Element en = (Element) node;
		this.substation = getAttRes(en,wanted[0]);
		this.baseV = getAttRes(en,wanted[1]);

	}

	public String substation() {
		return substation;
	}

	public String baseVoltage() {
		return baseV;
	}
}
