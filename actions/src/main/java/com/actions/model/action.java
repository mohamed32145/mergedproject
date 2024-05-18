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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}
//
//	public String getAccount_id() {
//		return account_id;
//	}
//
//	public void setAccount_id(String account_id) {
//		this.account_id = account_id;
//	}
//
//	public LocalDate getCreate_date() {
//		return create_date;
//	}
//
//	public void setCreate_date(LocalDate create_date) {
//		this.create_date = create_date;
//	}
//
//	public String getCreated_by() {
//		return created_by;
//	}
//
//	public void setCreated_by(String created_by) {
//		this.created_by = created_by;
//	}
 
	
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
//
//	public String getAction_type() {
//		return action_type;
//	}
//
//	public void setAction_type(String action_type) {
//		this.action_type = action_type;
//	}
//
//	public String getRun_on_time() {
//		return run_on_time;
//	}
//
//	public void setRun_on_time(String run_on_time) {
//		this.run_on_time = run_on_time;
//	}
//
//	public int getRun_on_day() {
//		return run_on_day;
//	}
//
//	public void setRun_on_day(int run_on_day) {
//		this.run_on_day = run_on_day;
//	}
//
//	public String getNotification_text() {
//		return notification_text;
//	}
//
//	public void setNotification_text(String notification_text) {
//		this.notification_text = notification_text;
//	}
//
//	public String getTo() {
//		return to_reciver;
//	}
//
//	public void setTo(String to) {
//		this.to_reciver = to;
//	}
//
//	public Boolean getEnabled() {
//		return is_enabled;
//	}
//
//	public void setEnabled(Boolean is_enabled) {
//		this.is_enabled = is_enabled;
//	}
//
//	public Boolean isIs_deleted() {
//		return is_deleted;
//	}
//	
//
//	public void setIs_deleted(Boolean is_deleted) {
//		this.is_deleted = is_deleted;
//	}
//
//	public String getLast_update() {
//		return last_update;
//	}
//
//	public void setLast_update(String last_update) {
//		this.last_update = last_update;
//	}
//
//	public String getLast_run() {
//		return last_run;
//	}
//
//	public void setLast_run(String last_run) {
//		this.last_run = last_run;
//	}
///*
//	@Override
//	public String toString() {
//		return "Action [id=" + id + ", account_id=" + account_id + ", create_date=" + create_date + ", created_by="
//				+ created_by + ", condition=" + condition + ", action_type=" + action_type + ", run_on_time="
//				+ run_on_time + ", run_on_day=" + run_on_day + ", notification_text=" + notification_text + ", to=" + to
//				+ ", isEnabled=" + isEnabled + ", is_deleted=" + is_deleted + ", last_update=" + last_update
//				+ ", last_run=" + last_run + "]";
//	}*/

}
