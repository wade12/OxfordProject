package com.wade.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;

import com.wade.model.Car;

// this class is responsible for talking to the database and getting the data
@Component
public class JdbcDaoImpl
{
	// @Autowired
	private DataSource dataSource;
	// private JdbcTemplate jdbcTemplate = new JdbcTemplate();
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	// private SimpleJdbcTemplate simpleJdbcTemplate;
	
	
	public DataSource getDataSource()
	{
		return dataSource;
	} // end method getDataSource
	

	@Autowired
	public void setDataSource(DataSource dataSource)
	{
		// this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	} // end method setDataSource
	

	public JdbcTemplate getJdbcTemplate()
	{
		return jdbcTemplate;
	} // end method getJdbcTemplate


	public void setJdbcTemplate(JdbcTemplate jdbcTemplate)
	{
		this.jdbcTemplate = jdbcTemplate;
	} // end method setJdbcTemplate
	
	
/*	public Car getCar(int carId)
	{
		// 1st thing needed is a connection object
		Connection conn = null;
		
		try
		{
		// create a connection
		conn = dataSource.getConnection();
			
		// create a prepared statement, then set id value
		// pass SQL query in PreparedStatement
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM car where id = ?");
		// set the id
		ps.setInt(1, carId);
			
		// initialise a car object to assign the result to the car object
		Car car = null;
		ResultSet rs = ps.executeQuery();
			
		// if there is a record then assign that to a new car
		if (rs.next())
		{
			car = new Cart(carId, rs.getString("make"), rs.getString("model"), rs.getString("colour"));
		} // end if
			
		// close the RecordSet
		rs.close();
			
		// close the PreparedStatement
		ps.close();
			
		return car;
		} // end try
		
		catch (Exception exception)
		{
			throw new RuntimeException(exception);
		} // end catch
		
		finally
		{
			try
			{
				// close the connection
				conn.close();
			}
			catch (SQLException exception)
			{
			} // end catch
		} //  end finally block
					
	} // end method getCar
*/
	
	// this method gets the total number of records in the car table
	public int getCarCount()
	{
		String sql = "SELECT COUNT(*) FROM car";
		// jdbcTemplate.setDataSource(getDataSource());
		return jdbcTemplate.queryForInt(sql);
	} // end method getCarCount
	
	
	public String getCarMake(int carId)
	{
		String sql = "SELECT MAKE FROM car WHERE ID = ?";
		return jdbcTemplate.queryForObject(sql, new Object[] {carId}, String.class);
		// Object[] is a 1-element array
		} // end method getCarMake
	
	public String getCarModel(int carId)
	{
		String sql = "SELECT MODEL FROM car WHERE ID = ?";
		return jdbcTemplate.queryForObject(sql, new Object[] {carId}, String.class);
		// Object[] is a 1-element array
		} // end method getCarModel
	
	public String getCarColour(int carId)
	{
		String sql = "SELECT COLOUR FROM car WHERE ID = ?";
		return jdbcTemplate.queryForObject(sql, new Object[] {carId}, String.class);
		// Object[] is a 1-element array
		} // end method getCarColour
	
	
	// method returns the object itself directly
	public Car getCarForId(int carId)
	{
		String sql = "SELECT * FROM car WHERE ID = ?";
		return jdbcTemplate.queryForObject(sql, new Object[] {carId}, new CarMapper());
	} // end method getCarForId
	
	
	// method returns a list of own objects
	public List<Car> getAllCars()
	{
		String sql = "SELECT * FROM car";
		return jdbcTemplate.query(sql, new CarMapper());
	} // end method getAllCars
	
	/*
	// insert a record into the table, using the jdbcTemplate update method
	// passing in the sql query string itself,
	// using an object array to pass in the actual values to be substituted into the "?" placeholders.
	// the values are the properties of the object that is being received as the argument to the method.
	public void insertCar(Car car)
	{
		String sql = "INSERT INTO car (ID, NAME) VALUES (?, ?)";
		jdbcTemplate.update(sql, new Object[] {car.getId(), car.getMake(), car.getModel(), car.getColour()});
	} // end method insertCar
	*/
	
	
	public void insertCar(Car car)
	{
		String sql = "INSERT INTO car (ID, NAME) VALUES (:id, :make, :model, :colour)";
		SqlParameterSource namedParameters = new MapSqlParameterSource("id", car.getId()).addValue("make", car.getMake()).addValue("model", car.getModel()).addValue("colour", car.getColour());
		/*
		SqlParameterSource namedParameters = new MapSqlParameterSource(sql, 3);
		namedParameters.put("id", car.getId());
		namedParameters.put("make", car.getMake());
		namedParameters.put("model", car.getModel());
		namedParameters.put("colour", car.getColour());		
        Statement insertCar;
		insertCar.execute(namedParameters);
		*/
		
		// a SqlParameterSource is an interface for an object that can be used to pass parameterSources as a map.
		// MapSqlParameterSource is an implementation of SqlParameterSource, stores values maps.
		namedParameterJdbcTemplate.update(sql, namedParameters);
		
	} // end method insertCar
	
	
	public void createTruckTable()
	{
		String sql = "CREATE TABLE TRUCK (ID INTEGER, MAKE VARCHAR(20), MODEL VARCHAR(20), COLOUR VARCHAR(20))";
		jdbcTemplate.execute(sql);
	} // end method createTruckTable
	
	
	// inner class
	// create own implementation of the RowMapper
	private static final class CarMapper implements RowMapper<Car>
	{
		@Override
		// this method gets called for every record in the result set
		// that the jdbcTemplate gets when it executes the sql query that was passed.
		public Car mapRow(ResultSet resultSet, int rowNum) throws SQLException
		{
			Car car = new Car();
			car.setId(resultSet.getInt("ID"));
			car.setMake(resultSet.getString("MAKE"));
			car.setModel(resultSet.getString("MODEL"));
			car.setColour(resultSet.getString("COLOUR"));
			return car;
		} // end method mapRow
		
	} // end inner Class CarMapper

} // end Class jdbcDaoImpl
