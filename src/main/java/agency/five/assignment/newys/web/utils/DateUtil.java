package agency.five.assignment.newys.web.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(MsgUtil.get("datetime.format"));

    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime != null ? DATETIME_FORMATTER.format(dateTime) : "";
    }
}
