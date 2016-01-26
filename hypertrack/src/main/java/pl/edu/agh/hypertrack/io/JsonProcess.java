package pl.edu.agh.hypertrack.io;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

final class JsonProcess {

	private final String processName;
	private final Set<String> inputSignals;
	private final Set<String> outputSignals;
	private final Map<String, Object> properties = new HashMap<>();

	@JsonCreator
	JsonProcess(@JsonProperty("name") String processName, 
			    @JsonProperty("ins") Set<String> inputSignals,
			    @JsonProperty("outs") Set<String> outputSignals) {
		this.processName = processName;
		this.inputSignals = inputSignals;
		this.outputSignals = outputSignals;
	}

	public String getProcessName() {
		return processName;
	}

	public Set<String> getInputSignals() {
		return inputSignals;
	}

	public Set<String> getOutputSignals() {
		return outputSignals;
	}

	@JsonAnyGetter
	public Map<String, Object> getProperties() {
		return properties;
	}

	@JsonAnySetter
	public void setProperty(String propertyName, String propertyValue) {
		properties.put(propertyName, propertyValue);
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
