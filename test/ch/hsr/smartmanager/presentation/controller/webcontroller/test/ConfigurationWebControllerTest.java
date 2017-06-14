package ch.hsr.smartmanager.presentation.controller.webcontroller.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;

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

import ch.hsr.smartmanager.presentation.webcontroller.ConfigurationWebController;

@WebAppConfiguration
@ActiveProfiles("dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:WebContent/WEB-INF/smartmanager-servlet.xml" })
public class ConfigurationWebControllerTest {

	@Autowired
	private ConfigurationWebController configurationWebController;
	private MockMvc mockMvc;
	private Principal principal;

	@Before
	public void setUp() {

		principal = new Principal() {
			@Override
			public String getName() {
				return "admin";
			}
		};

		mockMvc = MockMvcBuilders.standaloneSetup(configurationWebController).build();
	}

	@Test
	public void configurations() throws Exception {
		mockMvc.perform(get("/configurations").principal(principal)).andExpect(status().isOk());
	}

	@Test
	public void createConfigurationFragment() throws Exception {
		mockMvc.perform(get("/configurations/createConfigurationFragment").principal(principal))
				.andExpect(status().isOk());
	}

	@Test
	public void editConfigurationFragment() throws Exception {
		mockMvc.perform(get("/configurations/0/editConfigurationFragment").principal(principal))
				.andExpect(status().isOk());
	}

}
