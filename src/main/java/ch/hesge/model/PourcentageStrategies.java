package ch.hesge.model;

import java.util.Map;

public class PourcentageStrategies {
	private String userName;
	private Map<String,Double> pourcentagesStrategy;
	public PourcentageStrategies(String userName, Map<String, Double> pourcentagesStrategy) {
		super();
		this.userName = userName;
		this.pourcentagesStrategy = pourcentagesStrategy;
	}
	public PourcentageStrategies() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Map<String, Double> getPourcentagesStrategy() {
		return pourcentagesStrategy;
	}
	public void setPourcentagesStrategy(Map<String, Double> pourcentagesStrategy) {
		this.pourcentagesStrategy = pourcentagesStrategy;
	}
	
}
