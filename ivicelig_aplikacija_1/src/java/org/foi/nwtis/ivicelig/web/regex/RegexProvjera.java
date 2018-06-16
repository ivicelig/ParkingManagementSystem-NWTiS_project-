package org.foi.nwtis.ivicelig.web.regex;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Ivica
 */
public class RegexProvjera {

    private final String sintaksa = "^([^\\s]+) ([^\\s]+); ([^\\s]+) ([^\\s]+); "
            + "(PAUZA|KRENI|PASIVNO|AKTIVNO|STANI|STANJE|LISTAJ|(DODAJ) \"([^\\s]+)\" \"([^\\s]+)\"|"
            + "(GRUPA) (DODAJ|PREKID|KRENI|PAUZA|STANJE)|(AZURIRAJ) \"([^\\s]+)\" \"([^\\s]+)\");";
     private final String sintaksaAutentikacija = "^([^\\s]+) ([^\\s]+); ([^\\s]+) ([^\\s]+);";

    public ArrayList<String> dohvatiRegexGrupe(String input) {
        ArrayList<String> array = new ArrayList<>();
        Matcher matcher = obradiRegex(input);
        for (int i = 0; i <= matcher.groupCount(); i++) {
            if (matcher.group(i) != null) {
                array.add(matcher.group(i));
            }
        }
        return array;
    }

    public ArrayList<String> dohvatiRegexGrupeAutentifikacija(String input) {
        ArrayList<String> array = new ArrayList<>();
        Matcher matcher = obradiRegexAutentifikacija(input);
        for (int i = 0; i <= matcher.groupCount(); i++) {
            if (matcher.group(i) != null) {
                array.add(matcher.group(i));
            }
        }
        return array;
    }
    private Matcher obradiRegex(String input) {
        String p = input.trim();
        Pattern pattern = Pattern.compile(sintaksa);
        Matcher m = pattern.matcher(p);

        m.matches();

        return m;
    }
    private Matcher obradiRegexAutentifikacija(String input) {
        String p = input.trim();
        Pattern pattern = Pattern.compile(sintaksaAutentikacija);
        Matcher m = pattern.matcher(p);

        m.matches();

        return m;
    }

    public boolean ulazZadovoljavavaRegex(String input) {
        return obradiRegex(input).matches();
    }
    public boolean ulazZadovoljavavaRegexAutentifikacija(String input) {
        return obradiRegexAutentifikacija(input).matches();
    }
    

}
