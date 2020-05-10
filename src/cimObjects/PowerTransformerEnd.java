package cimObjects;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class PowerTransformerEnd extends General{

	String pTE = "cim:PowerTransformerEnd.";
	String[] wanted = {pTE+"r", pTE+"x",pTE+"b", pTE+"g", pTE+"PowerTransformer", "cim:TransformerEnd.BaseVoltage", "cim:TransformerEnd.endNumber", "cim:TransformerEnd.Terminal"};
	public double r;
	public double x;
	public double b;
	public double g;
	public String trans;
	public String baseV;
	public double endNumber;
	public String terminal;
	public int end;
	
	public PowerTransformerEnd(Node node) {

		super(node);
		this.node = node;
		Element en = (Element) node;
		this.r = Double.parseDouble(getEle(en,wanted[0]));
		this.x = Double.parseDouble(getEle(en,wanted[1]));
		this.b = Double.parseDouble(getEle(en,wanted[2]));
		this.g = Double.parseDouble(getEle(en,wanted[3]));
		this.trans = getAttRes(en,wanted[4]);
		this.baseV = getAttRes(en,wanted[5]);
		this.endNumber = Double.parseDouble(getEle(en,wanted[6]));
		this.terminal = getAttRes(en,wanted[7]);
		this.end = Integer.parseInt(getEle(en,wanted[6]));
		
		/**
		- rdf:ID
		- name
		- Transformer.r
		- Transformer.x
		- Transformer.b
		- Transformer.g
		- Transformer_rdf:ID
		- baseVoltage_ rdf:ID
		- Terminal
		- endNumber
		**/
		
	}

}
