package com.wade.dao;

import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

@SuppressWarnings("deprecation")
public class SimpleJdbcDaoImpl extends SimpleJdbcDaoSupport
{
		// this method gets the total number of records in the car table
		public int getCarCount()
		{
			String sql = "SELECT COUNT(*) FROM car";
			// jdbcTemplate.setDataSource(getDataSource());
			return this.getJdbcTemplate().queryForInt(sql);
		} // end method getCarCount
		
} // end Class SimpleJdbcDaoImpl
