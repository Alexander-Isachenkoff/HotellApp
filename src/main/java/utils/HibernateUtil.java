package utils;

import entities.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil
{
	private static SessionFactory sessionFactory;

	private HibernateUtil() {}

	public static SessionFactory getSessionFactory() {
		sessionLazyInit();
		return sessionFactory;
	}

	private static void sessionLazyInit() {
		if (sessionFactory == null) {
			Configuration configuration = new Configuration().configure();
			configuration.addAnnotatedClass(Sex.class);
			configuration.addAnnotatedClass(Guest.class);
			configuration.addAnnotatedClass(Service.class);
			configuration.addAnnotatedClass(ServiceProvided.class);
			configuration.addAnnotatedClass(Registration.class);
			configuration.addAnnotatedClass(Room.class);
			configuration.addAnnotatedClass(Category.class);
			StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
			sessionFactory = configuration.buildSessionFactory(builder.build());
		}
	}
}