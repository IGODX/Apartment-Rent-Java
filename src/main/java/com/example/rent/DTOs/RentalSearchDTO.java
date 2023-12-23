package com.example.rent.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RentalSearchDTO {
    @DateTimeFormat(pattern = "yyyy-MM")
    private Date rentStartMonth;

    @DateTimeFormat(pattern = "yyyy-MM")
    private Date rentEndMonth;

    private boolean avgRentTermLessMonth;
    private boolean avgRentTermGreaterYear;
}
