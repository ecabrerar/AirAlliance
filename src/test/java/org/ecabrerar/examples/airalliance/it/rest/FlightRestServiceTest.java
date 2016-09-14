package org.ecabrerar.examples.airalliance.it.rest;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;

import java.io.StringReader;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
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
public class FlightRestServiceTest {

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
    @UsingDataSet(value = {"sectors.json", "flight.json"})
    public void shouldListFlights() {

		System.out.println(basePath+"webapi/flights");

		given()
		.when()
				.get(basePath+"webapi/flights")
		.then()
				.statusCode(Status.OK.getStatusCode())
				.body("", hasSize(1))
				.body("name", hasItem("AA056"));

    }

    @Test
    @UsingDataSet(value = {"sectors.json", "flight.json"})
    public void shouldFindFlight() {

    	String json = given().
				when().
						get(basePath+"webapi/flights/1"). //dataset has flight with id =1
				then().
						statusCode(Status.OK.getStatusCode()).
						body("id", equalTo(1)).
						body("name", equalTo("AA056")).extract().asString();

			try (JsonReader reader = Json.createReader(new StringReader(json))) {
			    JsonObject jsonObject = reader.readObject();
			    assertEquals("AA056", jsonObject.getString("name"));
			}
    }

    @Test
    @UsingDataSet(value = {"sectors.json"})
	public void shouldCreateFlight() {

    	JsonObject flightToCreate = Json.createObjectBuilder()
								    .add("name", "AA003")
					      		    .add("sourceSector",
					      				  			Json.createObjectBuilder()
					      				  			     .add("id", 1)
					      				  			     .add("sector", "BLR")
					      				  			     .build())
					      		    .add("destSector",
						                				  Json.createObjectBuilder()
					   				  			     .add("id", 3)
					   				  			     .add("sector", "BOM")
					   				  			     .build())
					      		    .build();

			given()
			        .content(flightToCreate.toString())
					.contentType("application/json")
			.when()
					.post(basePath + "webapi/flights")
			.then()
					.statusCode(Status.CREATED.getStatusCode()).extract()
					.asString();

			// new flight should be there
			given()
			.when()
					.get(basePath + "webapi/flights")
			.then()
					.statusCode(Status.OK.getStatusCode())
					.body("name", hasItem("AA003"));


	}


    @Test
    @UsingDataSet(value = {"sectors.json", "flight.json"})
    public void shouldUpdateFlight() {

		JsonObject flightToUpdate = Json.createObjectBuilder()
									  .add("id", 1)
			                		  .add("name", "AA057")
			                		  .add("sourceSector",
			                				  			Json.createObjectBuilder()
			                				  			     .add("id", 1)
			                				  			     .add("sector", "BLR")
			                				  			     .build())
			                		  .add("destSector",
						                				  Json.createObjectBuilder()
			             				  			     .add("id", 3)
			             				  			     .add("sector", "BOM")
			             				  			     .build())
			                		  .build();

		given().
	        	content(flightToUpdate.toString()).
	        	contentType("application/json").
	    when().
	        	put(basePath+"webapi/flights/1").  //dataset has flight with id =1
	    then().
	        	statusCode(Status.NO_CONTENT.getStatusCode());

    }


    @Test
    @UsingDataSet(value = {"sectors.json", "flight.json"})
    public void shouldDeleteFlight() {

		 given().
	         	contentType(ContentType.JSON).
	     when().
	         	delete(basePath+"webapi/flights/1").  //dataset has flight with id =1
	     then().
	         	statusCode(Status.NO_CONTENT.getStatusCode());

	    //AA056 should not be in db anymore
		given().
	    when().
	         	get(basePath + "webapi/flights").
	    then().
	         	statusCode(Status.OK.getStatusCode()).
	         	body("", hasSize(0)).
	         	body("name", not(hasItem("AA056")));

    }


}
