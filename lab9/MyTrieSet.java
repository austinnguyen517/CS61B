import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyTrieSet implements TrieSet61B {
    Node root = new Node('F', false);
    private class Node {
        HashMap<Character, Node> map;
        char character;
        boolean isKey;

        private Node(Character letter, boolean end) {
            map = new HashMap<>();
            character = letter;
            isKey = end;
        }

    }
    public void clear() {
        root.map = new HashMap<>();
    }

    public boolean contains(String key) {
        return contain(key, 0, root);
    }

    private boolean contain(String key, int place, Node curr) {
        if (place == key.length()) {
            return curr.isKey;
        }
        if (curr.map.containsKey(key.charAt(place))) {
            return contain(key, place + 1, curr.map.get(key.charAt(place)));
        }
        return false;
    }


    @Override
    public void add(String key) {
        if (key == null || key.length() < 1) {
            return;
        }
        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i++) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                curr.map.put(c, new Node(c, false));
            }
            curr = curr.map.get(c);
        }
        curr.isKey = true;
    }

    public List<String> keysWithPrefix(String prefix) {
        List<String> suffix = suffixes(prefix, 0, root);
        for (String thing: suffix) {
            thing = prefix + thing;
        }
        return suffix;
    }

    private List<String> suffixes (String prefix, int place, Node curr) {
        if (place < prefix.length()) {
            return suffixes(prefix, place + 1, curr.map.get(prefix.charAt(place)));
        }
        return allWords(curr);
    }

    private List<String> allWords(Node curr) {
        List<String> result = new ArrayList<>();
        for (char character: curr.map.keySet()) {
            List<String> temp = allWords(curr.map.get(character));
            result.addAll(temp);
        }
        for (String suffix: result) {
            suffix = String.valueOf(curr.character) + suffix;
        }
        if (curr.isKey) {
            result.add(String.valueOf(curr.character));
        }
        return result;
    }

    public String longestPrefixOf (String key) {
        throw new UnsupportedOperationException ("Not supported");
    }
}