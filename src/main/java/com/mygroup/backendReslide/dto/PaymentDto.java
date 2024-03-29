package com.mygroup.backendReslide.dto;

import com.mygroup.backendReslide.dto.PaymentMethodDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {
    private Long id;
    private Long transactionId;
    private Long orderId;
    private Long invoiceId;

    private String username;
    private String date;
    private String paymentMethod;

    private BigDecimal paid;
    private BigDecimal owedBefore;
    private BigDecimal owedAfter;

    private String status;  // payment status
    private String notes;

}
