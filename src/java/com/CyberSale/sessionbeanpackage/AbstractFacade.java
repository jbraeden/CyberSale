/*
 * Created by Joseph Sebastian on 2016.04.12  * 
 * Copyright © 2016 Patrick Abod. All rights reserved. * 
 */
package com.CyberSale.sessionbeanpackage;

import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Braeden
 * @author Patrick Abod
 * @author Shawn Amjad
 * 
 * This class is used to abstractly define all facade classes used
 * in the Cyber Sale project.
 * 
 * The following methods are used by all facade classes
 */
public abstract class AbstractFacade<T> {

    /* The entity class field */
    private Class<T> entityClass;

    /**
     * Constructor for a particular entity class
     * @param entityClass the entity class which extends this facade
     */
    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Get the particular entity manager that extends this facade
     * @return the appropriate entity manager
     */
    protected abstract EntityManager getEntityManager();

    /**
     * Creating (persisting) an entity to the DB
     * @param entity the entity object to create
     */
    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    /**
     * Editing (merging) an entity to the DB
     * @param entity the entity to update
     */
    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    /**
     * Removing (deleting) an entity from the DB
     * @param entity the entity to remove
     */
    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    /**
     * Finding (reading) an entity from the DB
     * @param id the id of the object to find
     * @return the found object
     */
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    /**
     * Find all the entities in a particular DB table
     * @return a list of all the entities
     */
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    /**
     * Find all enitiy objects in a particular range
     * @param range the id range array
     * @return the list of objects found in the range
     */
    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    /**
     * Get the number of objects in a particular table
     * @return the number of objects
     */
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
}
