package by.training.lysiuk.project.service;

import java.util.List;

import javax.transaction.Transactional;

import by.training.lysiuk.project.dataaccess.filters.EnrolleeFilter;
import by.training.lysiuk.project.datamodel.Enrolee;
import by.training.lysiuk.project.datamodel.Faculty;
import by.training.lysiuk.project.datamodel.PlanSet;
import by.training.lysiuk.project.datamodel.ScoresInSubjects;

public interface EnroleeService {

	Enrolee getEnrolee(Long id);

	@Transactional
	void saveOrUpdate(Enrolee enrolee);

	@Transactional
	void saveOrUpdateEnroleeWithPoints(Enrolee enrolee, List<ScoresInSubjects> scoresInSubjects);

	@Transactional
	void delete(Long id);

	@Transactional
	void delete(Enrolee enrolee);

	List<Enrolee> getAll();

	Long count(EnrolleeFilter filter);

	List<Enrolee> find(EnrolleeFilter filter);

	Long countByDateAndFaculty(Faculty faculty);

	List<Enrolee> getEnroleesByPlanSet(PlanSet planSet);

	Enrolee getByIdentificationNumber(String value);

}
