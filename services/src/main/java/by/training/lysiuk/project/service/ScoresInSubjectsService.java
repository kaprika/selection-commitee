package by.training.lysiuk.project.service;

import java.util.List;

import javax.transaction.Transactional;

import by.training.lysiuk.project.datamodel.Enrolee;
import by.training.lysiuk.project.datamodel.Faculty;
import by.training.lysiuk.project.datamodel.ScoresInSubjects;

public interface ScoresInSubjectsService {
	ScoresInSubjects getScoresInSubjects(Long id);

	@Transactional
	void saveOrUpdate(ScoresInSubjects scoresInSubjects);

	@Transactional
	void delete(Long id);

	@Transactional
	void delete(ScoresInSubjects scoresInSubjects);
	
	List<ScoresInSubjects> getAll();
	
	List<ScoresInSubjects> getByDateAndFaculty(Faculty faculty);

	List<Integer> getSumPointsByDateAndFaculty(Faculty faculty);
	
	List<Enrolee> getEnroleesListByDateAndFAculty(Faculty faculty);
}
