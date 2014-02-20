package infra.dao;

import infra.filter.Filter;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import exceptions.DaoException;
import exceptions.NonUniqueReferenceException;
import exceptions.ObjectExistsException;
import exceptions.ObjectNotFoundException;

public abstract class AbstractDao<T, K> implements Dao<T, K> {

    protected Logger logger = LoggerFactory.getLogger(AbstractDao.class);

    protected abstract SessionFactory getSessionFactory();

    protected abstract Class<T> getEntityKlass();

    private Map<String, String> params;

    protected Session getSession() {
        Session oSession = getSessionFactory().getCurrentSession();
        return oSession;
    }

    @SuppressWarnings("unchecked")
    public T findByKey(K key) throws DaoException, ObjectNotFoundException {
        Session sessao = getSession();
        T elemento = (T) sessao.get(getEntityKlass(), (Serializable) key);
        if (elemento == null) {
            throw new ObjectNotFoundException("erro.dao.naoEncontrado."
                    + getEntityKlass().getName(), getEntityKlass().getName(), key, this.getClass()
                    .getName());
        }
        return elemento;
    }

    @SuppressWarnings("unchecked")
    public T loadByKey(K key) throws DaoException, ObjectNotFoundException {
        Session sessao = getSession();
        T elemento = (T) sessao.load(getEntityKlass(), (Serializable) key);
        if (elemento == null) {
            throw new ObjectNotFoundException("erro.dao.naoEncontrado."
                    + getEntityKlass().getName(), getEntityKlass().getName(), key, this.getClass()
                    .getName());
        }

        refreshEntity(elemento);

        return elemento;
    }

    public void refreshEntity(T t) {
        getSession().flush();
        getSession().refresh(t);
    }

    public String getConfigParam(String paramName) {
        return this.params.get(paramName);
    }

    @SuppressWarnings("unchecked")
    public Collection<T> findAll() {
        return getSession().createCriteria(this.getEntityKlass()).list();
    }

    public void update(T x) throws DaoException, ObjectNotFoundException {
        Session sessao = getSession();
        try {
            sessao.update(x);
            sessao.flush();
        } catch (HibernateException e) {
            throw new DaoException(e, "erro.dao", x);
        } catch (Exception e) {
            throw new DaoException(e, "erro.dao.inesperado", x);
        }
    }

    @SuppressWarnings("unchecked")
    public K insert(T x) throws DaoException, ObjectExistsException {
        K k;

        try {
            Session sessao = getSession();
            k = (K) sessao.save(x);
            sessao.flush();
        } catch (javax.validation.ConstraintViolationException e) {

            Set<ConstraintViolation<?>> constraints = e.getConstraintViolations();
            ConstraintViolation<?> firstConstraint = constraints.iterator().next();
            String message = firstConstraint.getPropertyPath() + " " + firstConstraint.getMessage();

            throw new DaoException(e, message, x);

        } catch (ConstraintViolationException e) {
            throw new DaoException(e, "erro.dao.constraint." + e.getConstraintName(), x);
        } catch (HibernateException e) {
            throw new DaoException(e, "erro.dao", x);
        } catch (Exception e) {
            throw new DaoException(e, "erro.dao.inesperado", x);
        }
        return k;
    }

    public void remove(K key) throws DaoException, ObjectNotFoundException {
        Session sessao = getSession();
        try {
            T elementoRemocao = this.findByKey(key);
            sessao.delete(elementoRemocao);
            sessao.flush();
        } catch (ConstraintViolationException e) {
            throw new DaoException(e, "erro.dao.constraint." + e.getConstraintName(), key);
        } catch (HibernateException e) {
            throw new DaoException(e, "erro.dao", key);
        } catch (Exception e) {
            throw new DaoException(e, "erro.dao.inesperado", key);
        }

    }

    public void removeElement(T objeto) throws DaoException, ObjectNotFoundException {
        Session sessao = getSession();
        try {
            sessao.delete(objeto);
            sessao.flush();
        } catch (ConstraintViolationException e) {
            throw new DaoException(e, "erro.dao.constraint." + e.getConstraintName(), objeto);
        } catch (HibernateException e) {
            throw new DaoException(e, "erro.dao", objeto);
        } catch (Exception e) {
            throw new DaoException(e, "erro.dao.inesperado", objeto);
        }
    }

    @SuppressWarnings("deprecation")
    public void lock(Object object) {
        Session session = getSession();
        try {
            session.lock(object, LockMode.NONE);
        } catch (Exception e) {
            // e.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    public void lockForUpdate(Object object) {
        Session session = getSession();
        try {
            session.lock(object, LockMode.UPGRADE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    public void unlockObject(Object object) {
        Session session = getSession();
        try {
            session.lock(object, LockMode.NONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public T findUniqueByFilter(Filter<? extends T> filtro) throws NonUniqueReferenceException,
            DaoException {
        T x = null;

        Collection<? extends T> colecao = this.findByFilter(filtro);

        final int quantos = colecao.size();
        if (quantos == 1) {
            x = colecao.iterator().next();
        } else if (quantos > 1) {
            throw new NonUniqueReferenceException("erro.dao.naoUnico");
        }

        return x;
    }

    public boolean existsKey(K k) throws DaoException {
        Session sessao = getSession();
        return sessao.get(getEntityKlass(), (Serializable) k) != null;
    }

    public Long getSequenceNextVal(String sequence) throws DaoException {
        Object val = null;

        try {

            Dialect hsql = ((SessionFactoryImplementor) getSessionFactory()).getDialect();

            // executa uma native query de acordo com o select q o dialect me retornou
            val =
                    getSession().createSQLQuery(hsql.getSequenceNextValString(sequence)).list()
                            .get(0);

        } catch (Exception e) {
            throw new DaoException("erro.dao.sequence", sequence);
        }

        return ((Number) val).longValue();
    }
}
