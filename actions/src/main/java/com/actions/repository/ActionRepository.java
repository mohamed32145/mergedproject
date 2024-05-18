package com.actions.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.actions.model.action;

import ch.qos.logback.core.joran.action.Action;

public interface ActionRepository extends JpaRepository<action, Integer> {
	
	//List<action> findByActionId(int actionid);
	
	
	
	

}
