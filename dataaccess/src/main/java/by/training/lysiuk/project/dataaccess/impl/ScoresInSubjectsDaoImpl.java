package by.training.lysiuk.project.dataaccess.impl;

import org.springframework.stereotype.Repository;
import by.training.lysiuk.project.dataaccess.ScoresInSubjectsDao;
import by.training.lysiuk.project.datamodel.ScoresInSubjects;

@Repository
public class ScoresInSubjectsDaoImpl extends AbstractDaoImpl<ScoresInSubjects, Long> implements ScoresInSubjectsDao {

	protected ScoresInSubjectsDaoImpl() {
		super(ScoresInSubjects.class);
	}
} 