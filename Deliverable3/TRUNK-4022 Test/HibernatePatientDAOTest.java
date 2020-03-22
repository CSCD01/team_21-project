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
import org.openmrs.api.context.Context;
import org.openmrs.test.BaseContextSensitiveTest;
import org.openmrs.notification.Alert;
import org.openmrs.User;
import org.hibernate.SessionFactory;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.openmrs.api.UserService;
import java.util.List;

public class CheckTableTest extends BaseContextSensitiveTest{
	private HibernatePatientDAO hibernatePatientDAO = new HibernatePatientDAO();
	
	@Before
	public void runBeforeTheTest(){
		Context.openSession();
		authenticate();
		hibernatePatientDAO.setSessionFactory((SessionFactory) applicationContext.getBean("sessionFactory"));
	}
	
	public User generateNewUser() {
		PatientIdentifierType p1 = new PatientIdentifierType(1);
		p1.setFormatDescription("Format1");
		p1.setLocationBehavior(LocationBehavior.NOT_USED);
		p1.setUniquenessBehavior(UniquenessBehavior.UNIQUE);
		p1.setFormat("Format1");
		p1.setValidator("validator1");
	}
	
	public User generateSameUser() {
		PatientIdentifierType p1 = new PatientIdentifierType(1);
		p1.setFormatDescription("Format1");
		p1.setLocationBehavior(LocationBehavior.NOT_USED);
		p1.setUniquenessBehavior(UniquenessBehavior.UNIQUE);
		p1.setFormat("Format1");
		p1.setValidator("validator1");
	}
	
	public User generateDifferentUser() {
		PatientIdentifierType p1 = new PatientIdentifierType(1);
		p1.setFormatDescription("Format2");
		p1.setLocationBehavior(LocationBehavior.NOT_USED);
		p1.setUniquenessBehavior(UniquenessBehavior.UNIQUE);
		p1.setFormat("Format2");
		p1.setValidator("validator2");
	}
	
	/*
	* put same users into the database. If check_Table return true, then the merge success.
	*/
	@Test
	public void testSameUser(){
		hibernatePatientDAO.deletePatient();
		hibernatePatientDAO.savePatientIdentifierType(generateNewUser());
		hibernatePatientDAO.savePatientIdentifierType(generateNewUser());
		assertTrue(check_Table());
	}
	
	/*
	* put different users into the database. If check_Table return false, then the merge failed.
	*/
	@Test
	public void testDifferentUser(){
		hibernatePatientDAO.deletePatient();
		hibernatePatientDAO.savePatientIdentifierType(generateNewUser());
		hibernatePatientDAO.savePatientIdentifierType(generateDifferentUser());
		assertFalse(check_Table());
	}
	
	/*
	* If the database is empty, then the check_Table should return false.
	*/
	@Test
	public void testNoUser(){
		hibernatePatientDAO.deletePatient();
		assertFalse(check_Table());
	}
} 