public class SumLongHex {
    public static void main(String[] args) {
        long ans = 0;
        for (String arg : args) {
            for (String s : arg.toLowerCase().split("\\p{javaWhitespace}+")) {
                if (!s.isEmpty()) {
                    if (s.startsWith("0x")) {
                        ans += Long.parseUnsignedLong(s.substring(2), 16);
                    } else {
                        ans += Long.valueOf(s);
                    }
                }
            }
        }
        System.out.println(ans);
    }
}
