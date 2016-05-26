package by.training.lysiuk.project.dataaccess.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import by.training.lysiuk.project.dataaccess.EnroleeDao;
import by.training.lysiuk.project.dataaccess.filters.EnroleeFilter;
import by.training.lysiuk.project.datamodel.Enrolee;

@Repository
public class EnroleeDaoImpl extends AbstractDaoImpl<Enrolee, Long> implements EnroleeDao {

	protected EnroleeDaoImpl() {
		super(Enrolee.class);
	}

	@Override
	public Long count(EnroleeFilter filter) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Enrolee> from = cq.from(Enrolee.class);
		cq.select(cb.count(from));
		TypedQuery<Long> q = em.createQuery(cq);
		return q.getSingleResult();
	}

	@Override
	public List<Enrolee> find(EnroleeFilter filter) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Enrolee> cq = cb.createQuery(Enrolee.class);
		Root<Enrolee> from = cq.from(Enrolee.class);
		cq.select(from);
		// set sort params

		if (filter.getSortProperty() != null) {
			cq.orderBy(new OrderImpl(from.get(filter.getSortProperty()), filter.isSortOrder()));
		}

		TypedQuery<Enrolee> q = em.createQuery(cq);
		setPaging(filter, q);
		return q.getResultList();
	}
}
