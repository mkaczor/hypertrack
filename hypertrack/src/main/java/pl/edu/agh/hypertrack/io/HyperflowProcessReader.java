package pl.edu.agh.hypertrack.io;

import static java.util.stream.Collectors.toSet;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import pl.edu.agh.hypertrack.model.HyperflowProcess;
import pl.edu.agh.hypertrack.model.HyperflowProcessType;
import pl.edu.agh.hypertrack.model.HypertrackEntityUniqueKey;

class HyperflowProcessReader {
	
	private static final String PROCESS_TYPE = "type";
	
	private String workflowName;
	private Map<String, HyperflowProcess> readedProcesses = new HashMap<>();

	private HyperflowSignalReader signalReader;
	
	public Set<HyperflowProcess> read(JsonWorkflow jsonWorkflow){
		
		workflowName = jsonWorkflow.getWorkflowName(); //TODO: konstruktor
		return jsonWorkflow.getProcesses().stream().map(this::readProcess).collect(toSet());
	}

	private HyperflowProcess readProcess(JsonProcess jsonPorcess) {
		
		if (processWithSameNameAlreadyRead(jsonPorcess)) {
			throw new IllegalArgumentException(String.format("Process name %s definition duplicated within workflow %s",
					jsonPorcess.getProcessName(), workflowName));
		}
		return readNewProcess(jsonPorcess);
	}
	
	private boolean processWithSameNameAlreadyRead(JsonProcess process) {
		return readedProcesses.containsKey(process.getProcessName());
	}
	
	private HyperflowProcess readNewProcess(JsonProcess process) {
		
		HypertrackEntityUniqueKey key = new HypertrackEntityUniqueKey(workflowName, process.getProcessName());
		HyperflowProcess hyperflowProcess = new HyperflowProcess(key, getProcessType(process));
		readProcessSignals(hyperflowProcess, process);
		readedProcesses.put(process.getProcessName(), hyperflowProcess);
		return hyperflowProcess;
	}
	
	private HyperflowProcessType getProcessType(JsonProcess process) {
		Optional<String> processType = Optional.ofNullable(process.getProperty(PROCESS_TYPE));
		return processType.map(HyperflowProcessType::valueOf).orElse(HyperflowProcessType.DATAFLOW);
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
