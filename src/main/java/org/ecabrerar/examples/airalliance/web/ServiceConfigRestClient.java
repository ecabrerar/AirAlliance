package org.ecabrerar.examples.airalliance.web;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

/**
 * @author ecabrerar
 * @date   May 24, 2016
 */
@Singleton
public class ServiceConfigRestClient implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject HttpServletRequest httpServletRequest;
	Client client;
	WebTarget webTarget;

	@PostConstruct
	public void init(){
		client = ClientBuilder.newClient();
		webTarget = client.target(getBaseUri());
	}

	public WebTarget getWebTarget() {
	     return webTarget;
	}

	public String getBaseUri() {

		final StringBuilder baseUri = new StringBuilder(45)
		.append("http://")
		.append(httpServletRequest.getLocalName())
		.append(":")
		.append(httpServletRequest.getLocalPort())
		.append("/");

		if("localhost".equals(httpServletRequest.getLocalName())
		   || "127.0.0.1".equals(httpServletRequest.getLocalName())){

			baseUri.append(httpServletRequest.getContextPath())
			       .append("/");
		}



		baseUri.append("webapi");

		return baseUri.toString();
	}

}
