package ch.hesge.enums;

import java.util.stream.Stream;

public enum CompletionStatus {

	NOT_ATTEMPTED("not_attempted"), 
	INCOMPLETE("incomplete"), 
	COMPLETE("complete");

	private String type;

	CompletionStatus(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public static CompletionStatus of(String status) {
		return Stream.of(CompletionStatus.values())
			.filter(t -> t.getType().equals(status))
			.findFirst()
			.orElseThrow(IllegalArgumentException::new);
	}

}
