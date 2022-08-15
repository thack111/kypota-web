package com.hacksnet.kypota.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
@RequestMapping("/results")
public class ResultsController {
	private ResultsRepository resultsRepo;

	@Autowired
	public ResultsController (ResultsRepository resultsRepo) {
		this.resultsRepo = resultsRepo;
	}
	
	
	String resultsYear = new SimpleDateFormat("yyyy").format(new Date());

	
	@RequestMapping(method=RequestMethod.GET)
	public String results(Map<String,Object> model) {
		List<ResultsSummary> results = resultsRepo.getResults("All", resultsYear);
		model.put("results",  results);
		
		List<ContestYear> years = resultsRepo.getContestYears();
		model.put("years",  years);
		
		model.put("resultsYear", resultsYear);
		
//		Calendar now = Calendar.getInstance();
//		int year = now.get(Calendar.YEAR);
//		String yearInString = String.valueOf(year);
//		
//		String thisYear = new SimpleDateFormat("yyyy").format(new Date());
		
		

		return "results";
	}

	@RequestMapping(method=RequestMethod.POST)
	public String analyse(@RequestParam("post_action") String postAction,
						  @RequestParam("kypotayear") String kypotayear,
            			  RedirectAttributes redirectAttributes) {
		
		if (postAction.contentEquals("analyse"))	{
			resultsRepo.performAssesment();
			
			redirectAttributes.addFlashAttribute("message",
					"Re-Calculating results on all logs!");
		} else if (postAction.equals("setYear")) {
			resultsYear = kypotayear;
		}
		
		return "redirect:/results";
	}

}
