package infra.manager;

import infra.dao.AbstractDaoCriteria;
import infra.filter.Filter;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import exceptions.DaoException;
import exceptions.NonUniqueReferenceException;
import exceptions.ObjectExistsException;
import exceptions.ObjectNotFoundException;
import exceptions.PersistenceManagerException;

public abstract class PersistenceManager<T, K> {

    protected abstract AbstractDaoCriteria<T, K> getDao();

    protected Logger logger = LoggerFactory.getLogger(PersistenceManager.class);

    public Collection<T> findByFilter(Filter<? extends T> filtro)
            throws PersistenceManagerException {

        AbstractDaoCriteria<T, K> dao = getDao();

        Collection<T> resultado;

        try {
            resultado = dao.findByFilter(filtro);
        } catch (DaoException e) {
            throw new PersistenceManagerException(e, filtro);
        }

        return resultado;
    }

    protected String getContextName() {
        return "default";
    }

    public T findByKey(K k) throws PersistenceManagerException, ObjectNotFoundException {

        AbstractDaoCriteria<T, K> dao = getDao();

        T elemento;

        try {
            elemento = dao.findByKey(k);
        } catch (ObjectNotFoundException e) {
            throw e;
        } catch (DaoException e) {
            throw new PersistenceManagerException(e);
        }

        return elemento;
    }

    public T loadByKey(K k) throws PersistenceManagerException, ObjectNotFoundException {

        AbstractDaoCriteria<T, K> dao = getDao();

        T elemento;

        try {
            elemento = dao.loadByKey(k);
        } catch (ObjectNotFoundException e) {
            throw e;
        } catch (DaoException e) {
            throw new PersistenceManagerException(e);
        }

        return elemento;
    }

    public K insert(T element) throws PersistenceManagerException {
        K key = null;

        AbstractDaoCriteria<T, K> dao = getDao();

        try {
            key = dao.insert(element);
        } catch (ObjectExistsException e) {
            throw new PersistenceManagerException(e, "erro." + element.getClass().getName()
                    + ".objetoJaExistente", element);
        } catch (DaoException e) {
            throw new PersistenceManagerException(e, element);
        }

        return key;
    }

    public void update(T element) throws PersistenceManagerException {

        AbstractDaoCriteria<T, K> dao = getDao();

        try {
            dao.update(element);
        } catch (ObjectNotFoundException e) {
            throw new PersistenceManagerException(e, "erro." + element.getClass().getName()
                    + ".objetoInexistente", element);
        } catch (DaoException e) {
            throw new PersistenceManagerException(e, element);
        }
    }

    public void remove(T element) throws PersistenceManagerException {

        AbstractDaoCriteria<T, K> dao = getDao();

        try {
            dao.removeElement(element);
        } catch (ObjectNotFoundException e) {
            throw new PersistenceManagerException(e, "erro." + element.getClass().getName()
                    + ".objetoInexistente", element);
        } catch (DaoException e) {
            throw new PersistenceManagerException(e, element);
        }
    }

    public Collection<T> findAll() throws PersistenceManagerException {

        AbstractDaoCriteria<T, K> dao = getDao();

        Collection<T> resultado;

        resultado = dao.findAll();

        return resultado;

    }

    public void insert(Collection<? extends T> elements) throws PersistenceManagerException {
        for (T element : elements) {
            this.insert(element);
        }
    }

    public T findUniqueByFilter(Filter<? extends T> filtro) throws PersistenceManagerException,
            NonUniqueReferenceException {
        try {
            return this.getDao().findUniqueByFilter(filtro);
        } catch (DaoException e) {
            throw new PersistenceManagerException(e, filtro);
        }
    }

    public void update(Collection<? extends T> elements) throws PersistenceManagerException {
        for (T element : elements) {
            update(element);
        }

    }

    public boolean exists(Filter<? extends T> filtro) throws PersistenceManagerException {

        AbstractDaoCriteria<T, K> dao = getDao();

        boolean existe;

        existe = dao.exists(filtro);

        return existe;
    }

    public boolean exists(K key) throws PersistenceManagerException {

        AbstractDaoCriteria<T, K> dao = getDao();

        boolean existe;

        try {
            existe = dao.existsKey(key);
        } catch (DaoException e) {
            throw new PersistenceManagerException(e, key);
        }

        return existe;
    }

    public long count(Filter<? extends T> filtro) throws PersistenceManagerException {

        AbstractDaoCriteria<T, K> dao = getDao();

        long resultado;

        resultado = dao.count(filtro);

        return resultado;
    }

    public long countAll() throws PersistenceManagerException {

        AbstractDaoCriteria<T, K> dao = getDao();

        long resultado;

        resultado = dao.countAll();

        return resultado;
    }

    public void refreshEntity(T t) {
        getDao().refreshEntity(t);
    }

}