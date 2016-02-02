package pl.edu.agh.hypertrack.io;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.Collections;

import org.junit.Test;

public class JsonProcessSignalsValidatorTest {

	private static final String PROCESS_NAME = "name";
	private static final String INPUT_SIGNAL = "in";
	private static final String OUTPUT_SIGNAL = "out";
	
	private JsonProcessSignalsValidator validator = new JsonProcessSignalsValidator();
	
	@Test
	public void shouldThrowHypertrackJsonReadExceptionWhenValidatingProcessWithDuplicatedInputSignals() {
		
		//given
		JsonProcess jsonProcess = new JsonProcess(PROCESS_NAME, asList(INPUT_SIGNAL, INPUT_SIGNAL), asList(OUTPUT_SIGNAL));
		
		//when
		Throwable thrown = catchThrowable(() -> validator.validate(jsonProcess));
				
		//then
		assertThat(thrown).isInstanceOf(HypertrackJsonReadException.class).hasMessageContaining("duplicated input signals defined");
	}
	
	@Test
	public void shouldThrowHypertrackJsonReadExceptionWhenValidatingProcessWithNoInputSignals() {

		// given
		JsonProcess jsonProcess = new JsonProcess(PROCESS_NAME, emptyList(), asList(OUTPUT_SIGNAL));

		// when
		Throwable thrown = catchThrowable(() -> validator.validate(jsonProcess));

		// then
		assertThat(thrown).isInstanceOf(HypertrackJsonReadException.class).hasMessageContaining("no input signals");
	}
	
	@Test
	public void shouldThrowHypertrackJsonReadExceptionWhenValidatingProcessWithDuplicatedOutputSignals() {
		
		//given
		JsonProcess jsonProcess = new JsonProcess(PROCESS_NAME, asList(INPUT_SIGNAL), asList(OUTPUT_SIGNAL, OUTPUT_SIGNAL));
		
		//when
		Throwable thrown = catchThrowable(() -> validator.validate(jsonProcess));
				
		//then
		assertThat(thrown).isInstanceOf(HypertrackJsonReadException.class).hasMessageContaining("duplicated output signals defined");
	}
	
	@Test
	public void shouldThrowHypertrackJsonReadExceptionWhenValidatingProcessWithNoOutputSignals() {

		// given
		JsonProcess jsonProcess = new JsonProcess(PROCESS_NAME, asList(INPUT_SIGNAL), Collections.emptyList());

		// when
		Throwable thrown = catchThrowable(() -> validator.validate(jsonProcess));

		// then
		assertThat(thrown).isInstanceOf(HypertrackJsonReadException.class).hasMessageContaining("no output signals");
	}
	
	@Test
	public void shouldNotThrowExceptionWhenValidatingProcessWithCorrectSignalsDefinition() {
		
		// given
		JsonProcess jsonProcess = new JsonProcess(PROCESS_NAME, asList(INPUT_SIGNAL), asList(OUTPUT_SIGNAL));

		// when
		Throwable thrown = catchThrowable(() -> validator.validate(jsonProcess));

		// then
		assertThat(thrown).isNull();
	}
}
