package ch.hesge.model;

import java.util.Date;
import java.util.SortedMap;

public class Completion {
private String userName;
private SortedMap<Date,Integer> delaysBefore;


public Completion() {
	super();
	// TODO Auto-generated constructor stub
}
public Completion(String userName, SortedMap<Date, Integer> delaysBefore) {
	super();
	this.userName = userName;
	this.delaysBefore = delaysBefore;
}
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public SortedMap<Date, Integer> getDelaysBefore() {
	return delaysBefore;
}
public void setDelaysBefore(SortedMap<Date, Integer> delaysBefore) {
	this.delaysBefore = delaysBefore;
}

}
