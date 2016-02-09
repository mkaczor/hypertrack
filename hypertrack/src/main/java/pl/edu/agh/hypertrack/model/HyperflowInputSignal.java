package pl.edu.agh.hypertrack.model;

public class HyperflowInputSignal {

	private HypertrackEntityKey key;
	private HyperflowOutputSignal source;
	private HyperflowProcess target;
	private HyperflowInputActivationIndicator activationIndicator;
	
	public HyperflowInputSignal(HyperflowProcess target, HypertrackEntityKey key) {
		this.target = target;
		this.key = key;
	}
	
	public void setActivationCondition(HyperflowInputActivationIndicator activationCondition) {
		this.activationIndicator = activationCondition;
	}
	
	public HypertrackEntityKey getKey() {
		return key;
	}
	
	public HyperflowOutputSignal getSource() {
		return source;
	}
	
	public HyperflowProcess getTarget() {
		return target;
	}
	
	public HyperflowInputActivationIndicator getActivationIndicator() {
		return activationIndicator;
	}
}
