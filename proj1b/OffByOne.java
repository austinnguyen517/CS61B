public class OffByOne implements CharacterComparator {
    @Override
    public boolean equalChars(char x, char y) {
        return java.lang.Math.abs(x - y) == 1;
    }
}
