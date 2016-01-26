package pl.edu.agh.hypertrack.io;

import java.util.Collection;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

final class JsonWorkflow {

	@JsonProperty("name")
	private String workflowName;
	
	@JsonProperty("processes")
	private Collection<JsonProcess> processes;
	
	@JsonProperty("signals")
	private Collection<JsonSignal> signals;
	
	@JsonProperty("ins")
	private Collection<String> inputSignals;
	
	@JsonProperty("outs")
	private Collection<String> outputSignals;

	private JsonWorkflow() {}
	
	public String getWorkflowName() {
		return workflowName;
	}
	
	public Collection<JsonProcess> getProcesses() {
		return processes;
	}
	
	public Collection<String> getInputSignals() {
		return inputSignals;
	}
	
	public Collection<String> getOutputSignals() {
		return outputSignals;
	}
	
	public Collection<JsonSignal> getSignals() {
		return signals;
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
