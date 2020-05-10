package main;

import java.util.ArrayList;

import cimObjects.PowerTransformerEnd;
import cimObjects.Terminal;

/**
 * This class will get the wanted values from each PowerTransFormer, pair them with the proper PowerTransformerEnd 
 * and decide which connectivity node and terminal these belong to.
 * This will be later be used to assign the values to the proper ConnectivityNodeGroup.
 */

public class PowerTransGroup {

	public String powerObj;
	public String cN;
	public String baseV;
	public Terminal terminalN;
	public int end;
	public int q;
	public ArrayList<PowerTransformerEnd> powerEndList;
	public ArrayList<Terminal> terminal;
	public ArrayList<Double> params = new ArrayList<Double>();

	public PowerTransGroup(String powerObj, ArrayList<PowerTransformerEnd> powerEndList, ArrayList<Terminal> terminal, int q) {

		this.powerObj = powerObj;
		this.powerEndList = powerEndList;
		this.terminal = terminal;
		this.q = q;

		for (int i = 0; i < powerEndList.size(); i++ ) {
			if (powerEndList.get(i).trans.contains(powerObj)) {
				for(int j = 0; j < terminal.size() ;j++) {
					if(terminal.get(j).rdfID.equals(powerEndList.get(i).terminal)) {
						if (powerEndList.get(i).endNumber == q) {
							terminalN = terminal.get(j);
							cN = terminal.get(j).connectNode;
							baseV = powerEndList.get(i).baseV;
							params.add(powerEndList.get(i).r);
							params.add(powerEndList.get(i).x);
							params.add(powerEndList.get(i).b);
							params.add(powerEndList.get(i).g);
							end = powerEndList.get(i).end;
						}
					}
				}
			}
		}
	}
	public Terminal getTerminal() {
		return terminalN;
	}
	public String getCN() {
		return cN;
	} 
	public String getBaseV() {
		return baseV;
	}
	public int end() {
		return end;
	}
	public ArrayList<Double> getParams() {
		return params;
	}	
	public double getR() {
		return params.get(0);
	}
	public double getX() {
		return params.get(1);
	}
	public double getB() {
		return params.get(2);
	}
	public double getG() {
		return params.get(3);
	}
}
