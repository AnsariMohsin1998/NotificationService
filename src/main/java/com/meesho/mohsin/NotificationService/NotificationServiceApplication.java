package com.meesho.mohsin.NotificationService;

import com.meesho.mohsin.NotificationService.model.SmsRequests;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.EntityManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class NotificationServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(NotificationServiceApplication.class, args);
		System.out.println("Notification Service");

	}

}


// DEMO CODE TO INSERT INTO MYSQL DATABASE BY USING HIBERNATE PROGRAMATICALLY


//		SmsRequests smsRequest = new SmsRequests();
//		smsRequest.setPhone_number("8962573270");
//		smsRequest.setMessage("Hello Mohsin Broooo");
//		smsRequest.setStatus("demo status 3");
//
//		Date date = new Date();
//		System.out.printf(String.valueOf(date));
//
//		smsRequest.setCreated_at(date);
//
//		Configuration configuration = new Configuration();
//		configuration.addAnnotatedClass(SmsRequests.class);
//
//		Map<String,String> dbSettings = new HashMap<>();
//		dbSettings.put(Environment.URL,"jdbc:mysql://localhost:3306/notif_service");
//		dbSettings.put(Environment.USER,"root");
//		dbSettings.put(Environment.PASS,"Mohsin@mysql12");
//		dbSettings.put(Environment.DRIVER,"com.mysql.cj.jdbc.Driver");
//		dbSettings.put(Environment.DIALECT,"org.hibernate.dialect.MySQL55Dialect");
//
//		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(dbSettings).build();
//
//		SessionFactory sf = configuration.buildSessionFactory(serviceRegistry);
//
//		Session session = sf.openSession();
//		session.beginTransaction();
//
//		session.save(smsRequest);
//
//		session.getTransaction().commit();
//		session.close();