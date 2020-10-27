package com.hacksnet.kypota.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hacksnet.kypota.model.ContestLog;
import com.hacksnet.kypota.model.LogQso;

public class LogParserRepository {

	public static ContestLog parseLog(ContestLog logFile, BufferedReader reader) throws IOException{
		

		List<LogQso> qsos = new ArrayList<>();
		StringBuffer rawLog = new StringBuffer();
		
		String line = reader.readLine();
    	while (line != null) {
    		
    		
    		Matcher m = Pattern.compile("Bogus").matcher(line);	
    		//Find and set all fields.
    		logFile.setLogType("Cabrillo");
    		m = Pattern.compile("START-OF-LOG: (.*)").matcher(line);	
    		if ( m.find()) { logFile.setFormat(m.group(1)); }
    		
    		m = Pattern.compile("LOCATION: (.*)").matcher(line);	
    		if ( m.find()) { logFile.setLocation(m.group(1)); }
    		
    		m = Pattern.compile("CLUB: (.*)").matcher(line);	
    		if ( m.find()) { logFile.setClub(m.group(1)); }
    		
    		m = Pattern.compile("CONTEST: (.*)").matcher(line);	
    		if ( m.find()) { logFile.setContest(m.group(1)); }
    		
    		m = Pattern.compile("CATEGORY-OPERATOR: (.*)").matcher(line);	
    		if ( m.find()) { logFile.setCatOperator(m.group(1)); }
    		
    		m = Pattern.compile("CATEGORY-ASSISTED: (.*)").matcher(line);	
    		if ( m.find()) { logFile.setCatAssisted(m.group(1)); }
    		
    		m = Pattern.compile("CATEGORY-BAND: (.*)").matcher(line);	
    		if ( m.find()) { logFile.setCatBand(m.group(1)); }
    		
    		m = Pattern.compile("CATEGORY-MODE: (.*)").matcher(line);	
    		if ( m.find()) { logFile.setCatMode(m.group(1)); }
    		
    		m = Pattern.compile("CATEGORY-POWER: (.*)").matcher(line);	
    		if ( m.find()) { logFile.setCatPower(m.group(1)); }
    		
    		m = Pattern.compile("CATEGORY-STATION: (.*)").matcher(line);	
    		if ( m.find()) { logFile.setCatStation(m.group(1)); }
    		
    		m = Pattern.compile("CATEGORY-TRANSMITTER: (.*)").matcher(line);	
    		if ( m.find()) { logFile.setCatTransmitter(m.group(1)); }
    		
    		m = Pattern.compile("CLAIMED-SCORE: (.*)").matcher(line);	
    		if ( m.find()) { logFile.setClaimedScore(m.group(1)); }
    		
    		m = Pattern.compile("OPERATORS: (.*)").matcher(line);	
    		if ( m.find()) { logFile.setOperators(m.group(1)); }
    		
    		m = Pattern.compile("NAME: (.*)").matcher(line);	
    		if ( m.find()) { logFile.setName(m.group(1)); }
    		
    		m = Pattern.compile("ADDRESS: (.*)").matcher(line);	
    		if ( m.find()) { logFile.setAddress(m.group(1)); }
    		
    		m = Pattern.compile("ADDRESS-CITY: (.*)").matcher(line);	
    		if ( m.find()) { logFile.setCity(m.group(1)); }
    		
    		m = Pattern.compile("ADDRESS-STATE: (.*)").matcher(line);	
    		if ( m.find()) { logFile.setState(m.group(1)); }
    		
    		m = Pattern.compile("ADDRESS-POSTALCODE: (.*)").matcher(line);	
    		if ( m.find()) { logFile.setZip(m.group(1)); }
    		
    		m = Pattern.compile("ADDRESS-COUNTRY: (.*)").matcher(line);	
    		if ( m.find()) { logFile.setCountry(m.group(1)); }
    		
    		m = Pattern.compile("EMAIL: (.*)").matcher(line);	
    		if ( m.find()) { logFile.setEmail(m.group(1)); }
    		
    		m = Pattern.compile("GRID_LOC: (.*)").matcher(line);	
    		if ( m.find()) { logFile.setGridLoc(m.group(1)); }
    		
    		m = Pattern.compile("SOAP_BOX: (.*)").matcher(line);	
    		if ( m.find()) { logFile.setSoapBox(m.group(1)); }
    		
    		m = Pattern.compile("OFFTIME:: (.*)").matcher(line);	
    		if ( m.find()) { System.out.println("times: "+ m.group(1)); } // Found OFFTIME, may do this later
    		
    		if (line.length() > 4 && line.substring(0, 4).equals("QSO:"))	
    		{
    			
    			if (logFile.getContest().equals("DX")) {
    				Matcher q = Pattern.compile("QSO:\\s+(\\S*)\\s+(\\S*)\\s+(\\S*)\\s+([0-9]*)\\s+(\\S*)\\s+(\\S*)\\s+(\\S*)\\s+(\\S*)\\s+(.*)").matcher(line);	
		    		if ( q.find()) { 
		    			LogQso qso = new LogQso();
		    			qso.setFreq(q.group(1));
		    			qso.setQsoMode(q.group(2));
		    			qso.setQsoDate(q.group(3) + " " + q.group(4));
		    			qso.setSntCall(q.group(5));
		    			qso.setSntRst(q.group(6));
		    			qso.setRcvCall(q.group(7));
		    			qso.setRcvRst(q.group(8));
		    			if (q.group(9).length() > 10)
		    				qso.setSntExch(q.group(9).substring(0, 11));
		    			if (q.group(9).length() > 11)
		    				qso.setRcvExch(q.group(9).substring(11));
		    			System.out.println("found : " + q.group(9));
		    			qsos.add(qso);
		    		}
    			}
    			else {
	    			Matcher q = Pattern.compile("QSO:\\s+(\\S*)\\s+(\\S*)\\s+(\\S*)\\s+([0-9]*)\\s+(\\S*)\\s+(\\S*)\\s+(\\S*)\\s+(\\S*)\\s+(\\S*)\\s+(\\S*)\\s+([0-9]?)").matcher(line);	
		    		if ( q.find()) { 
		    			LogQso qso = new LogQso();
		    			qso.setFreq(q.group(1));
		    			qso.setQsoMode(q.group(2));
		    			qso.setQsoDate(q.group(3) + " " + q.group(4));
		    			qso.setSntCall(q.group(5));
		    			qso.setSntRst(q.group(6));
		    			qso.setSntExch(q.group(7));
		    			qso.setRcvCall(q.group(8));
		    			qso.setRcvRst(q.group(9));
		    			qso.setRcvExch(q.group(10));
		    			qso.setTransmitterId( q.group(11));
		    			qsos.add(qso);
		    		}
    			}
    			
    		} // end of if QSO
    		
    		
    		rawLog.append(line).append("\n");
    		//System.out.println("Line: " + line);
    		line = reader.readLine();
    		// Next Line
    	}
    	logFile.setRawLog(rawLog.toString());
    	logFile.setQsos(qsos);	
    	
    	return logFile;
	}
}
