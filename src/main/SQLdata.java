package main;

import java.sql.*;
import java.util.*;

import cimObjects.*;

/**
 * This class will get the already created Arraylists with the requested data and
 * add them to seperate tables, for each CIM-object type, in a created SQL-database.
 */

public class SQLdata {

	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/";
	static final String TIME = "?serverTimezone=EST#/";
	
	static final String USER = "root";
	static final String PASS = "root"; 


	ArrayList<BaseVoltage> baseVoltList;
	ArrayList<Substation> subList;
	ArrayList<VoltageLevel> voltList;
	ArrayList<GenUnit> genUnitList;
	ArrayList<SyncMachine> syncList;
	ArrayList<RegControl> regList;
	ArrayList<PowerTrans> powerList;
	ArrayList<EnergyCons> energyList;
	ArrayList<PowerTransformerEnd> powerEndList;
	ArrayList<Breaker> breakerList;
	ArrayList<RatioTapChanger> ratioList;
	ArrayList<ACLines> acList;
	ArrayList<Busbar> busList;
	ArrayList<ConnNode> connList;
	ArrayList<Terminal> termList;

	public SQLdata(ArrayList<BaseVoltage> baseVoltList,	
			ArrayList<Substation> subList,	
			ArrayList<VoltageLevel> voltList, 
			ArrayList<GenUnit> genUnitList, 
			ArrayList<SyncMachine> syncList,
			ArrayList<RegControl> regList,
			ArrayList<PowerTrans> powerList,
			ArrayList<EnergyCons> energyList,
			ArrayList<PowerTransformerEnd> powerEndList,
			ArrayList<Breaker> breakerList,
			ArrayList<RatioTapChanger> ratioList, 	
			ArrayList<ACLines> acList,
			ArrayList<Busbar> busList,
			ArrayList<ConnNode> connList,
			ArrayList<Terminal> termList) {

		this.baseVoltList = baseVoltList;
		this.subList = subList;
		this.voltList = voltList;
		this.genUnitList = genUnitList;
		this.syncList = syncList;
		this.regList = regList;
		this.powerList = powerList;
		this.energyList = energyList;
		this.powerEndList = powerEndList;
		this.breakerList = breakerList;
		this.ratioList = ratioList;

		sync();

	}
	public void sync() {

		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pStmt = null;

		String name = "Grid";
		
		String[] objects = {"BaseVoltage", "Substation","VoltageLevel","GeneratingUnit","SynchronousMachine","RegulatingControl"
				,"PowerTransformer","EnergyConsumer","PowerTransformerEnd","Breaker","RatioTapChanger"};

		try {
			Class.forName(JDBC_DRIVER);
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL+TIME, USER, PASS);
			System.out.println("Creating database...");

			stmt = conn.createStatement();
			String sql = "DROP DATABASE IF EXISTS "+ name;
			stmt.executeUpdate(sql);

			stmt = conn.createStatement();
			sql = "CREATE DATABASE IF NOT EXISTS "+ name ;
			stmt.executeUpdate(sql);

			System.out.println("Database "+ name +" created successfully...");
			conn = DriverManager.getConnection(DB_URL+ name + TIME, USER, PASS);

			sql = "USE " + name ;
			stmt.executeUpdate(sql);

			for(int i = 0; i < objects.length; i++) {

			}

			String drop = "DROP TABLE IF EXISTS ";
			String create = "CREATE TABLE IF NOT EXISTS ";
			String norm = "VARCHAR(40) NOT NULL, name VARCHAR(40)";


			/////////////////////// BASEVOLTAGE
			sql = drop +objects[0];
			stmt.executeUpdate(sql);
			sql = create +objects[0]+"(BaseVoltage_rdfID VARCHAR(40) NOT NULL, NominalValue DOUBLE, PRIMARY KEY(BaseVoltage_rdfID))";
			stmt.executeUpdate(sql); 

			for (int i = 0; i < baseVoltList.size(); i++) {
				String query = "INSERT INTO "+objects[0]+ " VALUES(?,?)";
				pStmt = conn.prepareStatement(query);
				pStmt.setString(1, baseVoltList.get(i).rdfID);
				pStmt.setDouble(2, baseVoltList.get(i).nominalValue);
				pStmt.executeUpdate();
			}

			/////////////////////// SUBSTATION
			sql = drop + objects[1];
			stmt.executeUpdate(sql);
			sql = create + objects[1] +"(Substation_rdfID VARCHAR(40) NOT NULL, name VARCHAR(40), Region_rdfID VARCHAR(40), PRIMARY KEY(Substation_rdfID))";
			stmt.executeUpdate(sql); 

			for (int i = 0; i < subList.size(); i++) {
				String query = "INSERT INTO "+objects[1]+ " VALUES(?,?,?)";
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, subList.get(i).rdfID);
				preparedStmt.setString(2, subList.get(i).name);
				preparedStmt.setString(3, subList.get(i).region);
				preparedStmt.executeUpdate();
			} 


			/////////////////////// VOLTAGELEVEL
			sql = drop + objects[2];
			stmt.executeUpdate(sql);
			sql = create + objects[2] +"(VoltageLevel_rdfID VARCHAR(40) NOT NULL, name VARCHAR(40), Substation_rdfID VARCHAR(40), BaseVoltage_rdfID VARCHAR(40), PRIMARY KEY(VoltageLevel_rdfID), "
					+ "FOREIGN KEY(Substation_rdfID) REFERENCES Substation(Substation_rdfID), "
					+ "FOREIGN KEY(BaseVoltage_rdfID) REFERENCES BaseVoltage(BaseVoltage_rdfID))";
			stmt.executeUpdate(sql); 

			for (int i = 0; i < voltList.size(); i++) {
				String query = "INSERT INTO "+objects[2]+" VALUES(?,?,?,?)";
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, voltList.get(i).rdfID);
				preparedStmt.setString(2, voltList.get(i).name);
				preparedStmt.setString(3, voltList.get(i).substation);
				preparedStmt.setString(4, voltList.get(i).baseV);
				preparedStmt.executeUpdate();
			}

			/////////////////////// GENERATIINGUNIT
			sql = drop + objects[3];
			stmt.executeUpdate(sql);
			sql = create + objects[3] +"(GeneratingUnit_rdfID VARCHAR(40) NOT NULL, name VARCHAR(40), maxP DOUBLE, minP DOUBLE, EquipmentContainer_rdfID VARCHAR(40), PRIMARY KEY(GeneratingUnit_rdfID), "
					+ "FOREIGN KEY(EquipmentContainer_rdfID) REFERENCES Substation(Substation_rdfID))";
			stmt.executeUpdate(sql); 

			for (int i = 0; i < genUnitList.size(); i++) {
				String query = "INSERT INTO "+objects[3]+" VALUES(?,?,?,?,?)";
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, genUnitList.get(i).rdfID);
				preparedStmt.setString(2, genUnitList.get(i).name);
				preparedStmt.setDouble(3, genUnitList.get(i).maxP);
				preparedStmt.setDouble(4, genUnitList.get(i).minP);
				preparedStmt.setString(5, genUnitList.get(i).equipC);

				preparedStmt.executeUpdate();
			}
			System.out.println("Table "+ objects[3]+ " created successfully...");

			/////////////////////// REGULATING CONTROL
			sql = drop + objects[5];
			stmt.executeUpdate(sql);
			sql = create + objects[5] +"(RegulatingControl_rdfID "+ norm +", Value DOUBLE , PRIMARY KEY(RegulatingControl_rdfID))";
			stmt.executeUpdate(sql); 

			for (int i = 0; i < regList.size(); i++) {
				String query = "INSERT INTO "+objects[5]+" VALUES(?,?,?)";
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, regList.get(i).rdfID);
				preparedStmt.setString(2, regList.get(i).name);
				preparedStmt.setDouble(3, regList.get(i).target);
				preparedStmt.executeUpdate();
			}
			System.out.println("Table "+ objects[5]+ " created successfully...");


			/////////////////////// SYNCH.MACHINE
			sql = drop + objects[4];
			stmt.executeUpdate(sql);
			sql = create + objects[4] +"(SyncMachine_rdfID  VARCHAR(40) NOT NULL, name VARCHAR(40), RatedS DOUBLE, p DOUBLE, q DOUBLE, GeneratingUnit_rdfID VARCHAR(40), "
					+ "RegulatingControl_rdfID VARCHAR(40), EquipmentContainer_rdfID VARCHAR(40), PRIMARY KEY(SyncMachine_rdfID), "
					+ "FOREIGN KEY(GeneratingUnit_rdfID) REFERENCES GeneratingUnit(GeneratingUnit_rdfID), "
					+ "FOREIGN KEY(RegulatingControl_rdfID) REFERENCES RegulatingControl(RegulatingControl_rdfID), "
					+ "FOREIGN KEY(EquipmentContainer_rdfID) REFERENCES VoltageLevel(VoltageLevel_rdfID))";
			stmt.executeUpdate(sql); 

			for (int i = 0; i < syncList.size(); i++) {
				String query = "INSERT INTO "+objects[4]+" VALUES(?,?,?,?,?,?,?,?)";
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, syncList.get(i).rdfID);
				preparedStmt.setString(2, syncList.get(i).name);
				preparedStmt.setDouble(3, syncList.get(i).ratedS);
				preparedStmt.setDouble(4, syncList.get(i).p);
				preparedStmt.setDouble(5, syncList.get(i).q);
				preparedStmt.setString(6, syncList.get(i).genU);
				preparedStmt.setString(7, syncList.get(i).regC);
				preparedStmt.setString(8, syncList.get(i).eequipC);

				preparedStmt.executeUpdate();
			}
			System.out.println("Table "+ objects[4]+ " created successfully...");


			/////////////////////// POWER TRANSFORMER
			sql = drop + objects[6];
			stmt.executeUpdate(sql);
			sql = create + objects[6] +"(PowerTransformer_rdfID "+ norm +", EquipmentContainer_rdfID VARCHAR(40) , PRIMARY KEY(PowerTransformer_rdfID), "
					+ "FOREIGN KEY(EquipmentContainer_rdfID) REFERENCES Substation(Substation_rdfID))";
			stmt.executeUpdate(sql); 

			for (int i = 0; i < powerList.size(); i++) {
				String query = "INSERT INTO "+objects[6]+" VALUES(?,?,?)";
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, powerList.get(i).rdfID);
				preparedStmt.setString(2, powerList.get(i).name);
				preparedStmt.setString(3, powerList.get(i).equipC);
				preparedStmt.executeUpdate();
			}
			System.out.println("Table "+ objects[6]+ " created successfully...");

			/////////////////////// ENERGY CONSUMER
			sql = drop + objects[7];
			stmt.executeUpdate(sql);
			sql = create + objects[7] +"(EnergyConsumer_rdfID "+ norm +", p DOUBLE, q DOUBLE, EquipmentContainer_rdfID VARCHAR(40), PRIMARY KEY(EnergyConsumer_rdfID), "
					+ "FOREIGN KEY(EquipmentContainer_rdfID) REFERENCES VoltageLevel(VoltageLevel_rdfID))";
			stmt.executeUpdate(sql); 

			for (int i = 0; i < energyList.size(); i++) {
				String query = "INSERT INTO "+objects[7]+" VALUES(?,?,?,?,?)";
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, energyList.get(i).rdfID);
				preparedStmt.setString(2, energyList.get(i).name);
				preparedStmt.setDouble(3, energyList.get(i).p);
				preparedStmt.setDouble(4, energyList.get(i).q);
				preparedStmt.setString(5, energyList.get(i).eequipC);

				preparedStmt.executeUpdate();
			}
			System.out.println("Table "+ objects[7]+ " created successfully...");

			/////////////////////// PowerTransformerEnd
			sql = drop + objects[8];
			stmt.executeUpdate(sql);
			sql = create + objects[8] +"(PowerTransformerEnd_rdfID "+ norm +", r DOUBLE, x DOUBLE,  b DOUBLE, g DOUBLE, PowerTransformer_rdfID VARCHAR(40), "
					+ "BaseVoltage_rdfID VARCHAR(40), PRIMARY KEY(PowerTransformerEnd_rdfID), "
					+ "FOREIGN KEY(PowerTransformer_rdfID) REFERENCES PowerTransformer(PowerTransformer_rdfID), "
					+ "FOREIGN KEY(BaseVoltage_rdfID) REFERENCES BaseVoltage(BaseVoltage_rdfID))";
			stmt.executeUpdate(sql); 

			for (int i = 0; i < powerEndList.size(); i++) {
				String query = "INSERT INTO "+objects[8]+" VALUES(?,?,?,?,?,?,?,?)";
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, powerEndList.get(i).rdfID);
				preparedStmt.setString(2, powerEndList.get(i).name);
				preparedStmt.setDouble(3, powerEndList.get(i).r);
				preparedStmt.setDouble(4, powerEndList.get(i).x);
				preparedStmt.setDouble(5, powerEndList.get(i).b);
				preparedStmt.setDouble(6, powerEndList.get(i).g);
				preparedStmt.setString(7, powerEndList.get(i).trans);
				preparedStmt.setString(8, powerEndList.get(i).baseV);

				preparedStmt.executeUpdate();
			}
			System.out.println("Table "+ objects[8]+ " created successfully...");

			/////////////////////// BREAKER
			sql = drop + objects[9];
			stmt.executeUpdate(sql);
			sql = create + objects[9] +"(Breaker_rdfID "+ norm +", BreakerState BOOL, EquipmentContainer_rdfID VARCHAR(40), PRIMARY KEY(Breaker_rdfID), "
					+ "FOREIGN KEY(EquipmentContainer_rdfID) REFERENCES VoltageLevel(VoltageLevel_rdfID))";
			stmt.executeUpdate(sql); 

			for (int i = 0; i < breakerList.size(); i++) {
				String query = "INSERT INTO "+objects[9]+" VALUES(?,?,?,?)";
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, breakerList.get(i).rdfID);
				preparedStmt.setString(2, breakerList.get(i).name);
				preparedStmt.setBoolean(3, breakerList.get(i).state);
				preparedStmt.setString(4, breakerList.get(i).eequipC);

				preparedStmt.executeUpdate();
			}
			System.out.println("Table "+ objects[9]+ " created successfully...");

			/////////////////////// RATIO TAP CHANGER
			sql = drop + objects[10];
			stmt.executeUpdate(sql);
			sql = create + objects[10] +"(RatioTapChanger_rdfID "+ norm +", Step DOUBLE, PowerTransformerEnd_rdfID VARCHAR(40), PRIMARY KEY(RatioTapChanger_rdfID), "
					+ "FOREIGN KEY(PowerTransformerEnd_rdfID) REFERENCES PowerTransformerEnd(PowerTransformerEnd_rdfID))";
			stmt.executeUpdate(sql); 

			for (int i = 0; i < ratioList.size(); i++) {
				String query = "INSERT INTO "+objects[10]+" VALUES(?,?,?,?)";
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, ratioList.get(i).rdfID);
				preparedStmt.setString(2, ratioList.get(i).name);
				preparedStmt.setDouble(3, ratioList.get(i).step);
				preparedStmt.setString(4, ratioList.get(i).transEnd);
				preparedStmt.executeUpdate();
			}
			System.out.println("Table "+ objects[10]+ " created successfully...");



			System.out.println("The tables were created successfully in database " + name+"...");

			conn.close();

		}
		catch(SQLException se) {
			se.printStackTrace(); 
		}
		catch(Exception f) {
			f.printStackTrace();
		}

	}

}
