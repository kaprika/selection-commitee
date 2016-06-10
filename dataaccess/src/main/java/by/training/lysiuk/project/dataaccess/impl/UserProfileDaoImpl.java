package by.training.lysiuk.project.dataaccess.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import by.training.lysiuk.project.dataaccess.UserProfileDao;
import by.training.lysiuk.project.dataaccess.filters.UserFilter;
import by.training.lysiuk.project.datamodel.UserCredentials_;
import by.training.lysiuk.project.datamodel.UserProfile;
import by.training.lysiuk.project.datamodel.UserProfile_;


@Repository
public class UserProfileDaoImpl extends AbstractDaoImpl<UserProfile, Long> implements UserProfileDao {

    protected UserProfileDaoImpl() {
        super(UserProfile.class);
    }

    @Override
    public List<UserProfile> find(UserFilter filter) {
        EntityManager em = getEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<UserProfile> cq = cb.createQuery(UserProfile.class);

        Root<UserProfile> from = cq.from(UserProfile.class);

        // set selection
        cq.select(from);
        handleFilterParameters(filter, cb, cq, from);
        // set fetching
        if (filter.isFetchCredentials()) {
            from.fetch(UserProfile_.credentials, JoinType.INNER);
        }

        // set sort params
        if (filter.getSortProperty() != null) {
            Path<Object> expression;
            if (UserCredentials_.email.equals(filter.getSortProperty())) {
                expression = from.get(UserProfile_.credentials).get(filter.getSortProperty());
            } else {
                expression = from.get(filter.getSortProperty());
            }
            cq.orderBy(new OrderImpl(expression, filter.isSortOrder()));
        }

        TypedQuery<UserProfile> q = em.createQuery(cq);

        // set paging
        if (filter.getOffset() != null && filter.getLimit() != null) {
            q.setFirstResult(filter.getOffset());
            q.setMaxResults(filter.getLimit());
        }

        // set execute query
        List<UserProfile> allitems = q.getResultList();
        return allitems;
    }

    @Override
    public long count(UserFilter filter) {
        EntityManager em = getEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<UserProfile> from = cq.from(UserProfile.class);

        // set selection
        cq.select(cb.count(from));

        handleFilterParameters(filter, cb, cq, from);

        TypedQuery<Long> q = em.createQuery(cq);

        // set execute query
        return q.getSingleResult();
    }

    private void handleFilterParameters(UserFilter filter, CriteriaBuilder cb, CriteriaQuery<?> cq, Root<UserProfile> from) {
        if (filter.getUserName() != null) {
            Predicate fNameEqualCondition = cb.equal(from.get(UserProfile_.firstName), filter.getUserName());
            Predicate lNameEqualCondition = cb.equal(from.get(UserProfile_.lastName), filter.getUserName());
            cq.where(cb.or(fNameEqualCondition, lNameEqualCondition));
        }

    }
}
