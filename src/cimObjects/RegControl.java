package cimObjects;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class RegControl extends General {
	
	public String[] wanted = {"cim:RegulatingControl.targetValue"};
	public double target;
	Node nodeSSH;


	public RegControl(Node node, Node nodeSSH) {
		
		super(node);
		this.node = node;
		this.nodeSSH = nodeSSH;
		Element ssh = (Element) nodeSSH;
		
		this.target = Double.parseDouble(getEle(ssh,wanted[0]));

		/**
		- rdf:ID
		- name
		- targetValue
		 **/
		
		
	}

}
