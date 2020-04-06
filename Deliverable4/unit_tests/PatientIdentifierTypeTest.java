import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertSame;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import org.openmrs.PatientIdentifierType;

/**
 * This class should test only equalIdentifierType methods on the PatientIdentifierType object.
 * other methods are test in PatientTest.java
 */
public class PatientIdentifierTypeTest {
	
	/**
	 * Test the equalIdentifierType method in PatientIdentifierType object
	 * two PatientIdentifierType objects have same id, should return true
	 */
	@Test
	public void same_id_objects() {
		PatientIdentifierType type1 = new PatientIdentifierType(1);
		PatientIdentifierType type2 = new PatientIdentifierType(1);

		assertEquals(true, type1.equalIdentifierType(type2));
	}

	/**
	 * Test the equalIdentifierType method in PatientIdentifierType object
	 * two PatientIdentifierType objects have different id, should return false
	 */
	@Test
	public void diff_id_objects() {
		PatientIdentifierType type1 = new PatientIdentifierType(1);
		PatientIdentifierType type2 = new PatientIdentifierType(2);

		assertEquals(false, type1.equalIdentifierType(type2));
	}

	/**
	 * Test the equalIdentifierType method in PatientIdentifierType object
	 * two PatientIdentifierType objects have different id but same format, should return true
	 */
	@Test
	public void diff_id_same_format_objects() {
		PatientIdentifierType type1 = new PatientIdentifierType(1);
		PatientIdentifierType type2 = new PatientIdentifierType(2);

        type1.setFormat("format 1");
        type2.setFormat("format 1");
		assertEquals(true, type1.equalIdentifierType(type2));
	}

	/**
	 * Test the equalIdentifierType method in PatientIdentifierType object
	 * two PatientIdentifierType objects have different id and different format, should return false
	 */
	@Test
	public void diff_id_diff_format_objects() {
		PatientIdentifierType type1 = new PatientIdentifierType(1);
		PatientIdentifierType type2 = new PatientIdentifierType(2);

        type1.setFormat("format 1");
        type2.setFormat("format 2");
		assertEquals(false, type1.equalIdentifierType(type2));
	}

	/**
	 * Test the equalIdentifierType method in PatientIdentifierType object
	 * two PatientIdentifierType objects have different id and different format but same formatDescription, should return true
	 */
	@Test
	public void diff_id_diff_format_same_describes_objects() {
		PatientIdentifierType type1 = new PatientIdentifierType(1);
		PatientIdentifierType type2 = new PatientIdentifierType(2);

        type1.setFormat("format 1");
        type2.setFormat("format 2");

        type1.setFormatDescription("this is format 1");
        type2.setFormatDescription("this is format 1");
		assertEquals(true, type1.equalIdentifierType(type2));
	}

	/**
	 * Test the equalIdentifierType method in PatientIdentifierType object
	 * two PatientIdentifierType objects have different id and different format and different formatDescription, should return true
	 */
	@Test
	public void diff_id_diff_format_diff_describes_objects() {
		PatientIdentifierType type1 = new PatientIdentifierType(1);
		PatientIdentifierType type2 = new PatientIdentifierType(2);

        type1.setFormat("format 1");
        type2.setFormat("format 2");

        type1.setFormatDescription("this is format 1");
        type2.setFormatDescription("this is format 2");
		assertEquals(false, type1.equalIdentifierType(type2));
	}
}
