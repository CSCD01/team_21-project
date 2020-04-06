package org.openmrs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PatientIdentifierTypeTest {

    @Test
    public void testEmpty() {
        PatientIdentifierType patientIdentifierTypeOne = new PatientIdentifierType();
        PatientIdentifierType patientIdentifierTypeTwo = new PatientIdentifierType();

        assertTrue(patientIdentifierTypeOne.isDuplicate(patientIdentifierTypeTwo));
    }

    @Test
    public void testNoDuplication() {
        PatientIdentifierType patientIdentifierType = new PatientIdentifierType(1);
        patientIdentifierType.setFormat("format1");
        patientIdentifierType.setFormatDescription("formatDescription1");
        PatientIdentifierType patientIdentifierType2 = new PatientIdentifierType(2);
        patientIdentifierType2.setFormat("format2");
        patientIdentifierType2.setFormatDescription("formatDescription2");

        assertFalse(patientIdentifierType.isDuplicate(patientIdentifierType2));
    }

    @Test
    public void testIdDuplication() {
        PatientIdentifierType patientIdentifierType = new PatientIdentifierType(1);
        patientIdentifierType.setFormat("format1");
        patientIdentifierType.setFormatDescription("formatDescription1");
        PatientIdentifierType patientIdentifierType2 = new PatientIdentifierType(1);
        patientIdentifierType2.setFormat("format2");
        patientIdentifierType2.setFormatDescription("formatDescription2");

        assertTrue(patientIdentifierType.isDuplicate(patientIdentifierType2));

    }

    @Test
    public void testFormatDuplication() {
        PatientIdentifierType patientIdentifierType = new PatientIdentifierType(1);
        patientIdentifierType.setFormat("format");
        patientIdentifierType.setFormatDescription("formatDescription1");
        PatientIdentifierType patientIdentifierType2 = new PatientIdentifierType(2);
        patientIdentifierType2.setFormat("format");
        patientIdentifierType2.setFormatDescription("formatDescription2");

        assertTrue(patientIdentifierType.isDuplicate(patientIdentifierType2));

    }

    @Test
    public void testFormatDescDuplication() {
        PatientIdentifierType patientIdentifierType = new PatientIdentifierType(1);
        patientIdentifierType.setFormat("format1");
        patientIdentifierType.setFormatDescription("formatDescription");
        PatientIdentifierType patientIdentifierType2 = new PatientIdentifierType(2);
        patientIdentifierType2.setFormat("format2");
        patientIdentifierType2.setFormatDescription("formatDescription");

        assertTrue(patientIdentifierType.isDuplicate(patientIdentifierType2));

    }

    @Test
    public void testMergeFormat() {
        PatientIdentifierType patientIdentifierType = new PatientIdentifierType(1);
        PatientIdentifierType patientIdentifierType2 = new PatientIdentifierType(1);
        patientIdentifierType2.setFormat("format2");

        assertTrue(patientIdentifierType.isDuplicate(patientIdentifierType2));

        patientIdentifierType.merge(patientIdentifierType2);
        assertEquals("format2", patientIdentifierType.getFormat());

    }

    @Test
    public void testMergeFormatDesc() {
        PatientIdentifierType patientIdentifierType = new PatientIdentifierType();
        PatientIdentifierType patientIdentifierType2 = new PatientIdentifierType();
        patientIdentifierType2.setFormatDescription("formatDescription2");

        assertTrue(patientIdentifierType.isDuplicate(patientIdentifierType2));
        patientIdentifierType.merge(patientIdentifierType2);

        assertEquals("formatDescription2", patientIdentifierType.getFormatDescription());
    }

    @Test
    public void testShortCircut() {
        PatientIdentifierType patientIdentifierType = new PatientIdentifierType();
        patientIdentifierType.setFormat("format1");

        PatientIdentifierType patientIdentifierType2 = new PatientIdentifierType();
        patientIdentifierType2.setFormat("format2");

        assertEquals("format1", patientIdentifierType.getFormat());
    }

}