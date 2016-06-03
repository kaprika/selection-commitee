package by.training.lysiuk.project.dataaccess.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import by.training.lysiuk.project.dataaccess.PlanSetDao;
import by.training.lysiuk.project.dataaccess.filters.PlanSetFilter;
import by.training.lysiuk.project.datamodel.PlanSet;
import by.training.lysiuk.project.datamodel.PlanSet_;

@Repository
public class PlanSetDaoImpl extends AbstractDaoImpl<PlanSet, Long> implements PlanSetDao {

	protected PlanSetDaoImpl() {
		super(PlanSet.class);
	}

	@Override
	public Long count(PlanSetFilter filter) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<PlanSet> from = cq.from(PlanSet.class);
		cq.select(cb.count(from));
		TypedQuery<Long> q = em.createQuery(cq);
		return q.getSingleResult();
	}

	@Override
	public List<PlanSet> find(PlanSetFilter filter) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<PlanSet> cq = cb.createQuery(PlanSet.class);
		Root<PlanSet> from = cq.from(PlanSet.class);
		//cq.select(from);
		if (filter.isFetchFaculty()) {
			from.fetch(PlanSet_.faculty, JoinType.LEFT);
		}
		if (filter.isFetchSubjects()) {
			from.fetch(PlanSet_.subjects, JoinType.LEFT);
		}
		cq.select(from).distinct(true);
		if (filter.getSortProperty() != null) {
			cq.orderBy(new OrderImpl(from.get(filter.getSortProperty()), filter.isSortOrder()));
		}
		TypedQuery<PlanSet> q = em.createQuery(cq);
		setPaging(filter, q);
		return q.getResultList();
	}

}