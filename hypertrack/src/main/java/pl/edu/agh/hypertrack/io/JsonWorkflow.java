package pl.edu.agh.hypertrack.io;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonWorkflow {

	private String name;
	private Collection<JsonProcess> processes;
	private Collection<JsonSignal> signals;
	private Collection<String> inputSignals;
	private Collection<String> outputSignals;
	
	public String getName() {
		return name;
	}
	
	public Collection<JsonProcess> getProcesses() {
		return processes;
	}
	
	@JsonProperty("ins")
	public Collection<String> getInputSignals() {
		return inputSignals;
	}
	
	@JsonProperty("outs")
	public Collection<String> getOutputSignals() {
		return outputSignals;
	}
	
	@JsonProperty("signals")
	public Collection<JsonSignal> getSignals() {
		return signals;
	}
}
