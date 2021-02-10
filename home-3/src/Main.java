public class Main {

    public static void main(String[] args) {
        Array array = new Array();
        array.Add(12);
        array.Add(12);
        array.Add(1);
        array.Add(2);
        array.Add(0);
        array.Add(40);

        array.SortByAsc();
        array.SortByDesc();

        System.out.println(array.Min());
        System.out.println(array.Max());
        System.out.println(array);
    }
}
