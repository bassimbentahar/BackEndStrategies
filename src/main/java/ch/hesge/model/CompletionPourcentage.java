package ch.hesge.model;

public class CompletionPourcentage {
 private String  userName;
 private Double initial;
 private Double content;
 private Double resume;
 private Double test;
 
 
 
public CompletionPourcentage() {
	super();
}

public CompletionPourcentage(String userName, Double initial, Double content, Double resume, Double test) {
	super();
	this.userName = userName;
	this.initial = initial;
	this.content = content;
	this.resume = resume;
	this.test = test;
}
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public Double getInitial() {
	return initial;
}
public void setInitial(Double initial) {
	this.initial = initial;
}
public Double getContent() {
	return content;
}
public void setContent(Double content) {
	this.content = content;
}
public Double getResume() {
	return resume;
}
public void setResume(Double resume) {
	this.resume = resume;
}
public Double getTest() {
	return test;
}
public void setTest(Double test) {
	this.test = test;
}

@Override
public String toString() {
	return "CompletionPourcentage [userName=" + userName + ", initial=" + initial + ", content=" + content + ", resume="
			+ resume + ", test=" + test + "]";
}
 
 
}
