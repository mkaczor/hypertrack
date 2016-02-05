package pl.edu.agh.hypertrack.io;

import static org.apache.commons.lang.StringUtils.isNumeric;

import java.util.Map;

import pl.edu.agh.hypertrack.model.HyperflowInputActivationCondition;
import pl.edu.agh.hypertrack.model.HyperflowInputSignal;
import pl.edu.agh.hypertrack.model.HyperflowOutputSignal;
import pl.edu.agh.hypertrack.model.HyperflowProcess;
import pl.edu.agh.hypertrack.model.HypertrackEntityKey;

class HyperflowSignalReader {

	private Map<String, JsonSignal> jsonSignals;
	private String workflowName;
	
	public HyperflowSignalReader(String workflowName) {
		this.workflowName = workflowName;
	}
	
	public HyperflowInputSignal readInputSignal(HyperflowProcess target, String signal) {
		InputSignalDescriprion inputSignalDescriprion = new InputSignalDescriprion(signal);
		throwExceptionIfNoInputSignalWithGivenNameSpecified(inputSignalDescriprion.getName(), target);
		HyperflowInputSignal hyperflowInputSignal = new HyperflowInputSignal(target, new HypertrackEntityKey(workflowName, signal));
		HyperflowInputActivationCondition activationCondition = getActivationCondition(inputSignalDescriprion);
		hyperflowInputSignal.setActivationCondition(activationCondition);
		return hyperflowInputSignal;
		
	}
	
	private void throwExceptionIfNoInputSignalWithGivenNameSpecified(String signal, HyperflowProcess target) {
		if (!jsonSignals.containsKey(signal)) {
			throw new HypertrackJsonReadException("No signal named %s defined, but used as input to process %s", signal,
					target.getKey().getEntityName());
		}
	}
	
	private HyperflowInputActivationCondition getActivationCondition(InputSignalDescriprion inputDescription) {
		String activationIndicator = inputDescription.getActivationIndicator();
		if (isNumeric(inputDescription.getActivationIndicator())) {
			return HyperflowInputActivationCondition.fixedNumberOfSignalInstances(Integer.valueOf(activationIndicator));
		}
		throwExceptionIfActivationIndicatorPointsToNonExistingSignal(inputDescription);
		throwExceptionIfActivationIndicatorPointsToNonCountSignal(inputDescription);
		return HyperflowInputActivationCondition.countedNumberOfSignalInstances(activationIndicator);
		
	}
	
	private void throwExceptionIfActivationIndicatorPointsToNonExistingSignal(InputSignalDescriprion inputDescription) {
		String activationIndicator = inputDescription.getActivationIndicator();
		if (!jsonSignals.containsKey(activationIndicator)) {
			throw new HypertrackJsonReadException("No signal named %s defined, but used as count by signal %s", activationIndicator,
					inputDescription.getName());
		}
	}
	
	private void throwExceptionIfActivationIndicatorPointsToNonCountSignal(InputSignalDescriprion inputDescription) {
		String activationIndicator = inputDescription.getActivationIndicator();
		JsonSignal jsonSignal = jsonSignals.get(activationIndicator);
		if (isNotCountSignal(jsonSignal)) {
			throw new HypertrackJsonReadException("Signal named %s is not count signal, but used as one by signal %s", activationIndicator,
					inputDescription.getName());
		}
	}
	
	private boolean isNotCountSignal(JsonSignal jsonSignal) {
		return !"count".equalsIgnoreCase(jsonSignal.getControlType());
	}
	
	public HyperflowOutputSignal readOutputSignal(HyperflowProcess source, String signal) {
		throwExceptionIfNoOutputSignalWithGivenNameSpecified(signal, source);
		return new HyperflowOutputSignal(source, new HypertrackEntityKey(workflowName, signal));
	}
	
	private void throwExceptionIfNoOutputSignalWithGivenNameSpecified(String signal, HyperflowProcess source) {
		if (!jsonSignals.containsKey(signal)) {
			throw new HypertrackJsonReadException("No signal named %s defined, but used as output of process %s", signal,
					source.getKey().getEntityName());
		}
	}
	
	private static class InputSignalDescriprion {
		
		private String name;
		private String activationIndicator;
		
		public InputSignalDescriprion(String jsonName) {
			String[] split = jsonName.split(":");
			name = split[0];
			if (split.length > 1) {
				activationIndicator = split[1];
			} else {
				activationIndicator = "1";
			}
		}
		
		public String getName() {
			return name;
		}
		
		public String getActivationIndicator() {
			return activationIndicator;
		}
	}
}
