package main;

import java.util.ArrayList;
import cimObjects.Terminal;

/*
 * This class merges ConnectivtyNodes into a ConnectiivtyNodeGroup, based on the current state of the breaker
 * It will add all the CN:rdfId, and arrays of it's connected terminals, to the CNG.
 */

public class ConnectivityNodeGroup {

	public String[] id;
	public int length;
	public ArrayList<String> cNId = new ArrayList<String>();
	public ArrayList<Terminal> tempTerm;


	public ConnectivityNodeGroup(String[] id, int length, ArrayList<Terminal> tempTerm) {
		this.id = id;
		this.length = length;
		this.tempTerm = tempTerm;

		for(int i = 0; i < length; i++) {
			this.cNId.add(id[i]);
		}

	}
	public ArrayList<String> getCNId() {
		return cNId;
	}
	public ArrayList<Terminal> getCNTerm() {
		return tempTerm;
	}
}
