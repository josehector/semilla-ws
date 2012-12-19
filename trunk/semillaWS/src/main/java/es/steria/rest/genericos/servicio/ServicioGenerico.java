package es.steria.rest.genericos.servicio;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import es.carm.javato.fc.excepciones.DAOLogException;

public interface ServicioGenerico<T, PK extends Serializable> {

    @Transactional
    void create(T newInstance) throws DAOLogException;

    T read(PK id) throws DAOLogException;

    @Transactional
    T update(T transientObject) throws DAOLogException;

    /* T update(PK id); */

    @Transactional
    void delete(T persistentObject) throws DAOLogException;

    @Transactional
    void delete(PK id) throws DAOLogException;

    public List<T> getList() throws DAOLogException;

    public List<T> findByExample(T exampleObject) throws DAOLogException;

    @Transactional
    void deleteByExample(T exampleObject) throws DAOLogException;

    @Transactional
    void createAll(Collection<T> objects) throws DAOLogException;

    void flush() throws DAOLogException;
}
