package cyberforum;

import java.math.BigInteger;

/* Name of the class has to be "Main" only if the class is public. */
class Ideone {
    public static void main(String[] args) {
        String s = "Привет Мир! I love you!";
        byte[] b = s.getBytes();
        BigInteger n = new BigInteger(b);
        System.out.println(s);
        System.out.println(new String(b));
        System.out.println(new String(n.toByteArray()));
    }
}