//package com.example.expense_sharing_webapp;
//
//import com.example.expense_sharing_webapp.controllers.ExpenseController;
//import com.example.expense_sharing_webapp.models.ExpenseDTO;
//import com.example.expense_sharing_webapp.models.ExpenseRequest;
//import com.example.expense_sharing_webapp.models.SplitRequest;
//import com.example.expense_sharing_webapp.services.ExpenseService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(ExpenseController.class)
//public class ExpenseControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ExpenseService expenseService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    private ExpenseDTO testExpense;
//
//    @BeforeEach
//    void setup() {
//        // Initialize testExpense object that the service will return
//        testExpense = new ExpenseDTO();
//        testExpense.setDescription("Dinner");
//        testExpense.setTotalAmount(1000.0);
//    }
//
//    @Test
//    public void testAddExpense() throws Exception {
//        // Build an ExpenseRequest object
//        ExpenseRequest expenseRequest = new ExpenseRequest();
//        expenseRequest.setDescription("Dinner");
//        expenseRequest.setTotalAmount(1000.0);
//        expenseRequest.setParticipantIds(Arrays.asList(1L, 2L));
//
//        expenseRequest.setSplits(List.of(
//                new SplitRequest(1L, "EQUAL", null, null),  // Equal split, no amount or percentage
//                new SplitRequest(2L, "EQUAL", null, null)   // Equal split, no amount or percentage
//        ));
//
//        // Mock the service to return the testExpense when addExpense is called
//        Mockito.when(expenseService.addExpense(any(ExpenseRequest.class))).thenReturn(testExpense);
//
//        // Perform the POST request and assert the status and response content
//        mockMvc.perform(post("/expenses")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(expenseRequest)))
//                .andExpect(status().isOk())  // Expect HTTP 200 OK
//                .andExpect(content().json(objectMapper.writeValueAsString(testExpense)));  // Expect the returned JSON to match testExpense
//    }
//}
