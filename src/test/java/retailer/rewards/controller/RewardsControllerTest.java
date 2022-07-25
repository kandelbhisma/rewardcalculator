package retailer.rewards.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import retailer.rewards.TotalRewardsPointCalculatorApplication;
import retailer.rewards.entity.Customer;
import retailer.rewards.model.Rewards;
import retailer.rewards.repository.CustomerRepository;
import retailer.rewards.service.RewardsService;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/** Unit test for {@link RewardsController} */
@AutoConfigureMockMvc
@SpringBootTest(classes = TotalRewardsPointCalculatorApplication.class)
class RewardsControllerTest {

    @Autowired protected MockMvc mockMvc;

    @MockBean RewardsService rewardsService;
    @MockBean CustomerRepository customerRepository;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testGetRewardsByCustomerId() throws Exception {
        when(customerRepository.findByCustomerId(any())).thenReturn(Customer.builder().customerId(100L).build());
        when(rewardsService.getRewardsByCustomerId(any()))
                .thenReturn(Rewards.builder()
                        .customerId(100L)
                        .lastMonthTotalRewardPoints(100)
                        .lastSecondMonthTotalRewardPoints(200)
                        .lastThirdMonthTotalRewardPoints(300)
                        .totalRewardPoints(600)
                        .build());
        mockMvc.perform(get("/customers/100/reward"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.customerId", is(100)))
                .andExpect(jsonPath("$.lastMonthTotalRewardPoints", is(100)))
                .andExpect(jsonPath("$.lastSecondMonthTotalRewardPoints", is(200)))
                .andExpect(jsonPath("$.lastThirdMonthTotalRewardPoints", is(300)))
                .andExpect(jsonPath("$.totalRewardPoints", is(600)));

    }




}