package ch.hesge.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

import ch.hesge.enums.ModuleType;

@Entity
@Table(name = "modules")
public class Module {

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "type")
	private String type;

	@Transient
	private ModuleType moduleType;

	@ManyToOne
	@JoinColumn(name="course_id")
	private Course course;
	
    @OneToOne
	private UserModule userModule;
	public Module() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ModuleType getModuleType() {
		return moduleType;
	}

	public void setModuleType(ModuleType moduleType) {
		this.moduleType = moduleType;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
	

	public UserModule getUserModule() {
		return userModule;
	}

	public void setUserModule(UserModule userModule) {
		this.userModule = userModule;
	}

	@PreUpdate
	@PrePersist
	private void beforeUpdatePersist() {
		type = moduleType != null ? moduleType.getType() : null;
	}

	@PostLoad
	private void afterLoad() {
		moduleType = type != null ? ModuleType.of(type) : null;
	}

	@Override
	public String toString() {
		return "Module [id=" + id + ", type=" + type + ", moduleType=" + moduleType + "]";
	}


}
