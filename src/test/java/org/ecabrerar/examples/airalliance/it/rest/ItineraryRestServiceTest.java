package org.ecabrerar.examples.airalliance.it.rest;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import java.net.URL;
import java.time.LocalDate;

import javax.json.Json;
import javax.json.JsonObject;
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

		given()
		.when()
				.get(basePath+"webapi/itineraries")
		.then()
				.statusCode(Status.OK.getStatusCode())
				.body("", hasSize(1));

    }

    @Test
    @UsingDataSet(value = {"schedule.yml","guests.json","sectors.json", "flight.json"})
    public void shouldCreateItinerary(){

    	JsonObject itineraryToCreate = Json.createObjectBuilder()
    									.add("guest", Json.createObjectBuilder()
												 		.add("id", 1)
													    .add("firstname", "Frank")
													    .add("lastname","Jennings")
													    .build())
    									.add("flight", Json.createObjectBuilder()
													  .add("id", 1)
							                		  .add("name", "AA056")
							                		  .add("sourceSector",
							                				  			Json.createObjectBuilder()
							                				  			     .add("id", 1)
							                				  			     .add("sector", "BLR")
							                				  			     .build())
							                		  .add("destSector",
										                				  Json.createObjectBuilder()
							             				  			     .add("id", 3)
							             				  			     .add("sector", "BOM")
							             				  			     .build()))
    									.add("schedule", Json.createObjectBuilder()
		    		    								  .add("id", 1)
		    											  .add("guest",  Json.createObjectBuilder()
		    														 		.add("id", 1)
		    															    .add("firstname", "Frank")
		    															    .add("lastname","Jennings")
		    															    .build())
		    											  .add("flight",  Json.createObjectBuilder()
		    															  .add("id", 1)
		    									                		  .add("name", "AA056")
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
		    									                		  .build())
		    													 .add("scheduleDate",LocalDate.of(2015,07,17).toString())
		    													 .build())
    								   .build();

			given()
			        .content(itineraryToCreate.toString())
					.contentType("application/json")
			.when()
					.post(basePath + "webapi/itineraries")
		    .then()
					.statusCode(Status.CREATED.getStatusCode()).extract()
					.asString();

    }

    @Test
    @UsingDataSet(value = {"itinerary.yml","schedule.yml","guests.json","sectors.json", "flight.json"})
    public void shouldUpdateItinerary(){

    	JsonObject itineraryToUpdate = Json.createObjectBuilder()
    									.add("id", 1)
										.add("guest", Json.createObjectBuilder()
												 		.add("id", 1)
													    .add("firstname", "Frank")
													    .add("lastname","Jennings")
													    .build())
										.add("flight", Json.createObjectBuilder()
													  .add("id", 1)
							                		  .add("name", "AA056")
							                		  .add("sourceSector",
							                				  			Json.createObjectBuilder()
							                				  			     .add("id", 1)
							                				  			     .add("sector", "BLR")
							                				  			     .build())
							                		  .add("destSector",
										                				  Json.createObjectBuilder()
							             				  			     .add("id", 3)
							             				  			     .add("sector", "BOM")
							             				  			     .build()))
										.add("schedule", Json.createObjectBuilder()
														  .add("id", 1)
														  .add("guest",  Json.createObjectBuilder()
																	 		.add("id", 1)
																		    .add("firstname", "Frank")
																		    .add("lastname","Jennings")
																		    .build())
														  .add("flight",  Json.createObjectBuilder()
																		  .add("id", 1)
												                		  .add("name", "AA056")
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
												                		  .build())
																 .add("scheduleDate",LocalDate.of(2015,07,17).toString())
																 .build())
									   .build();




		given()
				.content(itineraryToUpdate.toString())
				.contentType("application/json")
		.when()
				.put(basePath+"webapi/itineraries/1")  //dataset has itinerary with id =1
		.then()
				.statusCode(Status.NO_CONTENT.getStatusCode());

    }

    @Test
    @UsingDataSet(value = {"itinerary.yml","schedule.yml","guests.json","sectors.json", "flight.json"})
    public void shouldFindItinerary() {

    	given()
    	.when()
		        .get(basePath+"webapi/itineraries/1") //dataset has itinerary with id =1
		.then()
		        .statusCode(Status.OK.getStatusCode())
		        .body("id", equalTo(1)).extract().asString();

    }

    @Test
    @UsingDataSet(value = {"itinerary.yml","schedule.yml","guests.json","sectors.json", "flight.json"})
    public void shouldDeleteItinerary() {

		 given()
		        .contentType(ContentType.JSON)
	     .when()
	            .delete(basePath+"webapi/itineraries/1")  //dataset has itinerary with id =1
	     .then()
	            .statusCode(Status.NO_CONTENT.getStatusCode());

	    //id 1 should not be in db anymore
		given()
		.when()
		        .get(basePath + "webapi/itineraries")
	    .then()
	            .statusCode(Status.OK.getStatusCode())
	            .body("", hasSize(0));

    }
}
