package com.hacksnet.kypota.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.hacksnet.kypota.model.ContestYear;
import com.hacksnet.kypota.model.ResultsSummary;

@Repository
public class ResultsRepository {
	
	private JdbcTemplate jdbc;
	//private NamedParameterJdbcTemplate namedJdbc;
	private SimpleJdbcCall jdbcCall;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbc = new JdbcTemplate(dataSource);
		//this.namedJdbc = new NamedParameterJdbcTemplate(dataSource);
		this.jdbcCall = new SimpleJdbcCall(dataSource);
	}	
	
	
	public List<ResultsSummary> getResults (String type, String resultsYear) {
		// just placeholder.. need actual implementation.

		String whereCri = "where  trunc(entered_on, 'YYYY') = trunc(to_date('"+resultsYear+"', 'YYYY'), 'YYYY') ";
		if (type.equals("Hunters")) {
			whereCri += "and l.park_abbr = 'Hunter' and l.operators not in ('K4MSU','K4Y','W4GZ') ";
		}
		else if (type.equals("Parks")) {
			whereCri += "and l.park_abbr != 'Hunter' and l.operators not in ('K4MSU','K4Y','W4GZ') ";
		}
		
		String sql = "select  l.log_id, l.submitted_name, l.submitted_email, l.park_abbr, " + 
		 			 "        q.num_qso_points, q.num_p2p,  q.num_bonus, q.num_parks, " + 
	 				 "        (nvl(num_qso_points, 0) + (nvl(num_bonus, 0) * 3)) * case when q.num_parks > 1 then q.num_parks else 1 end as total_score " + 
					 "from    kypota.logs l " + 
					 "    left outer join (select log_id, " + 
					 "                            count(*) as num_qso_points," + 
					 "                            count( distinct park_abbr) as num_parks, " +
					 "                            sum(p2p) as num_p2p, " + 
					 "                            sum(bonus_stn) as num_bonus " + 
					 "                    from    kypota.qsos " + 
					 "                    where   dup = 0 " +  
					 "                    group by log_id) q on l.log_id = q.log_id " + 
					 whereCri +
					 "order by (nvl(num_qso_points, 0) + (nvl(num_bonus, 0) * 3)) * case when q.num_parks > 1 then q.num_parks else 1 end desc";
		if (type.matches("Hunters|Parks")) {
			sql = "select log_id, submitted_name, submitted_email, park_abbr, num_qso_points, num_p2p, num_bonus, num_parks, total_score " +
				  "from  (" + sql + ") where rownum < 6 ";
		}
		
		return jdbc.query(sql, 
				new RowMapper<ResultsSummary>() {
			public ResultsSummary mapRow(ResultSet rs, int rowNum) throws SQLException {
				ResultsSummary resSum = new ResultsSummary();
				resSum.setLogId(rs.getInt(1));
				resSum.setSubmittedName(rs.getString(2));
				resSum.setSubmittedEmail(rs.getString(3));
				resSum.setParkWorked(rs.getString(4));
				resSum.setNumQsoPoints(rs.getInt(5));
				resSum.setNumPark2Park(rs.getInt(6));
				resSum.setNumBonus(rs.getInt(7));
				resSum.setNumParks(rs.getInt(8));
				resSum.setTotalScore(rs.getInt(9));
				return resSum;
			}
		});
	}
	
	public List<ContestYear> getContestYears () {
		
		String sql = String.join(" ", 
				"select to_char(trunc(entered_on, 'YYYY'), 'YYYY') as year ", 
				"from 	kypota.logs  where entered_on is not null ",
				"group by trunc(entered_on, 'YYYY') ",
				"order by trunc(entered_on, 'YYYY') ");
		return jdbc.query(sql, 
				new RowMapper<ContestYear>() {
			public ContestYear mapRow(ResultSet rs, int rowNum) throws SQLException {
				ContestYear year = new ContestYear();
				year.setYear(rs.getString("year"));
				return year;
			}
		});
	}
	
	public int performAssesment() {
		jdbcCall.withProcedureName("ANALYZE_RESULTS");
		SqlParameterSource in = new MapSqlParameterSource().addValue("IN_CONTEST_YEAR", "2021");
		jdbcCall.execute(in);

		return 1;
	}
}
