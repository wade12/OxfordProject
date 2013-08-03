package com.wade;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;

import com.wade.dao.JdbcDaoImpl;
import com.wade.dao.SimpleJdbcDaoImpl;
import com.wade.model.Car;

public class JdbcSpring
{
	public static void main(String[] args)
	{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		SimpleJdbcDaoImpl dao = ctx.getBean("simpleJdbcDaoImpl", SimpleJdbcDaoImpl.class);
		
		// System.out.println(dao.getAllCars().size());
		System.out.println(dao.getCarCount());

	} // end method main

} // end Class JdbcSpring
