package pl.edu.agh.hypertrack.io;

import static java.util.Arrays.asList;

import java.util.Collection;

public class JsonWorkflowBuilder {

	private String workflowName;
	private Collection<JsonProcess> processes;
	private Collection<JsonSignal> signals;
	private Collection<String> inputSignals;
	private Collection<String> outputSignals;
	
	public static JsonWorkflowBuilder aJsonWorkflow()
	{
		return new JsonWorkflowBuilder();
	}
	
	public JsonWorkflowBuilder withName(String workflowName) {
		this.workflowName = workflowName;
		return this;
	}
	
	public JsonWorkflowBuilder withProcesses(JsonProcess... processes) {
		this.processes = asList(processes);
		return this;
	}
	
	public JsonWorkflowBuilder withSignals(JsonSignal... signlas){
		this.signals = asList(signlas);
		return this;
	}
	
	public JsonWorkflowBuilder withInputSignals(String... inputSignals) {
		this.inputSignals = asList(inputSignals);
		return this;
	}
	
	public JsonWorkflowBuilder withOutputSignals(String... outputSignals) {
		this.outputSignals = asList(outputSignals);
		return this;
	}
	
	public JsonWorkflow build()
	{
		return new JsonWorkflow(workflowName, processes, signals, inputSignals, outputSignals);
	}
	
}
