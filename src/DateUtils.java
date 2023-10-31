import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Die DateUtils-Klasse bietet Hilfsfunktionen zur Darstellung von LocalDate-Objekten.
 */
public class DateUtils {

    /**
     * Formatieren eines LocalDate-Datums in das deutsche Datumsformat "dd.MM.yyyy".
     *
     * @param date Das zu formatierende LocalDate-Datum.
     * @return Das formatierte Datum als Zeichenkette im Format "dd.MM.yyyy".
     */
    public static String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return date.format(formatter);
    }

}
