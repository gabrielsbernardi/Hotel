package br.com.hotel.data;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConnectionDB {
	
	private static EntityManagerFactory manager;
	
	static {
		manager = Persistence.createEntityManagerFactory("public");
		manager.createEntityManager();		
	}
	
	/**
	 * 
	 * @return EntityManager instanciado
	 */
	public static EntityManager getEntityManager() {
		return manager.createEntityManager();
	}
	
}
