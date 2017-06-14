package ch.hsr.smartmanager.presentation.controller.restcontroller.test;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ch.hsr.smartmanager.data.ManagementUser;
import ch.hsr.smartmanager.data.repositories.ManagementUserRepository;
import ch.hsr.smartmanager.presentation.restcontroller.UserRestController;
import ch.hsr.smartmanager.service.applicationservices.UserService;

@WebAppConfiguration
@ActiveProfiles("dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:WebContent/WEB-INF/smartmanager-servlet.xml" })
public class UserRestControllerTest {

	@Autowired
	private UserRestController userRestController;
	@Autowired
	private ManagementUserRepository managementUserRepository;
	@Autowired
	private UserService userService;

	private MockMvc mockMvc;
	private Principal principal;

	@Before
	public void setUp() throws Exception {

		this.mockMvc = MockMvcBuilders.standaloneSetup(userRestController).build();

		principal = new Principal() {
			@Override
			public String getName() {
				return "admin";
			}
		};

	}

	@After
	public void tearDown() throws Exception {
		managementUserRepository.deleteAll();
	}

	@Test
	public void addUserTest() throws Exception {
		mockMvc.perform(post("/users/add").principal(principal).accept(MediaType.APPLICATION_JSON)
				.param("username", "Test").param("firstPassword", "12345678").param("secondPassword", "12345678"))
				.andExpect(status().is(200));
	}

	@Test
	public void deleteUserTest() throws Exception {
		mockMvc.perform(post("/users/add").principal(principal).accept(MediaType.APPLICATION_JSON)
				.param("username", "Test").param("firstPassword", "12345678").param("secondPassword", "12345678"));

		MvcResult result = mockMvc.perform(
				post("/users/delete").principal(principal).accept(MediaType.APPLICATION_JSON).param("username", "Test"))
				.andReturn();
		assertEquals("true", result.getResponse().getContentAsString());
	}

	@Test
	public void deleteAdminTest() throws Exception {
		mockMvc.perform(post("/users/add").principal(principal).accept(MediaType.APPLICATION_JSON)
				.param("username", "Test").param("firstPassword", "12345678").param("secondPassword", "12345678"));

		MvcResult result = mockMvc.perform(post("/users/delete").principal(principal).accept(MediaType.APPLICATION_JSON)
				.param("username", "admin")).andReturn();
		assertEquals("false", result.getResponse().getContentAsString());
	}

	@Test
	public void addUserErrorDuplicatetUsername() throws Exception {
		createDefaultUser();
		MvcResult result = mockMvc.perform(post("/users/add").principal(principal).accept(MediaType.APPLICATION_JSON)
				.param("username", "test").param("firstPassword", "12345678").param("secondPassword", "12345678"))
				.andReturn();

		assertEquals("{\"map\":{\"existsError\":true}}", result.getResponse().getContentAsString());
	}

	@Test
	public void addUserErrorInvalidChar() throws Exception {
		MvcResult result = mockMvc.perform(post("/users/add").principal(principal).accept(MediaType.APPLICATION_JSON)
				.param("username", "Test<>").param("firstPassword", "12345678").param("secondPassword", "12345678"))
				.andReturn();

		assertEquals("{\"map\":{\"invalidCharError\":true}}", result.getResponse().getContentAsString());
	}

	@Test
	public void addUserErrorPasswordMatch() throws Exception {
		MvcResult result = mockMvc.perform(post("/users/add").principal(principal).accept(MediaType.APPLICATION_JSON)
				.param("username", "Test").param("firstPassword", "12345678").param("secondPassword", "123456789"))
				.andReturn();

		assertEquals("{\"map\":{\"matchError\":true}}", result.getResponse().getContentAsString());
	}

	@Test
	public void addUserErrorPasswordLengthTooShort() throws Exception {
		MvcResult result = mockMvc
				.perform(post("/users/add").principal(principal).accept(MediaType.APPLICATION_JSON)
						.param("username", "Test").param("firstPassword", "1234").param("secondPassword", "1234"))
				.andReturn();
		assertEquals("{\"map\":{\"passwordLength\":true}}", result.getResponse().getContentAsString());

	}

	@Test
	public void addUserErrorPasswordLength() throws Exception {
		MvcResult result = mockMvc.perform(post("/users/add").principal(principal).accept(MediaType.APPLICATION_JSON)
				.param("username", "Test").param("firstPassword", "123456789012345678901234567890123456789012345678901")
				.param("secondPassword", "123456789012345678901234567890123456789012345678901")).andReturn();

		assertEquals("{\"map\":{\"passwordLength\":true}}", result.getResponse().getContentAsString());

	}

	@Test
	public void addUserErrorUsernameLengthTooShort() throws Exception {
		MvcResult result = mockMvc.perform(post("/users/add").principal(principal).accept(MediaType.APPLICATION_JSON)
				.param("username", "Tes").param("firstPassword", "12345678").param("secondPassword", "12345678"))
				.andReturn();
		assertEquals("{\"map\":{\"usernameLength\":true}}", result.getResponse().getContentAsString());

	}

	@Test
	public void addUserErrorUsernameLengthTooLong() throws Exception {
		MvcResult result = mockMvc.perform(post("/users/add").principal(principal).accept(MediaType.APPLICATION_JSON)
				.param("username", "TesterTesterTesterTester").param("firstPassword", "12345678")
				.param("secondPassword", "12345678")).andReturn();

		assertEquals("{\"map\":{\"usernameLength\":true}}", result.getResponse().getContentAsString());
	}

	@Test
	public void editUserPositiv() throws Exception {
		createDefaultUser();

		ManagementUser user = userService.findUserByName("test");

		MvcResult result = mockMvc.perform(post("/users/" + user.getId() + "/edit").principal(principal)
				.accept(MediaType.APPLICATION_JSON).param("oldPassword", "87654321")
				.param("firstPassword", "12345678").param("secondPassword", "12345678")).andReturn();

		assertEquals("{\"map\":{\"Changed\":true}}", result.getResponse().getContentAsString());
	}
	
	@Test
	public void editUserErrorMatch() throws Exception {
		createDefaultUser();

		ManagementUser user = userService.findUserByName("test");

		MvcResult result = mockMvc.perform(post("/users/" + user.getId() + "/edit").principal(principal)
				.accept(MediaType.APPLICATION_JSON).param("oldPassword", "87654321")
				.param("firstPassword", "12345678").param("secondPassword", "1223456788")).andReturn();

		assertEquals("{\"map\":{\"matchError\":true}}", result.getResponse().getContentAsString());
	}
	
	@Test
	public void editUserErrorLengthTooShort() throws Exception {
		createDefaultUser();

		ManagementUser user = userService.findUserByName("test");

		MvcResult result = mockMvc.perform(post("/users/" + user.getId() + "/edit").principal(principal)
				.accept(MediaType.APPLICATION_JSON).param("oldPassword", "87654321")
				.param("firstPassword", "1234").param("secondPassword", "1234")).andReturn();

		assertEquals("{\"map\":{\"passwordLength\":true}}", result.getResponse().getContentAsString());
	}
	
	@Test
	public void editUserErrorLengthTooLong() throws Exception {
		createDefaultUser();

		ManagementUser user = userService.findUserByName("test");

		MvcResult result = mockMvc.perform(post("/users/" + user.getId() + "/edit").principal(principal)
				.accept(MediaType.APPLICATION_JSON).param("oldPassword", "87654321")
				.param("firstPassword", "123456789012345678901234567890123456789012345678901").param("secondPassword", "123456789012345678901234567890123456789012345678901")).andReturn();

		assertEquals("{\"map\":{\"passwordLength\":true}}", result.getResponse().getContentAsString());
	}
	
	@Test
	public void editUserErrorWrongOldPassword() throws Exception {
		createDefaultUser();
		
		ManagementUser user = userService.findUserByName("test");

		MvcResult result = mockMvc.perform(post("/users/" + user.getId() + "/edit").principal(principal)
				.accept(MediaType.APPLICATION_JSON).param("oldPassword", "817654321")
				.param("firstPassword", "12345678").param("secondPassword", "12345678")).andReturn();

		assertEquals("{\"map\":{\"oldPasswordError\":true}}", result.getResponse().getContentAsString());
	}
	
	@Test
	public void loadUserByUsernamePositiv() throws Exception {
		createDefaultUser();
		UserDetails user = userService.loadUserByUsername("test");
		assertEquals(user.getUsername(), "test");
	}
	
	@Test(expected=UsernameNotFoundException.class)
	public void loadUserByUsernameNegativ() throws Exception {
		UserDetails user = userService.loadUserByUsername("test");
		assertEquals(user.getUsername(), "test");
	}
	
	
	
	private void createDefaultUser() throws Exception {
		mockMvc.perform(post("/users/add").principal(principal).accept(MediaType.APPLICATION_JSON)
				.param("username", "test").param("firstPassword", "87654321").param("secondPassword", "87654321"))
				.andReturn();
	}
	
	
}
