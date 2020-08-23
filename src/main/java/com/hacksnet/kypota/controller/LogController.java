package com.hacksnet.kypota.controller;

import java.util.Map;

import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hacksnet.kypota.dao.ContestLogRepository;
import com.hacksnet.kypota.model.ContestLog;

@Controller
@RequestMapping("/show_log")
public class LogController {
	private ContestLogRepository contestRepo;

	@Autowired
	public LogController (ContestLogRepository repo) {
		this.contestRepo = repo;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String log(@PathParam("logId") Integer logId, Map<String,Object> model) {
		ContestLog log = contestRepo.getLog(logId);
		model.put("log",  log);

		return "show_log";
	}

	@RequestMapping(method=RequestMethod.POST)
	public String process(@RequestParam("post_action") String postAction,
						  @RequestParam("log_id") String logId,
						  RedirectAttributes redirectAttributes) {
		
		if (postAction.contentEquals("deleteLog"))	{
			contestRepo.deleteLog(Integer.parseInt(logId));
			
			redirectAttributes.addFlashAttribute("message",
					"Deleted Log File!");
		}
		
		return "redirect:/results";
	}
}
