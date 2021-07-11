package ch.hesge.model;

import java.util.List;
import java.util.Map;

public class Suivi {
	List<Map<String,String>> strategiesList;
	String userName;
	
	
	public Suivi() {
		super();
	}
	
	public List<Map<String, String>> getStrategiesList() {
		return strategiesList;
	}

	public void setStrategiesList(List<Map<String, String>> strategies) {
		this.strategiesList = strategies;
	}

	public Suivi( List<Map<String, String>> strategies, String userName) {
		super();

		this.strategiesList = strategies;
		this.userName = userName;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
