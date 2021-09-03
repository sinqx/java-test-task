package com.testTask.test.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.testTask.test.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JacksonXmlRootElement(localName = "response")
public class PaymentResponse {
    @JacksonXmlProperty(isAttribute = true, localName = "id")
    Long id;

    @JacksonXmlProperty(isAttribute = true, localName = "dts")
    LocalDateTime Date;

    Long p_id;
    Long status;
    String message;

    public static PaymentResponse constructor(Payment payment, String message, Long status){
        String stringSupId = payment.getAccount().toString();
        Long last6IndexOfSupId = Long.valueOf(stringSupId.substring(stringSupId.length() - 6));
        return PaymentResponse.builder()
                .id(payment.getId())
                .Date(payment.getDate())
                .p_id(last6IndexOfSupId)
                .status(status)
                .message(message)
                .build();
    }
}

