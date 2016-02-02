package pl.edu.agh.hypertrack.io;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class,property="name")
final class JsonProcess {

	@JsonProperty("name") 
	private String processName;
	
	@JsonProperty("ins")
	private Collection<String> inputSignals;
	
	@JsonProperty("outs")
	private Collection<String> outputSignals;
	private Map<String, String> properties = new HashMap<>();

	@SuppressWarnings("unused")
	private JsonProcess() {}
	
	JsonProcess(String processName, Collection<String> inputSignals, Collection<String> outputSignals) {
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
