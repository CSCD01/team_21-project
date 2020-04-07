package org.openmrs.api.db.hibernate;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.PatientIdentifierType;

import org.openmrs.test.BaseContextSensitiveTest;

import java.util.List;

public class test_User2 extends BaseContextSensitiveTest{
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
		
		// Save a new PatientIdentifierType with id be 1
		PatientIdentifierType type1 = new PatientIdentifierType();
		type1.setName("type1");
		type1.setId(1);
		hibernatePatientDao.savePatientIdentifierType(type1);

        // The output should be 4 since there are 4 objects already exists in the database and the new one will not save into the database
		assertEquals(4, hibernatePatientDao.getAllPatientIdentifierTypes(true).size());
	}

}
