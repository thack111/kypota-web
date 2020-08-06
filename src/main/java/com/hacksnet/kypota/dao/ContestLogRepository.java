package com.hacksnet.kypota.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.hacksnet.kypota.model.ContestLog;
import com.hacksnet.kypota.model.LogQso;

@Repository
public class ContestLogRepository {
	
	private JdbcTemplate jdbc;
	private NamedParameterJdbcTemplate namedJdbc;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbc = new JdbcTemplate(dataSource);
		this.namedJdbc = new NamedParameterJdbcTemplate(dataSource);
	}	
	
	
	public int addLog (ContestLog log) {
		// just placeholder.. need actual implementation.
		
		String sqlTicket = " select  kypota.log_seq.nextval as ticket_id from dual ";
		int logId = jdbc.queryForObject(sqlTicket, Integer.class);
		log.setLogId(logId);
		
		String sql = "insert into kypota.logs (log_id, entered_on, log_type, format, location, callsign, club, contest, " +
						"   cat_operator, cat_assisted, cat_band, cat_mode, cat_power, cat_station, cat_transmitter, " +
						"   claimed_score, operators, name, address, city, state, zip, country, email, grid_loc, soap_box, " +
						"   time_start, time_end, raw_log) "+
				     "values  (:log_id, sysdate, :log_type, :format, :location, :callsign, :club, :contest, " + 
				        "   :cat_operator, :cat_assisted, :cat_band, :cat_mode, :cat_power, :cat_station, :cat_transmitter, " + 
				        "   :claimed_score, :operators, :name, :address, :city, :state, :zip, :country, :email, :grid_loc, :soap_box, " + 
				        "   :time_start, :time_end, :raw_log)";
		SqlParameterSource namedParam = new MapSqlParameterSource().addValue("log_id", log.getLogId())
																	.addValue("log_type", log.getLogType())
																	.addValue("format", log.getFormat())
																	.addValue("location", log.getLocation())
																	.addValue("callsign", log.getCallsign())
																	.addValue("club", log.getClub())
																	.addValue("contest", log.getContest())
																	.addValue("cat_operator", log.getCatOperator())
																	.addValue("cat_assisted", log.getCatAssisted())
																	.addValue("cat_band", log.getCatBand())
																	.addValue("cat_mode", log.getCatMode())
																	.addValue("cat_power", log.getCatPower())
																	.addValue("cat_station", log.getCatStation())
																	.addValue("cat_transmitter", log.getCatTransmitter())
																	.addValue("claimed_score", log.getClaimedScore())
																	.addValue("operators", log.getOperators())
																	.addValue("name", log.getName())
																	.addValue("address", log.getAddress())
																	.addValue("city", log.getCity())
																	.addValue("state", log.getState())
																	.addValue("zip", log.getZip())
																	.addValue("country", log.getCountry())
																	.addValue("email", log.getEmail())
																	.addValue("grid_loc", log.getGridLoc())
																	.addValue("soap_box", log.getSoapBox())
																	.addValue("time_start", log.getTimeStart())
																	.addValue("time_end", log.getTimeEnd())
																	.addValue("raw_log", log.getRawLog());

		int logAdded =namedJdbc.update(sql, namedParam);
		
		for (LogQso qso : log.getQsos()) {
			
			
			String qsoSql = "insert into kypota.qsos (log_id, qso_id, qso_mode, qso_date, snt_call, snt_rst, snt_exch, rcv_call, rcv_rst, rcv_exch, freq, transmitter_id)" + 
					        "values  (:log_id, kypota.qso_seq.nextval, :qso_mode, to_date(:qso_date, 'YYYY-MM-DD HH24MI'), :snt_call, :snt_rst, :snt_exch, :rcv_call, :rcv_rst, :rcv_exch, :freq, :transmitter_id)";
			SqlParameterSource namedParamQso = new MapSqlParameterSource().addValue("log_id", log.getLogId())
																		.addValue("qso_mode", qso.getQsoMode())
																		.addValue("qso_date", qso.getQsoDate())
																		.addValue("snt_call", qso.getSntCall())
																		.addValue("snt_rst", qso.getSntRst())
																		.addValue("snt_exch", qso.getSntExch())
																		.addValue("rcv_call", qso.getRcvCall())
																		.addValue("rcv_rst", qso.getRcvRst())
																		.addValue("rcv_exch", qso.getRcvExch())
																		.addValue("freq", qso.getFreq())
																		.addValue("transmitter_id", qso.getTransmitterId());
			int qsoUpdated = namedJdbc.update(qsoSql, namedParamQso);
		}
		return logAdded;
	}
}
