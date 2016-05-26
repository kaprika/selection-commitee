package by.training.lysiuk.project.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import by.training.lysiuk.project.dataaccess.PlanSetDao;
import by.training.lysiuk.project.dataaccess.filters.PlanSetFilter;
import by.training.lysiuk.project.dataaccess.impl.AbstractDaoImpl;
import by.training.lysiuk.project.datamodel.PlanSet;
import by.training.lysiuk.project.service.PlanSetService;

@Service
public class PlanSetServiceImpl extends AbstractDaoImpl<PlanSet, Long> implements PlanSetService {

	protected PlanSetServiceImpl() {
		super(PlanSet.class);
	}

	@Inject
	private PlanSetDao dao;

	@Override
	public Long count(PlanSetFilter filter) {
		return dao.count(filter);
	}

	@Override
	public List<PlanSet> find(PlanSetFilter filter) {
		return dao.find(filter);
	}

	@Override
	public void saveOrUpdate(PlanSet planSet) {
		if (planSet.getId() == null) {
			dao.insert(planSet);
		} else {
			dao.update(planSet);
		}
	}

	@Override
	public void delete(PlanSet planSet) {
		dao.delete(planSet.getId());
	}

	@Override
	public PlanSet getPlanSet(Long id) {
		return dao.get(id);
	}

	@Override
	public void delete(Long id) {
		dao.delete(id);
	}

	@Override
	public List<PlanSet> getAll() {
		return dao.getAll();
	}

}
