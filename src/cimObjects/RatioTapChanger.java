package cimObjects;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class RatioTapChanger extends General{
	
	String wanted[] = {"cim:TapChanger.step", "cim:RatioTapChanger.TransformerEnd"};
	public double step;
	public String transEnd;
	Node nodeSSH;
	
	public RatioTapChanger(Node node, Node nodeSSH) {

		super(node);
		this.node = node;
		this.nodeSSH = nodeSSH;
		Element en = (Element) node;
		Element ssh = (Element) nodeSSH;
		this.step = Double.parseDouble(getEle(ssh,wanted[0]));
		this.transEnd = getAttRes(en,wanted[1]);

	/**
	  - rdf:ID 
	  - name 
	  - step
	 **/
		
	
	}

}
