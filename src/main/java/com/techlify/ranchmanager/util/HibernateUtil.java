package com.techlify.ranchmanager.util;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author kamal
 *
 */
public class HibernateUtil {

	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			return new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void shutdown() {
		// Close caches and connection pools
		getSessionFactory().close();
	}

	/**
	 * @param object
	 */
	public static boolean addObjectToDatabase(Object object) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Serializable save = session.save(object);
		session.getTransaction().commit();
		if (save != null) {
			return true;
		}
		return false;
	}

	/**
	 * @param object
	 */
	public static boolean updateObjectToDatabase(Object object) {
		try{
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.saveOrUpdate(object);
		session.getTransaction().commit();
		return true;
		} catch (Exception e){
			return false;
		}
		
	}
	
	/**
	 * @param object
	 */
	public static Object getObjectFromDatabase(Class c, Long id) {
		try{
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			return session.get(c, id);
		} catch (Exception e){
			return null;
		}
		
	}
	
	/**
	 * @return
	 */
	public static Session getSession() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		if(session!=null){
			session = HibernateUtil.getSessionFactory().openSession();
		}
		return session;
	}

}