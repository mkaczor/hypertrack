package pl.edu.agh.hypertrack.io;

import pl.edu.agh.hypertrack.model.HyperflowProcess;
import pl.edu.agh.hypertrack.model.HypertrackEntityUniqueKey;

class HyperflowProcessFactory {

	public HyperflowProcess createNewEmptyProcess(String workflowName, JsonProcess jsonProcess) {
		HypertrackEntityUniqueKey key = new HypertrackEntityUniqueKey(workflowName, jsonProcess.getProcessName());
		return new HyperflowProcess(key, jsonProcess.getProcessType(), jsonProcess.getProperties());
	}
}
