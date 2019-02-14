package nl.qnh.qforce.integration;

import nl.qnh.qforce.QforceApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = QforceApplication.class)
@WebAppConfiguration
public class QforceApplicationTests {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void setupMockMvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void checkSearchEndpointHealth() throws Exception {
		mockMvc.perform(get("/persons?q="))
				.andExpect(status().isOk());
	}


	@Test
	public void searchPersonBodyTest() throws Exception {
		mockMvc.perform(get("/persons?q=a"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", notNullValue()));
	}

	@Test
	public void getPersonBodyTest() throws Exception {
		mockMvc.perform(get("/persons/1/"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1)));
	}

}

