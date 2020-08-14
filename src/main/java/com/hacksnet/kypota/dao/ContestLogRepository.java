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

import com.hacksnet.kypota.model.ContestLog;
import com.hacksnet.kypota.model.LogQso;
import com.hacksnet.kypota.model.Park;

@Repository
public class ContestLogRepository {
	
	private JdbcTemplate jdbc;
	private NamedParameterJdbcTemplate namedJdbc;
	private SimpleJdbcCall jdbcCall;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbc = new JdbcTemplate(dataSource);
		this.namedJdbc = new NamedParameterJdbcTemplate(dataSource);
		this.jdbcCall = new SimpleJdbcCall(dataSource);
	}	
	
	
	public int addLog (ContestLog log) {
		
		String sqlTicket = " select  kypota.log_seq.nextval as ticket_id from dual ";
		int logId = jdbc.queryForObject(sqlTicket, Integer.class);
		log.setLogId(logId);
		
		String sql = "insert into kypota.logs (log_id, entered_on, log_type, format, location, callsign, club, contest, " +
						"   cat_operator, cat_assisted, cat_band, cat_mode, cat_power, cat_station, cat_transmitter, " +
						"   claimed_score, operators, name, address, city, state, zip, country, email, grid_loc, soap_box, " +
						"   time_start, time_end, raw_log, submitted_name, submitted_email, park_abbr) "+
				     "values  (:log_id, sysdate, :log_type, :format, :location, :callsign, :club, :contest, " + 
				        "   :cat_operator, :cat_assisted, :cat_band, :cat_mode, :cat_power, :cat_station, :cat_transmitter, " + 
				        "   :claimed_score, :operators, :name, :address, :city, :state, :zip, :country, :email, :grid_loc, :soap_box, " + 
				        "   :time_start, :time_end, :raw_log, :submitted_name, :submitted_email, :park_abbr)";
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
																	.addValue("raw_log", log.getRawLog())
																	.addValue("submitted_name", log.getSubmittedName())
																	.addValue("submitted_email", log.getSubmittedEmail())
																	.addValue("park_abbr", log.getParkAbbr());

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
		
		jdbcCall.withProcedureName("ANALYZE_RESULTS");
		SqlParameterSource in = new MapSqlParameterSource().addValue("IN_CONTEST_YEAR", "2020");
		jdbcCall.execute(in);
		
		return logAdded;
	}
	

	public ContestLog getLog (int logId) {

		String sql = "select	log_id, entered_on, log_type, format, location, callsign, club, contest, " + 
					 "       	cat_operator, cat_assisted, cat_band, cat_mode, cat_power, cat_station, cat_transmitter, " + 
					 "			claimed_score, operators, name, address, city, state, zip, country, email, grid_loc, soap_box, " + 
					 "			time_start, time_end, raw_log, submitted_name, submitted_email, park_abbr, p.park_name "+ 
					 "from    	kypota.logs l " +
					 "    left outer join kypota.kyparks p on l.park_abbr = p.abbr " +
					 "where	    log_id  = :log_id ";
		SqlParameterSource namedParamLog = new MapSqlParameterSource().addValue("log_id", logId);
		ContestLog log = namedJdbc.queryForObject(sql, 
				namedParamLog,
				new RowMapper<ContestLog>() {
			public ContestLog mapRow(ResultSet rs, int rowNum) throws SQLException {
				ContestLog log = new ContestLog();
				log.setLogId(rs.getInt("log_id"));
				log.setEnteredOn(rs.getString("entered_on"));
				log.setLogType(rs.getString("log_type"));
				log.setFormat(rs.getString("format"));
				log.setLocation(rs.getString("location"));
				log.setCallsign(rs.getString("callsign"));
				log.setClub(rs.getString("club"));
				log.setContest(rs.getString("contest"));
				log.setCatOperator(rs.getString("cat_operator"));
				log.setCatAssisted(rs.getString("cat_assisted"));
				log.setCatBand(rs.getString("cat_band"));
				log.setCatMode(rs.getString("cat_mode"));
				log.setCatPower(rs.getString("cat_power"));
				log.setCatStation(rs.getString("cat_station"));
				log.setCatTransmitter(rs.getString("cat_transmitter"));
				log.setClaimedScore(rs.getString("claimed_score"));
				log.setOperators(rs.getString("operators"));
				log.setName(rs.getString("name"));
				log.setAddress(rs.getString("address"));
				log.setCity(rs.getString("city"));
				log.setState(rs.getString("state"));
				log.setZip(rs.getString("zip"));
				log.setCountry(rs.getString("country"));
				log.setEmail(rs.getString("email")); 
				log.setGridLoc(rs.getString("grid_loc")); 
				log.setSoapBox(rs.getString("soap_box"));
				log.setTimeStart(rs.getString("time_start"));
				log.setTimeEnd(rs.getString("time_end"));
				log.setRawLog(rs.getString("raw_log"));
				log.setSubmittedName(rs.getString("submitted_name")); 
				log.setSubmittedEmail(rs.getString("submitted_email"));
				log.setParkAbbr(rs.getString("park_abbr"));
				log.setParkName(rs.getString("park_name"));

				return log;
			}
		});
		
		String qsoSql = "select	log_id, qso_id, qso_mode, qso_date, snt_call, snt_rst, snt_exch, rcv_call, rcv_rst, rcv_exch, freq, transmitter_id, dup, p2p, bonus_stn, band "+ 
					 "   from    	kypota.qsos q " + 
					 "   where	    log_id  = :log_id ";
		SqlParameterSource namedParamQso = new MapSqlParameterSource().addValue("log_id", logId);
		List<LogQso> qsos = namedJdbc.query(qsoSql, 
				namedParamQso,
				new RowMapper<LogQso>() {
			public LogQso mapRow(ResultSet rs, int rowNum) throws SQLException {
				LogQso qso = new LogQso();
				qso.setLogId(rs.getInt("log_id")); 
				qso.setQsoId(rs.getInt("qso_id")); 
				qso.setQsoMode(rs.getString("qso_mode"));
				qso.setQsoDate(rs.getString("qso_date"));
				qso.setSntCall(rs.getString("snt_call"));
				qso.setSntRst(rs.getString("snt_rst"));
				qso.setSntExch(rs.getString("snt_exch"));
				qso.setRcvCall(rs.getString("rcv_call"));
				qso.setRcvRst(rs.getString("rcv_rst"));
				qso.setRcvExch(rs.getString("rcv_exch"));
				qso.setFreq(rs.getString("freq"));
				qso.setTransmitterId(rs.getString("transmitter_id"));
				qso.setDup(rs.getString("dup")); 
				qso.setPark2park(rs.getString("p2p"));
				qso.setBonus(rs.getString("bonus_stn"));
				qso.setBand(rs.getString("band"));
				
				return qso;
			}
		});
		log.setQsos(qsos);
		return log;
		
	}
	public List<Park> getParkList () {
		
		String sql = "select  abbr, park_name from kypota.kyparks order by abbr asc";
		return jdbc.query(sql, 
				new RowMapper<Park>() {
			public Park mapRow(ResultSet rs, int rowNum) throws SQLException {
				Park park = new Park();
				park.setAbbr(rs.getString("abbr"));
				park.setParkName(rs.getString("park_name"));
				return park;
			}
		});
	}
}
