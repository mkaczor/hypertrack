package pl.edu.agh.hypertrack.io;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class,property="name")
final class JsonSignal {
	
	@JsonProperty("name")
	private String signalName;
	
	@JsonProperty("control")
	private String controlType;

	@SuppressWarnings("unused")
	private JsonSignal() {}
	
	JsonSignal(String signalName) {
		this(signalName, null);
	}

	JsonSignal(String signalName, String controlType) {
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