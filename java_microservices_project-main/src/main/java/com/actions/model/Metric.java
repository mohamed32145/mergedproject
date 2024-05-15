package com.actions.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "metric")
public class Metric {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int metric_id ;
	
	@Column(name="name")
    private String name;
	
	@Column(name="metric")
    private String metric;
	
	@Column(name="threshold")
    private int threshold;
	
	@Column(name="time_frame_hours")
    private int time_frame_hours;

	public int getMetric_id() {
		return metric_id;
	}

	public void setMetric_id(int metric_id) {
		this.metric_id = metric_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMetric() {
		return metric;
	}

	public void setMetric(String metric) {
		this.metric = metric;
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	public int getTime_frame_hours() {
		return time_frame_hours;
	}

	public void setTime_frame_hours(int time_frame_hours) {
		this.time_frame_hours = time_frame_hours;
	}

	@Override
	public String toString() {
		return "Metric [metric_id=" + metric_id + ", name=" + name + ", metric=" + metric + ", threshold=" + threshold
				+ ", time_frame_hours=" + time_frame_hours + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
