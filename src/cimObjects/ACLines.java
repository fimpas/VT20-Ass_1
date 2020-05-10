package cimObjects;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class ACLines extends General{

	String ac = "cim:ACLineSegment.";
	String[] wanted = {ac+"r", ac+"x", ac+"bch", ac+"gch", "cim:ConductingEquipment.BaseVoltage"};

	public double r;
	public double x;
	public double b;
	public double g;
	public String baseV;

	public ACLines(Node node) {

		super(node);
		Element en = (Element) node;
		this.r = Double.parseDouble(getEle(en, wanted[0]));
		this.x = Double.parseDouble(getEle(en, wanted[1]));
		this.b = Double.parseDouble(getEle(en, wanted[2]));
		this.g = Double.parseDouble(getEle(en, wanted[3]));
		this.baseV = getAttRes(en, wanted[4]);
	
	}
}

