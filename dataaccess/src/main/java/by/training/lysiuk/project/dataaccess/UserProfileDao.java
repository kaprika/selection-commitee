package by.training.lysiuk.project.dataaccess;

import java.util.List;

import by.training.lysiuk.project.dataaccess.filters.UserFilter;
import by.training.lysiuk.project.datamodel.UserProfile;


public interface UserProfileDao extends AbstractDao<UserProfile, Long> {

    List<UserProfile> find(UserFilter filter);

    long count(UserFilter filter);

}
