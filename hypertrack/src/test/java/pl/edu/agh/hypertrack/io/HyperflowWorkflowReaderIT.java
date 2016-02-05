package pl.edu.agh.hypertrack.io;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static pl.edu.agh.hypertrack.io.JsonWorkflowBuilder.aJsonWorkflow;
import static pl.edu.agh.hypertrack.model.HyperflowInputActivationCondition.fixedNumberOfSignalInstances;
import static pl.edu.agh.hypertrack.model.HyperflowInputSignalAssert.assertThat;
import static pl.edu.agh.hypertrack.model.HyperflowOutputSignalAssert.assertThat;
import static pl.edu.agh.hypertrack.model.HyperflowProcessAssert.assertThat;

import java.util.Iterator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pl.edu.agh.hypertrack.model.HyperflowInputSignal;
import pl.edu.agh.hypertrack.model.HyperflowOutputSignal;
import pl.edu.agh.hypertrack.model.HyperflowProcess;
import pl.edu.agh.hypertrack.model.HyperflowWorkflow;
import pl.edu.agh.hypertrack.model.HypertrackEntityKey;

@RunWith(MockitoJUnitRunner.class)
public class HyperflowWorkflowReaderIT {

	private final static String JSON_STRING = "json";
	private final static String WORKFLOW_NAME = "workflowName";
	private final static String WORKFLOW_INPUT_SIGNAL = "workflowInput";
	private final static String WORKFLOW_OUTPUT_SIGNAL = "workflowOutput";
	private final static String PROCESS_1_NAME = "process1";
	private final static String PROCESS_2_NAME = "process2";
	private final static String PROCESS_3_NAME = "process3";
	private final static String PROCESS_1_OUTPUT_SIGNAL_NAME = "p1Out";
	private final static String PROCESS_2_OUTPUT_SIGNAL_NAME = "p2Out";
	
	
	//TODO: byc moze osobno jakis process/signal reader....ale czy to ma sens?
	//TODO: prawdopodobnie trzeba wyciagnac signal provider
	@Mock
	private JsonWorkflowReader jsonReader;
	
	@InjectMocks
	private HyperflowWorkflowReader workflowReader;
	
	@Test
	public void shouldReadedWorkflowHaveInputSignalsLikeInJsonWorkflow()
	{
		//given
		given(jsonReader.read(JSON_STRING)).willReturn(aCorrectJsonWorkflow().build());
		
		//when
		HyperflowWorkflow workflow = workflowReader.read(JSON_STRING);
		
		//then
		assertThat(workflow.getWorkflowInput()).hasSize(1);
		HyperflowInputSignal workflowInput = workflow.getWorkflowInput().iterator().next();
		assertThat(workflowInput).hasTarget(workflow.getProcessForName(PROCESS_1_NAME).get()); //TODO: skad referencja na sygnal?
		assertThat(workflowInput).hasActivationCondition(fixedNumberOfSignalInstances(1));
		assertThat(workflowInput).hasSource(null); //TODO: hasNoSource
		assertThat(workflowInput).hasKey(new HypertrackEntityKey(WORKFLOW_NAME, WORKFLOW_INPUT_SIGNAL));
	}
	
	@Test
	public void shouldReadedWorkflowHaveOutputSignalsLikeInJsonWorkflow()
	{
		//given
		given(jsonReader.read(JSON_STRING)).willReturn(aCorrectJsonWorkflow().build());
		
		//when
		HyperflowWorkflow workflow = workflowReader.read(JSON_STRING);
		
		//then
		assertThat(workflow.getWorkflowOutput()).hasSize(1);
		HyperflowOutputSignal workflowOutput = workflow.getWorkflowOutput().iterator().next();
		assertThat(workflowOutput).hasKey(new HypertrackEntityKey(WORKFLOW_NAME, WORKFLOW_OUTPUT_SIGNAL));
		assertThat(workflowOutput).hasSource(workflow.getProcessForName(PROCESS_3_NAME).get());
		assertThat(workflowOutput).hasNoTarget();
	}
	
	@Test
	public void shouldReadedWorkflowHaveProcessLikeInJsonWorkflow() {
		
		// given
		given(jsonReader.read(JSON_STRING)).willReturn(aCorrectJsonWorkflow().build());

		// when
		HyperflowWorkflow workflow = workflowReader.read(JSON_STRING);
		
		//then
		assertThat(workflow.getProcesses()).hasSize(3);
		HyperflowProcess hyperflowProcess1 = workflow.getProcessForName(PROCESS_1_NAME).get();
		HyperflowProcess hyperflowProcess2 = workflow.getProcessForName(PROCESS_2_NAME).get();
		HyperflowProcess hyperflowProcess3 = workflow.getProcessForName(PROCESS_3_NAME).get();
		
		assertThat(hyperflowProcess1).hasKey(new HypertrackEntityKey(WORKFLOW_NAME, PROCESS_1_NAME));
		assertThat(hyperflowProcess1).hasOnlyInputSignals(workflow.getWorkflowInput().iterator().next());
		assertThat(hyperflowProcess1).hasOnlyOutputSignals(workflow.getOutputSignalForName(PROCESS_1_OUTPUT_SIGNAL_NAME).get());

		assertThat(hyperflowProcess2).hasKey(new HypertrackEntityKey(WORKFLOW_NAME, PROCESS_2_NAME));
		assertThat(hyperflowProcess2).hasOnlyInputSignals(workflow.getInputSignalForName(PROCESS_1_OUTPUT_SIGNAL_NAME).get());
		assertThat(hyperflowProcess2).hasOnlyOutputSignals(workflow.getOutputSignalForName(PROCESS_2_OUTPUT_SIGNAL_NAME).get());
		
		assertThat(hyperflowProcess3).hasKey(new HypertrackEntityKey(WORKFLOW_NAME, PROCESS_3_NAME));
		assertThat(hyperflowProcess3).hasOnlyInputSignals(
				workflow.getInputSignalForName(PROCESS_2_OUTPUT_SIGNAL_NAME).get(),
				workflow.getInputSignalForName(PROCESS_1_OUTPUT_SIGNAL_NAME).get());
		assertThat(hyperflowProcess3).hasOnlyOutputSignals(workflow.getWorkflowOutput().iterator().next());
	}
	
	@Test
	public void shouldReadedWorkflowHaveCorrectlyConnectedProcesses() {
		
		// given
		given(jsonReader.read(JSON_STRING)).willReturn(aCorrectJsonWorkflow().build());

		// when
		HyperflowWorkflow workflow = workflowReader.read(JSON_STRING);
		HyperflowOutputSignal process1outputSignal = workflow.getProcessForName(PROCESS_1_NAME).get().getOutputSignals().iterator().next();
		HyperflowInputSignal process2inputSignal = workflow.getProcessForName(PROCESS_2_NAME).get().getInputSignals().iterator().next();
		HyperflowOutputSignal process2outputSignal = workflow.getProcessForName(PROCESS_2_NAME).get().getOutputSignals().iterator().next();
		Iterator<HyperflowInputSignal> process3inputSignals = workflow.getProcessForName(PROCESS_3_NAME).get().getInputSignals().iterator();
		HyperflowInputSignal process3firstInputSignal = process3inputSignals.next();
		HyperflowInputSignal process3secondInputSignal = process3inputSignals.next();
		
		//then
		//TODO: co z kluczem -> wychodzi ze moze sie dublowac
		assertThat(process1outputSignal).hasOnlyTarget(process2inputSignal, process3secondInputSignal);//TODO: slabe -> do zmiany
		assertThat(process1outputSignal).hasSource(workflow.getProcessForName(PROCESS_1_NAME).get());
		
		assertThat(process2inputSignal).hasSource(process1outputSignal);
		assertThat(process2inputSignal).hasTarget(workflow.getProcessForName(PROCESS_2_NAME).get());
		assertThat(process2outputSignal).hasSource(workflow.getProcessForName(PROCESS_2_NAME).get());
		assertThat(process2outputSignal).hasOnlyTarget(process3firstInputSignal);
		
		assertThat(process3firstInputSignal).hasTarget(workflow.getProcessForName(PROCESS_3_NAME).get());
		assertThat(process3firstInputSignal).hasSource(process2outputSignal);
		assertThat(process3secondInputSignal).hasTarget(workflow.getProcessForName(PROCESS_3_NAME).get());
		assertThat(process3secondInputSignal).hasSource(process1outputSignal);
	}
	
	private JsonWorkflowBuilder aCorrectJsonWorkflow() {
		JsonProcess process1 = new JsonProcess(PROCESS_1_NAME, asList(WORKFLOW_INPUT_SIGNAL), asList(PROCESS_1_OUTPUT_SIGNAL_NAME));
		JsonProcess process2 = new JsonProcess(PROCESS_2_NAME, asList(PROCESS_1_OUTPUT_SIGNAL_NAME), asList(PROCESS_1_OUTPUT_SIGNAL_NAME));
		JsonProcess process3 = new JsonProcess(PROCESS_3_NAME, asList(PROCESS_1_OUTPUT_SIGNAL_NAME, PROCESS_2_OUTPUT_SIGNAL_NAME), asList(WORKFLOW_OUTPUT_SIGNAL));
		JsonSignal inputSignal = new JsonSignal(WORKFLOW_INPUT_SIGNAL);
		JsonSignal process1Signal = new JsonSignal(PROCESS_1_OUTPUT_SIGNAL_NAME);
		JsonSignal process2Signal = new JsonSignal(PROCESS_2_OUTPUT_SIGNAL_NAME);
		JsonSignal outputSignal = new JsonSignal(WORKFLOW_OUTPUT_SIGNAL);
		return aJsonWorkflow().withName(WORKFLOW_NAME)
			.withInputSignals(WORKFLOW_INPUT_SIGNAL)
			.withOutputSignals(WORKFLOW_OUTPUT_SIGNAL)
			.withSignals(inputSignal, process1Signal, process2Signal, outputSignal)
			.withProcesses(process1, process2, process3);
	}
}
