import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.openmrs.PatientIdentifierType;
import org.openmrs.api.db.hibernate.HibernatePatientDAO;

public class test_User4 {

	private HibernatePatientDAO hibernatePatientDao;

    public static void main(string args[]) {
        updateSearchIndex();
		hibernatePatientDao = (HibernatePatientDAO) applicationContext.getBean("patientDAO");

		PatientIdentifierType type1 = new PatientIdentifierType(1);
		PatientIdentifierType type2 = new PatientIdentifierType(2);
		type1.setFormat("format 1");
		type2.setFormat("format 1");
		hibernatePatientDao.savePatientIdentifierType(type1);
		hibernatePatientDao.savePatientIdentifierType(type2);

        // The output should be 1
		System.out.print(hibernatePatientDao.getAllPatientIdentifierTypes(true).size());
	}

}
