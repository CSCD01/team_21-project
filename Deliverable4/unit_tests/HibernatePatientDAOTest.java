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
		List<PatientIdentifier> identifiers = hibernatePatientDao
				.getPatientIdentifiers(null, identifierTypes, emptyList(), emptyList(), null);
		List<Integer> identifierIds = identifiers.stream().map(PatientIdentifier::getId)
				.collect(Collectors.toList());

		Assert.assertEquals(2, identifiers.size());
		Assert.assertThat(identifierIds, hasItems(1, 3));
	}

	@Test
	public void getPatientIdentifiers_shouldGetByPatients() {
		List<Patient> patients = Arrays.asList(
				hibernatePatientDao.getPatient(6),
				hibernatePatientDao.getPatient(7)
		);
		List<PatientIdentifier> identifiers = hibernatePatientDao
				.getPatientIdentifiers(null, emptyList(), emptyList(), patients, null);
		List<Integer> identifierIds = identifiers.stream().map(PatientIdentifier::getId)
				.collect(Collectors.toList());

		Assert.assertEquals(2, identifiers.size());
		Assert.assertThat(identifierIds, hasItems(3, 4));
	}

	/**
	 * Test the check_Table method in HibernatePatientDAO object
	 * two PatientIdentifierType objects have same id, should return true
	 */
	@Test
	public void testCheckTableSameId(){
		deleteAllIdentifierTypes();
		PatientIdentifierType identifierType = new PatientIdentifierType(1);
		hibernatePatientDao.savePatientIdentifierType(identifierType);
		assertTrue(hibernatePatientDao.check_Table(new PatientIdentifierType(1)));
	}

	/**
	 * Test the check_Table method in HibernatePatientDAO object
	 * two PatientIdentifierType objects have same format, should return true
	 */
	@Test
	public void testCheckTableSameFormat(){
		deleteAllIdentifierTypes();
		PatientIdentifierType identifierType = new PatientIdentifierType(1);
		identifierType.setFormat("Format1");
		PatientIdentifierType identifierTypeSame = new PatientIdentifierType(2);
		identifierTypeSame.setFormat("Format1");
		hibernatePatientDao.savePatientIdentifierType(identifierType);
		assertTrue(hibernatePatientDao.check_Table(identifierTypeSame));
	}

	/**
	 * Test the check_Table method in HibernatePatientDAO object
	 * two PatientIdentifierType objects have different id, should return false
	 */
	@Test
	public void testCheckTableDifferent(){
		deleteAllIdentifierTypes();
		PatientIdentifierType identifierType = new PatientIdentifierType(1);
		hibernatePatientDao.savePatientIdentifierType(identifierType);
		assertFalse(hibernatePatientDao.check_Table(new PatientIdentifierType(2)));
	}

	/**
	 * Test the check_Table method in HibernatePatientDAO object
	 * if database is empty, then check_Table should return false
	 */
	@Test
	public void testCheckTableVoid(){
		deleteAllIdentifierTypes();
		assertFalse(hibernatePatientDao.check_Table(new PatientIdentifierType(2)));
	}

	/**
	 * function that delete all IdentifierTypes objects from database
	 */
	public void deleteAllIdentifierTypes(){
		List<PatientIdentifierType> identifierTypes = hibernatePatientDao.getAllPatientIdentifierTypes(true);
		for(PatientIdentifierType identifierType: identifierTypes){
			hibernatePatientDao.deletePatientIdentifierType(identifierType);
		}
	}
}
