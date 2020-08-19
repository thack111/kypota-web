package com.hacksnet.kypota.dao;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.hacksnet.kypota.model.User;

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
	
	public boolean storeNewUser(User user) {
			
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

		namedJdbc.update(sql, namedParam);
		
		String authSql = "insert into authorities " + 
						  "values(username, 'ROLE_USER')";
		SqlParameterSource authNamedParam = new MapSqlParameterSource().addValue("username", user.getUser());
		namedJdbc.update(authSql, authNamedParam);
		
		return true;
	}
	
	public List<User> getAllUsers() {
		String sql = "select  username, password, enabled, email, " +
					"         case when (select count(*) from kypota.authorities where username = u.username and authority = 'ROLE_ADMIN') > 0 then 'Y' else 'N' end as admin " +
					" from    kypota.users u " +
				    " order by username asc ";
		return jdbc.query(sql, 
				new RowMapper<User>() {
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setUser(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setEnabled(rs.getString("enabled"));
				user.setEmail(rs.getString("email"));
				user.setAdminFlag(rs.getString("admin"));
				
				return user;
			}
		});
	}
	
	public boolean updateUser(User user)	{
		String sql = "update kypota.users " +
				     "set 	email = :email " +
					 "where username = :username ";
		SqlParameterSource namedParam = new MapSqlParameterSource().addValue("username", user.getUser())
																   .addValue("email", user.getEmail());
	
		namedJdbc.update(sql, namedParam);
		
		if (user.getAdminFlag().equals("N")) {
			String updSql = "delete " +
					        "from   kypota.authorities " +
						    "where  username = :username and authority = 'ROLE_ADMIN' ";
			SqlParameterSource updParam = new MapSqlParameterSource().addValue("username", user.getUser());
			namedJdbc.update(updSql, updParam);
		}
		else {
			String updSql = "merge into kypota.authorities a  " +
					        "	using (select   :username as username, 'ROLE_ADMIN' as authority from dual ) s " +
					        " on (a.username = s.username and a.authority = s.authority ) " +
						    "when not matched then " + 
					        "insert (username, authority) " +
						    "values (s.username, s.authority ) ";
			SqlParameterSource updParam = new MapSqlParameterSource().addValue("username", user.getUser());
		
			namedJdbc.update(updSql, updParam);
		}
		return true;
	}
	
	public boolean deleteUser(String username)	{
		String sql = "delete  " +
				     "from   kypota.users " +
					 "where  username = :username ";
		SqlParameterSource namedParam = new MapSqlParameterSource().addValue("username", username);
	
		namedJdbc.update(sql, namedParam);

		String updSql = "delete " +
				        "from   kypota.authorities " +
					    "where  username = :username";
		SqlParameterSource updParam = new MapSqlParameterSource().addValue("username", username);
		namedJdbc.update(updSql, updParam);
		
		return true;
	}
}
