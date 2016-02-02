package pl.edu.agh.hypertrack.io;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import pl.edu.agh.hypertrack.model.HyperflowProcessType;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class,property="name")
final class JsonProcess {

	@JsonProperty("name") 
	private String processName;
	
	@JsonProperty("type")
	@JsonFormat(shape= JsonFormat.Shape.STRING)
	private HyperflowProcessType processType = HyperflowProcessType.DATAFLOW;

	@JsonProperty("ins")
	private Collection<String> inputSignals;
	
	@JsonProperty("outs")
	private Collection<String> outputSignals;
	
	
	private Map<String, String> properties = new HashMap<>();

	@SuppressWarnings("unused")
	private JsonProcess() {}
	
	JsonProcess(String processName, HyperflowProcessType processType, Collection<String> inputSignals,
			Collection<String> outputSignals) {
		this(processName, inputSignals, outputSignals);
		this.processType = processType;
	}

	JsonProcess(String processName, Collection<String> inputSignals,
			Collection<String> outputSignals) {
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

	public Map<String, String> getProperties() {
		return properties;
	}
	
	public HyperflowProcessType getProcessType() {
		return processType;
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
