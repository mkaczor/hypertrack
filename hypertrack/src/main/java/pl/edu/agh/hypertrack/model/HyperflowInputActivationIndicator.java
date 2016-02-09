package pl.edu.agh.hypertrack.model;

import org.apache.commons.lang.builder.ToStringBuilder;

public class HyperflowInputActivationIndicator {
	//TODO: rozwazyc uzycie Optional (pytanie czy neo4j z tym wspolpracuje)
	private Integer instancesToActivate;
	private String signalToCountName;
	private HyperflowOutputSignal signalToCount;
	
	private HyperflowInputActivationIndicator(int instancesToActivate) {
		this.instancesToActivate = instancesToActivate;
	}
	
	private HyperflowInputActivationIndicator(String signalToCountName) {
		this.signalToCountName = signalToCountName;
	}
	
	public static HyperflowInputActivationIndicator fixedNumberOfSignalInstances(int instancesToActivate){
		return new HyperflowInputActivationIndicator(instancesToActivate);
	}
	
	public static HyperflowInputActivationIndicator countedNumberOfSignalInstances(String signalToCountName){
		return new HyperflowInputActivationIndicator(signalToCountName);
	}
	
	public HyperflowOutputSignal getSignalToCount() {
		return signalToCount;
	}
	
	public Integer getInstancesToActivate() {
		return instancesToActivate;
	}
	
	public String getSignalToCountName() {
		return signalToCountName;
	}
	
	public void setSignalToCount(HyperflowOutputSignal signalToCount) {
		this.signalToCount = signalToCount;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
