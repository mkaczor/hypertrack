package pl.edu.agh.hypertrack.io;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.util.Collection;
import java.util.Set;

class JsonProcessValidator {

	public void validate(JsonProcess jsonProcess) {
		validateInputSignals(jsonProcess);
		validateOutputSignals(jsonProcess);
	}
	
	private void validateInputSignals(JsonProcess jsonProcess) {
		if (hasDuplicatedInputSignals(jsonProcess)) {
			throw new HypertrackJsonReadException("Process %s has duplicated input signals defined: %s",
					jsonProcess.getProcessName(), jsonProcess.getInputSignals());
		}
		if (hasNoInputSignals(jsonProcess)) {
			throw new HypertrackJsonReadException("Process %s has no input signals.", jsonProcess.getProcessName());
		}
	}
	
	private void validateOutputSignals(JsonProcess jsonProcess) {
		if (hasDuplicatedOutputSignals(jsonProcess)) {
			throw new HypertrackJsonReadException("Process %s has duplicated output signals defined: %s",
					jsonProcess.getProcessName(), jsonProcess.getInputSignals());
		}
		if (hasNoOutputSignals(jsonProcess)) {
			throw new HypertrackJsonReadException("Process %s has no output signals.", jsonProcess.getProcessName());
		}
	}

	private boolean hasDuplicatedInputSignals(JsonProcess jsonProcess) {
		return hasDuplicates(jsonProcess.getInputSignals().stream()
				.map(JsonProcessInputSignal::getName)
				.collect(toList()));
	}
	
	private boolean hasDuplicatedOutputSignals(JsonProcess jsonProcess) {
		return hasDuplicates(jsonProcess.getOutputSignals());
	}
	
	private boolean hasDuplicates(Collection<String> signals) {
		Set<String> signalsSet = signals.stream().collect(toSet());
		return signalsSet.size() != signals.size();
	}
	
	private boolean hasNoInputSignals(JsonProcess jsonProcess) {
		return jsonProcess.getInputSignals().isEmpty();
	}
	
	private boolean hasNoOutputSignals(JsonProcess jsonProcess) {
		return jsonProcess.getOutputSignals().isEmpty();
	}
}