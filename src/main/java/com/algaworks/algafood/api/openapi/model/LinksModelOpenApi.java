package com.algaworks.algafood.api.openapi.model;

import io.swagger.annotations.ApiModel;

@ApiModel("Links")
public class LinksModelOpenApi {
	
	private LinkModel rel;
		
	public LinkModel getRel() {
		return rel;
	}
	public void setRel(LinkModel rel) {
		this.rel = rel;
	}


	@ApiModel("Link")
	public class LinkModel {
		
		private String href;
		private Boolean templated;
		public String getHref() {
			return href;
		}
		public void setHref(String href) {
			this.href = href;
		}
		public Boolean getTemplated() {
			return templated;
		}
		public void setTemplated(Boolean templated) {
			this.templated = templated;
		}
		
	}

}
