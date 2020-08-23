package com.hacksnet.kypota.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TestRepository {
	
	private JdbcTemplate jdbc;
	//private NamedParameterJdbcTemplate namedJdbc;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbc = new JdbcTemplate(dataSource);
		//this.namedJdbc = new NamedParameterJdbcTemplate(dataSource);
	}	
	
	
	public String getDbTest() {
		String sql = " select  user || ' ' || to_char(sysdate, 'YYYY-MM-HH HH24:MI:SS') from dual ";
		String rtnValue = jdbc.queryForObject(sql, String.class);
		return rtnValue;
	}
}
