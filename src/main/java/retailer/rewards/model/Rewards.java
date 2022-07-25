package retailer.rewards.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class Rewards {
    private long customerId;
	private long lastMonthTotalRewardPoints;
    private long lastSecondMonthTotalRewardPoints;
    private long lastThirdMonthTotalRewardPoints;
    private long totalRewardPoints;

}
