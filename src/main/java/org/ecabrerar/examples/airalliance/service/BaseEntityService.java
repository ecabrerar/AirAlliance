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

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.ecabrerar.examples.airalliance.entities.BaseEntity;

/**
 *
 * @author ecabrerar
 *
 */
public abstract class BaseEntityService {

    protected abstract EntityManager getEntityManager();

    public <T extends BaseEntity> T create(T entity) {
        getEntityManager().persist(entity);
        return entity;
    }

    public <T extends BaseEntity> void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public <T extends BaseEntity> void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public <T extends BaseEntity, ID extends Serializable> T find(Class<T> entityClass, ID id) {
        return getEntityManager().find(entityClass, id);
    }

    public <T extends BaseEntity> List<T> findAll(Class<T> entityClass) {
         final CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
         final CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
           Root<T> root = criteriaQuery.from(entityClass);
           criteriaQuery.select(root);

          TypedQuery<T> query = getEntityManager().createQuery(criteriaQuery);

        return query.getResultList();
    }

    public <T extends BaseEntity> List<T> findRange(Class<T> entityClass, int[] range) {
        CriteriaQuery<T> cq = getEntityManager().getCriteriaBuilder().createQuery(entityClass);
        cq.select(cq.from(entityClass));

        TypedQuery<T> q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);

        return q.getResultList();
    }

    public <T extends BaseEntity> int count(Class<T> entityClass) {
        CriteriaQuery<T> cq = getEntityManager().getCriteriaBuilder().createQuery(entityClass);
        Root<T> rt = cq.from(entityClass);
        cq.select(rt);
        Query q = getEntityManager().createQuery(cq);
        return q.getResultList().size();
    }

}
