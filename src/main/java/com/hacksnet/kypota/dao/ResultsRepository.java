package com.hacksnet.kypota.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.hacksnet.kypota.model.ResultsSummary;

@Repository
public class ResultsRepository {
	
	DataSource dataSource;
	private JdbcTemplate jdbc;
	//private NamedParameterJdbcTemplate namedJdbc;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbc = new JdbcTemplate(dataSource);
		//this.namedJdbc = new NamedParameterJdbcTemplate(dataSource);
	}	
	
	
	public List<ResultsSummary> getResults () {
		// just placeholder.. need actual implementation.
		
		
		String sql = "select  l.log_id, l.submitted_name, l.submitted_email, " + 
		 			 "        q.num_qso_points, q.num_p2p,  q.num_bonus, " + 
	 				 "        (nvl(num_qso_points, 0) + nvl(num_bonus, 0)) * case when num_p2p > 1 then num_p2p else 1 end as total_score " + 
					 "from    kypota.logs l " + 
					 "    left outer join (select log_id, " + 
					 "                            count(*) as num_qso_points," + 
					 "                            sum(p2p) as num_p2p, " + 
					 "                            sum(bonus_stn) as num_bonus " + 
					 "                    from    kypota.qsos " + 
					 "                    where   dup = 0 " +  
					 "                    group by log_id) q on l.log_id = q.log_id " + 
					 "order by (nvl(num_qso_points, 0) + nvl(num_bonus, 0)) * case when num_p2p > 1 then num_p2p else 1 end desc";
		return jdbc.query(sql, 
				new RowMapper<ResultsSummary>() {
			public ResultsSummary mapRow(ResultSet rs, int rowNum) throws SQLException {
				ResultsSummary resSum = new ResultsSummary();
				resSum.setLogId(rs.getInt(1));
				resSum.setSubmittedName(rs.getString(2));
				resSum.setSubmittedEmail(rs.getString(3));
				resSum.setNumQsoPoints(rs.getInt(4));
				resSum.setNumPark2Park(rs.getInt(5));
				resSum.setNumBonus(rs.getInt(6));
				resSum.setTotalScore(rs.getInt(7));
				return resSum;
			}
		});
	}
	
	
	public int performAssesment() {
		SimpleJdbcCall jdbcCall = new 
		         SimpleJdbcCall(dataSource).withProcedureName("ANALYZE_RESULTS");

		SqlParameterSource in = new MapSqlParameterSource().addValue("IN_CONTEST_YEAR", "2020");
		jdbcCall.execute(in);
//		String sqlClearDup = "update kypota.qsos " +
//							 "set dup = 0, " +
//							 "    band = case when freq between '1800' and '2000' then '1800' " + 
//							 "                when freq between '3500' and '4000' then '3500' " + 
//							 "                when freq between '7000' and '7300' then '7000' " + 
//							 "                when freq between '14000' and '14350' then '14000' " + 
//							 "                when freq between '21000' and '21450' then '21000' " + 
//							 "                when freq between '28000' and '29700' then '28000' " + 
//							 "                else freq end ";
//		jdbc.update(sqlClearDup);
//		
//		String sqlSetDup = " merge into  kypota.qsos q using " + 
//							"(	select 	log_id, qso_mode, rcv_call,  " + 
//							"			band, min(qso_id) as low_qso_id, count(*) as num_cont " + 
//							"	from 	kypota.qsos " + 
//							"	group by log_id, qso_mode, rcv_call, band " + 
//							"	having count(*) > 1 " + 
//							") d on (q.log_id = d.log_id " + 
//							"	and q.qso_mode = d.qso_mode " + 
//							"	and q.rcv_call = d.rcv_call " + 
//							"	and q.band = d.band  " + 
//							"	and q.qso_id > d.low_qso_id) " + 
//							"when matched then update " + 
//							"set dup = 1 ";
//		jdbc.update(sqlSetDup);
//		
//		String sqlSetP2P = " update kypota.qsos " + 
//							"set p2p = 1 " + 
//							"where qso_id in (   select l.qso_id " + 
//							"                    from kypota.qsos l " + 
//							"                    inner join kypota.qsos p on l.snt_call = p.rcv_call  " + 
//							"                                            and l.rcv_call = p.snt_call " + 
//							"                                            and l.qso_date between p.qso_date - (1/24/60*10) and p.qso_date + (1/24/60*10) " + 
//							"                                            and l.band = p.band  ) ";
//	
//		jdbc.update(sqlSetP2P);
		
		return 1;
	}
}
