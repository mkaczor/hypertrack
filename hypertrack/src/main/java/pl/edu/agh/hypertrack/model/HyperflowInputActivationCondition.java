package pl.edu.agh.hypertrack.model;

import org.apache.commons.lang.builder.ToStringBuilder;

public class HyperflowInputActivationCondition {
	
	private int instancesToActivate;
	private String signalToCountName;
	private HyperflowOutputSignal signalToCount;
	
	private HyperflowInputActivationCondition(int instancesToActivate) {
		this.instancesToActivate = instancesToActivate;
	}
	
	private HyperflowInputActivationCondition(String signalToCountName) {
		this.signalToCountName = signalToCountName;
	}
	
	public static HyperflowInputActivationCondition fixedNumberOfSignalInstances(int instancesToActivate){
		return new HyperflowInputActivationCondition(instancesToActivate);
	}
	
	public static HyperflowInputActivationCondition countedNumberOfSignalInstances(String signalToCountName){
		return new HyperflowInputActivationCondition(signalToCountName);
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
