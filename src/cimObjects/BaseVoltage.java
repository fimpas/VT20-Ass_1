package cimObjects;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class BaseVoltage extends General {

	String[] wanted = {"cim:BaseVoltage.nominalVoltage"};
	public double nominalValue;
	public String[] titles;
	public String[] outS = {rdfID()};

	public BaseVoltage(Node node) {
		super(node);
		this.node = node;
		Element en = (Element) node;
		this.nominalValue = Double.parseDouble(getEle(en,wanted[0]));


		this.titles = title();

	}

	public double nomVal() {

		return nominalValue;
		
	}
	public String[] title() {
		String[] titles = {"BaseVoltage", "rdfID", "nominalValue"};

		return titles;
	}

}

