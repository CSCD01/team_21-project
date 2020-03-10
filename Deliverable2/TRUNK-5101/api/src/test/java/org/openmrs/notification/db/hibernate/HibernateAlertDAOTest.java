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

/**
 * Class to test HibernateAlertDAO
 * Some of the code is based on haripriya999's work
 */
public class HibernateAlertDAOTest extends BaseContextSensitiveTest {

	private HibernateAlertDAO hibernateAlertDAO;

	@Before
	public void runBeforeEachTest() {
		Context.openSession();
		authenticate();
		hibernateAlertDAO = new HibernateAlertDAO();
		hibernateAlertDAO.setSessionFactory((SessionFactory) applicationContext.getBean("sessionFactory"));
	}

	/**
	 * Returns user with userId 1
	 * 
	 * @return User
	 */
	private User generateNewUser() {
		UserService userService = Context.getUserService();
		User user = userService.getUser(1);
		return user;
	}

	/**
	 * Create an Alert object
	 * 
	 * @return Alert 
	 */
	private Alert genereateNewAlert() {
		Alert alert = new Alert(1);
		User user = generateNewUser();
		alert.setCreator(user);
		alert.setText("This is an alert");
		alert.setId(1);
		return alert;
	}

	/**
	 * @see HibernateAlertDAO#saveAlert
	 */
	@Test
	public void testSaveAlertShouldReturnNotNull() {
		Alert alert = genereateNewAlert();
		Alert savedAlert = hibernateAlertDAO.saveAlert(alert);
		assertNotNull(Integer.toString(savedAlert.getAlertId()));
	}

	/**
	 * @see HibernateAlertDAO#getAlert
	 */
	@Test
	public void testGetAlertShouldReturnAlert() {
		Alert alert = genereateNewAlert();
		hibernateAlertDAO.saveAlert(alert);
		alert = hibernateAlertDAO.getAlert(1);
		assertEquals(alert.getText(), "This is an alert");
	}

	/**
	 * @see HibernateAlertDAO#deleteAlert
	 */
	@Test
	@Ignore
	public void testDeleteAlertShouldReturnNull() {
		Alert alert = genereateNewAlert();
		Alert save = hibernateAlertDAO.saveAlert(alert);
		hibernateAlertDAO.deleteAlert(save);
		assertNull(save.getId());

	}

	/**
	 * @see HibernateAlertDAO#getAllAlerts
	 */
	@Test
	public void testGetAllAlertsShouldReturnNotNull() {
		List<Alert> alerts = hibernateAlertDAO.getAllAlerts(true);
		assertNotNull(alerts);
	}

	/**
	 * @see HibernateAlertDAO#getAlerts
	 */
	@Test
	public void testGetAlertsShouldReturnNotNull() {
		User user = generateNewUser();
		List<Alert> alerts = hibernateAlertDAO.getAlerts(user, true, true);
		assertNotNull(alerts);
	}

}
