package com.yanolja_final.domain.packages.dto.response;

import java.util.List;

public record PackageScheduleResponse(
    Integer day,
    List<String> schedule,
    String breakfast,
    String lunch,
    String dinner
) {

}
