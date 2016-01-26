package pl.edu.agh.hypertrack.io;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

final class JsonSignal {
	
	private final String signalName;
	private final String controlType;

	JsonSignal(String signalName) {
		this(signalName, null);
	}

	@JsonCreator
	JsonSignal(@JsonProperty("name") String signalName, 
			   @JsonProperty("control") String controlType) {
		this.signalName = signalName;
		this.controlType = controlType;
	}
	
	
	public String getSignalName() {
		return signalName;
	}
	
	public String getControlType() {
		return controlType;
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	
}
