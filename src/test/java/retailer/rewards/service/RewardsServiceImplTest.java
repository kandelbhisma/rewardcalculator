package retailer.rewards.service;

import com.sun.tools.javac.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import retailer.rewards.constants.Constants;
import retailer.rewards.entity.Transaction;
import retailer.rewards.model.Rewards;
import retailer.rewards.repository.TransactionRepository;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/** Unit test for {@link RewardsServiceImpl} */
class RewardsServiceImplTest {

    private static final Transaction TRANSACTION1 = Transaction.builder().transactionId(1L).transactionAmount(100).build();
    private static final Transaction TRANSACTION2 = Transaction.builder().transactionId(2L).transactionAmount(200).build();
    private static final Transaction TRANSACTION3 = Transaction.builder().transactionId(3L).transactionAmount(300).build();
    private static final Transaction TRANSACTION4 = Transaction.builder().transactionId(4L).transactionAmount(40).build();
    private static final Transaction TRANSACTION5 = Transaction.builder().transactionId(5L).transactionAmount(50).build();
    private static final Transaction TRANSACTION6 = Transaction.builder().transactionId(6L).transactionAmount(20).build();
    private static final Transaction TRANSACTION7 = Transaction.builder().transactionId(7L).transactionAmount(500).build();
    private static final Transaction TRANSACTION8 = Transaction.builder().transactionId(8L).transactionAmount(10).build();
    private static final Transaction TRANSACTION9 = Transaction.builder().transactionId(9L).transactionAmount(150).build();

    private static final Long CUSTOMER_ID = 1L;

    @Mock TransactionRepository transactionRepository;
    @Mock RewardsServiceUtil rewardsServiceUtil;


    @InjectMocks RewardsServiceImpl testObject;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetRewardsByCustomerId(){
        // ARRANGE
        when(rewardsServiceUtil.getDateBasedOnOffSetDays(Constants.totalDaysInMonth))
                .thenReturn(Timestamp.valueOf("2020-02-02 00:00:00"));
        when(rewardsServiceUtil.getDateBasedOnOffSetDays(2*Constants.totalDaysInMonth))
                .thenReturn(Timestamp.valueOf("2020-02-02 00:00:00"));
        when(rewardsServiceUtil.getDateBasedOnOffSetDays(3*Constants.totalDaysInMonth))
                .thenReturn(Timestamp.valueOf("2020-02-02 00:00:00"));

        Timestamp lastMonthTimestamp = rewardsServiceUtil.getDateBasedOnOffSetDays(Constants.totalDaysInMonth);
        Timestamp lastSecondMonthTimestamp = rewardsServiceUtil.getDateBasedOnOffSetDays(2*Constants.totalDaysInMonth);
        Timestamp lastThirdMonthTimestamp = rewardsServiceUtil.getDateBasedOnOffSetDays(3*Constants.totalDaysInMonth);

        when(transactionRepository.findAllByCustomerIdAndTransactionDateBetween( eq(CUSTOMER_ID), eq(lastMonthTimestamp), any()))
                .thenReturn(List.of(TRANSACTION1, TRANSACTION2, TRANSACTION3));
        when(transactionRepository.findAllByCustomerIdAndTransactionDateBetween( eq(CUSTOMER_ID), eq(lastSecondMonthTimestamp), eq(lastMonthTimestamp)))
                .thenReturn(List.of(TRANSACTION6, TRANSACTION4, TRANSACTION5));
        when(transactionRepository.findAllByCustomerIdAndTransactionDateBetween( eq(CUSTOMER_ID), eq(lastThirdMonthTimestamp), eq(lastSecondMonthTimestamp)))
                .thenReturn(List.of(TRANSACTION7, TRANSACTION8, TRANSACTION9));

        // ACT
        Rewards rewards = testObject.getRewardsByCustomerId(CUSTOMER_ID);

        // ASSERT
        System.out.println(rewards);
        assertEquals(rewards.getCustomerId(), CUSTOMER_ID);
        assertEquals(rewards.getLastMonthTotalRewardPoints(), 750);
        assertEquals(rewards.getLastSecondMonthTotalRewardPoints(), 1000);
        assertEquals(rewards.getLastThirdMonthTotalRewardPoints(), 1000);
        assertEquals(rewards.getTotalRewardPoints(), 2750);
    }
}