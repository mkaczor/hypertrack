package pl.edu.agh.hypertrack.io;

import pl.edu.agh.hypertrack.model.HyperflowInputActivationIndicator;
import pl.edu.agh.hypertrack.model.HyperflowInputSignal;
import pl.edu.agh.hypertrack.model.HyperflowOutputSignal;
import pl.edu.agh.hypertrack.model.HyperflowProcess;
import pl.edu.agh.hypertrack.model.HypertrackEntityKey;

class HyperflowSignalReader {

	private String workflowName;
	private JsonSignalValidator signalValidator;
	private HyperflowInputActivatioIndicatorFactory activationIndicatorFactory;
	
	public HyperflowSignalReader(String workflowName) {
		this.workflowName = workflowName;
	}
	
	public HyperflowInputSignal readInputSignal(HyperflowProcess target, JsonProcessInputSignal inputSignal) {
		signalValidator.validateInputSignal(inputSignal, target);
		HyperflowInputSignal hyperflowInputSignal = new HyperflowInputSignal(target,
				new HypertrackEntityKey(workflowName, inputSignal.getName()));
		HyperflowInputActivationIndicator activationIndicator = activationIndicatorFactory.createActivationIndicator(inputSignal);
		hyperflowInputSignal.setActivationCondition(activationIndicator);
		return hyperflowInputSignal;
	}
	
	public HyperflowOutputSignal readOutputSignal(HyperflowProcess source, String signal) {
		signalValidator.validateOutputSignal(signal, source);
		return new HyperflowOutputSignal(source, new HypertrackEntityKey(workflowName, signal));
	}
}
