package cimObjects;

import org.w3c.dom.Node;

public class ConnNode extends General{
	
	String cN = "cim:ConnectivityNode.";
	String[] wanted = {cN+"ConnectivityNodeContainer"};

	String connectNodeCont;
	public ConnNode(Node node) {
		
		super(node);
		this.connectNodeCont = getAttRes(node, wanted[0]);
 
		/**
		- rdf:ID
		- name
		- ConnectivityNodeContainer rdf:resource:
		**/
		
	}
}
