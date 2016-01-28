package pl.edu.agh.hypertrack.model;

import java.util.Collection;
import java.util.Optional;

public class HyperflowWorkflow {

	private String name;
	private Collection<HyperflowInputSignal> workflowInput;
	private Collection<HyperflowOutputSignal> workflowOutput;
	
	public String getName() {
		return name;
	}
	
	public Collection<HyperflowInputSignal> getWorkflowInput() {
		return workflowInput;
	}
	
	public Collection<HyperflowOutputSignal> getWorkflowOutput() {
		return workflowOutput;
	}
	
	public Optional<HyperflowProcess> getProcessForName(String name)
	{
		return null;
	}
	
	public Optional<HyperflowInputSignal> getInputSignalForName(String name)
	{
		return null;
	}
	
	public Optional<HyperflowOutputSignal> getOutputSignalForName(String name)
	{
		return null;
	}
	
	public Collection<HyperflowProcess> getProcesses()
	{
		return null;
	}
	
}
