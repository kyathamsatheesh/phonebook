package com.cse5382.assignment;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
////import jdk.nashorn.internal.ir.annotations.Ignore;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
////@SpringBootTest
////@Ignore
//class AssignmentApplicationTests {
//
//	@Test
//	void contextLoads() {
//	}
//
//}

import com.cse5382.assignment.Component.JwtUtil;
import com.cse5382.assignment.Model.PhoneBookEntry;
import com.cse5382.assignment.Service.PhoneBookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
public class AssignmentApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PhoneBookService phoneBookService;

    @MockBean
    private JwtUtil jwtUtil;

    private String token;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        token = "test.jwt.token";
        when(jwtUtil.isTokenValid(token)).thenReturn(true);
        when(jwtUtil.extractUsername(token)).thenReturn("testUser");
    }

    private void performPhoneTest(String phoneNumber, boolean isValid) throws Exception {
    	PhoneBookEntry entry = new PhoneBookEntry();
        entry.setName("Test Name");
        entry.setPhoneNumber(phoneNumber);

        when(phoneBookService.findByNumber(phoneNumber)).thenReturn(null);
        
        String requestBody = objectMapper.writeValueAsString(entry);

        if (isValid) {
            mockMvc.perform(post("/phoneBook/add")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer " + token)
                    .content(requestBody))
                    .andExpect(status().isOk());
        } else {
            mockMvc.perform(post("/phoneBook/add")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer " + token)
                    .content(requestBody))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.error").value("Validation Failed"))
                    .andExpect(jsonPath("$.details.phoneNumber").exists());
        }
               
        
        //.andExpect(isValid ? status().isOk() : status().isBadRequest());
    }

    @Test
    public void testValidPhoneNumbers() throws Exception {
        List<String> validPhones = List.of(
                "12345",
                "(703)111-2121",
                "123-1234",
                "+1(703)111-2121",
                "+32 (21) 212-2324",
                "1(703)123-1234",
                "011 701 111 1234",
                "12345.12345",
                "011 1 703 111 1234"
        );
        for (String phone : validPhones) {
            performPhoneTest(phone, true);
        }
    }

    @Test
    public void testInvalidPhoneNumbers() throws Exception {
        List<String> invalidPhones = List.of(
                "123",
                "1/703/123/1234",
                "Nr 102-123-1234",
                "<script>alert(“XSS”)</script>",
                "7031111234",
                "+1234 (201) 123-1234",
                "(001) 123-1234",
                "+01 (703) 123-1234",
                "(703) 123-1234 ext 204"
        );
        for (String phone : invalidPhones) {
            performPhoneTest(phone, false);
        }
    }
}

