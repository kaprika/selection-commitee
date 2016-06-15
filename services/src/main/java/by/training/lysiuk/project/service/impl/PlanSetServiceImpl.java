package by.training.lysiuk.project.service.impl;

import java.util.Calendar;
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

import org.apache.commons.lang3.time.DateUtils;
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
	public Long countByYearAndFaculty(PlanSetFilter filter) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<PlanSet> from = cq.from(PlanSet.class);
		Date fromDate = new Date();
		Date toDate = new Date();
		fromDate.setMonth(0);
		fromDate.setDate(1);
		toDate.setMonth(11);
		toDate.setDate(31);
		fromDate.setHours(0);
		fromDate.setMinutes(0);
		fromDate.setSeconds(0);
		toDate.setMinutes(23);
		toDate.setSeconds(59);
		toDate.setHours(59);
		Predicate startDateGreaterThanOrEqualCondition = cb.greaterThanOrEqualTo(from.get(PlanSet_.startDateSet),
				fromDate);
		Predicate endDateLessThanOrEqualCondition = cb.lessThanOrEqualTo(from.get(PlanSet_.endDateSet), toDate);
		Predicate facultyEqualCondition = cb.equal(from.join(PlanSet_.faculty).get(Faculty_.name),
				filter.getFacultyName());
		cq.select(cb.count(from)).where(
				cb.and(startDateGreaterThanOrEqualCondition, endDateLessThanOrEqualCondition, facultyEqualCondition));
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
		Predicate startDateLessThanOrEqualCondition = cb.lessThanOrEqualTo(from.get(PlanSet_.startDateSet),
				currentDate);
		Predicate endDateGreatThanOrEqualCondition = cb.greaterThanOrEqualTo(from.get(PlanSet_.endDateSet),
				currentDate);
		cq.select(from).where(cb.and(startDateLessThanOrEqualCondition, endDateGreatThanOrEqualCondition))
				.distinct(true);
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
		Date fromDate = new Date();
		Date toDate = new Date();
		fromDate.setMonth(0);
		fromDate.setDate(1);
		toDate.setMonth(11);
		toDate.setDate(31);
		fromDate.setHours(0);
		fromDate.setMinutes(0);
		fromDate.setSeconds(0);
		toDate.setMinutes(23);
		toDate.setSeconds(59);
		toDate.setHours(59);
		// Calendar fromDate = DateUtils.truncate(Calendar.getInstance(),
		// Calendar.YEAR);
		// Calendar toDate = DateUtils.truncate(Calendar.getInstance(),
		// Calendar.YEAR);
		Predicate startDateGreaterThanOrEqualCondition = cb.greaterThanOrEqualTo(from.get(PlanSet_.startDateSet),
				fromDate);
		Predicate endDateLessThanOrEqualCondition = cb.lessThanOrEqualTo(from.get(PlanSet_.endDateSet), toDate);

		cq.select(from).where(cb.and(startDateGreaterThanOrEqualCondition, endDateLessThanOrEqualCondition))
				.distinct(true);
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
		Predicate endDateGreaterThanOrEqualCondition = cb.greaterThanOrEqualTo(from.get(PlanSet_.endDateSet),
				filter.getStartDate());
		Predicate endDateLessThanOrEqualCondition = cb.lessThanOrEqualTo(from.get(PlanSet_.endDateSet),
				filter.getEndDate());
		cq.select(from).where(cb.and(endDateGreaterThanOrEqualCondition, endDateLessThanOrEqualCondition))
				.distinct(true);
		TypedQuery<PlanSet> q = em.createQuery(cq);
		return q.getResultList();
	}

}
