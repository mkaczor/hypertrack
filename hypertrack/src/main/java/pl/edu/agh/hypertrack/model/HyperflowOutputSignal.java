package pl.edu.agh.hypertrack.model;

import java.util.Set;

public class HyperflowOutputSignal {

	private HypertrackEntityUniqueKey key;
	private HyperflowProcess source;
	private Set<HyperflowInputSignal> target;
	
	public HypertrackEntityUniqueKey getKey() {
		return key;
	}
	
	public HyperflowProcess getSource() {
		return source;
	}
	
	public Set<HyperflowInputSignal> getTarget() {
		return target;
	}
}
