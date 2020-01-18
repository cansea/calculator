package com.oyous.cal.dao.jpa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.oyous.cal.dao.GenericDao;

public class GenericDaoJpa<T, PK extends Serializable> implements GenericDao<T, PK> {
	protected final Log log = LogFactory.getLog(getClass());
	private Class<T> persistentClass;
	@PersistenceContext
	EntityManager entityManager;
	
	public void setPersistentClass(Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}

	public T get(PK id) {
		T entity = (T) entityManager.find(persistentClass, id);
		
		if (entity == null) {
			log.warn("Uh oh, '" + this.persistentClass + "' object with id '" + id + "' not found...");
			throw new ObjectRetrievalFailureException(this.persistentClass, id);
		}
		
		return entity;
	}
	
	public List<T> findAll() {
		return entityManager.createQuery("from " + persistentClass.getName()).getResultList();
	}
	
	public T save(T object) {
		//entityManager.persist(object);
		return entityManager.merge(object);
	}

	public void remove(T object) {
		entityManager.remove(object);
	}

	public void remove(PK id) {
		T entity = (T) get(id);
		remove(entity);
	}
}
