package com.actions.model;

import java.time.LocalDate;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@Table(name = "actions")

public class action {

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "account_id")
	private String account_id;

	@Column(name = "create_date")
	private LocalDate create_date;

	@Column(name = "created_by")
	private String created_by;

	 @Column(name = "condition_json", columnDefinition = "json")
	    private String conditionJson;

	@Column(name = "action_type")
	private String action_type;

	@Column(name = "run_on_time")
	private String run_on_time;            

	@Column(name = "run_on_day")
	private int run_on_day;

	@Column(name = "notification_text")
	private String notification_text;

	@Column(name = "to_reciver")
	private String to_reciver;

	@Column(name = "is_enabled")
	private Boolean is_enabled;

	@Column(name = "is_deleted")
	private Boolean is_deleted;

	@Column(name = "last_update")
	private String last_update;

	@Column(name = "last_run")
	private String last_run;

 
	
	public void setCondition(Map<String, Object> condition) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            this.conditionJson = objectMapper.writeValueAsString(condition);
        } catch (JsonProcessingException e) {
            // Handle the exception appropriately (e.g., log or throw)
            e.printStackTrace();
        }
    }

    public Map<String, Object> getCondition() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(conditionJson, Map.class);
        } catch (JsonProcessingException e) {
            // Handle the exception appropriately (e.g., log or throw)
            e.printStackTrace();
            return null;
        }
    }

}
