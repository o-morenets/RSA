import java.math.BigInteger;

/**
 * Created by MORA on 09.11.2014.
 */
public class Main {
    public static void main(String[] args) {
        String messageStr = "є * Hello, Олексію! Бажаємо здоров'я! Good luck! * ї";
        String encryptedStr;
        String decryptedStr;

        int N = 1024; //количество бит для генерации RSA ключей
        MyRSA myRsa = new MyRSA(N);

        System.out.println(messageStr);
        encryptedStr = myRsa.encrypt(messageStr);
        System.out.println(encryptedStr);
        decryptedStr = myRsa.decrypt(encryptedStr);
        System.out.println(decryptedStr);
    }
}
