package ch.hsr.smartmanager.presentation.controller.webcontroller.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;

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
import ch.hsr.smartmanager.presentation.webcontroller.FrontWebController;

@WebAppConfiguration
@ActiveProfiles("dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:WebContent/WEB-INF/smartmanager-servlet.xml" })
public class FrontWebControllerTest {

	@Autowired
	private FrontWebController frontWebController;
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

		this.mockMvc = MockMvcBuilders.standaloneSetup(frontWebController).build();
	}
	
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void receiveResponse() throws Exception {
		mockMvc.perform(get("/").principal(principal)).andExpect(status().isOk());
	}
}
