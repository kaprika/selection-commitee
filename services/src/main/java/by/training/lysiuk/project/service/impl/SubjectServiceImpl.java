package by.training.lysiuk.project.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Service;

import by.training.lysiuk.project.dataaccess.SubjectDao;
import by.training.lysiuk.project.dataaccess.filters.SubjectFilter;
import by.training.lysiuk.project.dataaccess.impl.AbstractDaoImpl;
import by.training.lysiuk.project.datamodel.Subject;
import by.training.lysiuk.project.datamodel.Subject_;
import by.training.lysiuk.project.service.SubjectService;

@Service
public class SubjectServiceImpl extends AbstractDaoImpl<Subject, Long> implements SubjectService {

	protected SubjectServiceImpl() {
		super(Subject.class);
	}

	@Inject
	private SubjectDao dao;

	@Override
	public Subject getSubject(Long id) {
		return dao.get(id);
	}

	@Override
	public void saveOrUpdate(Subject subject) {
		if (subject.getId() == null) {
			dao.insert(subject);
		} else {
			dao.update(subject);
		}
	}

	@Override
	public void delete(Long id) {
		dao.delete(id);
	}

	@Override
	public List<Subject> getAll() {
		return dao.getAll();
	}

	@Override
	public Long count(SubjectFilter filter) {
		return dao.count(filter);
	}

	@Override
	public List<Subject> find(SubjectFilter filter) {
		return dao.find(filter);
	}

	@Override
	public void delete(Subject subject) {
		dao.delete(subject.getId());
	}

	@Override
	public Subject getByName(String subjectName) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Subject> cq = cb.createQuery(Subject.class);
		Root<Subject> from = cq.from(Subject.class);
		cq.select(from);
		Predicate nameEqualCondition = cb.equal(from.get(Subject_.name), subjectName);
		cq.where(nameEqualCondition);
		TypedQuery<Subject> q = em.createQuery(cq);
		List<Subject> subjects = q.getResultList();
		if (subjects.isEmpty()) {
			return null;
		} else {
			return subjects.get(0);
		}
		/*
		 * Faculty faculty; try { faculty = q.getSingleResult(); } catch
		 * (EntityNotFoundException e) { return null; } return faculty;
		 */

	}

}
