package pl.edu.agh.hypertrack.io;

import java.util.Map;

import pl.edu.agh.hypertrack.model.HyperflowProcess;

class JsonSignalValidator {

	private Map<String, JsonSignal> jsonSignals; 
	
	public void validateInputSignal(JsonProcessInputSignal inputSignal, HyperflowProcess target) {
		throwExceptionIfNoInputSignalWithGivenNameSpecified(inputSignal.getName(), target);
	}
	
	private void throwExceptionIfNoInputSignalWithGivenNameSpecified(String signal, HyperflowProcess target) {
		if (!jsonSignals.containsKey(signal)) {
			throw new HypertrackJsonReadException("No signal named %s defined, but used as input to process %s", signal,
					target.getKey().getEntityName());
		}
	}
	
	public void validateOutputSignal(String outputSignal, HyperflowProcess source) {
		throwExceptionIfNoOutputSignalWithGivenNameSpecified(outputSignal, source);
	}

	private void throwExceptionIfNoOutputSignalWithGivenNameSpecified(String signal, HyperflowProcess source) {
		if (!jsonSignals.containsKey(signal)) {
			throw new HypertrackJsonReadException("No signal named %s defined, but used as output of process %s", signal,
					source.getKey().getEntityName());
		}
	}
}
