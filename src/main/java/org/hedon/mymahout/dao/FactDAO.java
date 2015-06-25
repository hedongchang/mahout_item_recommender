package org.hedon.mymahout.dao;

import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class FactDAO {
	
	private static final String driverClassName = "org.postgresql.Driver";
    private static final String url = "jdbc:postgresql://greenplum.dsapp.dc2.dsghost.net:5432/fmd3";
    private static final String dbUsername = "dmo";
    private static final String dbPassword = "dmo";
    
    static DataSource dataSource = getDataSource();
	static JdbcTemplate template = new JdbcTemplate(dataSource);
	
	public static TreeMap<String, String> selectVinWebId(Set<String> vins) {
		TreeMap<String, String> vinWebId = new TreeMap<String, String>();
    	/*String insert = "INSERT INTO inv_intern_dim_visitor_vin_pair_num " +
    			"(global_visitor_id, vin) VALUES (?, ?)";*/
		String prefix = "SELECT DISTINCT inv_intern_dim_securable_items.web_id "
				+ "FROM inv_intern_fact_vehicle_events "
				+ "INNER JOIN inv_intern_dim_securable_items "
				+ "ON inv_intern_fact_vehicle_events.dim_site_key = inv_intern_dim_securable_items.dim_securable_item_key "
				+ "WHERE inv_intern_fact_vehicle_events.vin = '";
		int count = 0;
		for (String vin: vins) {
			count++;
			if (count > 100) {
				break;
			}
			String sql = prefix;
			sql = sql + vin + "';";
	    	List<InvOwner> result = template.query(sql, new InvOwnerMapper());
	    	vinWebId.put(vin, result.get(0).toString());
	    	//System.out.println(result.get(0));
		}
		return vinWebId;
    	//template.update(sql, new Object[]{pair.getFirst(), pair.getSecond()});
	}
	
	private static DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);
        return (DataSource) dataSource;
    }
}
