package com.hacksnet.kypota.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hacksnet.kypota.dao.ContestLogRepository;
import com.hacksnet.kypota.model.ContestLog;
import com.hacksnet.kypota.model.LogQso;
import com.hacksnet.kypota.model.Park;

@Controller
@RequestMapping("/upload")
public class UploadController {
	private ContestLogRepository contestRepo;
	
	@Autowired
	public void UpdateController (ContestLogRepository repo) {
		this.contestRepo = repo;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String home(Map<String,Object> model) {
		List<Park> parks = contestRepo.getParkList();
		model.put("parks",  parks);
		
		return "upload";
	}
	
//	@RequestMapping(method=RequestMethod.POST)
//	public String submit(Contact contact) {
//		contactRepo.save(contact);
//		return "redirect:/";
//	}
	

	@RequestMapping(method=RequestMethod.POST)
	public String handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("name") String submittedName,
			  @RequestParam("email") String submittedEmail, @RequestParam("park") String park,
			RedirectAttributes redirectAttributes) throws IOException {

		System.out.println("Found file " + file.getOriginalFilename() + "!");
		ContestLog logFile = new ContestLog();
		logFile.setSubmittedName(submittedName);
		logFile.setSubmittedEmail(submittedEmail);
		logFile.setParkAbbr(park);
		List<LogQso> qsos = new ArrayList<>();
		StringBuffer rawLog = new StringBuffer();
		int qsoCount = 0;

		InputStream resource = file.getInputStream();
	    try ( BufferedReader reader = new BufferedReader(
	      new InputStreamReader(resource)) ) {
	       

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
	    		
	    		if (line.substring(0, 4).equals("QSO:"))	
	    		{
	    			//System.out.println("Found QSO");
	    			//QSO: ***** ** yyyy-mm-dd nnnn ************* nnn ****** ************* nnn ****** n
	    			//QSO:  3799 PH 1999-03-06 0711 HC8N           59 700    W1AW           59 CT     0
	    			//QSO: 3689  PH 2003-01-18 1502 G4XYZ         59  001    G4TSH         59  005 
	    			//QSO:  3799 PH 2000-10-26 0711 AA1ZZZ          59  05     K9QZO         59  04     0
	    			//QSO: 14205 PH 2020-06-27 2056 W4GV          1E     KY  K4FC          5A   NFL   
	    			//QSO: 28048 CW 2014-01-11 1802 N5KO            TREY       CA  WA1S            ANN        GA
	    			//Matcher q = Pattern.compile("QSO:\s+(.*)\s+(.*)\s+([0-9-]*)\s+(0-9*)\s+(.*)\s+(.*)\s+(.*)\s+(.*)\s+(.*)\s+(.*)\s+(0-9)? ").matcher(line);	
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
		    			qsoCount++;
		    		}
	    			
	    		}
	    		
	    		
	    		rawLog.append(line).append("\n");
	    		//System.out.println("Line: " + line);
	    		line = reader.readLine();
	    		// Next Line
	    	}
	    	logFile.setRawLog(rawLog.toString());
	    	logFile.setQsos(qsos);	
	    	contestRepo.addLog(logFile);
	    	// End of buffer Reader
	    }
	    
		
		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!  We found " + qsoCount + " QSOs!");

		return "redirect:/";
	}	
	
}
