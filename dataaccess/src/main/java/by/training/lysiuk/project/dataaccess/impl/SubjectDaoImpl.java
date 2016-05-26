package by.training.lysiuk.project.dataaccess.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import by.training.lysiuk.project.dataaccess.SubjectDao;
import by.training.lysiuk.project.dataaccess.filters.SubjectFilter;
import by.training.lysiuk.project.datamodel.Subject;

@Repository
public class SubjectDaoImpl extends AbstractDaoImpl<Subject, Long> implements SubjectDao {

	protected SubjectDaoImpl() {
		super(Subject.class);
	}
	 @Override
	    public Long count(SubjectFilter filter) {
	        EntityManager em = getEntityManager();
	        CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
	        Root<Subject> from = cq.from(Subject.class);
	        cq.select(cb.count(from));
	        TypedQuery<Long> q = em.createQuery(cq);
	        return q.getSingleResult();
	    }

	    @Override
	    public List<Subject> find(SubjectFilter filter) {
	        EntityManager em = getEntityManager();
	        CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery<Subject> cq = cb.createQuery(Subject.class);
	        Root<Subject> from = cq.from(Subject.class);
	        cq.select(from);
	        // set sort params

	        if (filter.getSortProperty() != null) {
	            cq.orderBy(new OrderImpl(from.get(filter.getSortProperty()), filter.isSortOrder()));
	        }
	        TypedQuery<Subject> q = em.createQuery(cq);
	        setPaging(filter, q);
	        return q.getResultList();
	    }
}