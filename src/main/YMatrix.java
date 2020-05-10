package main;

import java.util.ArrayList;

import cimObjects.BaseVoltage;
import cimObjects.Terminal;

/**
 * This class will do the last step in calculatin the Y-matrix by first calculate the Zbase
 * and then calculate each line addmittance for all Node Groups. 
 * These line addmittances will be used to calculate the complete admittance matrix for each node. 
 */

public class YMatrix {


	double baseTemp;
	double sBase;
	double [] zBase;
	String[][] yMatrix;
	ArrayList<ACLineSegment> acL;
	ArrayList<BaseVoltage> bVolt;
	ArrayList<ConnectivityNodeGroup> cNG;
	ArrayList<PowerTransGroup> pTGL;
	ArrayList<Terminal> terminalList;


	public YMatrix( int sBase, 
			ArrayList<ACLineSegment> acL,
			ArrayList<BaseVoltage> bVolt,
			ArrayList<ConnectivityNodeGroup> cNGList,
			ArrayList<PowerTransGroup> pTGL,
			ArrayList<Terminal> terminalList) {

		this.acL = acL;
		this.bVolt = bVolt;
		this.cNG = cNGList;
		this.pTGL = pTGL;
		this.terminalList = terminalList;
		this.sBase = sBase;


		zBaseCalc();
		yMatrix = yMatrix(zBase);
	}

	public void zBaseCalc() {


		double [] zBase = new double[cNG.size()];
		for (int i = 0; i < cNG.size(); i++) {

			for (int j = 0; j < cNG.get(i).getCNId().size(); j++) {
				if(zBase[i] != 0) {
					break;
				}
				else {
					for (int k = 0; k < pTGL.size(); k++) {
						if(pTGL.get(k).cN.contains(cNG.get(i).getCNId().get(j))) {
							for(int l = 0; l < bVolt.size(); l++) {
								if(pTGL.get(k).baseV.contains(bVolt.get(l).rdfID)) {
									baseTemp = bVolt.get(l).nominalValue;
									baseTemp = baseTemp*baseTemp/sBase;
									zBase[i] = baseTemp;
								}
							}
						}
					}
				}

				for (int m = 0; m < acL.size(); m++) {
					if(acL.get(m).cN.contains(cNG.get(i).getCNId().get(j))) {
						if(zBase[i] != 0) {
							break;
						}
						else {
							for(int l = 0; l < bVolt.size(); l++) {
								if(acL.get(m).baseV.contains(bVolt.get(l).rdfID)) {
									baseTemp = bVolt.get(l).nominalValue;
									baseTemp = baseTemp*baseTemp/sBase;
									zBase[i] = baseTemp;
								}
							}
						}
					}
				}
			}
		}
		this.zBase = zBase;
	}

	public String[][] yMatrix(double [] zBase) {

		/* Each nodes in ConnectivityNodeGroup represents a node in the power grid.
		 * These CN-id are compared with the help of the objects in powerTransformerGroups and ACLineSegement.
		 * These are used to calculate the admittances for the final Admittance Matrix.
		 * The Admittance Matrix is of the same size as there are nodes in the CNG.
		 */


		// Total admittance matrix
		double yRe [][] = new double [cNG.size()][cNG.size()];
		double yIm [][] = new double [cNG.size()][cNG.size()];

		// Each line admittances
		double ylRe [][] = new double [cNG.size()][cNG.size()];
		double ylIm [][] = new double [cNG.size()][cNG.size()];

		String yMatrix [][] = new String [cNG.size()][cNG.size()];


		for (int i = 0; i < cNG.size(); i++) {
			for (int k = 0; k < pTGL.size(); k=k+2) {
				if (cNG.get(i).cNId.contains(pTGL.get(k).cN)) {
					for (int q = 0; q < cNG.size(); q++) {
						if (cNG.get(q).cNId.contains(pTGL.get(k+1).cN) && q != i) {

							int m = k;
							double yG =  real(pTGL.get(m).getR(), pTGL.get(m).getX(), pTGL.get(m).getG(), pTGL.get(m).getB(), zBase[i]);
							double yB =  img (pTGL.get(m).getR(), pTGL.get(m).getX(), pTGL.get(m).getG(), pTGL.get(m).getB(), zBase[i]);

							ylRe [i][q] = yG + ylRe [i][q];
							ylIm [i][q] = yB + ylIm [i][q];
							ylRe [q][i] = yG + ylRe [q][i];
							ylIm [q][i] = yB + ylIm [q][i];

						}
					}
				}
			}

			for (int l = 0; l < acL.size(); l=l+2) {
				if (cNG.get(i).cNId.contains(acL.get(l).cN)) {
					for (int q = 0; q < cNG.size(); q++) {
						if (cNG.get(q).cNId.contains(acL.get(l+1).cN) && q != i) {
							int m = l;
							double yG =  real(acL.get(m).getR(), acL.get(m).getX(), acL.get(m).getG(), acL.get(m).getB(), zBase[q]);
							double yB =  img(acL.get(m).getR(), acL.get(m).getX(), acL.get(m).getG(), acL.get(m).getB(), zBase[q]);

							ylRe [i][q] = yG + ylRe [i][q];
							ylIm [i][q] = yB + ylIm [i][q];
							ylRe [q][i] = yG + ylRe [q][i];
							ylIm [q][i] = yB + ylIm [q][i];

						}
					}
				}
			}
		}
		// Adds the line admittances to the total Admittance matrix.
		// Y1 = y12 + y13 +... 
		for (int i = 0; i < ylRe.length; i++) {
			for (int j = 0; j < ylRe.length; j++) {
				if(i!=j) {
					yRe [i][i] = yRe [i][i] - ylRe [i][j];
					yIm [i][i] = yIm [i][i] - ylIm [i][j];
					yRe [i][j] = ylRe [i][j];
					yIm [i][j] = ylIm [i][j];
				}
			}
		}
		for (int i = 0; i < ylRe.length; i++) {
			for (int j = 0; j < ylRe.length; j++) {
				String kk = complex(yRe [i][j], yIm [i][j]);
				yMatrix[i][j] = kk;
			}
		}
		return yMatrix;
	}

	// Method to calculate the real part of the Z-impedance to the real part of the Y-admittance
	public double real(double r, double x, double g, double b, double zb) {

		double zR = r / zb;
		double zX = x / zb;
		double z = (zR*zR) + (zX*zX);
		double yR = zR/z;
		double yG =  (g*zb/2) + yR;
		yG = -yG;
		yG = Math.round(yG * 10000d) / 10000d;
		return yG;
	}
	
	// Method to calculate the imaginary part of the Z-impedance to the imaginary part of the Y-admittance
	public double img(double r, double x, double g, double b, double zb) {

		double zR = r / zb;
		double zX = x / zb;
		double z = (zR*zR) + (zX*zX);
		double yX = -zX/z;
		double yB =  (b*zb/2) + yX;
		yB = - yB;
		yB = Math.round(yB * 10000d) / 10000d;
		return yB;
	}

	public String complex(double re, double im) {
		re = Math.round(re * 10000d) / 10000d;
		im = Math.round(im * 10000d) / 10000d;
		if (im>=0) {
			return re + "+" + im + "j";
		}
		else {
			return re + "" + im + "j";
		}
	}

	public void print() {
		for(ConnectivityNodeGroup p : cNG) {
			System.out.printf(" \n %s  %s \t \n",p.getCNId().size(), p.getCNId());
			for (int tt=0; tt< p.getCNTerm().size(); tt++) {
				System.out.println(tt+1 + "/" + p.getCNTerm().size() + " " + p.getCNTerm().get(tt).name);
			}	
		}
		System.out.println("\n ZBase ");
		for(Double z:zBase) {
			System.out.println(z);
		}

		System.out.println("\n PowerTransGroup :");
		for(PowerTransGroup p : pTGL) {
			System.out.printf(" \n %s \n %s \t \n",p.cN, p.terminalN.name);
			String[] kk = {" r = ", " x = ", " b = ", " g = ", " end = "};
			for (int tt=0; tt< p.getParams().size(); tt++) {
				System.out.println(kk[tt] + p.getParams().get(tt).doubleValue());
			}	
			System.out.println(kk[4] + p.end);
		}

		System.out.println("\n ACLineSegments :");
		for(ACLineSegment a : acL) {
			System.out.printf(" \n %s \t \n",a.cN);
			String[] kk = {" r = ", " x = ", " b = ", " g = ", " end = "};
			for (int tt=0; tt<a.getParams().size(); tt++) {
				System.out.println(kk[tt] + a.getParams().get(tt).doubleValue());
			}	
			System.out.println(kk[4] + a.end);
		}
	}
	public String[][] getYMatrix() {
		return yMatrix;
	}
}
