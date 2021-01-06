package com.neu.flightbooking.dao;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.neu.flightTicket.model.CustomerInfo;
import com.neu.flightTicket.service.CredentialService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
class SearchRepositoryTest {
	@Autowired
    private WebApplicationContext wac;
	private MockMvc mockMvc;
	
	@Mock
	private CredentialService mockRepository;
	
	
	   @Before
	    public void setUp(){
	        MockitoAnnotations.initMocks(this);
	        
	    }
	     
	@Test
	void test() throws Exception  {
		CustomerInfo details= mockRepository.userValidation("ajith","ajith");
		System.out.println("Details "+details);
	}

}
