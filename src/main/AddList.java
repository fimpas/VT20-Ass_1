
package main;

import org.w3c.dom.Node;
import cimObjects.*;

/**
 * This class will help with organising which class that is needed to be called.
 * This class is called with the correct CIM-files, EQ or EQ and SSH file.
 * It will return the required values from each class to the class ReadXML.
 */

public class AddList {

	String nodeName;
	Node node;
	Node nodeSSH;
 
	public AddList(String nodeName, Node node, Node nodeSSH) {

		this.node = node;
		this.nodeSSH = nodeSSH;
	}
	public BaseVoltage baseVolt() {
		return new BaseVoltage(node);
	}
	public Substation subs() {
		return new Substation(node);
	}
	public VoltageLevel volt() {
		return new VoltageLevel(node);
	}
	public GenUnit gen() {
		return new GenUnit(node);
	}
	public SyncMachine syncM() {
		return new SyncMachine(node, nodeSSH);
	}
	public RegControl regC() {
		return new RegControl(node, nodeSSH);
	}
	public PowerTrans powerT() {
		return new PowerTrans(node);
	}
	public EnergyCons energyCons() {
		return new EnergyCons(node, nodeSSH);
	}
	public PowerTransformerEnd powerTEnd() {
		return new PowerTransformerEnd(node);
	}
	public Breaker breaker() {
		return new Breaker(node, nodeSSH);
	}
	public RatioTapChanger ratio() {
		return new RatioTapChanger(node, nodeSSH);
	}
	public ACLines ac() {
		return new ACLines(node);
	}
	public Busbar busbar() {
		return new Busbar(node);
	}
	public ConnNode conn() {
		return new ConnNode(node);
	}
	public Terminal terminal() {
		return new Terminal(node, nodeSSH);
	}
}