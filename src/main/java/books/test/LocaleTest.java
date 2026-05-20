package books.test;

import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class LocaleTest {

    static void main() {
        Locale.availableLocales().forEach(System.out::println);

        System.out.println("Default locale: " + Locale.getDefault());

        Locale localeUS = Locale.US;
        Locale localePtBR = Locale.of("pt", "BR");

        DateTimeFormatter formatterDateHour = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG);
        System.out.println(formatterDateHour.format(ZonedDateTime.now()));
        System.out.println(formatterDateHour.withLocale(localeUS).format(ZonedDateTime.now()));
        System.out.println(formatterDateHour.withLocale(localePtBR).format(ZonedDateTime.now()));

        DateTimeFormatter formatterYearMonth = DateTimeFormatter.ofPattern("MMMM/yyyy");
        System.out.println(formatterYearMonth.format(YearMonth.now()));
        System.out.println(formatterYearMonth.withLocale(localeUS).format(YearMonth.now()));
        System.out.println(formatterYearMonth.withLocale(localePtBR).format(YearMonth.now()));
    }
}
