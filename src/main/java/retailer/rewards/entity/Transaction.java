package retailer.rewards.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TRANSACTION")
@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class Transaction {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "TRANS_ID")
    private Long transactionId;

    @Column(name="CUST_ID")
    private Long customerId;

    @Column(name = "TRANS_DATE")
    private Timestamp transactionDate;

    @Column(name = "AMOUNT")
    private double transactionAmount;
}
