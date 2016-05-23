/*
 * Copyright 2014 ecabrerar.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ecabrerar.examples.airalliance.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author ecabrerar
 * @param <T>
 */
public abstract class BaseEntityService<T> {

    private final Class<T> entityClass;

    @PersistenceContext(unitName = "AirAlliancePU")
    private EntityManager entityManager;


    public BaseEntityService(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        entityManager.merge(entity);
    }

    public void remove(T entity) {
        entityManager.remove(entityManager.merge(entity));
    }

    public T find(Object id) {
        return entityManager.find(entityClass, id);
    }

    public List<T> findAll() {

    	final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    	final CriteriaQuery<T> cq = criteriaBuilder.createQuery(entityClass);
        cq.select(cq.from(entityClass));
        return entityManager.createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
    	final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    	final CriteriaQuery<T> cq = criteriaBuilder.createQuery(entityClass);
    	cq.select(cq.from(entityClass));

    	TypedQuery<T> q = entityManager.createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);

        return q.getResultList();
    }

    public int count() {
    	final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    	final CriteriaQuery<Long> cq = criteriaBuilder.createQuery(Long.class);
        cq.select(criteriaBuilder.count(cq.from(entityClass)));

        return entityManager.createQuery(cq).getSingleResult().intValue();
    }

}
