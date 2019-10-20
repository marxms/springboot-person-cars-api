package com.marx.personmanagement.model.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Component;

import com.marx.personmanagement.model.util.FiltroPesquisa;

import javassist.NotFoundException;

@Component
@Transactional
@NoRepositoryBean
public abstract class GenericDAO<T, I extends Serializable> {

	   
	   @Autowired
	   protected EntityManagerFactory entityManagerFactory;

	   @Autowired
	   protected EntityManager entityManager;
	   
	   @Autowired
	   private SessionFactory sessionFactory;
	   
	   protected Class<T> persistedClass;

	   protected GenericDAO() {
	   }
	   
	   protected GenericDAO(Class<T> persistedClass) {
	       this();
	       this.persistedClass = persistedClass;
	   }

	   public T save(@Valid T entity) {
	       EntityTransaction t = entityManager.getTransaction();
	       if(!t.isActive()) {
	    	   t.begin();
	       }
	       entityManager.persist(entity);
	       entityManager.flush();
	       t.commit();
	       return entity;
	   }

	   public T update(@Valid T entity) {
	       EntityTransaction t = entityManager.getTransaction();
	       t.begin();
	       entityManager.merge(entity);
	       entityManager.flush();
	       t.commit();
	       return entity;
	   }

	   public void remove(I id) throws NotFoundException {
	       T entity = findById(id);
	       if(entity == null) {
	    	   throw new NotFoundException("Cliente não existe");
	       }
	       EntityTransaction tx = entityManager.getTransaction();
	       tx.begin();
	       entityManager.remove(entity);
	       entityManager.flush();
	       tx.commit();
	   }

	   public List<T> getList() {
		   CriteriaBuilder builder = entityManager.getCriteriaBuilder();
	       CriteriaQuery<T> query = builder.createQuery(persistedClass);
	       query.from(persistedClass);
	       return entityManager.createQuery(query).getResultList();
	   }

	   public T findById(I id) {
	       T retorno = entityManager.find(persistedClass, id);
		   return retorno;
	   }
	   
	   public List<T> findByField(FiltroPesquisa<T> filtroPesquisa) {
		    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		    CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(persistedClass);
		    Root<T> entityRoot = criteriaQuery.from(persistedClass);
		    criteriaQuery.select(entityRoot);
		    criteriaQuery.where(filtroPesquisa.buildPredicate(criteriaBuilder, entityRoot));
		    TypedQuery<T> query = entityManager
		            .createQuery(criteriaQuery);
		    return query.getResultList();
		}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	   
}
