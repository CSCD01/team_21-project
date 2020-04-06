/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.api.db.hibernate;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PatientIdentifierType;
import org.openmrs.test.BaseContextSensitiveTest;

public class HibernatePatientDAOTest extends BaseContextSensitiveTest {

	private HibernatePatientDAO hibernatePatientDao;

	@Before
	public void beforeEach() {
		updateSearchIndex();
		hibernatePatientDao = (HibernatePatientDAO) applicationContext.getBean("patientDAO");
	}

	@Test
	public void getPatientIdentifiers_shouldGetByIdentifierType() {
		List<PatientIdentifierType> identifierTypes = singletonList(new PatientIdentifierType(2));
		List<PatientIdentifier> identifiers = hibernatePatientDao.getPatientIdentifiers(null, identifierTypes,
				emptyList(), emptyList(), null);
		List<Integer> identifierIds = identifiers.stream().map(PatientIdentifier::getId).collect(Collectors.toList());

		Assert.assertEquals(2, identifiers.size());
		Assert.assertThat(identifierIds, hasItems(1, 3));
	}

	@Test
	public void getPatientIdentifiers_shouldGetByPatients() {
		List<Patient> patients = Arrays.asList(hibernatePatientDao.getPatient(6), hibernatePatientDao.getPatient(7));
		List<PatientIdentifier> identifiers = hibernatePatientDao.getPatientIdentifiers(null, emptyList(), emptyList(),
				patients, null);
		List<Integer> identifierIds = identifiers.stream().map(PatientIdentifier::getId).collect(Collectors.toList());

		Assert.assertEquals(2, identifiers.size());
		Assert.assertThat(identifierIds, hasItems(3, 4));
	}

	@Test
	public void checkExistedType() {
		PatientIdentifierType patientIdentifierType = hibernatePatientDao.getPatientIdentifierType(1);
		assertTrue(hibernatePatientDao.hasDuplicate(patientIdentifierType).isPresent());
	}

	@Test
	public void checkAnotherExistedType() {
		PatientIdentifierType patientIdentifierType = hibernatePatientDao.getPatientIdentifierType(2);
		assertTrue(hibernatePatientDao.hasDuplicate(patientIdentifierType).isPresent());
	}

	@Test
	public void checkNonExistentType() {
		assertFalse(hibernatePatientDao.hasDuplicate(new PatientIdentifierType(114514)).isPresent());
	}

	@Test
	public void addAndCheckType() {
		PatientIdentifierType patientIdentifierType = new PatientIdentifierType();
		patientIdentifierType.setName("test");
		patientIdentifierType.setDescription("description");
		patientIdentifierType.setRequired(false);

		assertNull(patientIdentifierType.getPatientIdentifierTypeId());

		hibernatePatientDao.savePatientIdentifierType(patientIdentifierType);
		assertNotNull(hibernatePatientDao.getPatientIdentifierType(patientIdentifierType.getPatientIdentifierTypeId()));

		assertTrue(hibernatePatientDao.hasDuplicate(patientIdentifierType).isPresent());
	}

	@Test
	public void addAndCheckTypeOfSameFormat() {
		PatientIdentifierType patientIdentifierTypeOne = new PatientIdentifierType();
		patientIdentifierTypeOne.setName("name1");
		patientIdentifierTypeOne.setDescription("description1");
		patientIdentifierTypeOne.setRequired(false);
		patientIdentifierTypeOne.setFormat("format");

		PatientIdentifierType patientIdentifierTypeTwo = new PatientIdentifierType();
		patientIdentifierTypeTwo.setName("name2");
		patientIdentifierTypeTwo.setDescription("description2");
		patientIdentifierTypeTwo.setRequired(false);
		patientIdentifierTypeTwo.setFormat("format");

		hibernatePatientDao.savePatientIdentifierType(patientIdentifierTypeOne);

		assertTrue(hibernatePatientDao.hasDuplicate(patientIdentifierTypeTwo).isPresent());
	}
}
