package by.training.lysiuk.project.dataaccess.impl;

import org.springframework.stereotype.Repository;

import by.training.lysiuk.project.dataaccess.AdministratorDao;
import by.training.lysiuk.project.datamodel.Administrator;

@Repository
public class AdministratorDaoImpl extends AbstractDaoImpl<Administrator, Long> implements AdministratorDao {

	protected AdministratorDaoImpl() {
		super(Administrator.class);
	}
}
