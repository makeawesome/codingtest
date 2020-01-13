public class Main {

    public static void main(String[] args) {
        TestCase tc1 = new TestCase(new String[]{"a 100", "a 200", "a 300", "a 400", "a 500"}, 3);
        TestCase tc2 = new TestCase(new String[]{"a 100", "b 200", "c 300", "d 400", "e 500"}, 5);
        TestCase tc3 = new TestCase(new String[]{"a 500", "b 400", "c 300", "d 200", "e 100"}, 3);
        TestCase tc4 = new TestCase(new String[]{"a 500", "b 400", "c 300", "d 1000", "e 1000"}, 3);

        TestCase[] testCases = {tc1, tc2, tc3, tc4};

        for(TestCase testCase : testCases) {
            System.out.println(new Solution().method(testCase.user_scores, testCase.K));
        }
    }
}

class TestCase {
    public String[] user_scores;
    public int K;

    public TestCase(String[] user_scores, int K) {
        this.user_scores = user_scores;
        this.K = K;
    }
}
