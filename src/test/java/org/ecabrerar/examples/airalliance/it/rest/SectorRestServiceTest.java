package org.ecabrerar.examples.airalliance.it.rest;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasItem;

import java.net.URL;

import javax.ws.rs.core.Response.Status;

import org.ecabrerar.examples.airalliance.util.Deployments;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.MavenResolverSystem;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author ecabrerar
 * @date Sep 12, 2016
 */
@RunWith(Arquillian.class)
public class SectorRestServiceTest {

        @ArquillianResource
        URL basePath;
        
	@Deployment
	public static Archive<?> deployment() {
		WebArchive war = Deployments.getBaseDeployment();

		MavenResolverSystem resolver = Maven.resolver();
		war.addAsLibraries(resolver.loadPomFromFile("pom.xml")
				.resolve("com.jayway.restassured:rest-assured")
				.withTransitivity().asFile());

		System.out.println(war.toString(true));
		return war;
	}

	

	@Test 
	@UsingDataSet(value = { "sectors.json" })
	public void shouldListSectors() {

		System.out.println(basePath+"webapi/sectors");

		given()
		.when()
				.get(basePath+"webapi/sectors")
				.then()
				.statusCode(Status.OK.getStatusCode())
				.body("", hasSize(4))
				.body("sector", hasItem("CAL"));

	}
}