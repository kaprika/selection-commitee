package by.training.lysiuk.project.datamodel;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@MappedSuperclass
public class AbstractModel implements Serializable {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

}
