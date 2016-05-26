package by.training.lysiuk.project.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import by.training.lysiuk.project.dataaccess.EnroleeDao;
import by.training.lysiuk.project.dataaccess.filters.EnroleeFilter;
import by.training.lysiuk.project.dataaccess.impl.AbstractDaoImpl;
import by.training.lysiuk.project.datamodel.Enrolee;
import by.training.lysiuk.project.service.EnroleeService;

@Service
public class EnroleeServiceImpl extends AbstractDaoImpl<Enrolee, Long> implements EnroleeService {

	protected EnroleeServiceImpl() {
		super(Enrolee.class);
	}

	@Inject
	private EnroleeDao dao;

	@Override
	public Enrolee getEnrolee(Long id) {
		return dao.get(id);
	}

	@Override
	public void saveOrUpdate(Enrolee enrolee) {
		if (enrolee.getId() == null) {
			dao.insert(enrolee);
		} else {
			dao.update(enrolee);
		}
	}

	@Override
	public void delete(Long id) {
		dao.delete(id);
	}

	@Override
	public List<Enrolee> getAll() {
		return dao.getAll();
	}

	@Override
	public Long count(EnroleeFilter filter) {
		return dao.count(filter);
	}

	@Override
	public List<Enrolee> find(EnroleeFilter filter) {
		return dao.find(filter);
	}

	@Override
	public void delete(Enrolee enrolee) {
		dao.delete(enrolee.getId());
	}
}
