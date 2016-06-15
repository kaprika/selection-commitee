package by.training.lysiuk.project.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Service;

import by.training.lysiuk.project.dataaccess.FacultyDao;
import by.training.lysiuk.project.dataaccess.filters.FacultyFilter;
import by.training.lysiuk.project.dataaccess.impl.AbstractDaoImpl;
import by.training.lysiuk.project.datamodel.Faculty;
import by.training.lysiuk.project.datamodel.Faculty_;
import by.training.lysiuk.project.service.FacultyService;

@Service
public class FacultyServiceImpl extends AbstractDaoImpl<Faculty, Long> implements FacultyService {

	protected FacultyServiceImpl() {
		super(Faculty.class);
	}

	@Inject
	private FacultyDao dao;

	@Override
	public Faculty getFaculty(Long id) {
		return dao.get(id);
	}

	@Override
	public void saveOrUpdate(Faculty faculty) {
		if (faculty.getId() == null) {
			dao.insert(faculty);
		} else {
			dao.update(faculty);
		}
	}

	@Override
	public void delete(Long id) {
		dao.delete(id);
	}

	@Override
	public List<Faculty> getAll() {
		return dao.getAll();
	}

	@Override
	public Long count(FacultyFilter filter) {
		return dao.count(filter);
	}

	@Override
	public List<Faculty> find(FacultyFilter filter) {
		return dao.find(filter);
	}

	@Override
	public void delete(Faculty faculty) {
		dao.delete(faculty.getId());
	}

	@Override
	public Faculty getByName(String facultyName) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Faculty> cq = cb.createQuery(Faculty.class);
		Root<Faculty> from = cq.from(Faculty.class);
		cq.select(from);
		Predicate nameEqualCondition = cb.equal(from.get(Faculty_.name), facultyName);
		cq.where(nameEqualCondition);
		TypedQuery<Faculty> q = em.createQuery(cq);
		List<Faculty> faculties = q.getResultList();
		if (faculties.isEmpty()) {
			return null;
		} else {
			return faculties.get(0);
		}

	}

}
