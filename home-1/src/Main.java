import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String [] args) throws IOException {

        /*
        *
        * Выведите на экран надпись "Your time is limited, so
don’t waste it living someone else’s life" Steve Jobs на разных
строках. Пример вывода:
“Your time is limited,
so don’t waste it
 living someone else’s life”
 Steve Jobs
        *
        * */
    /*
        System.out.println("“Your time is limited,");
        System.out.println("\tso don’t waste it");
        System.out.println("\t\tliving someone else’s life”");
        System.out.println("\t\t\tSteve Jobs");
        */

        /*
        * Пользователь вводит с клавиатуры два числа. Первое
число — это значение, второе число процент, который
необходимо посчитать. Например, мы ввели с клавиатуры
50 и 10. Требуется вывести на экран 10 процентов от 50.
Результат: 5
        * */

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter value: ");
        String sValue = reader.readLine();

        System.out.println("Enter percent: ");
        String sPercent = reader.readLine();

        int iValue = Integer.parseInt(sValue);
        int iPercent = Integer.parseInt(sPercent);
        int result = iValue*iPercent/100;

        System.out.println(result);

    }
}
