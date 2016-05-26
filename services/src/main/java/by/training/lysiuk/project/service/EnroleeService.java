package by.training.lysiuk.project.service;

import java.util.List;

import javax.transaction.Transactional;

import by.training.lysiuk.project.dataaccess.filters.EnroleeFilter;
import by.training.lysiuk.project.datamodel.Enrolee;

public interface EnroleeService {
	
	Enrolee getEnrolee(Long id);

	@Transactional
	void saveOrUpdate(Enrolee enrolee);

	@Transactional
	void delete(Long id);
	
	@Transactional
	void delete(Enrolee enrolee);

	List<Enrolee> getAll();

	Long count(EnroleeFilter filter);

	List<Enrolee> find(EnroleeFilter filter);

}
