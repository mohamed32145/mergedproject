package com.actions.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.plaf.ActionMapUIResource;
//import javax.swing.text.html.HTMLDocument.Iterator;
import java.util.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.actions.model.action;
import com.actions.repository.ActionRepository;
import com.actions.service.exceptions.ActionNotFoundException;
import com.actions.service.exceptions.actionAlreadyExitException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ch.qos.logback.core.joran.action.Action;

@Service
public class ActionsService {

	@Autowired
	ActionRepository actionrepository;

	public action addAction(action action) {

		if (actionrepository.existsById(action.getId())) {
			throw new actionAlreadyExitException("action already exists");

		}
		System.out.println(action.getIs_enabled());
		return actionrepository.save(action);

	}

	public action getActionById(int actionid) {
		return actionrepository.findById(actionid)
				.orElseThrow(() -> new ActionNotFoundException("action not found with ID: " + actionid));

	}

	public action updateAction(action updatedaction) {

		if (actionrepository.existsById(updatedaction.getId())) {
			throw new actionAlreadyExitException("action already exists");

		}
		return actionrepository.save(updatedaction);
	}

	public void deleteAction(int actionId) {
		if (!actionrepository.existsById(actionId)) {
			throw new ActionNotFoundException("action not found with ID: " + actionId);
		}
		actionrepository.deleteById(actionId);

	}

	public action reverseEnabled(action action) {
		// Reverse the enabled property
		action.setIs_enabled(!action.getIs_enabled());

		// Save the modified action to the repository
		action savedAction = actionrepository.save(action);

		// Check if the save operation was successful
		if (savedAction == null) {
			throw new RuntimeException("Failed to save action");
		}

		return savedAction;
	}

	public List<List<Integer>> getmetric(Map<String, Object> metric) {
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			String metricJson = objectMapper.writeValueAsString(metric); // Convert map to JSON string
			JsonNode jsonNode = objectMapper.readTree(metricJson); // Parse JSON string
			List<List<Integer>> integerLists = new ArrayList<>();

			Iterator<Map.Entry<String, JsonNode>> fieldsIterator = jsonNode.fields();
			while (fieldsIterator.hasNext()) {
				Map.Entry<String, JsonNode> entry = fieldsIterator.next();
				JsonNode arrayNode = entry.getValue();

				List<Integer> integerList = new ArrayList<>();
				for (JsonNode node : arrayNode) {
					integerList.add(Integer.parseInt(node.asText()));
				}
				integerLists.add(integerList);
			}
       
			return integerLists;  
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}

	}

}
