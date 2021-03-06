/*
 * Copyright 2014 ecabrerar.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ecabrerar.examples.airalliance.it.service;

import static junit.framework.TestCase.assertNotNull;
import static org.ecabrerar.examples.airalliance.test.helpers.TestHelpers.createGuest;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;

import org.ecabrerar.examples.airalliance.entities.Guest;
import org.ecabrerar.examples.airalliance.service.GuestService;
import org.ecabrerar.examples.airalliance.util.Deployments;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.ShouldMatchDataSet;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author ecabrerar
 */
@RunWith(Arquillian.class)
public class GuestServiceTest {

	@Inject
	private GuestService guestService;

	@Deployment
	public static Archive<?> deployment() {

		WebArchive webArchive = Deployments.getBaseDeployment();

		System.out.println(webArchive.toString(true));

		return webArchive;

	}

	@Test
	public void guestServiceShouldNotBeNull() {
		assertNotNull(guestService);
	}

	@Test
	@ShouldMatchDataSet(value = { "guest.json" }, excludeColumns = { "id" })
	public void save_NewGuest_ShouldBeCreated() throws Exception {

		Guest saved = guestService.create(createGuest());

		assertNotNull(saved);
		assertNotNull(saved.getId());
	}

	@Test
	@UsingDataSet(value = { "guest.json" })
	@ShouldMatchDataSet(value = { "guest_updated.json" }, excludeColumns = { "id" })
	public void shouldUpdateGuest(){

		Guest guest = guestService.find(Guest.class, 1);
		guest.setFirstname("James");
		guest.setLastname("Gosling");

		guestService.edit(guest);

	}

	@Test
	@UsingDataSet(value = { "guest.json" })
	@ShouldMatchDataSet(value = { "guest.json" }, excludeColumns = { "id" })
	public void get_ExistingGuest_Found() throws Exception {

		Guest guest = guestService.find(Guest.class, 1);
		assertNotNull(guest);
	}

	@Test
	@UsingDataSet(value = { "guests.json" })
	public void findAll_guestStoredInDatabase_3GuestFound() throws Exception {

		List<Guest> result = guestService.findAll(Guest.class);
		assertThat(result, hasSize(2));
	}

	@Test
	@UsingDataSet(value = { "guests.json" })
	public void count_guestStoredInDatabase_3GuestFound() throws Exception {

		int count = guestService.count(Guest.class);

		assertTrue(count == 2);
	}

}
