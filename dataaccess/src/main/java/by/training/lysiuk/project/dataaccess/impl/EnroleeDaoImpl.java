package by.training.lysiuk.project.dataaccess.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import by.training.lysiuk.project.dataaccess.EnroleeDao;
import by.training.lysiuk.project.dataaccess.filters.EnrolleeFilter;
import by.training.lysiuk.project.dataaccess.filters.PlanSetFilter;
import by.training.lysiuk.project.datamodel.Enrolee;
import by.training.lysiuk.project.datamodel.Enrolee_;
import by.training.lysiuk.project.datamodel.PlanSet;
import by.training.lysiuk.project.datamodel.PlanSet_;

@Repository
public class EnroleeDaoImpl extends AbstractDaoImpl<Enrolee, Long> implements EnroleeDao {

	protected EnroleeDaoImpl() {
		super(Enrolee.class);
	}

	@Override
	public Long count(EnrolleeFilter filter) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Enrolee> from = cq.from(Enrolee.class);
		cq.select(cb.count(from));
		TypedQuery<Long> q = em.createQuery(cq);
		return q.getSingleResult();
	}

	@Override
	public List<Enrolee> find(EnrolleeFilter filter) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Enrolee> cq = cb.createQuery(Enrolee.class);
		Root<Enrolee> from = cq.from(Enrolee.class);

		Fetch<Enrolee, PlanSet> fetchPlanSet = from.fetch(Enrolee_.planSet, JoinType.LEFT);
		if (filter.isFetchSubjects()) {
			fetchPlanSet.fetch(PlanSet_.subjects);
		}

		if (filter.isFetchFaculty()) {
			fetchPlanSet.fetch(PlanSet_.faculty);
		}

		cq.select(from).distinct(true);

		if (filter.getSortProperty() != null) {
			cq.orderBy(new OrderImpl(from.get(filter.getSortProperty()), filter.isSortOrder()));
		}

		TypedQuery<Enrolee> q = em.createQuery(cq);
		setPaging(filter, q);
		return q.getResultList();
	}

}
