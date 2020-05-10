package main;

import java.io.File;

// 

@SuppressWarnings("unused")
public class Start {
	int sBase;
	String [][] yM;

	public Start(File xmlEQ, File xmlSSH, int sBase) {

		File eqXML = xmlEQ;
		File sshXML = xmlSSH;
		this.sBase = sBase;
		ReadXML xml = new ReadXML(eqXML, sshXML);
		
		SQLdata sql = new SQLdata(xml.getBaseVoltList(), xml.getSubList(), xml.getVoltList(), xml.getGenUnitList(), 
				xml.getSyncList(), xml.getRegList(), xml.getPowerList(), xml.getenergyList(), xml.getPowerEndList(), 
				xml.getBreakerList(), xml.getRatioList(), xml.getACLines(), xml.getBusbar(), xml.getConnNode(), xml.getTerminal());
		
		Topology top = new Topology(xml.getConnNode(), xml.getTerminal(), xml.getBreakerList(), xml.getPowerList(), xml.getPowerEndList(), xml.getACLines());
		
		YMatrix yMatrix = new YMatrix(sBase, top.getacL(), xml.getBaseVoltList(), top.getcNGList(), top.getpTGL(), xml.getTerminal());
		this.yM = yMatrix.getYMatrix();
		
	}
	
	public String[][] getYMatrix() {
		return yM;
	}
}
