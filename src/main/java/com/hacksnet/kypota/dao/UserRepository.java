package com.hacksnet.kypota.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.hacksnet.kypota.model.NewUser;

@Repository
public class UserRepository {

	DataSource dataSource;
	private JdbcTemplate jdbc;
	private NamedParameterJdbcTemplate namedJdbc;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbc = new JdbcTemplate(dataSource);
		this.namedJdbc = new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public boolean storeNewUser(NewUser user) {
			
		String sqlTicket = " select  count(*) as user_count from kypota.users where username = :username ";
		SqlParameterSource checkNamedParam = new MapSqlParameterSource().addValue("username", user.getUser());
		int numUsers = namedJdbc.queryForObject(sqlTicket, checkNamedParam, Integer.class);
		
		if (numUsers > 0) {
			return false;
		}
		
		String sql = "insert into kypota.users (username, password, enabled, email)" +
					 "values (:username, :password, :enabled, :email) ";
		SqlParameterSource namedParam = new MapSqlParameterSource().addValue("username", user.getUser())
																   .addValue("password", passwordEncoder.encode(user.getPassword()))
																	.addValue("enabled", "Y")
																	.addValue("email", user.getEmail());

		int logAdded = namedJdbc.update(sql, namedParam);
		return true;
	}
}
