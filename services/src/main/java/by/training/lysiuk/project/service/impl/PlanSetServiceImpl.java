package by.training.lysiuk.project.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.jpa.criteria.OrderImpl;
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
	public List<PlanSet> getByCurrentDate(PlanSetFilter filter) {
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
		if (filter.getSortProperty() != null) {
			cq.orderBy(new OrderImpl(from.get(filter.getSortProperty()), filter.isSortOrder()));
		}
		TypedQuery<PlanSet> q = em.createQuery(cq);
		return q.getResultList();
	}
	
	@Override
	public List<PlanSet> getByCurrentYear(PlanSetFilter filter) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<PlanSet> cq = cb.createQuery(PlanSet.class);
		Root<PlanSet> from = cq.from(PlanSet.class);

		from.fetch(PlanSet_.faculty, JoinType.LEFT);
		from.fetch(PlanSet_.subjects, JoinType.LEFT);
		Date startDate = new Date();
		Date endDate = new Date();
		startDate.setMonth(0);
		startDate.setDate(1);
		endDate.setMonth(11);
		endDate.setDate(31);
		startDate.setHours(0);
		startDate.setMinutes(0);
		startDate.setSeconds(0);
		endDate.setMinutes(0);
		endDate.setSeconds(0);
		endDate.setHours(0);

		Predicate startDateGreaterThanOrEqualCondition = cb.greaterThanOrEqualTo(from.get(PlanSet_.startDateSet), startDate);
		Predicate endDateLessThanOrEqualCondition = cb.lessThanOrEqualTo(from.get(PlanSet_.endDateSet), endDate);
		cq.select(from).where(cb.and(startDateGreaterThanOrEqualCondition, endDateLessThanOrEqualCondition)).distinct(true);
		if (filter.getSortProperty() != null) {
			cq.orderBy(new OrderImpl(from.get(filter.getSortProperty()), filter.isSortOrder()));
		}
		TypedQuery<PlanSet> q = em.createQuery(cq);
		return q.getResultList();
	}

	@Override
	public List<PlanSet> getClosedPlansSet(PlanSetFilter filter) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<PlanSet> cq = cb.createQuery(PlanSet.class);
		Root<PlanSet> from = cq.from(PlanSet.class);

		from.fetch(PlanSet_.faculty, JoinType.LEFT);
		from.fetch(PlanSet_.subjects, JoinType.LEFT);

		Predicate endDateGreaterThanOrEqualCondition = cb.greaterThanOrEqualTo(from.get(PlanSet_.endDateSet), filter.getStartDate());
		Predicate endDateLessThanOrEqualCondition = cb.lessThanOrEqualTo(from.get(PlanSet_.endDateSet), filter.getEndDate());
		cq.select(from).where(cb.and(endDateGreaterThanOrEqualCondition, endDateLessThanOrEqualCondition)).distinct(true);
		TypedQuery<PlanSet> q = em.createQuery(cq);
		return q.getResultList();
	}
	
	

}
