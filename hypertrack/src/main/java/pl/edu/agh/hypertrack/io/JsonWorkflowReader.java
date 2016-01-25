package pl.edu.agh.hypertrack.io;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonWorkflowReader {

	public JsonWorkflow readWorkflow(String json)
	{
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(json, JsonWorkflow.class);
		} catch (IOException e) {
			throw new IllegalArgumentException("Error reading " + json, e);
		}
	}
}
