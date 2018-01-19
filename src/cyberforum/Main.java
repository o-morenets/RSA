package cyberforum;

import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        int N = 1024; //количество бит для генерации RSA ключей
        RSA rsa = new RSA(N);

        //создаем сообщение в BigInteger формате
        BigInteger message = new BigInteger(1, "Привет Мир! I love you!".getBytes());

        //далее, зашифровываем секретное сообщение
        BigInteger encryptMessage = rsa.encrypt(message);

        BigInteger decryptMessage = rsa.decrypt(encryptMessage);

        /* Выводим наш результат на экран */
        System.out.println(new String(decryptMessage.toByteArray()));
    }
}
