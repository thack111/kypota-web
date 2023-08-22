package com.hacksnet.kypota.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hacksnet.kypota.dao.ContestLogRepository;
import com.hacksnet.kypota.dao.LogParserRepository;
import com.hacksnet.kypota.model.ContestLog;
import com.hacksnet.kypota.model.Park;

@Controller
@RequestMapping("/reupload")
public class ReUploadController {
	private ContestLogRepository contestRepo;
	
	@Autowired
	public void UpdateController (ContestLogRepository repo) {
		this.contestRepo = repo;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String reShow(@PathParam("logId") Integer logId, Map<String,Object> model) {
		List<Park> parks = contestRepo.getParkList();
		model.put("parks",  parks);
		ContestLog log = contestRepo.getLog(logId);
		model.put("log",  log);
		
		return "reupload";
	}
	
	

	@RequestMapping(method=RequestMethod.POST)
	public String handleFileReUpload(@RequestParam("file") MultipartFile file, @RequestParam("name") String submittedName,
			  @RequestParam("log_id") String logId,
			  @RequestParam("email") String submittedEmail, @RequestParam("park") String park, @RequestParam("type") String type,
			  @RequestParam("operator") String operator,
			  RedirectAttributes redirectAttributes) throws IOException {

		System.out.println("Found file " + file.getOriginalFilename() + "!");
		ContestLog logFile = new ContestLog();
		logFile.setLogId(Integer.parseInt(logId));
		logFile.setSubmittedName(submittedName);
		logFile.setSubmittedEmail(submittedEmail);
		logFile.setParkAbbr(park);
		logFile.setLogType(type);
		logFile.setOperators(operator);

		InputStream resource = file.getInputStream();
	    try ( BufferedReader reader = new BufferedReader(
	      new InputStreamReader(resource)) ) {
	       
	    	logFile = LogParserRepository.parseLog(logFile, reader);
	    	contestRepo.updateLog(logFile);
	    }
	    
		
		redirectAttributes.addFlashAttribute("message",
				"You successfully re-uploaded " + file.getOriginalFilename() + "!  We found " + logFile.getQsos().size() + " QSOs!");

		return "redirect:/";
	}	
	
}
