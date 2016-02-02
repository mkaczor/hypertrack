package pl.edu.agh.hypertrack.model;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HyperflowProcess {

	private HypertrackEntityUniqueKey key;
	private HyperflowProcessType processType;
	private Map<String, String> properties;
	private Set<HyperflowInputSignal> inputSignals = new HashSet<>();
	private Set<HyperflowOutputSignal> outputSignals = new HashSet<>();
	
	public HyperflowProcess(HypertrackEntityUniqueKey key, HyperflowProcessType processType, Map<String, String> properties) {
		this.key = key;
		this.processType = processType;
		this.properties = properties;
	}
	
	public HypertrackEntityUniqueKey getKey() {
		return key;
	}
	
	public HyperflowProcessType getProcessType() {
		return processType;
	}
	
	public Set<HyperflowInputSignal> getInputSignals() {
		return inputSignals;
	}
	
	public Set<HyperflowOutputSignal> getOutputSignals() {
		return outputSignals;
	}
	
	public Map<String, String> getProperties() {
		return this.properties;
	}
	
	public void addInputSignal(HyperflowInputSignal inputSignal) {
		inputSignals.add(inputSignal);
	}
	
	public void addOutputSignal(HyperflowOutputSignal outputSignal) {
		outputSignals.add(outputSignal);
	}
}
