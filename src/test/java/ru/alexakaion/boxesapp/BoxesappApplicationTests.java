package ru.alexakaion.boxesapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(args={"classpath:test.xml"})
@AutoConfigureMockMvc
class BoxesappApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testShouldReturnCorrectAnswer() throws Exception {
		String testJson = "{\"box\":\"1\",\"color\":\"red\"}";

		this.mockMvc.perform(post("/test")
						.contentType(MediaType.APPLICATION_JSON)
						.content(testJson))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().json("[2,3]"));
	}

}
