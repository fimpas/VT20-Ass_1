package main;
import cimObjects.*;
import java.util.ArrayList;

/**
 * This class will decide the topogoly of the grid with the help of the classes:
 * ConnectivityNodeGroup (cNG) - Decides the which connectivitys nodes that belong to the same group (Decides the number of nodes in the Grid)
 * PowerTransGroup (pTGL) - Pairs the PowerTransformer and PowerTranfsformerEnd, and add them to which cNG belong to.
 * ACLineSegments (acL) - Gathers the information from the ACLines and 
 */

public class Topology {

	ArrayList<ArrayList<Terminal>> cNt;
	ArrayList<ConnectivityNodeGroup> cNG;
	ArrayList<ConnectivityNodeGroup> cNGList;
	ArrayList<ConnNode> connList;
	ArrayList<ConnNode> conn2;
	ArrayList<Breaker> breakerList;
	ArrayList<Terminal> termList;
	ArrayList<PowerTrans> powerList;
	ArrayList<PowerTransformerEnd> powerEndList;
	ArrayList<PowerTransGroup> pTGL;
	ArrayList<ACLines> acLines;
	ArrayList<ACLineSegment> acL;


	public Topology(ArrayList<ConnNode> connList, 
			ArrayList<Terminal> termList,
			ArrayList<Breaker> breakerList,
			ArrayList<PowerTrans> powerList,
			ArrayList<PowerTransformerEnd> powerEndList,
			ArrayList<ACLines> acLines) {

		this.cNt = new ArrayList<ArrayList<Terminal>>();
		this.cNG = new ArrayList<ConnectivityNodeGroup>();
		this.cNGList = new ArrayList<ConnectivityNodeGroup>();
		this.pTGL = new ArrayList<PowerTransGroup>();
		this.acL = new ArrayList<ACLineSegment>();

		this.connList = connList;
		this.conn2 = connList;
		this.breakerList = breakerList;
		this.termList = termList;
		this.powerList = powerList;
		this.powerEndList = powerEndList;
		this.acLines = acLines;

		processor();
	}
	public void processor() {

		// BREAKER ADDING
		//Search for both terminals that are connected to each breakers
		for (int i = 0; i < breakerList.size(); i++) {

			//Amount of terminals, control if all terminals are found for each breaker
			boolean found_terminal = false;
			boolean breaker_state = breakerList.get(i).state;
			int first_terminal = 0;

			ArrayList<Terminal> tempTerm = new ArrayList<Terminal>();
			ArrayList<Terminal> tempTerm1 = new ArrayList<Terminal>();
			ArrayList<Terminal> tempTerm2 = new ArrayList<Terminal>();

			for (int j = 0; j < termList.size(); j++) {

				if (termList.get(j).cequip.contains(breakerList.get(i).rdfID)) {

					if (!found_terminal) {
						found_terminal=true;
						first_terminal = j;	
						tempTerm.add(termList.get(first_terminal));

					}
					else {
						tempTerm.add(termList.get(j));
						found_terminal=false;
						String[] id = {termList.get(first_terminal).connectNode, termList.get(j).connectNode};

						if (!breaker_state) {
							cNG.add(new ConnectivityNodeGroup(id, id.length, tempTerm));
						}
						else {
							tempTerm1.add(termList.get(first_terminal));
							tempTerm2.add(termList.get(j));
							String [] iq = {termList.get(j).connectNode};
							cNG.add(new ConnectivityNodeGroup(id, 1, tempTerm1));
							cNG.add(new ConnectivityNodeGroup(iq, 1, tempTerm2));
						}
					}
				}
			}
		}
		for(ConnectivityNodeGroup p : cNG) {
			for (int l = 0; l < p.getCNId().size(); l++) {
				for (int m = 0; m < connList.size(); m++) {
					if(p.cNId.get(l).contains(connList.get(m).rdfID)) {
						conn2.remove(connList.get(m));
					}
				}
			}
		}
		for (int n = 0; n < conn2.size(); n++) {
			ArrayList<Terminal> tempTerm = new ArrayList<Terminal>(); 
			int first_terminal = 0;
			boolean found_terminal = false ;
			for (int o = 0; o < termList.size(); o++) {
				if (termList.get(o).connectNode.contains(conn2.get(n).rdfID)) {
					if (!found_terminal) {	
						found_terminal=true;
						first_terminal = o;	
						tempTerm.add(termList.get(first_terminal));
					}	
					else {
						tempTerm.add(termList.get(o));
						found_terminal=false;
						String[] id = {conn2.get(n).rdfID};
						cNG.add(new ConnectivityNodeGroup(id, id.length, tempTerm));
					} 
				}
			}
		}

		for (int i = 0; i < cNG.size(); i++) {
			ArrayList<Terminal> tempTerm = new ArrayList<Terminal>();
			ArrayList<String> ngL = new ArrayList<String>();
			int added = 0;

			// Compare the CNs of the breakers and add all unique CNs with their affiliated terminals
			for(int j = i+1; j < cNG.size(); j++) {				
				int sizej = cNG.get(j).cNId.size();

				if (sizej>1) {

					if (cNG.get(i).cNId.get(0).contains(cNG.get(j).cNId.get(0)) || 
							cNG.get(i).cNId.get(0).contains(cNG.get(j).cNId.get(1))) {

						cnAdd(ngL,cNG,i, j, 1);
						added = added + 1;

					}
					else if (cNG.get(i).cNId.get(1).contains(cNG.get(j).cNId.get(0)) || 
							cNG.get(i).cNId.get(1).contains(cNG.get(j).cNId.get(1))) {

						cnAdd(ngL,cNG,i, j, 0);
						added = added + 1;
					}
				}
				else { 
					if (cNG.get(i).cNId.get(0).contains(cNG.get(j).cNId.get(0))) {

						cnAdd(ngL,cNG,i, j, 1);
						added = added + 1;
					}
					else if(cNG.get(i).cNId.get(1).contains(cNG.get(j).cNId.get(0))) {

						cnAdd(ngL,cNG,i, j, 0);
						added = added + 1;
					} 
				}

				// if counter is bigger than 0, add all terminals, remove them for org.list and back the counter one step
				if (added > 0) {
					tempTerm.addAll(cNG.get(j).getCNTerm());
					cNG.remove(j);
					j=j-1;
					added = 0;
				}
			}
			// If the list is bigger than 0, add terminals, remove them for the org.list and reset counter
			if (ngL.size() > 0) {
				tempTerm.addAll(cNG.get(i).getCNTerm());
				cNG.remove(i);
				i=-1;
				int size = ngL.size();
				String[] ng = new String [size];

				for (int q = 0; q < ngL.size(); q++) {
					ng[q] = ngL.get(q);
				}
				cNGList.add(new ConnectivityNodeGroup(ng, ng.length, tempTerm));
			}
		}		

		// Add all eventually missed CNs and its terminals

		if (cNG.size()>0) {
			for (int u = 0; u<cNG.size(); ++u) {
				if (1<cNG.get(u).cNId.size()) {
					String op [] = {cNG.get(u).cNId.get(0), cNG.get(u).cNId.get(1)};
					cNGList.add(new ConnectivityNodeGroup(op, op.length, cNG.get(u).getCNTerm()));
				}
				else {
					String op [] = {cNG.get(u).cNId.get(0)};				
					cNGList.add(new ConnectivityNodeGroup(op, op.length, cNG.get(u).getCNTerm()));
				} 
				cNG.remove(u);
				u=u-1;
			}
		}


		// Powertransformer merging and  add parameters
		for (int i = 0; i<powerList.size(); i++ ) {
			pTGL.add(new PowerTransGroup(powerList.get(i).rdfID, powerEndList, termList, 1));
			pTGL.add(new PowerTransGroup(powerList.get(i).rdfID, powerEndList, termList, 2));
		}

		// AC-Lines parameters
		for (int i = 0; i < acLines.size(); i++ ) {
			for (int j = 0; j < termList.size(); j++ ) {
				if (termList.get(j).cequip.contains(acLines.get(i).rdfID)) {
					acL.add(new ACLineSegment(termList.get(j), acLines.get(i)));
				}
			}
		}
	}

	public ArrayList<ConnectivityNodeGroup> getcNGList() {
		return cNGList;
	}
	public ArrayList<PowerTransGroup> getpTGL() {
		return pTGL;
	}
	public ArrayList<ACLineSegment> getacL() {
		return acL;
	}

	public void cnAdd(ArrayList<String> obj, ArrayList<ConnectivityNodeGroup> inObj, int i, int j, int k){
		obj.remove(inObj.get(i).cNId.get(k));
		obj.remove(inObj.get(j).cNId.get(0));
		obj.remove(inObj.get(j).cNId.get(1));

		obj.add(inObj.get(i).cNId.get(k));
		obj.add(inObj.get(j).cNId.get(0));
		obj.add(inObj.get(j).cNId.get(1));

	}
}

