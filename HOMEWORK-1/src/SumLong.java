public class SumLong {
    public static void main(String[] args) {
        long ans = 0;
        for (String arg : args) {
            for (String s : arg.toLowerCase().split("\\p{javaWhitespace}+")) {
                if(!s.isEmpty())
                    ans += Long.parseLong(s);
            }
        }
        System.out.println(ans);
    }
}
