public class Palindrome {
    public Deque<Character> wordToDeque(String word){
        /*Keep adding last until we reach the last character of the word*/
        Deque <Character> separatedWord = new ArrayDeque();
        int length = word.length();
        for (int i = 0; i < length; i++){
            separatedWord.addLast(word.charAt(i));
        }
        return separatedWord;
    }

    public boolean isPalindrome(String word){
        Deque <Character> segmentWord = wordToDeque(word);
        int last = segmentWord.size() - 1;
        int first = 0;
        while ((last != first) && (last > first)) {
            if (segmentWord.get(first) != segmentWord.get(last)){
                return false;
            }
            first += 1;
            last -= 1;
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc){
        Deque <Character> segmentWord = wordToDeque(word);
        int last = segmentWord.size() - 1;
        int first = 0;
        while ((last != first) && (last > first)) {
            if (!(cc.equalChars(segmentWord.get(first), segmentWord.get(last)))){
                return false;
            }
            first += 1;
            last -= 1;
        }
        return true;
    }
}
