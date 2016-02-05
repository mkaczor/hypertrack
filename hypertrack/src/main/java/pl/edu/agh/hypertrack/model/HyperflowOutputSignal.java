package pl.edu.agh.hypertrack.model;

import java.util.HashSet;
import java.util.Set;

public class HyperflowOutputSignal {

	private HypertrackEntityKey key;
	private HyperflowProcess source;
	private Set<HyperflowInputSignal> target = new HashSet<>();
	
	public HyperflowOutputSignal(HyperflowProcess source, HypertrackEntityKey key) {
		this.source = source;
		this.key = key;
	}
	
	public HypertrackEntityKey getKey() {
		return key;
	}
	
	public HyperflowProcess getSource() {
		return source;
	}
	
	public Set<HyperflowInputSignal> getTarget() {
		return target;
	}
}
