package cimObjects;

import org.w3c.dom.Node;


public class Substation extends General {

	public String region;
	String[] wanted = {"cim:Substation.Region"};
	
	public Substation(Node node) {
		super(node);
		this.node = node;
		this.region = getAttRes(node,wanted[0]);

		
		}	
	public String region() {
		return region;
	}
	
	public String [] getInfo() {
		String[] info = {"Substation","rdfID", "name", "region_rdfID"};
		return info;
	}
	public String [] sub() {
		String[] info = {rdfID, name, region};
		return info;
	}
}

