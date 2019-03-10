package hw3.hash;

import java.util.Iterator;
import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        Iterator oo = oomages.iterator();
        int[] track = new int[M];
        while (oo.hasNext()) {
            int val = oo.next().hashCode();
            val = ((val % M) + M) % M;
            track[val] += 1;
        }
        int size = oomages.size();
        for (int i = 0; i < M; i += 1) {
            if (track[i] < size / 50 || track[i] > size / 2.5) {
                return false;
            }
        }
        return true;
    }
}
