package by.training.lysiuk.project.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class ScoresInSubjects extends AbstractModel {

	@ManyToOne(targetEntity = Enrolee.class, fetch = FetchType.LAZY)
	private Enrolee enrolee;

	@ManyToOne(targetEntity = ScoresInSubjects.class, fetch = FetchType.LAZY)
	private Subject subject;

	@Column
	private Integer points;

	public Enrolee getEnrolee() {
		return enrolee;
	}

	public void setEnrolee(Enrolee enrolee) {
		this.enrolee = enrolee;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

}
