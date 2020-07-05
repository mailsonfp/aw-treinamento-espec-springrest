package com.algaworks.algafood.api.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class Problema {
	
	private Integer status;
	private String type;
	private String title;	
	private String detail;
	private String userMessage;
	private LocalDateTime timeStamp;
	private List<ProblemaObjects> objects;
	
	public static class ProblemaObjects {
		
		private String fieldName;
		private String fieldUserMessage;
		
		public ProblemaObjects(String fieldName, String fieldUserMessage) {
			super();
			this.fieldName = fieldName;
			this.fieldUserMessage = fieldUserMessage;
		}
		
		public String getFieldName() {
			return fieldName;
		}
		public void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}
		public String getfieldUserMessage() {
			return fieldUserMessage;
		}
		public void setfieldUserMessage(String fieldUserMessage) {
			this.fieldUserMessage = fieldUserMessage;
		}
	}
	
	public Problema(Integer status, String type, String title, String detail, String userMessage) {
		this.status = status;
		this.type = type;
		this.title = title;
		this.detail = detail;
		this.userMessage = userMessage;
		this.timeStamp = LocalDateTime.now();
		this.objects = null;
	}
	
	public Problema(Integer status, String type, String title, String detail, String userMessage, List<ProblemaObjects> fields) {
		this.status = status;
		this.type = type;
		this.title = title;
		this.detail = detail;
		this.userMessage = userMessage;
		this.timeStamp = LocalDateTime.now();
		this.objects = fields;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getUserMessage() {
		return userMessage;
	}

	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	public List<ProblemaObjects> getObjects() {
		return objects;
	}

	public void setObjects(List<ProblemaObjects> fields) {
		this.objects = fields;
	}
	
}
