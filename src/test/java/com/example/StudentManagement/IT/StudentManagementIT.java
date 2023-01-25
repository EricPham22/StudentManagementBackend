package com.example.StudentManagement.IT;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.hamcrest.CoreMatchers;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.StudentManagement.StudentManagementApplication;
import com.example.StudentManagement.service.StudentService;


@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ContextConfiguration(classes = StudentManagementApplication.class)
public class StudentManagementIT {

	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private StudentService studentService;
	
	@Sql(value = "classpath:init/StudentData.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	void canFindAllStudents() throws Exception {
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/Students").contentType(MediaType.APPLICATION_JSON));
		result.andExpect(MockMvcResultMatchers.status().isOk());
		result.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(studentService.getAllStudents().size())));
		result.andDo(MockMvcResultHandlers.print());
		assertThat(this.studentService.getAllStudents()).hasSize(4);
	}
	
	@Test
	void canAddStudent() throws Exception {
		JSONObject json = new JSONObject();
		json.put("name", "aria");
		json.put("age", 3);
		json.put("email", "aria@bigdog.com");
		json.put("address", "24 aria rd");

		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/Students/AddNewStudent")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString()));
		result.andExpect(MockMvcResultMatchers.status().isOk());
		result.andDo(MockMvcResultHandlers.print());
		assertThat(this.studentService.getAllStudents()).hasSize(1);
		assertEquals(this.studentService.getStudentById(1).getName(), "aria");
		assertEquals(this.studentService.getStudentById(1).getAge(), 3);
		assertEquals(this.studentService.getStudentById(1).getEmail(), "aria@bigdog.com");
		assertEquals(this.studentService.getStudentById(1).getAddress(), "24 aria rd");
	}
	
	@Test
	void canAddMultipleStudents() throws Exception {
		JSONObject json = new JSONObject();
		json.put("name", "aria");
		json.put("age", 3);
		json.put("email", "aria@bigdog.com");
		json.put("address", "24 aria rd");
		
		JSONObject json2 = new JSONObject();
		json2.put("name", "happy");
		json2.put("age", 1);
		json2.put("email", "happy@deku.com");
		json2.put("address", "1 youn rd");
		
		JSONArray jsonArray = new JSONArray();
		jsonArray.put(json);
		jsonArray.put(json2);
		
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/Students/AddMultipleNewStudents")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonArray.toString()));
		result.andExpect(MockMvcResultMatchers.status().isOk());
		result.andDo(MockMvcResultHandlers.print());
		assertThat(this.studentService.getAllStudents()).hasSize(2);
		assertEquals(this.studentService.getStudentById(1).getName(), "aria");
		assertEquals(this.studentService.getStudentById(1).getAge(), 3);
		assertEquals(this.studentService.getStudentById(1).getEmail(), "aria@bigdog.com");
		assertEquals(this.studentService.getStudentById(1).getAddress(), "24 aria rd");
		assertEquals(this.studentService.getStudentById(2).getName(), "happy");
		assertEquals(this.studentService.getStudentById(2).getAge(), 1);
		assertEquals(this.studentService.getStudentById(2).getEmail(), "happy@deku.com");
		assertEquals(this.studentService.getStudentById(2).getAddress(), "1 youn rd");
	}
	
	@Sql(value = "classpath:init/StudentData.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	void canUpdateStudent() throws Exception {
		JSONObject json = new JSONObject();
		json.put("studentId", 3);
		json.put("age", 2);
		json.put("email", "gusty@aronce.com");
		json.put("address", "9 gaston rd");
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/Students/Update")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString()));
		result.andDo(MockMvcResultHandlers.print());
		result.andExpect(MockMvcResultMatchers.status().isOk());
		assertEquals(this.studentService.getStudentById(3).getName(), "Gaston");
		assertEquals(this.studentService.getStudentById(3).getAge(), 2);
		assertEquals(this.studentService.getStudentById(3).getEmail(), "gusty@aronce.com");
		assertEquals(this.studentService.getStudentById(3).getAddress(), "9 gaston rd");
	}
	
	@Sql(value = "classpath:init/StudentData.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	void canDeleteStudent() throws Exception{
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete("/Students/Delete")
				.param("id", "3"));
		result.andDo(MockMvcResultHandlers.print());
		result.andExpect(MockMvcResultMatchers.status().isOk());
		assertThat(this.studentService.getAllStudents()).hasSize(3);
	}
	
	@Sql(value = "classpath:init/StudentData.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	void canHandleExceptionsThrownFromDelete() throws Exception {
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete("/Students/Delete")
				.param("id", "6"));
		result.andDo(MockMvcResultHandlers.print());
		result.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	@Sql(value = "classpath:init/StudentData.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	void canHandleExceptionsThrownFromPost() throws Exception {
		JSONObject json = new JSONObject();
		json.put("name", null);
		json.put("age", null);
		json.put("email", null);
		json.put("address", null);
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/Students/AddNewStudent")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString()));
		result.andDo(MockMvcResultHandlers.print());
		result.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	@Sql(value = "classpath:init/StudentData.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	void canHandleExceptionsThrownFromPut() throws Exception {
		JSONObject json = new JSONObject();
		json.put("name", "aria");
		json.put("age", 6);
		json.put("email", "aria@bigdog.com");
		json.put("address", "24 aria rd");
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/Students/Update")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString()));
		result.andDo(MockMvcResultHandlers.print());
		result.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	@Sql(value = "classpath:init/StudentData.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
	@Test
	void canHandleExceptionsThrownFromGet() throws Exception {
		JSONObject json = new JSONObject();
		json.put("name", "aria");
		json.put("age", 6);
		json.put("email", "aria@bigdog.com");
		json.put("address", "24 aria rd");
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/Students/findById")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString()));
		result.andDo(MockMvcResultHandlers.print());
		result.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
}
