package org.ecabrerar.examples.airalliance.web;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

/**
 * @author ecabrerar
 * @date   May 24, 2016
 */
@Stateless
public class AbstractBaseRestClient {

	@Inject HttpServletRequest httpServletRequest;

	private Client client;
	private WebTarget webTarget;

	@PostConstruct
    public void init() {
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
		.append("/")
		.append(httpServletRequest.getContextPath())
		.append("/webapi");

		return baseUri.toString();
	}

}
