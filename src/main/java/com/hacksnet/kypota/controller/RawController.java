package com.hacksnet.kypota.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hacksnet.kypota.dao.ContestLogRepository;
import com.hacksnet.kypota.model.ContestLog;

@Controller
@RequestMapping("/show_raw")
public class RawController {
	private ContestLogRepository contestRepo;

	@Autowired
	public RawController (ContestLogRepository repo) {
		this.contestRepo = repo;
	}
	
	@RequestMapping(method=RequestMethod.GET, produces="text/plain;charset=UTF-8")
	public @ResponseBody String log(@PathParam("logId") Integer logId, Map<String,Object> model, HttpServletResponse response) {
		ContestLog log = contestRepo.getLog(logId);
		

		//response.setContentType("text/plain;charset=UTF-8");
		

		return log.getRawLog();
	}

}
