package pl.edu.agh.hypertrack.io;

import pl.edu.agh.hypertrack.model.HyperflowProcess;

final class HyperflowProcessReader {

	private HyperflowSignalReader signalReader;
	private JsonProcessSignalsValidator signalsValidator;
	private HyperflowProcessFactory processFactory;

	public HyperflowProcess read(String workflowName, JsonProcess jsonProcess) {
		signalsValidator.validate(jsonProcess);
		HyperflowProcess hyperflowProcess = processFactory.createNewEmptyProcess(workflowName, jsonProcess);
		readProcessSignals(hyperflowProcess, jsonProcess);
		return hyperflowProcess;
	}
	
	private void readProcessSignals(HyperflowProcess readedProcess, JsonProcess jsonProcess) {
		readInputSignals(readedProcess, jsonProcess);
		readOutputSignals(readedProcess, jsonProcess);
	}
	
	private void readInputSignals(HyperflowProcess readedProcess, JsonProcess jsonProcess) {
		jsonProcess.getInputSignals().stream()
			.map(in -> signalReader.readInputSignal(readedProcess, in))
			.forEach(readedProcess::addInputSignal);
	}
	
	private void readOutputSignals(HyperflowProcess readedProcess, JsonProcess jsonProcess) {
		jsonProcess.getOutputSignals().stream()
				.map(out -> signalReader.readOutputSignal(readedProcess, out))
				.forEach(readedProcess::addOutputSignal);
	}
}
