package com.hacksnet.kypota.service;

import javax.websocket.server.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hacksnet.kypota.dao.TestRepository;

@Component
@Path("/test")
public class TestService {
	//private static Logger log = LoggerFactory.getLogger(TestService.class);

	
	@Autowired
	private TestRepository repo;
	
	
	@GET
	@Path("/getuser")
	@Produces({MediaType.TEXT_PLAIN})
	//@Consumes(MediaType.APPLICATION_JSON)
	public String getDbUser() {
		
		String dbResults = repo.getDbTest();
		System.out.println("DB Results: "+ dbResults);
		return dbResults;
	}
	
	@GET
	@Path("/teststr")
	@Produces({MediaType.TEXT_PLAIN})
	//@Consumes(MediaType.APPLICATION_JSON)
	public String index() {
		return "hello this is jersey";
	}
	
	@GET
	@Path("/test/{testvar}")
	@Produces({MediaType.TEXT_PLAIN})
	//@Consumes(MediaType.APPLICATION_JSON)
	public String index2(@PathParam("testvar") String testvar) {
		return "hello this is jersey "+testvar;
	}
	
	@GET
	@Path("/test2")
	@Produces({MediaType.TEXT_PLAIN})
	//@Consumes(MediaType.APPLICATION_JSON)
	public String index3(@QueryParam("qryvar") String qryvar) {
		// use this to allow nulls
		return "hello this is jersey "+String.valueOf(qryvar);
	}
}
