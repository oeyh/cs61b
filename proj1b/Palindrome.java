public class Palindrome {

    /** Convert given string word to deque of character in the same order */
    public static Deque<Character> wordToDeque(String word) {

        Deque<Character> result = new ArrayDeque<>();

        for (int k = 0; k < word.length(); k += 1) {
            result.addLast(word.charAt(k));
        }

        return result;
    }

    /** Helper method: Return true if characters in deque d forms a Palindrome */
    private static boolean isPalindromeHelper(Deque<Character> d) {
        if (d.size() <= 1) {
            return true;
        }
        return (d.removeFirst() == d.removeLast()) && isPalindromeHelper(d);
    }

    /** Return true if word is a Palindrome */
    public static boolean isPalindrome(String word) {

        Deque<Character> d = wordToDeque(word);

        return isPalindromeHelper(d);

    }

    /** Helper method: Return true if characters in deque d forms a Palindrome according to CharacterComparator test */
    private static boolean isPalindromeHelper(Deque<Character> d, CharacterComparator cc) {
        if (d.size() <= 1) {
            return true;
        }
        return (cc.equalChars(d.removeFirst(), d.removeLast())) && isPalindromeHelper(d, cc);
    }

    /** Return true if word is a Palindrome according to CharacterComparator test */
    public static boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> d = wordToDeque(word);

        return isPalindromeHelper(d, cc);
    }

}
