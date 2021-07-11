package ch.hesge.model;

import java.util.Date;

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

import ch.hesge.enums.CompletionStatus;
import ch.hesge.enums.ModuleType;

@Entity
@Table(name = "user_modules")
public class UserModule {

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "sendDate")
	private Date sendDate;

	@Column(name = "startDate")
	private Date startDate;

	@Column(name = "endDate")
	private Date endDate;

	@Column(name = "testSuccess")
	private boolean success;

	@Column(name = "testResponse")
	private String testResponse;

	@Column(name = "status")
	private String status;

	@OneToOne
	@JoinColumn(name="module_id")
	private Module module;

	@ManyToOne
	@JoinColumn(name="user_course_id")
	private UserCourse userCourse;

	@Transient
	private int sendDelay;

	@Transient
	private CompletionStatus completionStatus;

	public UserModule() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getTestResponse() {
		return testResponse;
	}

	public void setTestResponse(String testResponse) {
		this.testResponse = testResponse;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getSendDelay() {
		return sendDelay;
	}

	public void setSendDelay(int sendDelay) {
		this.sendDelay = sendDelay;
	}

	public CompletionStatus getCompletionStatus() {
		return completionStatus;
	}

	public void setCompletionStatus(CompletionStatus completionStatus) {
		this.completionStatus = completionStatus;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public UserCourse getUserCourse() {
		return userCourse;
	}
	
	public void setUserCourse(UserCourse userCourse) {
		this.userCourse = userCourse;
	}

	@PreUpdate
	@PrePersist
	private void beforeUpdatePersist() {
		status = completionStatus != null ? completionStatus.getType() : null;
	}

	@PostLoad
	private void afterLoad() {
		completionStatus = status != null ? CompletionStatus.of(status) : null;
	}

	@Override
	public String toString() {
		return "UserModule [id=" + id + ", sendDate=" + sendDate + ", startDate=" + startDate + ", endDate=" + endDate + ", success=" + success + ", testResponse=" + testResponse + ", status=" + status + ", sendDelay=" + sendDelay + ", completionStatus=" + completionStatus + "]";
	}
	public Integer supposedOrder() {
		if(ModuleType.INITIAL.equals(getModule().getModuleType())) {
			return 1;
		}else if(ModuleType.CONTENT.equals(getModule().getModuleType())) {
			return 2;
		}else if(ModuleType.RESUME.equals(getModule().getModuleType())) {
			return 3;
		}else {
			return 4;
		}
	}

}
