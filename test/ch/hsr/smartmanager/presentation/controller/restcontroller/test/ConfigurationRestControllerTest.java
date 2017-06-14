package ch.hsr.smartmanager.presentation.controller.restcontroller.test;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;

import org.json.JSONArray;
import org.json.JSONObject;
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

import ch.hsr.smartmanager.data.Configuration;
import ch.hsr.smartmanager.data.repositories.ConfigurationItemRepository;
import ch.hsr.smartmanager.presentation.restcontroller.ConfigurationRestController;
import ch.hsr.smartmanager.service.applicationservices.ConfigurationService;

@WebAppConfiguration
@ActiveProfiles("dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:WebContent/WEB-INF/smartmanager-servlet.xml" })
public class ConfigurationRestControllerTest {

	@Autowired
	private ConfigurationRestController configurationRestController;
	@Autowired
	private ConfigurationService configurationService;
	@Autowired
	private ConfigurationItemRepository configurationItemRepository;
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

		mockMvc = MockMvcBuilders.standaloneSetup(configurationRestController).build();
	}

	@After
	public void tearDown() throws Exception {
		configurationItemRepository.deleteAll();
	}

	@Test
	public void addConfigurations() throws Exception {
		
		String response = "{\"Object Link\":\"3/0/14\",\"Value\":\"+2\"}";
		JSONObject jsonObject = new JSONObject(response);

		JSONArray jsonArray = new JSONArray();
		jsonArray.put("Test");	
		jsonArray.put("Test");
		jsonArray.put(jsonObject);

		mockMvc.perform(post("/configurations/add").param("value", jsonArray.toString()).principal(principal)).andExpect(status().isOk());
		assertEquals(1, configurationService.getAllConfigurations().size());
	
	}
	
	@Test
	public void addTwoSameConfigurations() throws Exception {
		
		String response = "{\"Object Link\":\"3/0/14\",\"Value\":\"+2\"}";
		JSONObject jsonObject = new JSONObject(response);

		JSONArray jsonArray = new JSONArray();
		jsonArray.put("Test");	
		jsonArray.put("Test");
		jsonArray.put(jsonObject);

		mockMvc.perform(post("/configurations/add").param("value", jsonArray.toString()).principal(principal));
		mockMvc.perform(post("/configurations/add").param("value", jsonArray.toString()).principal(principal)).andExpect(status().isOk());

		assertEquals(1, configurationService.getAllConfigurations().size());
	
	}
	
	@Test
	public void deleteConfigurations() throws Exception {

		String response = "{\"Object Link\":\"3/0/14\",\"Value\":\"+2\"}";
		JSONObject jsonObject = new JSONObject(response);

		JSONArray jsonArray = new JSONArray();
		jsonArray.put("Test");
		jsonArray.put("Test");
		jsonArray.put(jsonObject);
		Configuration configuration = configurationService.saveConfiguration(jsonArray);

		mockMvc.perform(post("/configurations/delete").param("value", configuration.getId()).principal(principal))
				.andExpect(status().isOk());
		assertEquals(0, configurationService.getAllConfigurations().size());

	}

}
