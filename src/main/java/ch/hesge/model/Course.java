package ch.hesge.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;


@Entity
@Table(name = "courses")
public class Course   {

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "title")
	private String title;

	@OrderBy("id")
	@OneToMany(mappedBy="course") // mappedBy = association owner, i.e. Module.course
	private List<Module> modules = new ArrayList<>();

	public Course() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Module> getModules() {
		return modules;
	}

//	public boolean isStartCourse() {
//		return id != null && id.equals(CourseService.START_COURSE_ID);
//	}
	
//	@Override
//	public int compareTo(Course c) {
//		
//		if (isStartCourse() && c.isStartCourse()) {
//			return 0;
//		}
//		else if (isStartCourse()) {
//			return -1;
//		}
//		else if (c.isStartCourse()) {
//			return 1;
//		}
//		
//		return id.compareTo(c.id);
//	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", title=" + title + "]";
	}


}
