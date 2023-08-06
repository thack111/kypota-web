package com.hacksnet.kypota.controller;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hacksnet.kypota.dao.TestRepository;

@RestController
@RequestMapping("/test/teststr")
public class TestController {
	private TestRepository testRepo;
	
	@Autowired
	public TestController (TestRepository testRepo) {
		this.testRepo = testRepo;
	}
	
	@RequestMapping(method=RequestMethod.GET )
	@Produces({MediaType.TEXT_PLAIN})
	public String test(Map<String,Object> model) {
		String line = " <CALL:6>KC3OLZ <QSO_DATE:8>20221209 <TIME_ON:6>002656 <TIME_OFF:6>002656 <BAND:3>40M <STATION_CALLSIGN:4>W4GV <FREQ:7>7.29301 <CONTEST_ID:2>DX <FREQ_RX:7>7.29301 <MODE:3>SSB <NAME:5>K5477 <RST_RCVD:2>57 <RST_SENT:2>57 <TX_PWR:3>100 <OPERATOR:6>TH4ACK <CQZ:1>5 <STX:1>1 <APP_N1MM_POINTS:1>1 <APP_N1MM_RADIO_NR:1>1 <APP_N1MM_CONTINENT:2>NA <APP_N1MM_RUN1RUN2:1>1 <APP_N1MM_RADIOINTERFACED:1>1 <APP_N1MM_ISORIGINAL:4>True <APP_N1MM_NETBIOSNAME:13>DESKTOP-THACK <APP_N1MM_ISRUNQSO:1>0 <PFX:3>KC3 <APP_N1MM_MULT1:1>1 <APP_N1MM_MULT2:1>0 <APP_N1MM_MULT3:1>0 <APP_N1MM_ID:32>2999b1f155ab4606b3d710beb1ad78e6 <APP_N1MM_CLAIMEDQSO:1>1 <EOR>";
		Matcher m = Pattern.compile("Bogus").matcher(line);	

		String qsoDate = "";
		String qsoTime = "";
		
		m = Pattern.compile("<QSO_DATE:([0-9]*)>(.*)<").matcher(line);	
		if ( m.find()) { 
			qsoDate = m.group(2).substring(0, Integer.parseInt(m.group(1))).substring(0, 4)+"-"+
					  m.group(2).substring(0, Integer.parseInt(m.group(1))).substring(4, 6)+"-"+
					  m.group(2).substring(0, Integer.parseInt(m.group(1))).substring(6, 8) ;
		}
		
		m = Pattern.compile("<TIME_ON:([0-9]*)>(.*)<").matcher(line);	
		if ( m.find()) { 
			qsoTime = m.group(2).substring(0, Integer.parseInt(m.group(1))).substring(0, 4) ;
		}
		
		return qsoDate+" "+qsoTime;
		
		
		//return testRepo.getDbTest();
	}
	


}
