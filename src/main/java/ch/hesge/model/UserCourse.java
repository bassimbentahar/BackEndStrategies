package ch.hesge.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "user_courses")
public class UserCourse {

	@Id
	@Column(name = "id")
	private String id;
		
	@Column(name = "moduleRule")
	private String moduleRule;

	@Column(name = "active")
	private boolean active;

	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToOne
	@JoinColumn(name="course_id")
	private Course course;

	@OrderBy("id")
	@OneToMany(mappedBy="userCourse") // mappedBy = association owner, i.e. UserModule.userCourse
	private List<UserModule> userModules  = new ArrayList<>();
	
	@Transient
	private long sendDelay;
	
	public UserCourse() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean isActive) {
		this.active = isActive;
	}

	public String getModuleRule() {
		return moduleRule;
	}

	public void setModuleRule(String moduleRule) {
		this.moduleRule = moduleRule;
	}

	public long getSendDelay() {
		return sendDelay;
	}

	public void setSendDelay(long sendDelay) {
		this.sendDelay = sendDelay;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public List<UserModule> getUserModules() {
		return userModules;
	}

	@PreUpdate
	@PrePersist
	private void beforeMergeOrPersist() {
		this.id = user.getId() + "_" + course.getId();
	}
	
	@Override
	public String toString() {
		return "UserCourse [id=" + id + ", isActive=" + active + ", moduleRule=" + moduleRule + ", sendDelay=" + sendDelay + "]";
	}

}
