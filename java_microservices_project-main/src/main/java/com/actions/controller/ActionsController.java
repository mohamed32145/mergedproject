package com.actions.controller;

import java.util.List;
import java.util.Map;

import com.actions.controller.client.loader.LoaderClient;
import com.actions.controller.client.loader.LoaderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.actions.model.action;
import com.actions.service.ActionsService;
import com.actions.service.exceptions.ActionNotFoundException;

import ch.qos.logback.core.joran.action.Action;

@RestController

public class ActionsController {

	@Autowired
	ActionsService actionservice;

	@Autowired
	private LoaderClient loaderClient;

	@GetMapping("/action/{actionid}")
	public ResponseEntity<?> getAction(@PathVariable int actionid) {

		try {
			action action1 = actionservice.getActionById(actionid);
			return new ResponseEntity<>(action1, HttpStatus.OK);

		} catch (ActionNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

		}

	}

	@GetMapping("/action/ConditionGet/{actionid}")
	public ResponseEntity<List<List<Integer>>> getCondition(@PathVariable int actionid) {

		try {
			action action1 = actionservice.getActionById(actionid);
			Map<String, Object> metric = action1.getCondition();

			List<List<Integer>> metrics = actionservice.getmetric(metric);
			return ResponseEntity.ok(metrics);

		} catch (ActionNotFoundException e) {
			throw new ActionNotFoundException("not found");

		}  
  
	} 

	@PostMapping(value = "/action/add", consumes = "application/json")
	public void createUser(@RequestBody action action1) {

		actionservice.addAction(action1);

	}

	@PutMapping("/action/update")
	public ResponseEntity<?> updateACtion(@RequestBody action updatedaction) {
		try {
			action action2 = actionservice.updateAction(updatedaction);
			return new ResponseEntity<>(action2, HttpStatus.OK);

		} catch (ActionNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

		}
	}

	@DeleteMapping("/action/delete/{actionId}")
	public ResponseEntity<?> deleteAction(@PathVariable int actionId) {

		try {
			actionservice.deleteAction(actionId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (ActionNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}

	}

	@PutMapping("/action/pause/{actionid}")
	public ResponseEntity<?> pauseAction(@PathVariable int actionid) {

		try {
			action action1 = actionservice.getActionById(actionid);
			action action2 = actionservice.reverseEnabled(action1);

			return new ResponseEntity<>(action2, HttpStatus.OK);

		} catch (ActionNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

		}

	}

	@PutMapping("/action/resume/{actionid}")
	public ResponseEntity<?> resumeAction(@PathVariable int actionid) {

		try {
			action action1 = actionservice.getActionById(actionid);
			action action2 = actionservice.reverseEnabled(action1);

			return new ResponseEntity<>(action2, HttpStatus.OK);

		} catch (ActionNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

		}

	}

	// endpoint fo the loader microservice
	@PostMapping("/manual-run/{loaderName}")
	public ResponseEntity<String> scanfiles(@PathVariable String loaderName,
									   @RequestBody LoaderRequest loaderRequest){
		return loaderClient.scanfiles(loaderName,loaderRequest);
	}

	@PostMapping("/enable/{loaderName}")
	public ResponseEntity<String> enableScan(@PathVariable String loaderName){
		return loaderClient.enableScan(loaderName);
	}

	@PostMapping("/disable/{loaderName}")
	public ResponseEntity<String> disableScan(@PathVariable String loaderName){
		return loaderClient.disableScan(loaderName);
	}

}
