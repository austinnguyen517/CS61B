public class OffByN implements CharacterComparator{
    private int boundary;

    public OffByN (int N) {
        boundary = N;
    }
    public boolean equalChars(char x, char y) {
        return java.lang.Math.abs(x - y) == boundary;
    }
}
