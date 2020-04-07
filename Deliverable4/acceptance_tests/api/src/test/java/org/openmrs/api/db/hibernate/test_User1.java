package org.openmrs.api.db.hibernate;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.PatientIdentifierType;

import org.openmrs.test.BaseContextSensitiveTest;

import java.util.List;

public class test_User1 extends BaseContextSensitiveTest{
	private HibernatePatientDAO hibernatePatientDao;

	@Before
	public void beforeEach() {
		updateSearchIndex();
		hibernatePatientDao = (HibernatePatientDAO) applicationContext.getBean("patientDAO");
	}
	
	@Test
    public void test() {
		// Get the id of all PatientIdentifierType objects
		List<PatientIdentifierType> list = hibernatePatientDao.getAllPatientIdentifierTypes(true);
		for (int i = 0; i < list.size(); i ++) {
			System.out.println(list.get(i).getId());
		}
		
		// Save a new PatientIdentifierType with id be unique
		PatientIdentifierType type1 = new PatientIdentifierType();
		type1.setName("type1");
		hibernatePatientDao.savePatientIdentifierType(type1);

        // The output should be 5 since there are 4 objects already exists in the database
		assertEquals(5, hibernatePatientDao.getAllPatientIdentifierTypes(true).size());
	}

}
