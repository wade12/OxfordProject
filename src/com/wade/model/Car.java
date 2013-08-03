package com.wade.model;

public class Car
{
	private int id;
	private String make;
	private String model;
	private String colour;
	

	public int getId()
	{
		return id;
	} // end method getId


	public void setId(int id)
	{
		this.id = id;
	} // end method setId


	public String getMake()
	{
		return make;
	} // end method getMake
	

	public void setMake(String make)
	{
		this.make = make;
	} // end method setMake
	
	public String getModel()
	{
		return model;
	} // end method getModel
	

	public void setModel(String model)
	{
		this.model = model;
	} // end method setModel
	
	
	public String getColour()
	{
		return colour;
	} // end method getColour
	

	public void setColour(String colour)
	{
		this.colour = colour;
	} // end method setColour
	
	
	public Car(int carId, String make, String model, String colour)
	{
		setId(carId);
		setMake(make);
		setModel(model);
		setColour(colour);
	} // end 4-argument constructor


	public Car()
	{
	} // end constructor
	
} // end Class Car
