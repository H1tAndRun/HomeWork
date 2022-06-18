
/**
 * // Экспертный уровень:
 * // Предыстория: Мы находимся в статистическом институте. Хочется понять уровень миграции между регионами за месяц.
 * // Для этого было решено произвести анализ передвижения автомобилей: на границе каждого региона стоят камеры,
 * // фиксирующие въезд и выезд автомобилей. Формат автомобильного номера: (буква)(3 цифры)(2 буквы)(2-3 цифры)
 * // К474СЕ178 - где 178 регион
 * <p>
 * // Задача №1: составить топ-5 популярных регионов (куда больше всего въехали).
 * // Сделать аналитическую раскладку: машины какого региона больше всего въезжали в этот топ-5.
 * // Ожидаемый результат:
 * // ТОП-5: 98, 178, 01, 22, 33
 * // 98 - больше всего въехало с номерами 178: 23 машины
 * // 178 - больше всего въехало с номера 98: 50 машин
 * // 01 - больше всего въехало с номера 178: 11 машин
 * // 22 - больше всего въехало с номера 01: 30 машин
 * // 33 - больше всего въехало с номера 12: 100 машин
 * <p>
 * // Задача №2: узнать сколько всего машин со спец номерами: начинаются на M и заканчиваются на АВ.
 * // Не повторяющиеся
 * <p>
 * //Входящие данные
 * // Map<Integer, Map<String, String[]>> - где ключ первого уровня - номер региона,
 * // out, input - ключи второго уровня (выезд, въезд), и String[] - массивы номеров.
 * // { 1 : {
 * //      "out" : ["К474СЕ178"],
 * //      "input": ["А222РТ178"]
 * //    },
 * //   2 : {
 * //        "out" : ["К722АВ12", "А222РТ178"],
 * //        "input" : ["М001АВ01", "А023РВ73"],
 * //   }
 * // ..
 * //  }
 * <p>
 * //Список технологий:
 * // Set (HashSet) - узнать что это, set.contains(), set.put()
 * // Map (HashMap) - узнать что это, map.get(), map.put(), map.entrySet() - для итерации, entry.getValue(), entry.getKey()
 * // <Integer> - обозначает тип который хранится в этой структуре данных (Generics)
 * // Регулярные выражения - вытащить регион авто
 */

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HomeWork {
    static Map<Integer, Map<String, String[]>> Generator = GeneratorExpertHomework.getData();

    public static void main(String[] args) {
        popular();
    }


    public static Set<Integer> ListOfKeys() {
        Set<Integer> listKeys = Generator.keySet();
        return listKeys;
    }

    public static void popular() {
        Integer[] listToArray = ListOfKeys().toArray(new Integer[0]);
        Map<Integer, Integer> list = new HashMap<>();
        for (int i = 0; i < listToArray.length; i++) {
            list.put(listToArray[i], (Generator.get(listToArray[i]).get("input")).length);
        }
        InputCar(Sort(list));
    }

    public static Integer[] Sort(Map<Integer, Integer> top5) {
        Map<Integer, Integer> sort = top5.entrySet()
                .stream().sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        Integer x[] = sort.keySet().toArray(new Integer[0]);
        return x;
    }

    public static void InputCar(Integer[] x) {
        System.out.println("Топ 5 регионов\n" + Arrays.toString(x));
        for (int i = 0; i < x.length; i++) {
            String[] CarNumber = Generator.get(x[i]).get("input");
            System.out.println(x[i] + " " + mostEntered(CarNumber));
        }


    }

    public static String mostEntered(String[] y) {
        ArrayList<String> string = new ArrayList<>();
        for (int i = 0; i < y.length; i++) {
            string.add(y[i].substring(6));
        }
        Map<String, Integer> wordMap = new HashMap<String, Integer>();

        for (String st : string) {
            String input = st.toUpperCase();
            if (wordMap.get(input) != null) {
                Integer count = wordMap.get(input) + 1;
                wordMap.put(input, count);
            } else {
                wordMap.put(input, 1);
            }
        }
        Object maxEntry = Collections.max(wordMap.entrySet(), Map.Entry.comparingByValue()).getKey();
        String max = (String) maxEntry;
        return "Больше всего въехало с номерами " + max + ": " + wordMap.get(max) + " машины";
    }
}
