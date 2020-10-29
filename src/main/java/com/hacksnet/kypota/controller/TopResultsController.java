package com.hacksnet.kypota.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hacksnet.kypota.dao.ResultsRepository;
import com.hacksnet.kypota.model.ResultsSummary;

@Controller
@RequestMapping("/top_results")
public class TopResultsController {
	private ResultsRepository resultsRepo;

	@Autowired
	public TopResultsController (ResultsRepository resultsRepo) {
		this.resultsRepo = resultsRepo;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String results(Map<String,Object> model) {
		List<ResultsSummary> hunters = resultsRepo.getResults("Hunters");
		List<ResultsSummary> parks = resultsRepo.getResults("Parks");
		model.put("hunters",  hunters);
		model.put("parks",  parks);

		return "top_results";
	}

}
