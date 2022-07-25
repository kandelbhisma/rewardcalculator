package retailer.rewards.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import retailer.rewards.entity.Customer;
import retailer.rewards.model.Rewards;
import retailer.rewards.repository.CustomerRepository;
import retailer.rewards.service.RewardsService;

@RestController
@RequestMapping("/customers")
public class RewardsController {

    @Autowired
    RewardsService rewardsService;

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping(value = "/{customerId}/reward",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Rewards> getRewardsByCustomerId(@PathVariable("customerId") Long customerId){
        Customer customer = customerRepository.findByCustomerId(customerId);
        if(customer == null)
        {
        	throw new RuntimeException("The Customer Id provided is either invalid or missing.");
        }
        Rewards customerRewards = rewardsService.getRewardsByCustomerId(customerId);
        return new ResponseEntity<>(customerRewards,HttpStatus.OK);
    }

}
