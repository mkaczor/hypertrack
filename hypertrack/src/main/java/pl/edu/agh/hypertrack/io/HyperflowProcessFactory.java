package pl.edu.agh.hypertrack.io;

import pl.edu.agh.hypertrack.model.HyperflowProcess;
import pl.edu.agh.hypertrack.model.HypertrackEntityKey;

class HyperflowProcessFactory {

	public HyperflowProcess createNewEmptyProcess(String workflowName, JsonProcess jsonProcess) {
		HypertrackEntityKey key = new HypertrackEntityKey(workflowName, jsonProcess.getProcessName());
		return new HyperflowProcess(key, jsonProcess.getProcessType(), jsonProcess.getProperties());
	}
}
