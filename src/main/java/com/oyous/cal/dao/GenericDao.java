package com.oyous.cal.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.type.Type;


/**
 * Generic DAO (Data Access Object) with common methods to CRUD POJOs.
 *
 * <p>Extend this interface if you want typesafe (no casting necessary) DAO's for your
 * domain objects.
 *
 * @author <a href="mailto:joeone.qian@gmail.com">Joe Qian</a>
 * @param <T> a type variable
 * @param <PK> the primary key for that type
 */
public interface GenericDao <T, PK extends Serializable> {

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