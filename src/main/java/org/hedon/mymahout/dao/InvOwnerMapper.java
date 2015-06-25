package org.hedon.mymahout.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class InvOwnerMapper implements RowMapper {
	public InvOwner mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new InvOwner(rs.getString("web_id"));
	}
}
