public class Palindrome {

    /** Convert given string word to deque of character in the same order */
    public static Deque<Character> wordToDeque(String word) {

        Deque<Character> result = new ArrayDeque<>();

        for (int k = 0; k < word.length(); k += 1) {
            result.addLast(word.charAt(k));
        }

        return result;
    }


//    /**************************** MY OWN IMPLEMENTATION **************************/
//    /** Return true if word is a Palindrome */
//    public static boolean isPalindrome(String word) {
//        if (word.length() < 2) {
//            return true;
//        } else {
//            Deque<Character> d = wordToDeque(word);
//            return (d.removeLast() == d.removeFirst()) && isPalindrome(word.substring(1, word.length() - 1));
//        }
//    }
//
//
//    /** Return true if word is a Palindrome according to CharacterComparator test */
//    public static boolean isPalindrome(String word, CharacterComparator cc) {
//        if (word.length() < 2) {
//            return true;
//        } else {
//            Deque<Character> d = wordToDeque(word);
//            return (cc.equalChars(d.removeFirst(), d.removeLast())) && isPalindrome(word.substring(1, word.length() - 1), cc);
//        }
//    }
//    /**************************** END OF MY OWN IMPLEMENTATION **************************/
//


    /**************************** DEQUE ONLY IMPLEMENTATION **************************/
    /** Credits to ladrift/ucb-cs61b-sp17 on GitHub
     * Below are cleaner codes using deque only
     * and then calling this method within isPalindrome(String word, CharacterComparator cc),
     * convert word to d using wordToDeque method
     * */

    /** Helper method using Deque as input */
    public static boolean isPalindrome(Deque<Character> d) {
        if (d.size() < 2) {
            return true;
        } else {
            return (d.removeFirst() == d.removeLast()) && isPalindrome(d);
        }
    }

    public static boolean isPalindrome(String word) {
        return isPalindrome(wordToDeque(word));
    }

    /** Helper method using Deque as input */
    public static boolean isPalindrome(Deque<Character> d, CharacterComparator cc) {
        if (d.size() < 2) {
            return true;
        } else {
            return (cc.equalChars(d.removeFirst(), d.removeLast())) && isPalindrome(d, cc);
        }
    }

    public static boolean isPalindrome(String word, CharacterComparator cc) {
        return isPalindrome(wordToDeque(word), cc);
    }
    /**************************** END OF DEQUE ONLY IMPLEMENTATION **************************/


//    /**************************** STRING ONLY IMPLEMENTATION **************************/
//    /** Alternatively, a way without using deque at all */
//    public static boolean isPalindrome(String word) {
//        if (word.length() < 2) {
//            return true;
//        } else {
//            return (word.charAt(0) == word.charAt(word.length()-1))
//                    && isPalindrome(word.substring(1, word.length() - 1));
//        }
//    }
//
//    public static boolean isPalindrome(String word, CharacterComparator cc) {
//        if (word.length() < 2) {
//            return true;
//        } else {
//            return (cc.equalChars(word.charAt(0), word.charAt(word.length()-1)))
//                    && isPalindrome(word.substring(1, word.length() - 1), cc);
//        }
//    }
//    /**************************** END OF STRING ONLY IMPLEMENTATION **************************/
}
