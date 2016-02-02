package pl.edu.agh.hypertrack.io;

import java.util.Collection;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

final class JsonWorkflow {

	private final String workflowName;
	private final Collection<JsonProcess> processes;
	private final Collection<JsonSignal> signals;
	private final Collection<String> inputSignals;
	private final Collection<String> outputSignals;

	JsonWorkflow(@JsonProperty("name") String workflowName,
			@JsonProperty("processes") Collection<JsonProcess> processes,
			@JsonProperty("signals") Collection<JsonSignal> signals,
			@JsonProperty("ins") Collection<String> inputSignals,
			@JsonProperty("outs") Collection<String> outputSignals) {
		this.workflowName = workflowName;
		this.processes = processes;
		this.signals = signals;
		this.inputSignals = inputSignals;
		this.outputSignals = outputSignals;
	}
	
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
