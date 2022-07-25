package retailer.rewards.service;

import retailer.rewards.model.Rewards;


public interface RewardsService {
    public Rewards getRewardsByCustomerId(Long customerId);
}
