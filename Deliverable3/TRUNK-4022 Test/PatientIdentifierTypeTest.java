/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.notification.db.hibernate;

import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import java.util.List;

/**
 * Class to test PatientIdentifierType
 */
public class PatientIdentifierTypeTest extends BaseContextSensitiveTest {

	private PatientIdentifierType pit;

	@Before
	public void runBeforeEachTest() {
		Context.openSession();
		authenticate();
		pit = new PatientIdentifierType(1);
		pit.setFormatDescription("Format1");
		pit.setLocationBehavior(LocationBehavior);
		pit.setUniquenessBehavior(UniquenessBehavior);
		pit.setFormat("Format1");
		pit.setValidator("Validator1");
	}

	@Test
	public void testIdIsEqual() {
	    test1 = new PatientIdentifierType(1);
		pit.equalIdentifierType(test1);
		assertTrue(pit.equalIdentifierType(test1));
	}

	@Test
	public void testIdNotEqual() {
	    test1 = new PatientIdentifierType(2);
		pit.equalIdentifierType(test1);
		assertFalse(pit.equalIdentifierType(test1));
	}

	@Test
	public void testFormatIsSame() {
	    test1 = new PatientIdentifierType(2);
	    test1.setFormat("Format1");
		pit.equalIdentifierType(test1);
		assertTrue(pit.equalIdentifierType(test1));
	}

	@Test
	public void testFormatNotSame() {
	    test1 = new PatientIdentifierType(2);
	    test1.setFormat("Format2");
		pit.equalIdentifierType(test1);
		assertFalse(pit.equalIdentifierType(test1));
	}

}