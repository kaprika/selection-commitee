package by.training.lysiuk.project.dataaccess.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import by.training.lysiuk.project.dataaccess.ScoresInSubjectsDao;
import by.training.lysiuk.project.dataaccess.filters.ScoresInSubjectsFilter;
import by.training.lysiuk.project.datamodel.Enrolee_;
import by.training.lysiuk.project.datamodel.ScoresInSubjects;
import by.training.lysiuk.project.datamodel.ScoresInSubjects_;

@Repository
public class ScoresInSubjectsDaoImpl extends AbstractDaoImpl<ScoresInSubjects, Long> implements ScoresInSubjectsDao {

	protected ScoresInSubjectsDaoImpl() {
		super(ScoresInSubjects.class);
	}
	
	@Override
	public List<ScoresInSubjects> find(ScoresInSubjectsFilter filter) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ScoresInSubjects> cq = cb.createQuery(ScoresInSubjects.class);
		Root<ScoresInSubjects> from = cq.from(ScoresInSubjects.class);
		//cq.select(from);
		if (filter.isFetchEnrolee()) {
			from.fetch(ScoresInSubjects_.enrolee, JoinType.LEFT);
		}
	//	Predicate enroleeEqualCondition = cb.equal(from.get(ScoresInSubjects_.enrolee).get(Enrolee_.id), )
		cq.select(from);
	
		TypedQuery<ScoresInSubjects> q = em.createQuery(cq);
		return q.getResultList();
	}
} 