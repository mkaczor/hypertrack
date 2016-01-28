package pl.edu.agh.hypertrack.model;

public class HyperflowInputSignal {

	private HypertrackEntityUniqueKey key;
	private HyperflowOutputSignal source;
	private HyperflowProcess target;
	private HyperflowInputActivationCondition activationCondition;
	
	public HypertrackEntityUniqueKey getKey() {
		return key;
	}
	
	public HyperflowOutputSignal getSource() {
		return source;
	}
	
	public HyperflowProcess getTarget() {
		return target;
	}
	
	public HyperflowInputActivationCondition getActivationCondition() {
		return activationCondition;
	}
}
