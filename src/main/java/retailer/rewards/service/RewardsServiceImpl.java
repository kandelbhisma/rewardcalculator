package retailer.rewards.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import retailer.rewards.constants.Constants;
import retailer.rewards.entity.Transaction;
import retailer.rewards.model.Rewards;
import retailer.rewards.repository.TransactionRepository;

@Service
public class RewardsServiceImpl implements RewardsService {

	
	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	RewardsServiceUtil rewardsServiceUtil;

	public Rewards getRewardsByCustomerId(Long customerId) {

		Timestamp lastMonthTimestamp = rewardsServiceUtil.getDateBasedOnOffSetDays(Constants.totalDaysInMonth);
		Timestamp lastSecondMonthTimestamp = rewardsServiceUtil.getDateBasedOnOffSetDays(2*Constants.totalDaysInMonth);
		Timestamp lastThirdMonthTimestamp = rewardsServiceUtil.getDateBasedOnOffSetDays(3*Constants.totalDaysInMonth);

		System.out.println(lastMonthTimestamp);
		System.out.println(lastSecondMonthTimestamp);
		System.out.println(lastThirdMonthTimestamp);

		List<Transaction> lastMonthTransactions = transactionRepository.findAllByCustomerIdAndTransactionDateBetween(
				customerId, lastMonthTimestamp, Timestamp.from(Instant.now()));
		List<Transaction> lastSecondMonthTransactions = transactionRepository
				.findAllByCustomerIdAndTransactionDateBetween(customerId, lastSecondMonthTimestamp, lastMonthTimestamp);
		List<Transaction> lastThirdMonthTransactions = transactionRepository
				.findAllByCustomerIdAndTransactionDateBetween(customerId, lastThirdMonthTimestamp,
						lastSecondMonthTimestamp);

		Long lastMonthTotalRewardPoints = getRewardsPerMonth(lastMonthTransactions);
		Long lastSecondMonthTotalRewardPoints = getRewardsPerMonth(lastSecondMonthTransactions);
		Long lastThirdMonthTotalRewardPoints = getRewardsPerMonth(lastThirdMonthTransactions);

		Rewards customerRewards = new Rewards();
		customerRewards.setCustomerId(customerId);
		customerRewards.setLastMonthTotalRewardPoints(lastMonthTotalRewardPoints);
		customerRewards.setLastSecondMonthTotalRewardPoints(lastSecondMonthTotalRewardPoints);
		customerRewards.setLastThirdMonthTotalRewardPoints(lastThirdMonthTotalRewardPoints);
		customerRewards.setTotalRewardPoints(lastMonthTotalRewardPoints + lastSecondMonthTotalRewardPoints + lastThirdMonthTotalRewardPoints);

		return customerRewards;

	}

	private Long getRewardsPerMonth(List<Transaction> transactions) {
		return transactions.stream().map(this::calculateRewards).mapToLong(r -> r).sum();
	}

	private Long calculateRewards(Transaction t) {
		if (t.getTransactionAmount() > Constants.firstPointRewardLimit && t.getTransactionAmount() <= Constants.secondPointRewardLimit) {
			return Math.round(t.getTransactionAmount() - Constants.firstPointRewardLimit);
		} else if (t.getTransactionAmount() > Constants.secondPointRewardLimit) {
			return Math.round(t.getTransactionAmount() - Constants.secondPointRewardLimit) * 2
					+ (Constants.secondPointRewardLimit - Constants.firstPointRewardLimit);
		} else
			return 0L;
	}
}
