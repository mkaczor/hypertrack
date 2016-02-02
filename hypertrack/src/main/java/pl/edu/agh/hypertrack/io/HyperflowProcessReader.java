package pl.edu.agh.hypertrack.io;

import pl.edu.agh.hypertrack.model.HyperflowProcess;
import pl.edu.agh.hypertrack.model.HypertrackEntityUniqueKey;

final class HyperflowProcessReader {
	
	private static final String PROCESS_TYPE = "type";
	
	private HyperflowSignalReader signalReader;
	private JsonProcessSignalsValidator signalsValidator;

	public HyperflowProcess read(String workflowName, JsonProcess jsonProcess) {
		signalsValidator.validate(jsonProcess);
		HypertrackEntityUniqueKey key = new HypertrackEntityUniqueKey(workflowName, jsonProcess.getProcessName());
		HyperflowProcess hyperflowProcess = new HyperflowProcess(key, jsonProcess.getProcessType(), jsonProcess.getProperties());
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
