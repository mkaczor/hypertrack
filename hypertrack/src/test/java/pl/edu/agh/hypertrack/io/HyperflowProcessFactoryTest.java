package pl.edu.agh.hypertrack.io;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.edu.agh.hypertrack.io.JsonProcessBuilder.aJsonProcess;
import static pl.edu.agh.hypertrack.model.HyperflowProcessAssert.assertThat;

import org.junit.Test;

import pl.edu.agh.hypertrack.model.HyperflowProcess;
import pl.edu.agh.hypertrack.model.HyperflowProcessType;
import pl.edu.agh.hypertrack.model.HypertrackEntityKey;

public class HyperflowProcessFactoryTest {

	private final static String WORKFLOW_NAME = "workflowName";
	private final static String PROCESS_NAME = "process";
	
	private HyperflowProcessFactory processFactory = new HyperflowProcessFactory();
	
	@Test
	public void shouldReadedProcessHasCorrectKey() {
		
		//given
		JsonProcess jsonProcess = aJsonProcess().withProcessName(PROCESS_NAME).build();
		
		//when
		HyperflowProcess hyperflowProcess = processFactory.createNewEmptyProcess(WORKFLOW_NAME, jsonProcess);
		
		//then
		assertThat(hyperflowProcess).hasKey(new HypertrackEntityKey(WORKFLOW_NAME, PROCESS_NAME));
	}
	
	@Test
	public void shouldReadedProcessHasSameTypeAsJsonProcess() {
		
		//given
		JsonProcess jsonProcess = aJsonProcess().withProcessType(HyperflowProcessType.FOREACH).build();
		
		//when
		HyperflowProcess hyperflowProcess = processFactory.createNewEmptyProcess(WORKFLOW_NAME, jsonProcess);
		
		//then
		assertThat(hyperflowProcess).hasProcessType(HyperflowProcessType.FOREACH);
	}
	
	@Test
	public void shouldReadedProcessHasSamePropertiesAsJsonProcess() {
		
		//given
		JsonProcess jsonProcess = aJsonProcess().withProperty("func", "funcVal").build();
		
		//when
		HyperflowProcess hyperflowProcess = processFactory.createNewEmptyProcess(WORKFLOW_NAME, jsonProcess);
		
		//then
		assertThat(hyperflowProcess.getProperties()).containsAllEntriesOf(jsonProcess.getProperties());
	}
	
}
