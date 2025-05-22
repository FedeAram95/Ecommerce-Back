package com.ecommerce.ecommerce.emails.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailBody {

    private String title;
    private String message;
    private Object content;
}
