package org.ecabrerar.examples.airalliance.it.rest;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasItem;
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

		System.out.println(basePath + "webapi/sectors");

		given()
		.when()
				.get(basePath + "webapi/sectors")
		.then()
				.statusCode(Status.OK.getStatusCode()).body("", hasSize(4))
				.body("sector", hasItem("BLR"));

	}

	@Test
	@UsingDataSet(value = { "sectors.json" })
	public void shouldFindSector() {

		String json = given()
				.when()
					.get(basePath + "webapi/sectors/1"). // dataset has sector with id =1
				then().statusCode(Status.OK.getStatusCode())
					.body("id", equalTo(1))
					.body("sector", equalTo("BLR")).extract().asString();

		try (JsonReader reader = Json.createReader(new StringReader(json))) {
			JsonObject jsonObject = reader.readObject();
			assertEquals("BLR", jsonObject.getString("sector"));
		}
	}

	@Test
	public void shouldCreateSector() {

		JsonObject sectorToCreade = Json.createObjectBuilder()
				.add("sector", "SFO").build();

		given().content(sectorToCreade.toString())
				.contentType("application/json").when()
				.post(basePath + "webapi/sectors").then()
				.statusCode(Status.CREATED.getStatusCode()).extract()
				.asString();

		// new sector should be there
		given().when().get(basePath + "webapi/sectors").then()
				.statusCode(Status.OK.getStatusCode())
				.body("sector", hasItem("SFO"));

	}

	@Test
	@UsingDataSet(value = { "sectors.json" })
	public void shouldUpdateSector() {

		JsonObject sectorToUpdate = Json.createObjectBuilder()
									.add("id", 1)
									.add("sector", "FL")
									.build();

		given().content(sectorToUpdate.toString())
			   .contentType("application/json")
		.when()
				.put(basePath + "webapi/sectors/1"). // dataset has sector with id =1
		then()
		        .statusCode(Status.NO_CONTENT.getStatusCode());

	}

	@Test
	@UsingDataSet(value = { "sectors.json" })
	public void shouldDeleteSector() {

		given().contentType(ContentType.JSON).when()
				.delete(basePath + "webapi/sectors/1"). // dataset has sector
														// with id =1
				then().statusCode(Status.NO_CONTENT.getStatusCode());

		// BLR should not be in db anymore
		given()
		.when()
				.get(basePath + "webapi/sectors")
		.then()
				.statusCode(Status.OK.getStatusCode())
				.body("", hasSize(3))
				.body("sector", not(hasItem("BLR")));

	}

}
