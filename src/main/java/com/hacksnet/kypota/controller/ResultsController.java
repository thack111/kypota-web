package com.hacksnet.kypota.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hacksnet.kypota.dao.ContestLogRepository;
import com.hacksnet.kypota.dao.ResultsRepository;
import com.hacksnet.kypota.model.ResultsSummary;

@Controller
@RequestMapping("/results")
public class ResultsController {
	private ResultsRepository resultsRepo;
	private ContestLogRepository contestRepo;

	@Autowired
	public ResultsController (ResultsRepository resultsRepo) {
		this.resultsRepo = resultsRepo;
	}
	
	@Autowired
	public void UpdateController (ContestLogRepository repo) {
		this.contestRepo = repo;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String results(Map<String,Object> model) {
		List<ResultsSummary> results = resultsRepo.getResults();
		model.put("results",  results);

		return "results";
	}

	@RequestMapping(method=RequestMethod.POST)
	public String analyse(@RequestParam("post_action") String postAction,
            			  RedirectAttributes redirectAttributes) {
		
		if (postAction.contentEquals("analyse"))	{
			resultsRepo.performAssesment();
			
			redirectAttributes.addFlashAttribute("message",
					"Re-Calculating results on all logs!");
		}
		
		return "redirect:/results";
	}

}
