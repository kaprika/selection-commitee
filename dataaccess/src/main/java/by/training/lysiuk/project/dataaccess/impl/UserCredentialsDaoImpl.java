package by.training.lysiuk.project.dataaccess.impl;

import org.springframework.stereotype.Repository;
import by.training.lysiuk.project.dataaccess.UserCredentialsDao;
import by.training.lysiuk.project.datamodel.UserCredentials;

@Repository
public class UserCredentialsDaoImpl extends AbstractDaoImpl<UserCredentials, Long> implements UserCredentialsDao {

	protected UserCredentialsDaoImpl() {
		super(UserCredentials.class);
	}
} 