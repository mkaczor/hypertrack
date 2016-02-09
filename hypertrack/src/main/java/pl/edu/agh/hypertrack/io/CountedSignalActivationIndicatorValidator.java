package pl.edu.agh.hypertrack.io;

import java.util.Map;

class CountedSignalActivationIndicatorValidator {

	private Map<String, JsonSignal> jsonSignals; 
	
	public void validateActivationIndicator(JsonProcessInputSignal inputSignal) {
		if (signalToBeCountedNotDefined(inputSignal)) {
			throw new HypertrackJsonReadException("No signal named %s defined, but used as count by signal %s",
					inputSignal.getActivationIndicator(), inputSignal.getName());
		}
		if (signalToBeCountedIsNotOfCountType(inputSignal)) {
			throw new HypertrackJsonReadException("Signal named %s is not count signal, but used as one by signal %s",
					inputSignal.getActivationIndicator(), inputSignal.getName());
		}
	}
	
	private boolean signalToBeCountedNotDefined(JsonProcessInputSignal inputSignal) {
		return !jsonSignals.containsKey(inputSignal.getActivationIndicator());
	}
	
	private boolean signalToBeCountedIsNotOfCountType(JsonProcessInputSignal inputSignal) {
		String activationIndicator = inputSignal.getActivationIndicator();
		JsonSignal jsonSignal = jsonSignals.get(activationIndicator);
		return !"count".equalsIgnoreCase(jsonSignal.getControlType());
	}
}
