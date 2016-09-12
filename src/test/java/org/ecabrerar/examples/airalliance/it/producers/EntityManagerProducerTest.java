/*
 * Copyright 2015 ecabrerar.
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
package org.ecabrerar.examples.airalliance.it.producers;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.ecabrerar.examples.airalliance.producers.EntityManagerProducer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.descriptor.api.persistence10.PersistenceDescriptor;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author ecabrerar
 */
@RunWith(Arquillian.class)
public class EntityManagerProducerTest {

	@Inject
	private EntityManager entityManager;

	@Deployment
	public static Archive<?> deployment() {
		String persistenceXml = persistenceDescriptor().exportAsString();
		System.out.println(persistenceXml);

		WebArchive webArchive = ShrinkWrap
				.create(WebArchive.class)
				.addAsLibraries(ShrinkWrap.create(JavaArchive.class)
				.addClass(EntityManagerProducer.class)
				.addAsManifestResource(new StringAsset(persistenceXml),	"persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE,	"beans.xml"))
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

		System.out.println(webArchive.toString(true));

		return webArchive;
	}

	@Test
	public void entityManagerShouldNotBeNull() throws Exception {
		 assertNotNull(entityManager);
	}

	private static PersistenceDescriptor persistenceDescriptor() {
		return Descriptors.create(PersistenceDescriptor.class)
			    .createPersistenceUnit()
                .name("test")
                .getOrCreateProperties()
                .createProperty()
                .name("hibernate.hbm2ddl.auto")
                .value("create-drop").up()
                .createProperty()
                .name("hibernate.show_sql")
                .value("true").up().up()
                .jtaDataSource("java:jboss/datasources/ExampleDS").up();
	}

}
