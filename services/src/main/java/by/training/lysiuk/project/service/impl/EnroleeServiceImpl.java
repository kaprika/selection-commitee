package by.training.lysiuk.project.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Service;

import by.training.lysiuk.project.dataaccess.EnroleeDao;
import by.training.lysiuk.project.dataaccess.ScoresInSubjectsDao;
import by.training.lysiuk.project.dataaccess.filters.EnrolleeFilter;
import by.training.lysiuk.project.dataaccess.impl.AbstractDaoImpl;
import by.training.lysiuk.project.datamodel.Enrolee;
import by.training.lysiuk.project.datamodel.Enrolee_;
import by.training.lysiuk.project.datamodel.Faculty;
import by.training.lysiuk.project.datamodel.Faculty_;
import by.training.lysiuk.project.datamodel.PlanSet;
import by.training.lysiuk.project.datamodel.PlanSet_;
import by.training.lysiuk.project.datamodel.ScoresInSubjects;
import by.training.lysiuk.project.service.EnroleeService;
import by.training.lysiuk.project.service.ScoresInSubjectsService;

@Service
public class EnroleeServiceImpl extends AbstractDaoImpl<Enrolee, Long> implements EnroleeService {

	protected EnroleeServiceImpl() {
		super(Enrolee.class);
	}

	@Inject
	private EnroleeDao dao;

	@Inject
	private ScoresInSubjectsDao scoresDao;
	
	@Inject
	private ScoresInSubjectsService scoresService;

	@Override
	public Enrolee getEnrolee(Long id) {
		return dao.get(id);
	}

	@Override
	public void saveOrUpdate(Enrolee enrolee) {
		if (enrolee.getId() == null) {
			dao.insert(enrolee);
		} else {
			dao.update(enrolee);
		}
	}

	@Override
	public void delete(Long id) {
		dao.delete(id);
	}

	@Override
	public List<Enrolee> getAll() {
		return dao.getAll();
	}

	@Override
	public Long count(EnrolleeFilter filter) {
		return dao.count(filter);
	}

	@Override
	public List<Enrolee> find(EnrolleeFilter filter) {
		return dao.find(filter);
	}

	@Override
	public void delete(Enrolee enrolee) {
		dao.delete(enrolee.getId());
	}

	@Override
	public void saveOrUpdateEnroleeWithPoints(Enrolee enrolee, List<ScoresInSubjects> scoresInSubjects) {
		if (enrolee.getId() == null) {
			dao.insert(enrolee);
			for (ScoresInSubjects scores : scoresInSubjects) {
				scoresDao.insert(scores);
			}
		} else {
			dao.update(enrolee);
			scoresService.deleteByEnrolleeId(enrolee.getId());
			for (ScoresInSubjects scores : scoresInSubjects) {
				scoresDao.insert(scores);
			}
		}
	}

	@Override
	public Long countByDateAndFaculty(Faculty faculty) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Enrolee> from = cq.from(Enrolee.class);

		Date currentDate = new Date();

		Predicate startDateLessThanOrEqualCondition = cb
				.lessThanOrEqualTo(from.join(Enrolee_.planSet).get(PlanSet_.startDateSet), currentDate);
		Predicate endDateGreatThanOrEqualCondition = cb
				.greaterThanOrEqualTo(from.join(Enrolee_.planSet).get(PlanSet_.endDateSet), currentDate);
		Predicate facultyEqualCondition = cb
				.equal(from.join(Enrolee_.planSet).join(PlanSet_.faculty).get(Faculty_.name), faculty.getName());

		cq.select(cb.count(from)).where(
				cb.and(startDateLessThanOrEqualCondition, endDateGreatThanOrEqualCondition, facultyEqualCondition));
		TypedQuery<Long> q = em.createQuery(cq);
		return q.getSingleResult();
	}

	@Override
	public List<Enrolee> getEnroleesByPlanSet(PlanSet planSet) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Enrolee> cq = cb.createQuery(Enrolee.class);
		Root<Enrolee> from = cq.from(Enrolee.class);

		Date currentDate = new Date();

		from.fetch(Enrolee_.planSet, JoinType.LEFT).fetch(PlanSet_.faculty, JoinType.LEFT);
		Path<PlanSet> planSetPath = from.get(Enrolee_.planSet);

		Predicate startDateLessThanOrEqualCondition = cb.lessThanOrEqualTo(planSetPath.get(PlanSet_.startDateSet),
				currentDate);
		Predicate endDateGreatThanOrEqualCondition = cb.greaterThanOrEqualTo(planSetPath.get(PlanSet_.endDateSet),
				currentDate);
		Predicate planSetEqualCondition = cb.equal(planSetPath, planSet);

		cq.select(from).where(
				cb.and(startDateLessThanOrEqualCondition, endDateGreatThanOrEqualCondition, planSetEqualCondition));

		TypedQuery<Enrolee> q = em.createQuery(cq);
		return q.getResultList();

	}

}
