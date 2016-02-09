package pl.edu.agh.hypertrack.io;

import static org.apache.commons.lang.StringUtils.isNumeric;
import static pl.edu.agh.hypertrack.model.HyperflowInputActivationIndicator.countedNumberOfSignalInstances;
import static pl.edu.agh.hypertrack.model.HyperflowInputActivationIndicator.fixedNumberOfSignalInstances;

import pl.edu.agh.hypertrack.model.HyperflowInputActivationIndicator;

public class HyperflowInputActivatioIndicatorFactory {

	private CountedSignalActivationIndicatorValidator validator;
	
	public HyperflowInputActivationIndicator createActivationIndicator(JsonProcessInputSignal inputSignal) {
		String activationIndicator = inputSignal.getActivationIndicator();
		if (isNumeric(activationIndicator)) {
			return fixedNumberOfSignalInstances(Integer.valueOf(activationIndicator));
		}
		validator.validateActivationIndicator(inputSignal);
		return countedNumberOfSignalInstances(activationIndicator);
	}
}
