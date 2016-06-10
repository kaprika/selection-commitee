package by.training.lysiuk.project.service.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import by.training.lysiuk.project.dataaccess.UserCredentialsDao;
import by.training.lysiuk.project.dataaccess.UserProfileDao;
import by.training.lysiuk.project.dataaccess.filters.UserFilter;
import by.training.lysiuk.project.datamodel.UserCredentials;
import by.training.lysiuk.project.datamodel.UserProfile;
import by.training.lysiuk.project.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Inject
    private UserProfileDao userProfileDao;

    @Inject
    private UserCredentialsDao userCredentialsDao;

    @Override
    public void register(UserProfile profile, UserCredentials userCredentials) {

        userCredentialsDao.insert(userCredentials);
        profile.setCredentials(userCredentials);

        profile.setCreated(new Date());
        userProfileDao.insert(profile);

        LOGGER.info("User regirstred: {}", userCredentials);
    }

    @Override
    public UserProfile getProfile(Long id) {
        return userProfileDao.get(id);
    }

    @Override
    public UserCredentials getCredentials(Long id) {
        return userCredentialsDao.get(id);
    }

    @Override
    public void update(UserProfile profile) {
        userProfileDao.update(profile);
    }

    @Override
    public void delete(Long id) {
        userProfileDao.delete(id);
        userCredentialsDao.delete(id);
    }

    @Override
    public List<UserProfile> find(UserFilter filter) {
        return userProfileDao.find(filter);
    }

    @Override
    public List<UserProfile> getAll() {
        return userProfileDao.getAll();
    }

    @Override
    public long count(UserFilter filter) {
        return userProfileDao.count(filter);
    }

    @Override
    public UserCredentials getByNameAndPassword(String userName, String password) {
        return userCredentialsDao.find(userName, password);
    }

    @Override
    public Collection<? extends String> resolveRoles(Long id) {
        UserCredentials userCredentials = userCredentialsDao.get(id);
        return Collections.singletonList(userCredentials.getRole().name());
    }

    @Override
    public void update(UserCredentials credentials) {
        userCredentialsDao.update(credentials);

    }
}
