package no.niths.application.rest;

import static org.junit.Assert.assertEquals;
import no.niths.application.rest.exception.ObjectNotFoundException;
import no.niths.application.rest.interfaces.FadderGroupController;
import no.niths.application.rest.interfaces.StudentController;
import no.niths.common.config.HibernateConfig;
import no.niths.common.config.TestAppConfig;
import no.niths.domain.FadderGroup;
import no.niths.domain.Student;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestAppConfig.class, HibernateConfig.class })
public class FadderGroupControllerTest {
	
    private MockHttpServletResponse res;

	@Autowired
	private FadderGroupController fadderController;

	@Autowired 
	private StudentController studentController;

	@Before
    public void setUp() {
        res = new MockHttpServletResponse();
    }

	@Test
	public void testAddAndRemoveLeaders(){
		Student s1 = new Student("mail@nith.com");
		Student s2 = new Student("mail2@nith.no");

		
		FadderGroup g1 = new FadderGroup(123);

		g1.getLeaders().add(s1);
		g1.getLeaders().add(s2);
		fadderController.create(g1, res);
		
		assertEquals(g1, fadderController.getById(g1.getId()));
				
		assertEquals(2, fadderController.getById(g1.getId()).getLeaders().size());
		
		fadderController.removeAllLeadersFromGroup(g1.getId());
		assertEquals(true, fadderController.getById(g1.getId()).getLeaders().isEmpty());
		
		fadderController.addLeaderToAGroup(g1.getId(), s1.getId());
		fadderController.addLeaderToAGroup(g1.getId(), s2.getId());
		assertEquals(2, fadderController.getById(g1.getId()).getLeaders().size());
		
		fadderController.removeLeaderFromAGroup(g1.getId(), s1.getId());
		assertEquals(1, fadderController.getById(g1.getId()).getLeaders().size());
		
		
		fadderController.hibernateDelete(g1.getId());
		
		studentController.hibernateDelete(s1.getId());
		studentController.hibernateDelete(s2.getId());
		int remainingGroups= -1;
		try{
			remainingGroups=fadderController.getAll(null).size();
		}catch(ObjectNotFoundException ex){			
			assertEquals(-1,remainingGroups);
		}
	}
	
	@Test
	public void testAddAndRemoveChildren(){
		Student s1 = new Student("mail@ds.com");
		Student s2 = new Student("mail2@mail.sdm");

		
		FadderGroup g1 = new FadderGroup(123);

		g1.getFadderChildren().add(s1);
		g1.getFadderChildren().add(s2);
		fadderController.create(g1, res);
		
		assertEquals(g1, fadderController.getById(g1.getId()));
				
		assertEquals(2, fadderController.getById(g1.getId()).getFadderChildren().size());
		
		fadderController.removeAllChildrenFromGroup(g1.getId());
		assertEquals(true, fadderController.getById(g1.getId()).getFadderChildren().isEmpty());
		
		fadderController.addChildToAGroup(g1.getId(), s1.getId());
		fadderController.addChildToAGroup(g1.getId(), s2.getId());
		assertEquals(2, fadderController.getById(g1.getId()).getFadderChildren().size());
		
		fadderController.removeChildFromGroup(g1.getId(), s1.getId());
		assertEquals(1, fadderController.getById(g1.getId()).getFadderChildren().size());
		
		
		fadderController.hibernateDelete(g1.getId());
		studentController.hibernateDelete(s1.getId());
		studentController.hibernateDelete(s2.getId());
		
		int remainingGroups= -1;
		try{
			remainingGroups=fadderController.getAll(null).size();
		}catch(ObjectNotFoundException ex){			
			assertEquals(-1,remainingGroups);
		}
	}
	
	@Test
	public void testCRUD(){
		int size = 0;
		try{
			size = fadderController.getAll(null).size();
		}catch(ObjectNotFoundException e){
			//Do nothing
		}
		
		FadderGroup group = new FadderGroup(1337);
		
		fadderController.create(group, res);
		
		assertEquals(size + 1, fadderController.getAll(null).size());
		
		assertEquals(1, fadderController.getAll(group).size());
		
		group.setGroupNumber(1338);
		fadderController.update(group);
		
		assertEquals(new Integer(1338), fadderController.getAll(group).get(0).getGroupNumber());
		
		fadderController.hibernateDelete(group.getId());
	}
	
}
