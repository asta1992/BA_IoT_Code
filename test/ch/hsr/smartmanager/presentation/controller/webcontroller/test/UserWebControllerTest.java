package ch.hsr.smartmanager.presentation.controller.webcontroller.test;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ch.hsr.smartmanager.data.ManagementUser;
import ch.hsr.smartmanager.data.repositories.ManagementUserRepository;
import ch.hsr.smartmanager.presentation.webcontroller.UserWebController;
import ch.hsr.smartmanager.service.applicationservices.UserService;

@WebAppConfiguration
@ActiveProfiles("dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:WebContent/WEB-INF/smartmanager-servlet.xml" })
public class UserWebControllerTest {

	@Autowired
	private UserWebController userWebController;
	@Autowired
	private ManagementUserRepository managementUserRepository;
	@Autowired
	private UserService userService;

	private MockMvc mockMvc;
	private Principal principal;

	@Before
	public void setUp() throws Exception {

		principal = new Principal() {
			@Override
			public String getName() {
				return "admin";
			}
		};

		this.mockMvc = MockMvcBuilders.standaloneSetup(userWebController).build();
	}

	@After
	public void tearDown() throws Exception {
		managementUserRepository.deleteAll();
	}

	@Test
	public void receiveUsersroot() throws Exception {
		mockMvc.perform(get("/users").principal(principal)).andExpect(status().isOk());
	}

	@Test
	public void receiveUserAdddFragment() throws Exception {
		mockMvc.perform(get("/users/userAddFragment").principal(principal)).andExpect(status().isOk());
	}

	@Test
	public void receiveUserDeleteFragment() throws Exception {
		mockMvc.perform(get("/users/userDeleteFragment").principal(principal)).andExpect(status().isOk());
	}

	@Test
	public void receiveUserEditFragment() throws Exception {
		mockMvc.perform(get("/users/userEditFragment").principal(principal)).andExpect(status().isOk());
	}

	@Test
	public void userNotInDatabase() throws Exception {
		principal = new Principal() {
			@Override
			public String getName() {
				return "notindb";
			}
		};
		mockMvc.perform(get("/users").principal(principal)).andExpect(status().isOk())
				.andExpect(model().attribute("user", nullValue()));
	}

	@Test
	public void allUserInDeteleFragment() throws Exception {
		List<ManagementUser> expectedUsers = new ArrayList<>();
		expectedUsers.add(new ManagementUser("testperson", "12345678"));
		expectedUsers.add(new ManagementUser("testperson2", "12345678"));
		expectedUsers.add(new ManagementUser("testperson3", "12345678"));

		userService.addUser("testperson", "12345678", "12345678");
		userService.addUser("testperson2", "12345678", "12345678");
		userService.addUser("testperson3", "12345678", "12345678");

		mockMvc.perform(get("/users").principal(principal)).andExpect(status().isOk())
				.andExpect(model().attribute("managementUsers", expectedUsers));
	}
}
