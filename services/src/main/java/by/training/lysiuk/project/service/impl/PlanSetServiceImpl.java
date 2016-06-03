package by.training.lysiuk.project.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Service;

import by.training.lysiuk.project.dataaccess.PlanSetDao;
import by.training.lysiuk.project.dataaccess.filters.PlanSetFilter;
import by.training.lysiuk.project.dataaccess.impl.AbstractDaoImpl;
import by.training.lysiuk.project.datamodel.Faculty_;
import by.training.lysiuk.project.datamodel.PlanSet;
import by.training.lysiuk.project.datamodel.PlanSet_;
import by.training.lysiuk.project.service.PlanSetService;

@Service
public class PlanSetServiceImpl extends AbstractDaoImpl<PlanSet, Long> implements PlanSetService {

	protected PlanSetServiceImpl() {
		super(PlanSet.class);
	}

	@Inject
	private PlanSetDao dao;

	@Override
	public Long count(PlanSetFilter filter) {
		return dao.count(filter);
	}

	@Override
	public List<PlanSet> find(PlanSetFilter filter) {
		return dao.find(filter);
	}

	@Override
	public void saveOrUpdate(PlanSet planSet) {
		if (planSet.getId() == null) {
			dao.insert(planSet);
		} else {
			dao.update(planSet);
		}
	}

	@Override
	public void delete(PlanSet planSet) {
		dao.delete(planSet.getId());
	}

	@Override
	public PlanSet getPlanSet(Long id) {
		return dao.get(id);
	}

	@Override
	public void delete(Long id) {
		dao.delete(id);
	}

	@Override
	public List<PlanSet> getAll() {
		return dao.getAll();
	}

	@Override
	public Long countByYearAndFaculty(Date date, String faculty) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<PlanSet> from = cq.from(PlanSet.class);
		Predicate yearEqualCondition = cb.equal(from.get(PlanSet_.startDateSet), date);
		Predicate facultyEqualCondition = cb.equal(from.join(PlanSet_.faculty).get(Faculty_.name), faculty);
		cq.select(cb.count(from)).where(cb.and(yearEqualCondition, facultyEqualCondition));
		TypedQuery<Long> q = em.createQuery(cq);
		return q.getSingleResult();
	}

	@Override
	public List<PlanSet> getByCurrentDate() {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<PlanSet> cq = cb.createQuery(PlanSet.class);
		Root<PlanSet> from = cq.from(PlanSet.class);

		from.fetch(PlanSet_.faculty, JoinType.LEFT);
		from.fetch(PlanSet_.subjects, JoinType.LEFT);
		Date currentDate = new Date();
		Predicate startDateLessThanOrEqualCondition = cb.lessThanOrEqualTo(from.get(PlanSet_.startDateSet), currentDate);
		Predicate endDateGreatThanOrEqualCondition = cb.greaterThanOrEqualTo(from.get(PlanSet_.endDateSet), currentDate);
		cq.select(from).where(cb.and(startDateLessThanOrEqualCondition, endDateGreatThanOrEqualCondition)).distinct(true);

		TypedQuery<PlanSet> q = em.createQuery(cq);
		return q.getResultList();
	}
	
	

}
