package pl.edu.agh.hypertrack.io;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

final class JsonProcess {

	private final String processName;
	private final Collection<String> inputSignals;
	private final Collection<String> outputSignals;
	private final Map<String, String> properties = new HashMap<>();

	@JsonCreator
	JsonProcess(@JsonProperty("name") String processName, 
			@JsonProperty("ins") Collection<String> inputSignals,
			@JsonProperty("outs") Collection<String> outputSignals) {
		this.processName = processName;
		this.inputSignals = inputSignals;
		this.outputSignals = outputSignals;
	}

	public String getProcessName() {
		return processName;
	}

	public Collection<String> getInputSignals() {
		return inputSignals;
	}

	public Collection<String> getOutputSignals() {
		return outputSignals;
	}

	public String getProperty(String propertyName) {
		return properties.get(propertyName);
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
