package com.hacksnet.kypota.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hacksnet.kypota.dao.ResultsRepository;
import com.hacksnet.kypota.model.ContestYear;
import com.hacksnet.kypota.model.ResultsSummary;

@Controller
@RequestMapping("/top_results")
public class TopResultsController {
	private ResultsRepository resultsRepo;

	@Autowired
	public TopResultsController (ResultsRepository resultsRepo) {
		this.resultsRepo = resultsRepo;
	}
	
	String resultsYear = new SimpleDateFormat("yyyy").format(new Date());
	
	@RequestMapping(method=RequestMethod.GET)
	public String top_results(Map<String,Object> model) {
		List<ResultsSummary> hunters = resultsRepo.getResults("Hunters", resultsYear);
		List<ResultsSummary> parks = resultsRepo.getResults("Parks", resultsYear);
		model.put("hunters",  hunters);
		model.put("parks",  parks);
		
		List<ContestYear> years = resultsRepo.getContestYears();
		model.put("years",  years);
		
		model.put("resultsYear", resultsYear);

		return "top_results";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String analyse(@RequestParam("post_action") String postAction,
						  @RequestParam("kypotayear") String kypotayear,
            			  RedirectAttributes redirectAttributes) {
		
		if (postAction.equals("setYear")) {
			resultsYear = kypotayear;
		}
		
		return "redirect:/top_results";
	}

}
