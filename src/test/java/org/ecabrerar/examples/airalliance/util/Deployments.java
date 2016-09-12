package org.ecabrerar.examples.airalliance.util;

import org.ecabrerar.examples.airalliance.test.helpers.TestHelpers;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;

/**
 * @author ecabrerar
 * @date   Sep 12, 2016
 */
public class Deployments {


	public static WebArchive getBaseDeployment() {

		WebArchive war = ShrinkWrap.create(WebArchive.class)
				     	 .addPackages(true,
										 new String[]{ "org.ecabrerar.examples.airalliance.converters",
										               "org.ecabrerar.examples.airalliance.entities",
										               "org.ecabrerar.examples.airalliance.producers",
													   "org.ecabrerar.examples.airalliance.rest",
													   "org.ecabrerar.examples.airalliance.service"
									             })

						 .addClasses(TestHelpers.class)
						 .addAsResource("META-INF/test_persistence.xml","META-INF/persistence.xml")
						 .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

		return war;
	}
}
