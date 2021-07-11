package ch.hesge.enums;

import java.util.stream.Stream;

public enum ModuleType {
	
	INITIAL("T0"),
	CONTENT("A"),
	RESUME("B"),
	EXERCICE("E"),
	TEST("T"),
	DELAY("D");
	
	private String type;
	
	private ModuleType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
	public static ModuleType of(String type) {
		return Stream.of(ModuleType.values())
			.filter(t -> t.getType().equals(type))
			.findFirst()
			.orElseThrow(IllegalArgumentException::new);
	}
	
}
