package pl.edu.agh.hypertrack.io;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

class JsonProcessInputSignal {
	
	private static final String DEFAULT_INDICATOR = "1";
	
	private String name;
	private String activationIndicator;
	
	public JsonProcessInputSignal(String jsonName) {
		String[] split = jsonName.split(":");
		name = split[0];
		if (split.length > 1) {
			activationIndicator = split[1];
		} else {
			activationIndicator = DEFAULT_INDICATOR;
		}
	}
	
	public String getName() {
		return name;
	}
	
	public String getActivationIndicator() {
		return activationIndicator;
	}
	
	@Override
	public String toString() {
		return name + ":" + activationIndicator;
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}