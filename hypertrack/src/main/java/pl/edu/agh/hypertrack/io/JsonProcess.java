package pl.edu.agh.hypertrack.io;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonProcess {

	@JsonProperty("name")
	private String processName;
	
	@JsonProperty("ins")
	private Set<String> inputSignals;
	
	@JsonProperty("outs")
	private Set<String> outputSignals;
	
	private Map<String, Object> properties = new HashMap<>();

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
}
