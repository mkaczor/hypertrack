package pl.edu.agh.hypertrack.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class HyperflowInputActivationCondition {
	
	private int instancesToActivate;
	private HyperflowOutputSignal countedSignal;
	
	private HyperflowInputActivationCondition(int instancesToActivate) {
		this.instancesToActivate = instancesToActivate;
	}
	
	private HyperflowInputActivationCondition(HyperflowOutputSignal countedSignal) {
		this.countedSignal = countedSignal;
	}
	
	public static HyperflowInputActivationCondition fixedNumberOfSignalInstances(int instancesToActivate){
		return new HyperflowInputActivationCondition(instancesToActivate);
	}
	
	public static HyperflowInputActivationCondition countedNumberOfSignalInstances(HyperflowOutputSignal countedSignal){
		return new HyperflowInputActivationCondition(countedSignal);
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}
