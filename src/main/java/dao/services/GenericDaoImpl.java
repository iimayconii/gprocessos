/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.services;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import org.hibernate.*;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;

/**
 *
 * @author Maycon
 */
public class GenericDaoImpl<T, ID extends Serializable> implements GenericDaoService<T, ID> {

    private Class<T> persistentClass;
    private Session session;

    public GenericDaoImpl() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }

    public GenericDaoImpl(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }

    public Session getSession() {
        if (session == null) {
            throw new IllegalStateException("Session error");
        }
        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        return session;
    }

    public Class<T> getPersistentClass() {
        return persistentClass;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T findById(ID id, boolean lock) {
        T entity = null;
        try {
            if (lock) {
                entity = (T) getSession().load(getPersistentClass(), id, LockOptions.UPGRADE);
            } else {

                entity = (T) getSession().load(getPersistentClass(), id);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return entity;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findAll() {
        return findByCriteria();
    }

    @Override
    public T makePersistent(T entity) {
        getSession().saveOrUpdate(entity);
        return entity;
    }

    @Override
    public void update(T entity) {
        getSession().merge(entity);
    }

    @Override
    public T save(T entity) throws HibernateException {
        try {
            getSession().save(entity);
            return entity;
        } catch (HibernateException e) {
            throw e;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public T makeTransient(T entity) {
        getSession().delete(entity);
        return entity;
    }

    public void flush() {
        getSession().flush();
    }

    public void clear() {
        getSession().clear();
    }

    @SuppressWarnings("unchecked")
    public List<T> findByCriteria(Criterion... criterion) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        for (Criterion c : criterion) {
            criteria.add(c);
        }
        return criteria.list();
    }

    @Override
    public List<T> findAll(int size, int first) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.setFirstResult(first);
        criteria.setMaxResults(size);
        return criteria.list();
    }   

    @Override
    public void saveList(List<T> entities) {
        for (T entity : entities) {
            entity = save(entity);
        }
    }

    @Override
    public int rowCount() {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.setProjection(Projections.rowCount());
        Integer rowCount = ((Long) criteria.uniqueResult()).intValue();
        return rowCount;
    }
}
