package pl.edu.agh.hypertrack.io;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static pl.edu.agh.hypertrack.model.HyperflowInputSignalAssert.assertThat;
import static pl.edu.agh.hypertrack.model.HyperflowOutputSignalAssert.assertThat;

import org.junit.Test;

import pl.edu.agh.hypertrack.model.HyperflowInputSignal;
import pl.edu.agh.hypertrack.model.HyperflowOutputSignal;
import pl.edu.agh.hypertrack.model.HyperflowProcess;
import pl.edu.agh.hypertrack.model.HyperflowProcessType;
import pl.edu.agh.hypertrack.model.HypertrackEntityUniqueKey;

public class HyperflowSignalReaderTest {

	private static final String WORKFLOW_NAME = "workflowName";
	private static final String SIGNAL_NAME = "signalName";
	private static final String PROCESS_NAME = "processName";
	
	private HyperflowSignalReader signalReader = new HyperflowSignalReader();
	
	private HypertrackEntityUniqueKey signalKey = new HypertrackEntityUniqueKey(WORKFLOW_NAME, SIGNAL_NAME);
	private HypertrackEntityUniqueKey processKey = new HypertrackEntityUniqueKey(WORKFLOW_NAME, PROCESS_NAME);
	
	
	
	@Test
	public void shouldReadInputSignalWhenSignalNameAndTargetProcessSpecified() {
		
		//given
		HyperflowProcess targetProcess = new HyperflowProcess(processKey, HyperflowProcessType.DATAFLOW);
		
		//when
		HyperflowInputSignal inputSignal = signalReader.readInputSignal(targetProcess, SIGNAL_NAME);
		
		//then
		assertThat(inputSignal).hasKey(signalKey);
		assertThat(inputSignal).hasTarget(targetProcess);
		assertThat(inputSignal).hasSource(null); //TODO: hasNoSource
	}
	
	@Test
	public void shouldReadOutputSignalWhenSignalNameAndSourceProcessSpecified() {
		
		//given
		HyperflowProcess sourceProcess = new HyperflowProcess(processKey, HyperflowProcessType.DATAFLOW);
		
		//when
		HyperflowOutputSignal outputSignal = signalReader.readOutputSignal(sourceProcess, SIGNAL_NAME);
		
		//then
		assertThat(outputSignal).hasKey(signalKey);
		assertThat(outputSignal).hasSource(sourceProcess);
		assertThat(outputSignal).hasNoTarget();
	}
	
	@Test
	public void shouldThrowIllegalArgumentExceptionWhenReadingOutputSignalWithSameNameButDifferentSource() {
		
		//given
		HyperflowProcess sourceProcess = new HyperflowProcess(processKey, HyperflowProcessType.DATAFLOW);
		HyperflowProcess secondSourceProcess = new HyperflowProcess(
				new HypertrackEntityUniqueKey(WORKFLOW_NAME, "differentProcessName"), HyperflowProcessType.DATAFLOW);
		
		//when
		signalReader.readOutputSignal(sourceProcess, SIGNAL_NAME);
		Throwable thrown = catchThrowable(() -> signalReader.readOutputSignal(secondSourceProcess, SIGNAL_NAME));
		
		//then
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}
}
