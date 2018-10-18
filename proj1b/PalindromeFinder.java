/** This class outputs all palindromes in the words file in the current directory. */
public class PalindromeFinder {
    public static void main(String[] args) {
        int minLength = 4;
        In in = new In("words.txt");
        int cnt = 0;

        CharacterComparator cc = new OffByN(0);

        while (!in.isEmpty()) {
            String word = in.readString();

            if (word.length() >= minLength && Palindrome.isPalindrome(word)) {
                System.out.println(word);
                cnt += 1;
            }

        }
        System.out.println("Total number of Palindrome is " + cnt);
    }
} 
