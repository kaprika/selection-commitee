package by.training.lysiuk.project.dataaccess.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import by.training.lysiuk.project.dataaccess.UserCredentialsDao;
import by.training.lysiuk.project.datamodel.UserCredentials;
import by.training.lysiuk.project.datamodel.UserCredentials_;


@Repository
public class UserCredentialsDaoImpl extends AbstractDaoImpl<UserCredentials, Long> implements UserCredentialsDao {

    protected UserCredentialsDaoImpl() {
        super(UserCredentials.class);
    }

    @Override
    public UserCredentials find(String userName, String password) {
        EntityManager em = getEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<UserCredentials> cq = cb.createQuery(UserCredentials.class);

        Root<UserCredentials> from = cq.from(UserCredentials.class);

        cq.select(from);
        Predicate usernameEqualCondition = cb.equal(from.get(UserCredentials_.email), userName);
        Predicate passwEqualCondition = cb.equal(from.get(UserCredentials_.password), password);
        cq.where(cb.and(usernameEqualCondition, passwEqualCondition));

        TypedQuery<UserCredentials> q = em.createQuery(cq);

        List<UserCredentials> allitems = q.getResultList();

        if (allitems.isEmpty()) {
            return null;
        } else if (allitems.size() == 1) {
            return allitems.get(0);
        } else {
            throw new IllegalArgumentException("more than 1 user found ");
        }
    }
}
