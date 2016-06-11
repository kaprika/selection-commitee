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
import javax.persistence.criteria.Subquery;

import org.springframework.stereotype.Service;

import by.training.lysiuk.project.dataaccess.ScoresInSubjectsDao;
import by.training.lysiuk.project.dataaccess.impl.AbstractDaoImpl;
import by.training.lysiuk.project.datamodel.Enrolee;
import by.training.lysiuk.project.datamodel.Enrolee_;
import by.training.lysiuk.project.datamodel.Faculty;
import by.training.lysiuk.project.datamodel.Faculty_;
import by.training.lysiuk.project.datamodel.PlanSet;
import by.training.lysiuk.project.datamodel.PlanSet_;
import by.training.lysiuk.project.datamodel.ScoresInSubjects;
import by.training.lysiuk.project.datamodel.ScoresInSubjects_;
import by.training.lysiuk.project.service.ScoresInSubjectsService;

@Service
public class ScoresInSubjectsServiceImpl extends AbstractDaoImpl<ScoresInSubjects, Long>
		implements ScoresInSubjectsService {

	protected ScoresInSubjectsServiceImpl() {
		super(ScoresInSubjects.class);
	}

	@Inject
	private ScoresInSubjectsDao dao;

	@Override
	public void saveOrUpdate(ScoresInSubjects scoresInSubjects) {
		if (scoresInSubjects.getId() == null) {
			dao.insert(scoresInSubjects);
		} else {
			dao.update(scoresInSubjects);
		}
	}

	@Override
	public void delete(ScoresInSubjects scoresInSubjects) {
		dao.delete(scoresInSubjects.getId());
	}

	@Override
	public ScoresInSubjects getScoresInSubjects(Long id) {
		return dao.get(id);
	}

	@Override
	public void delete(Long id) {
		dao.delete(id);
	}

	@Override
	public List<ScoresInSubjects> getAll() {
		return dao.getAll();
	}

	@Override
	public List<ScoresInSubjects> getByDateAndFaculty(Faculty faculty) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ScoresInSubjects> cq = cb.createQuery(ScoresInSubjects.class);
		Root<ScoresInSubjects> from = cq.from(ScoresInSubjects.class);

		Date currentDate = new Date();
		from.fetch(ScoresInSubjects_.enrolee, JoinType.LEFT).fetch(Enrolee_.planSet).fetch(PlanSet_.faculty);

		Path<PlanSet> planSetPath = from.get(ScoresInSubjects_.enrolee).get(Enrolee_.planSet);

		Predicate startDateLessThanOrEqualCondition = cb.lessThanOrEqualTo(planSetPath.get(PlanSet_.startDateSet),
				currentDate);
		Predicate endDateGreatThanOrEqualCondition = cb.greaterThanOrEqualTo(planSetPath.get(PlanSet_.endDateSet),
				currentDate);
		Predicate facultyEqualCondition = cb.equal(planSetPath.get(PlanSet_.faculty).get(Faculty_.name),
				faculty.getName());

		cq.select(from).where(
				cb.and(startDateLessThanOrEqualCondition, endDateGreatThanOrEqualCondition, facultyEqualCondition));
		TypedQuery<ScoresInSubjects> q = em.createQuery(cq);
		return q.getResultList();
	}

	@Override
	public List<Integer> getSumPointsByDateAndFaculty(Faculty faculty) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Integer> cq = cb.createQuery(Integer.class);
		Root<ScoresInSubjects> from = cq.from(ScoresInSubjects.class);

		Subquery<Enrolee> subcq = cq.subquery(Enrolee.class);

		Date currentDate = new Date();

		Path<PlanSet> planSetPath = from.get(ScoresInSubjects_.enrolee).get(Enrolee_.planSet);

		Predicate startDateLessThanOrEqualCondition = cb.lessThanOrEqualTo(planSetPath.get(PlanSet_.startDateSet),
				currentDate);
		Predicate endDateGreatThanOrEqualCondition = cb.greaterThanOrEqualTo(planSetPath.get(PlanSet_.endDateSet),
				currentDate);
		Predicate facultyEqualCondition = cb.equal(planSetPath.get(PlanSet_.faculty).get(Faculty_.name),
				faculty.getName());
		
		cq.select(cb.sum(from.get(ScoresInSubjects_.points))).groupBy(from.get(ScoresInSubjects_.enrolee)).where(
				cb.and(startDateLessThanOrEqualCondition, endDateGreatThanOrEqualCondition, facultyEqualCondition));

		TypedQuery<Integer> q = em.createQuery(cq);
		List<Integer> sumPointsList = q.getResultList();

		return sumPointsList;
	}

	@Override
	public List<Enrolee> getEnroleesListByDateAndFaculty(Faculty faculty) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Enrolee> cq = cb.createQuery(Enrolee.class);
		Root<ScoresInSubjects> from = cq.from(ScoresInSubjects.class);

		Date currentDate = new Date();
		
		//from.fetch(ScoresInSubjects_.enrolee, JoinType.LEFT).fetch(Enrolee_.planSet).fetch(PlanSet_.faculty);
		Path<PlanSet> planSetPath = from.get(ScoresInSubjects_.enrolee).get(Enrolee_.planSet);

		Predicate startDateLessThanOrEqualCondition = cb.lessThanOrEqualTo(planSetPath.get(PlanSet_.startDateSet),
				currentDate);
		Predicate endDateGreatThanOrEqualCondition = cb.greaterThanOrEqualTo(planSetPath.get(PlanSet_.endDateSet),
				currentDate);
		Predicate facultyEqualCondition = cb.equal(planSetPath.get(PlanSet_.faculty).get(Faculty_.name),
				faculty.getName());

		cq.select(from.get(ScoresInSubjects_.enrolee)).where(
				cb.and(startDateLessThanOrEqualCondition, endDateGreatThanOrEqualCondition, facultyEqualCondition)).distinct(true);
		
		TypedQuery<Enrolee> q = em.createQuery(cq);
		return q.getResultList();
	}

	@Override
	public List<ScoresInSubjects> getScoresInSubjectsByEnrolee(Enrolee enrolee) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ScoresInSubjects> cq = cb.createQuery(ScoresInSubjects.class);
		Root<ScoresInSubjects> from = cq.from(ScoresInSubjects.class);
		
		from.fetch(ScoresInSubjects_.enrolee);
		from.fetch(ScoresInSubjects_.subject);

		Predicate enroleeEqualCondition = cb.equal(from.get(ScoresInSubjects_.enrolee).get(Enrolee_.id), enrolee.getId());
		

		cq.select(from).where(enroleeEqualCondition);
		
		TypedQuery<ScoresInSubjects> q = em.createQuery(cq);
		return q.getResultList();
	}

	@Override
	public void deleteByEnrolleeId(Long id) {
		List<ScoresInSubjects> listScores = dao.getAll();
		for (ScoresInSubjects scores : listScores) {
			if(scores.getEnrolee().getId() == id) {
				dao.delete(scores.getId());
			}
		}
	}

}