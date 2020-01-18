/**
 * 
 */
package com.oyous.cal.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.type.Type;

/**
 * Generic Manager that talks to GenericDao to CRUD POJOs.
 *
 * @author Joe
 * @param <T> a type variable
 * @param <PK> the primary key for that type
 */
public interface GenericManager<T, PK extends Serializable> {
	/**
	 * Get single object
	 * 
	 * @param id
	 * @return
	 */
	T get(PK id);
	T save(T object);
	List<T> findAll();
	void remove(T object);
	void remove(PK id);
}
