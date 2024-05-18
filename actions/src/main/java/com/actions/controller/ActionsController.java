package com.actions.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.actions.feginclient.LoaderClient;
import com.actions.feginclient.LoaderRequest;
import com.actions.service.TokenValidationService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.actions.model.action;
import com.actions.service.ActionsService;
import com.actions.service.exceptions.ActionNotFoundException;

import ch.qos.logback.core.joran.action.Action;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ActionsController {

	@Autowired
	ActionsService actionService;
	@Autowired
	private TokenValidationService tokenValidationService;

	@Autowired
	private LoaderClient loaderClient;

	@GetMapping("/action/{actionId}")
	public ResponseEntity<?> getAction(@PathVariable int actionId) {

		try {
			action action1 = actionService.getActionById(actionId);
			return new ResponseEntity<>(action1, HttpStatus.OK);

		} catch (ActionNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

		}

	}

	@GetMapping("/action/ConditionGet/{actionId}")
	public ResponseEntity<List<List<Integer>>> getCondition(@PathVariable int actionId) {

		try {
			action action1 = actionService.getActionById(actionId);
			Map<String, Object> metric = action1.getCondition();

			List<List<Integer>> metrics = actionService.getmetric(metric);
			return ResponseEntity.ok(metrics);

		} catch (ActionNotFoundException e) {
			throw new ActionNotFoundException("not found");

		}  
  
	} 

	@PostMapping(value = "/action/add", consumes = "application/json")
	public ResponseEntity<?> createAction(@RequestHeader("Authorization") String token, @RequestBody action action1) throws Exception {

		if(tokenValidationService.isValidToken(token)){
			List<String> roles = tokenValidationService.getUserRoles(token);
			System.out.println(roles);
			if(roles.contains("CREATE_ACTION") || roles.contains("ADMIN")){

				actionService.addAction(action1);
				return ResponseEntity.status(HttpStatus.CREATED).body("Action creation successfully");
			}else {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Sorry you don't have the permissions");
			}
		}else{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid or expired token");
		}


	}

	@PutMapping("/action/update")
	public ResponseEntity<?> updateAction(@RequestHeader("Authorization") String token, @RequestBody action updatedaction) {
//		try {
//			action action2 = actionService.updateAction(updatedaction);
//			return new ResponseEntity<>(action2, HttpStatus.OK);
//
//		} catch (ActionNotFoundException e) {
//			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//
//		}
		if(tokenValidationService.isValidToken(token)){
			List<String> roles = tokenValidationService.getUserRoles(token);
			if(roles.contains("UPDATE_ACTION") || roles.contains("ADMIN")){

				action action2 = actionService.updateAction(updatedaction);
				return ResponseEntity.status(HttpStatus.CREATED).body("Action updated successfully");
			}else {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Sorry you don't have the permissions");
			}
		}else{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid or expired token");
		}
	}

	@DeleteMapping("/action/delete/{actionId}")
	public ResponseEntity<?> deleteAction(@RequestHeader("Authorization") String token,@PathVariable int actionId) {
//
//		try {
//			actionService.deleteAction(actionId);
//			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//		} catch (ActionNotFoundException e) {
//			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//		}

		if(tokenValidationService.isValidToken(token)){
			List<String> roles = tokenValidationService.getUserRoles(token);
			if(roles.contains("DELETE_ACTION") || roles.contains("ADMIN")){

				actionService.deleteAction(actionId);
				return ResponseEntity.status(HttpStatus.CREATED).body("Action deleted successfully");
			}else {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Sorry you don't have the permissions");
			}
		}else{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid or expired token");
		}
	}

	@PutMapping("/action/pause/{actionId}")
	public ResponseEntity<?> pauseAction(@PathVariable int actionId) {

		try {
			action action1 = actionService.getActionById(actionId);
			action action2 = actionService.reverseEnabled(action1);

			return new ResponseEntity<>(action2, HttpStatus.OK);

		} catch (ActionNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

		}

	}

	@PutMapping("/action/resume/{actionId}")
	public ResponseEntity<?> resumeAction(@PathVariable int actionId) {

		try {
			action action1 = actionService.getActionById(actionId);
			action action2 = actionService.reverseEnabled(action1);

			return new ResponseEntity<>(action2, HttpStatus.OK);

		} catch (ActionNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

		}

	}

	// endpoint fo the loader microservice
	@PostMapping("/manual-run/{loaderName}")
	public ResponseEntity<String> scanfiles(@RequestHeader("Authorization") String token,@PathVariable String loaderName,
											@RequestBody LoaderRequest loaderRequest){
		try {


			return loaderClient.scanfiles(token, loaderName, loaderRequest);
		}
	 catch (FeignException.Forbidden ex) {
		// If 403 Forbidden received from Feign client, return 403 from your API
		 return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid or expired token");
	}
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
