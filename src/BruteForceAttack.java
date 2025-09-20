import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BruteForceAttack {

    public static void main(String[] args) {
        int N = 1024; //количество бит для генерации RSA ключей
        MyRSA myRsa = new MyRSA(N);
        System.out.println(myRsa);
        System.out.println("=======================================================================================");

        // Атака перебором
        final BigInteger zero = BigInteger.ZERO;
        final BigInteger one = BigInteger.ONE;

        BigInteger test_p = zero;
        BigInteger test_q = zero;
        BigInteger test_phi;
        BigInteger test_privateKey;
        Map<BigInteger, Long> freq = new HashMap<>();

        for (int k = 63; k <= 1024; k++) { // 62 is already hacked
            MyRSA testRsa = new MyRSA(k);
            BigInteger test_publicKey = testRsa.getPublicKey();
            BigInteger test_modulus = testRsa.getModulus();

            System.out.println("N = " + k);
            System.out.println(testRsa);

            BigInteger i;
/*
            // пробуем делить на простые числа, начиная с 2
            i = one.nextProbablePrime();
            while (i.compareTo(test_modulus) < 0)
                if (test_modulus.remainder(i).compareTo(zero) == 0) {
                    test_p = i;
                    test_q = test_modulus.divide(test_p);
                    break;
                }
                i = i.nextProbablePrime();
            }
            test_phi = (test_p.subtract(one)).multiply(test_q.subtract(one));
            test_privateKey = test_publicKey.modInverse(test_phi);

            System.out.println();
            System.out.println("real_private = " + testRsa.getPrivateKey());
            System.out.println("test_private = " + test_privateKey);
            System.out.println("=====================================================================================");
*/

            // пробуем делить на СЛУЧАЙНЫЕ простые числа в диапазоне 0..test_modulus
            long j = 1;
            freq.clear();
            do {
                i = BigInteger.probablePrime(k / 2, new Random());

//                if (countDuplicates(freq, i)) continue;

                if (test_modulus.remainder(i).compareTo(zero) == 0) {
                    test_p = i;
                    test_q = test_modulus.divide(test_p);
                    break;
                }

                j++;
                if (j % 1000 == 0)
                    System.out.print(".");
                if (j % 100_000 == 0)
                    System.out.println();
            } while (i.compareTo(test_modulus) < 0);

            test_phi = (test_p.subtract(one)).multiply(test_q.subtract(one));
            test_privateKey = test_publicKey.modInverse(test_phi);

            System.out.println();
            System.out.println("real_private = " + testRsa.getPrivateKey());
            System.out.println("test_private = " + test_privateKey);
            System.out.println("К-во итераций подбора: " + j);
            System.out.println("=====================================================================================");
        }
    }

    private static boolean countDuplicates(Map<BigInteger, Long> freq, BigInteger i) {
        Long count = freq.get(i);
        if (count == null)
            freq.put(i, 1L);
        else {
//                	System.out.println("\n" + i + ": " + ++count);
            freq.put(i, count);
            return true;
        }
        return false;
    }

}
