package main;

import java.util.ArrayList;

import cimObjects.ACLines;
import cimObjects.Terminal;

/**
 * This class will gather the impedance and admittance parameters from the AC-Lines.
 * It will return  which terminal and connectivity node to which they belong. 
 * Later this will be used to decide which ConnectivityNodeGroup these belong to.
 */

public class ACLineSegment {

	public Terminal terminal;
	public String cN;
	public String baseV;
	public ACLines acLines;
	public int end;

	public ArrayList<Double> params = new ArrayList<Double>();

	public ACLineSegment(Terminal terminal, ACLines acLines) {

		this.terminal = terminal;
		this.acLines = acLines;
		
		baseV = acLines.baseV;
		cN = terminal.connectNode;
		params.add(acLines.r);
		params.add(acLines.x);
		params.add(acLines.b);
		params.add(acLines.g);
		end = terminal.end;

	}
	public Terminal getTerminal() {
		return terminal;
	}
	public String getCN() {
		return cN;
	}
	public int getEnd() {
		return end;
	}
	public String getBaseV() {
		return baseV;
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
