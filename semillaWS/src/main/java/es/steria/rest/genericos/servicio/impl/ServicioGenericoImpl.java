package es.steria.rest.genericos.servicio.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import es.steria.rest.excepciones.DAOLogException;
import es.steria.rest.genericos.dao.DAOGenerico;
import es.steria.rest.genericos.servicio.ServicioGenerico;

public class ServicioGenericoImpl<T, PK extends Serializable> implements
	ServicioGenerico<T, PK> {

    protected DAOGenerico<T, PK> dao;

    @SuppressWarnings("unchecked")
    public void create(T o) throws DAOLogException {
	getDao().create(o);
    }

    @SuppressWarnings("unchecked")
    public T read(PK id) throws DAOLogException {
	return (T) getDao().read(id);
    }

    public T update(T o) throws DAOLogException {
	return (T) getDao().update(o);
    }

    public void delete(T o) throws DAOLogException {
	getDao().delete(o);
    }

    @SuppressWarnings("unchecked")
    public List<T> getList() throws DAOLogException {
	return getDao().getList();
    }

    public DAOGenerico<T, PK> getDao() {
	return dao;
    }

    public void setDao(DAOGenerico<T, PK> dao) {
	this.dao = dao;
    }

    public void delete(PK id) throws DAOLogException {
	getDao().delete(id);
    }

    public List<T> findByExample(T exampleObject) {
	return getDao().findByExample(exampleObject);
    }

    public void deleteByExample(T exampleObject) throws DAOLogException {
	getDao().deleteByExample(exampleObject);

    }

    public void createAll(Collection<T> objects) throws DAOLogException {
	getDao().createAll(objects);
    }

    public void flush() throws DAOLogException {
	getDao().flush();

    }
}
