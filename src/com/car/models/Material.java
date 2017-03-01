package com.car.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_material")
public class Material {
	private Long id;
	private File file;
	
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "file_id") 
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
