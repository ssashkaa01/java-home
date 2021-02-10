import java.util.ArrayList;
import java.util.Collections;

public class Array {
    ArrayList<Integer> arrayList;

    public Array() {
        arrayList = new ArrayList<>();
    }

    public ArrayList<Integer> getArrayList() {
        return arrayList;
    }

    public void Add(int number)
    {
        arrayList.add(number);
    }

    public void SortByAsc()
    {
        Collections.sort(arrayList);
    }

    public void SortByDesc()
    {
        arrayList.sort(Collections.reverseOrder());
    }

    public int Min()
    {
        return Collections.min(arrayList);
    }

    @Override
    public String toString() {
        return "Array{" +
                "arrayList=" + arrayList +
                '}';
    }

    public int Max()
    {
        return Collections.max(arrayList);
    }
}
