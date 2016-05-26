package by.training.lysiuk.project.dataaccess.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import by.training.lysiuk.project.dataaccess.FacultyDao;
import by.training.lysiuk.project.dataaccess.filters.FacultyFilter;
import by.training.lysiuk.project.datamodel.Faculty;

@Repository
public class FacultyDaoImpl extends AbstractDaoImpl<Faculty, Long> implements FacultyDao {

	protected FacultyDaoImpl() {
		super(Faculty.class);
	}
	
	 @Override
	    public Long count(FacultyFilter filter) {
	        EntityManager em = getEntityManager();
	        CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
	        Root<Faculty> from = cq.from(Faculty.class);
	        cq.select(cb.count(from));
	        TypedQuery<Long> q = em.createQuery(cq);
	        return q.getSingleResult();
	    }

	    @Override
	    public List<Faculty> find(FacultyFilter filter) {
	        EntityManager em = getEntityManager();
	        CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery<Faculty> cq = cb.createQuery(Faculty.class);
	        Root<Faculty> from = cq.from(Faculty.class);
	        cq.select(from);
	        // set sort params

	        if (filter.getSortProperty() != null) {
	            cq.orderBy(new OrderImpl(from.get(filter.getSortProperty()), filter.isSortOrder()));
	        }

	        TypedQuery<Faculty> q = em.createQuery(cq);
	        setPaging(filter, q);
	        return q.getResultList();
	    }
}