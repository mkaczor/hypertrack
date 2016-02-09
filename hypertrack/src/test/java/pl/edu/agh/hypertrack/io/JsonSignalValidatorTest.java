package pl.edu.agh.hypertrack.io;

import static java.util.Collections.emptyMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pl.edu.agh.hypertrack.model.HyperflowProcess;
import pl.edu.agh.hypertrack.model.HyperflowProcessType;
import pl.edu.agh.hypertrack.model.HypertrackEntityKey;

@RunWith(MockitoJUnitRunner.class)
public class JsonSignalValidatorTest {

	private static final String INPUT_SIGNAL_NAME = "input";
	private static final String PROCESS_NAME = "process";
	private static final String OUTPUT_SIGNAL_NAME = "output";
	
	@Mock
	private Map<String, JsonSignal> jsonSignals;
	
	@InjectMocks
	private JsonSignalValidator validator;
	
	private HyperflowProcess process = new HyperflowProcess(new HypertrackEntityKey("", PROCESS_NAME),
			HyperflowProcessType.DATAFLOW, emptyMap());
	
	@Test
	public void shouldThrowExceptionWhenNoInputSignalWithGivenNameSpecified() {
		
		//given
		JsonProcessInputSignal input = new JsonProcessInputSignal(INPUT_SIGNAL_NAME);
		
		//when
		Throwable thrown = catchThrowable(() -> validator.validateInputSignal(input, process));
						
		//then
		assertThat(thrown).isInstanceOf(HypertrackJsonReadException.class).hasMessage(
				"No signal named " + INPUT_SIGNAL_NAME + " defined, but used as input to process " + PROCESS_NAME);
	}
	
	@Test
	public void shouldNotThrowExceptionWhenInputSignalWithGivenNameSpecified() {
		
		//given
		JsonProcessInputSignal input = new JsonProcessInputSignal(INPUT_SIGNAL_NAME);
		given(jsonSignals.containsKey(INPUT_SIGNAL_NAME)).willReturn(true);
		
		//when
		Throwable thrown = catchThrowable(() -> validator.validateInputSignal(input, process));
						
		//then
		assertThat(thrown).isNull();
	}
	
	
	@Test
	public void shouldThrowExceptionWhenNoOutputSignalWithGivenNameSpecified() {
		
		//when
		Throwable thrown = catchThrowable(() -> validator.validateOutputSignal(OUTPUT_SIGNAL_NAME, process));
						
		//then
		assertThat(thrown).isInstanceOf(HypertrackJsonReadException.class).hasMessage(
				"No signal named " + OUTPUT_SIGNAL_NAME + " defined, but used as output of process " + PROCESS_NAME);
	}
	
	@Test
	public void shouldNotThrowExceptionWhenOutputSignalWithGivenNameSpecified() {
		
		//given
		given(jsonSignals.containsKey(OUTPUT_SIGNAL_NAME)).willReturn(true);
		
		//when
		Throwable thrown = catchThrowable(() -> validator.validateOutputSignal(OUTPUT_SIGNAL_NAME, process));
						
		//then
		assertThat(thrown).isNull();
	}
}
