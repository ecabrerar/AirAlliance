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
package org.ecabrerar.examples.airalliance.service;

import java.util.List;


import javax.inject.Inject;


import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.ecabrerar.examples.airalliance.test.helpers.TestHelpers.createSector;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import org.ecabrerar.examples.airalliance.entities.BaseEntity;
import org.ecabrerar.examples.airalliance.entities.Sector;
import org.ecabrerar.examples.airalliance.producers.EntityManagerProducer;
import org.ecabrerar.examples.airalliance.test.helpers.TestHelpers;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.ShouldMatchDataSet;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author ecabrerar
 */
@RunWith(Arquillian.class)
public class SectorServiceTest {

    @Inject
    private SectorService sectorService;

    @Deployment
    public static Archive<?> deployment() {
        WebArchive webArchive = ShrinkWrap.create(WebArchive.class)
                .addClasses(Sector.class, SectorService.class, EntityManagerProducer.class,
                		     BaseEntityService.class, BaseEntity.class,TestHelpers.class
                		    )
                .addAsResource("META-INF/test_persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

        System.out.println(webArchive.toString(true));

        return webArchive;
    }

    @Test
    public void sectorRestServiceShouldNotBeNull() {
        assertNotNull(sectorService);
    }

    @Test
	@ShouldMatchDataSet(value = { "sector.json" }, excludeColumns = { "id" })
	public void save_NewSector_ShouldBeCreated() throws Exception {

		Sector saved = sectorService.create(createSector());

		assertNotNull(saved);
		assertNotNull(saved.getId());
	}

	@Test
    @UsingDataSet({"sector.json"})
    @ShouldMatchDataSet(value = {"sector.json"}, excludeColumns = {"id"})
    public void get_ExistingSector_Found() throws Exception {

		Sector sector = sectorService.find(Sector.class, 1);
        assertNotNull(sector);
    }

	@Test
    @UsingDataSet({"sectors.json"})
    public void findAll_sectorStoredInDatabase_3SectorFound() throws Exception {

		List<Sector> result = sectorService.findAll(Sector.class);
        assertThat(result, hasSize(4));
    }


	@Test
    @UsingDataSet({"sectors.json"})
	 public void count_sectorStoredInDatabase_3SectorFound() throws Exception {

	     int count  = sectorService.count(Sector.class);

	     assertTrue(count==4);
	 }

}
