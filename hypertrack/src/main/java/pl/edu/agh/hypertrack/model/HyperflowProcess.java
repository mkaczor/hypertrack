package pl.edu.agh.hypertrack.model;

import java.util.Set;

public class HyperflowProcess {

	private HypertrackEntityUniqueKey key;
	private HyperflowProcessType processType; //TODO: here we may store some extra info
//	private String function; //TODO: maybe some link to impl?, what about config?
//	private int parallelLevel;
//	private boolean ordered;
	private Set<HyperflowInputSignal> inputSignals;
	private Set<HyperflowOutputSignal> outputSignals;
	
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
}
