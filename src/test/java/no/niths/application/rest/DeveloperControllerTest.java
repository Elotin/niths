package no.niths.application.rest;


import static org.junit.Assert.assertEquals;
import no.niths.application.rest.developing.interfaces.ApplicationController;
import no.niths.application.rest.developing.interfaces.DeveloperController;
import no.niths.application.rest.exception.ObjectNotFoundException;
import no.niths.common.config.HibernateConfig;
import no.niths.common.config.TestAppConfig;
import no.niths.domain.developing.Application;
import no.niths.domain.developing.Developer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestAppConfig.class, HibernateConfig.class })
public class DeveloperControllerTest {

    private MockHttpServletResponse res;

	@Autowired 
	private DeveloperController controller;
	
	@Autowired
	private ApplicationController aController;

	@Before
	public void setUp() {
	    res = new MockHttpServletResponse();
	}

	@Test
	public void addApp(){
		int size = 0;
		try{
			size = controller.getAll(new Developer()).size();
		}catch(ObjectNotFoundException e){}
		
		Developer dev = new Developer("Per", "person@nith.no");
		controller.create(dev, res);
		assertEquals(size + 1, controller.getAll(new Developer()).size());
		
		Application app = new Application("The title");
		aController.create(app, res);
		
		Developer fetched = controller.getById(dev.getId());
		Application aFetched = aController.getById(app.getId());
		assertEquals(true, fetched.getApps().isEmpty());
		controller.addApp(fetched.getId(), aFetched.getId());
		fetched = controller.getById(dev.getId());
		assertEquals(1, fetched.getApps().size());
		
		controller.removeApp(fetched.getId(), aFetched.getId());
		fetched = controller.getById(dev.getId());
		assertEquals(true, fetched.getApps().isEmpty());
	}

	@Test
	public void testController(){
		int size = 0;
		try{
			size = controller.getAll(new Developer()).size();
		}catch(ObjectNotFoundException e){}
		
		
		Developer dev = new Developer();
		dev.setEmail("mail@mail.com");
		dev.setName("DevName");
		controller.create(dev, res);
		
		assertEquals(size + 1, controller.getAll(new Developer()).size());
		Developer get = controller.getById(dev.getId());
		assertEquals(get, dev);
		assertEquals(null, get.getEnabled());
		
		get.setEnabled(true);
		controller.enableDeveloper(get.getId());
		
		get = controller.getById(dev.getId());
		assertEquals(get, dev);
		assertEquals(true, get.getEnabled());
		
		controller.delete(get.getId());
		
	}
}
