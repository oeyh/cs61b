public class MyTest {
    public static void main(String[] args) {
        OffByOne obo = new OffByOne();
        System.out.println(obo.equalChars('a', 'b'));
        System.out.println(obo.equalChars('r', 'q'));
        System.out.println(obo.equalChars('a', 'z'));

        // Test OffByN
        OffByN obo5 = new OffByN(5);
        System.out.println(obo5.equalChars('a', 'f'));
        System.out.println(obo5.equalChars('f', 'a'));
        System.out.println(obo5.equalChars('f', 'h'));
    }
}
