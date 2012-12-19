package es.steria.rest.genericos.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.EntityMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.EntityType;
import org.hibernate.type.Type;

import es.steria.rest.excepciones.DAOLogException;
import es.steria.rest.genericos.dao.DAOGenerico;

public class JPADAOGenerico<T, PK extends Serializable> implements
	DAOGenerico<T, PK> {

    protected static final Log log = LogFactory.getLog(JPADAOGenerico.class);
    protected Class<T> type;

    public JPADAOGenerico(Class<T> type) {
	this.type = type;
    }

    
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
	return entityManager;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
	this.entityManager = entityManager;
    }

    @SuppressWarnings("unchecked")
    public void create(T o) throws DAOLogException {
	try {
	    this.entityManager.persist(o);
	} catch (Exception e) {
	    log.error(e);
	    throw new DAOLogException("Error en el metodo create de"
		    + " JPDAOGenerico: " + e.getMessage());
	}
    }

    @SuppressWarnings("unchecked")
    public T read(PK id) throws DAOLogException {
	try {
	    return (T) this.entityManager.find(type, id);
	} catch (Exception e) {
	    log.error(e);
	    throw new DAOLogException("Error en el metodo read de"
		    + " JPDAOGenerico: " + e.getMessage());
	}
    }

    public T update(T o) throws DAOLogException {
	try {
	    return ((T) this.entityManager.merge(o));
	} catch (Exception e) {
	    log.error(e);
	    throw new DAOLogException("Error en el metodo update de"
		    + " JPDAOGenerico: " + e.getMessage());
	}
    }

    public void delete(T o) throws DAOLogException {
	try {
	    this.entityManager.remove(o);
	} catch (Exception e) {
	    log.error(e);
	    throw new DAOLogException("Error en el metodo delete de"
		    + " JPDAOGenerico: " + e.getMessage());
	}
    }

    public void delete(PK o) throws DAOLogException {
	try {
	    this.entityManager.remove(this.entityManager.find(type, o));
	} catch (Exception e) {
	    log.error(e);
	    throw new DAOLogException("Error en el metodo delete de"
		    + " JPDAOGenerico: " + e.getMessage());
	}
    }

    @SuppressWarnings("unchecked")
    public List<T> getList() throws DAOLogException {
	try {
	    String cadena = "Select o FROM " + type.getName() + " o";
	    javax.persistence.Query query = this.entityManager
		    .createQuery(cadena);
	    return query.getResultList();
	} catch (Exception e) {
	    log.error(e);
	    throw new DAOLogException("Error en el metodo delete de"
		    + " JPDAOGenerico: " + e.getMessage());
	}
    }

    public List<T> findByExample(T exampleObject) throws DAOLogException {
	try {
	    Session sesion = (Session) this.entityManager.getDelegate();
	    Criteria criteria = createExampleCriteria(exampleObject, sesion);
	    return criteria.list();
	} catch (HibernateException e) {
	    log.error(e);
	    throw new DAOLogException("Error en el metodo delete de"
		    + " JPDAOGenerico: " + e.getMessage());
	}
    }

    public void deleteByExample(T exampleObject) throws DAOLogException {
	try {
	    Session sesion = (Session) this.entityManager.getDelegate();
	    Example example = Example.create(exampleObject).enableLike()
		    .ignoreCase();
	    List<T> list = sesion.createCriteria(type).add(example).list();

	    for (T e : list) {
		sesion.delete(e);
	    }
	} catch (HibernateException e) {
	    log.error(e);
	    throw new DAOLogException("Error en el metodo deleteByExample de"
		    + " JPDAOGenerico: " + e.getMessage());
	}
    }

    public void createAll(Collection<T> objects) throws DAOLogException {
	try {
	    Session sesion = (Session) this.entityManager.getDelegate();
	    for (T e : objects) {
		sesion.persist(e);
	    }
	} catch (HibernateException e) {
	    log.error(e);
	    throw new DAOLogException("Error en el metodo deleteByExample de"
		    + " JPDAOGenerico: " + e.getMessage());
	}
    }

    private void addChildExampleCriteria(Object exampleInstance,
	    Criteria criteria, Session sesion) {
	ClassMetadata metadata = sesion.getSessionFactory().getClassMetadata(
		exampleInstance.getClass());

	String[] propertyNames = metadata.getPropertyNames();
	Type[] propertyTypes = metadata.getPropertyTypes();

	// see if this example instance has any properties that are entities
	for (int i = 0; i < propertyNames.length; i++) {

	    String propertyName = propertyNames[i];
	    Type propertyType = propertyTypes[i];

	    if (propertyType instanceof EntityType) {
		// this property is an association - Hibernate's Example ignores
		// these
		Object value = metadata.getPropertyValue(exampleInstance,
			propertyName, EntityMode.POJO);

		if (value != null) {

		    ClassMetadata childMetadata = sesion.getSessionFactory()
			    .getClassMetadata(value.getClass());
		    Criteria childCriteria = criteria
			    .createCriteria(propertyName);

		    if (childMetadata.hasIdentifierProperty()) {

			Object id = childMetadata.getIdentifier(value,
				EntityMode.POJO);
			if (id != null) {
			    // add the identifier to the child criteria
			    childCriteria.add(Restrictions.eq(
				    childMetadata.getIdentifierPropertyName(),
				    id));
			}
		    }

		    // add the entity's fields as Example fields
		    childCriteria.add(Example.create(value));

		    // add this entity's associations
		    addChildExampleCriteria(value, childCriteria, sesion);
		}
	    }
	} // ~for
    }

    protected Criteria createExampleCriteria(T exampleInstance, Session sesion) {

	Criteria criteria = sesion.createCriteria(type);
	criteria.add(Example.create(exampleInstance));

	// Hibernate ignores fields that identifiers or are Entity types;
	// call addChildExampleCriteria to add these.
	addChildExampleCriteria(exampleInstance, criteria, sesion);

	return criteria;
    }

    public void flush() throws DAOLogException {
	// this.entityManager.flush();

    }

}
