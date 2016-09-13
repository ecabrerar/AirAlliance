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
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.MavenResolverSystem;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.jayway.restassured.http.ContentType;



/**
 * @author ecabrerar
 * @date   Sep 12, 2016
 */
@RunWith(Arquillian.class)
public class GuestRestServiceTest {

    @ArquillianResource 
    URL basePath;


    @Deployment
    public static Archive<?> deployment() {
	MavenResolverSystem resolver = Maven.resolver();

	WebArchive war = Deployments.getBaseDeployment();


	war.addAsLibraries(resolver.loadPomFromFile("pom.xml")
		.resolve("com.jayway.restassured:rest-assured")
		.withTransitivity().asFile())
	.addAsResource("META-INF/test_persistence.xml","META-INF/persistence.xml")
	.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

	System.out.println(war.toString(true));

	return war;
    }


    @Test
    @UsingDataSet(value = { "guests.json" })
    public void shouldListGuests() {

	System.out.println(basePath+"webapi/guests");

	given()
	.when()
	.get(basePath+"webapi/guests")
	.then()
	.statusCode(Status.OK.getStatusCode())
	.body("", hasSize(2))
	.body("firstname", hasItem("Frank"))
	.body("lastname", hasItem("Jennings"));

    }

    @Test
    @UsingDataSet(value = { "guests.json" })
    public void shouldFindGuest() {
	String json =
		given().
		when().
		get(basePath + "webapi/guests/1"). //dataset has guest with id =1
		then().
		statusCode(Status.OK.getStatusCode()).
		body("id", equalTo(1)).
		body("firstname", equalTo("Frank")).
		body("lastname", equalTo("Jennings")).extract().asString();

	try (JsonReader reader = Json.createReader(new StringReader(json))) {
	    JsonObject jsonObject = reader.readObject();
	    assertEquals("Jennings", jsonObject.getString("lastname"));

	}        	

    }

    @Test
    public void shouldCreateGuest() {
	
	JsonObject guestToCreade = Json.createObjectBuilder()
				  .add("firstname", "Ada")
				  .add("lastname","Lovelace")
				  .build();
	
        given().
                content(guestToCreade.toString()).
                contentType("application/json").
        when().
                post(basePath+"webapi/guests").
        then().
        	statusCode(Status.CREATED.getStatusCode()).extract().asString();
	
	//new guest should be there
        given().
        when().
                get(basePath+"webapi/guests").
        then().
                statusCode(Status.OK.getStatusCode()).
                body("firstname", hasItem("Ada")).
                body("lastname", hasItem("Lovelace"));

    }

    @Test
    @UsingDataSet(value = { "guests.json" })
    public void shouldFailToCreateGuestWithoutName() {
	
	JsonObject guestToCreade = Json.createObjectBuilder()
		  .add("lastname","Lovelace")
		  .build();
	
	given().
                content(guestToCreade.toString()).
                contentType("application/json").
        when().
                post(basePath+"webapi/guests").
        then().
                statusCode(Status.BAD_REQUEST.getStatusCode()).
                body("message", equalTo("Guest firstname cannot be empty"));

    }

    @Test
    @UsingDataSet(value = { "guests.json" })
    public void shouldFailToCreateGuestWithNonUniqueName() {

	JsonObject guestToCreade = Json.createObjectBuilder()
                		  .add("firstname", "Frank")
                		  .add("lastname","Jennings")
                		  .build();
	
	
	given().
                content(guestToCreade.toString()).
                contentType("application/json").
        when().
        	post(basePath+"webapi/guests").
        then().
        	statusCode(Status.BAD_REQUEST.getStatusCode()).
        	body("message", equalTo("Guest firstname must be unique"));
    }

    @Test
    @UsingDataSet(value = { "guests.json" })
    public void shouldUpdateGuest() {
	
	JsonObject guestToCreade = Json.createObjectBuilder()
				  .add("id", 1)
                		  .add("firstname", "Frank updated")
                		  .add("lastname","Jennings updated")
                		  .build();
	
	given().
        	content(guestToCreade.toString()).
        	contentType("application/json").
        when().
        	put(basePath+"webapi/guests/1").  //dataset has guest with id =1
        then().
        	statusCode(Status.NO_CONTENT.getStatusCode());

    }

    @Test
    @UsingDataSet(value = { "guests.json" })
    public void shouldDeleteGuest() {

	 given().
         	contentType(ContentType.JSON).
         	
         when().
         	delete(basePath+"webapi/guests/1").  //dataset has guest with id =1
         then().
         	statusCode(Status.NO_CONTENT.getStatusCode());

          //Frank should not be in db anymore
	 given().
         when().
         	get(basePath + "webapi/guests").
         then().
         	statusCode(Status.OK.getStatusCode()).
         	body("", hasSize(1)).
         	body("firstname", not(hasItem("Frank")));
	
    }

}
