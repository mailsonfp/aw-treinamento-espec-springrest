package com.algaworks.algafood.api.ExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@JsonInclude(Include.NON_EMPTY)
public class Problema {
	
	@ApiModelProperty(example="404", position = 1)
	private Integer status;
	
	@ApiModelProperty(example="https://algafood.com.br/recurso-nao-encontrado", position = 11)
	private String type;
	
	@ApiModelProperty(example="Recurso não encontrado", position = 21)
	private String title;	
	
	@ApiModelProperty(example="Não foi possível localizar uma cidade com o código: 4", position = 31)
	private String detail;
	
	@ApiModelProperty(example="Não foi possível localizar uma cidade com o código: 4", position = 41)
	private String userMessage;
	
	@ApiModelProperty(example="2020-07-20T01:51:36.3619628Z", position = 51)
	private OffsetDateTime timeStamp;
	
	@ApiModelProperty(value="Quando houver, retorna uma lista de objetos ou campos que geraram erro", position = 61)
	private List<ProblemaObjects> objects;
	
	@ApiModel("Problema-objetos")
	public static class ProblemaObjects {
		
		@ApiModelProperty(example = "nome", position = 71)
		private String fieldName;
		
		@ApiModelProperty(example = "é um informação obrigatória", position = 81)
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
		this.timeStamp = OffsetDateTime.now();
		this.objects = null;
	}
	
	public Problema(Integer status, String type, String title, String detail, String userMessage, List<ProblemaObjects> fields) {
		this.status = status;
		this.type = type;
		this.title = title;
		this.detail = detail;
		this.userMessage = userMessage;
		this.timeStamp = OffsetDateTime.now();
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

	public OffsetDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(OffsetDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	public List<ProblemaObjects> getObjects() {
		return objects;
	}

	public void setObjects(List<ProblemaObjects> fields) {
		this.objects = fields;
	}
	
}
