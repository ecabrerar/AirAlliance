package org.ecabrerar.examples.airalliance.it.rest;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

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

import com.jayway.restassured.http.ContentType;

/**
 * @author ecabrerar
 * @date   Sep 13, 2016
 */
@RunWith(Arquillian.class)
public class ItineraryRestServiceTest {

	@ArquillianResource
    URL basePath;

    @Deployment
    public static Archive<?> deployment() {

    	MavenResolverSystem resolver = Maven.resolver();

		WebArchive war = Deployments.getBaseDeployment();

		war.addAsLibraries(resolver.loadPomFromFile("pom.xml")
							.resolve("com.jayway.restassured:rest-assured").withTransitivity().asFile());

		System.out.println(war.toString(true));

		return war;
    }

    @Test
    @UsingDataSet(value = {"itinerary.yml","schedule.yml","guests.json","sectors.json", "flight.json"})
    public void shouldListItineraries() {

		System.out.println(basePath+"webapi/itineraries");

		given()
		.when()
				.get(basePath+"webapi/itineraries")
		.then()
				.statusCode(Status.OK.getStatusCode())
				.body("", hasSize(1));

    }

    @Test
    @UsingDataSet(value = {"itinerary.yml","schedule.yml","guests.json","sectors.json", "flight.json"})
    public void shouldFindItinerary() {

    	given().
				when().
						get(basePath+"webapi/itineraries/1"). //dataset has itinerary with id =1
				then().
						statusCode(Status.OK.getStatusCode()).
						body("id", equalTo(1)).extract().asString();


    }

    @Test
    @UsingDataSet(value = {"itinerary.yml","schedule.yml","guests.json","sectors.json", "flight.json"})
    public void shouldDeleteItinerary() {

		 given().
	         	contentType(ContentType.JSON).
	     when().
	         	delete(basePath+"webapi/itineraries/1").  //dataset has itinerary with id =1
	     then().
	         	statusCode(Status.NO_CONTENT.getStatusCode());

	    //id 1 should not be in db anymore
		given().
	    when().
	         	get(basePath + "webapi/itineraries").
	    then().
	         	statusCode(Status.OK.getStatusCode()).
	         	body("", hasSize(0));

    }


}
