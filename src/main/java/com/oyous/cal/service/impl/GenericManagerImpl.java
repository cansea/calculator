package com.oyous.cal.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.type.Type;

import com.oyous.cal.dao.GenericDao;
import com.oyous.cal.service.GenericManager;

/**
 * This class serves as the Base class for all other Managers - namely to hold
 * common CRUD methods that they might all use. You should only need to extend
 * this class when your require custom CRUD logic.
 * 
 * @author Joe
 * 
 * @param <T> a type variable
 * @param <PK> the primary key for that type
 */
public class GenericManagerImpl<T, PK extends Serializable> implements GenericManager<T, PK> {
    protected final Log log = LogFactory.getLog(getClass());
    
    protected com.oyous.cal.dao.GenericDao<T, PK> dao;
    
    public GenericManagerImpl() {}

    public GenericManagerImpl(GenericDao<T, PK> genericDao) {
        this.dao = genericDao;
    }
    
	public T get(PK id) {
		return dao.get(id);
	}
	
	public List<T> findAll() {
		return dao.findAll();
	}

	public T save(T object) {
		return dao.save(object);
	}

	public void remove(T object) {
		dao.remove(object);
	}

	public void remove(PK id) {
		dao.remove(id);
	}

}
