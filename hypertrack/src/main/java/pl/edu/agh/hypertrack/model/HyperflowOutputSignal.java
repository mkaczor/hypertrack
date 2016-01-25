package pl.edu.agh.hypertrack.model;

import java.util.Set;

public class HyperflowOutputSignal {

	private HypertrackEntityUniqueKey key;
	private HyperflowProcess source;
	private Set<HyperflowInputSignal> target;
}
