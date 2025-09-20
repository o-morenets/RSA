/***********************************************************************
 Compilation:  javac RSA.java
 Execution:    java RSA N

 Generate an N-bit public and private RSA key and use to encrypt
 and decrypt a random message.

 % java RSA 50
 public  = 65537
 private = 553699199426609
 modulus = 825641896390631
 message   = 48194775244950
 encrpyted = 321340212160104
 decrypted = 48194775244950

 Known bugs (not addressed for simplicity)
 -----------------------------------------
 - It could be the case that the message >= modulus. To avoid, use
 a do-while loop to generate key until modulus happen to be exactly N bits.

 - It's possible that gcd(phi, publicKey) != 1 in which case
 the key generation fails. This will only happen if phi is a
 multiple of 65537. To avoid, use a do-while loop to generate
 keys until the gcd is 1. * Fixed by MORA *
 */

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

public class MyRSA {
    private final static BigInteger ONE = BigInteger.ONE;
    private final static SecureRandom RANDOM = new SecureRandom();

    private BigInteger privateKey;
    private BigInteger publicKey;
    private BigInteger modulus;

    // generate an N-bit (roughly) public and private key
    MyRSA(int N) {
        BigInteger phi;
        do {
            BigInteger p = BigInteger.probablePrime(N / 2, RANDOM);
            BigInteger q = BigInteger.probablePrime(N / 2, RANDOM);
            phi = p.subtract(ONE).multiply(q.subtract(ONE));

            modulus = p.multiply(q);
            publicKey = new BigInteger("65537");     // common value in practice = 2^16 + 1
            privateKey = publicKey.modInverse(phi);
        } while (!phi.gcd(publicKey).equals(ONE));
    }

    public String encrypt(String msg) {
        BigInteger encryptedMsg = encrypt(msg.getBytes());
        return new BASE64Encoder().encode(encryptedMsg.toByteArray());
    }

    private BigInteger encrypt(byte[] msg) {
        return new BigInteger(1, msg).modPow(publicKey, modulus);
    }

    public String decrypt(String msgBASE64) {
        byte[] decodeBASE64 = new byte[0];
        try {
            decodeBASE64 = new BASE64Decoder().decodeBuffer(msgBASE64);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BigInteger decryptedMsg = decrypt(decodeBASE64);
        return new String(decryptedMsg.toByteArray());
    }

    private BigInteger decrypt(byte[] msg) {
        return new BigInteger(msg).modPow(privateKey, modulus);
    }

    public BigInteger getPublicKey() {
        return publicKey;
    }

    public BigInteger getPrivateKey() {
        return privateKey;
    }

    public BigInteger getModulus() {
        return modulus;
    }

    @Override
    public String toString() {
        return "MyRSA{" +
                "publicKey=" + publicKey +
                ", privateKey=" + privateKey +
                ", modulus=" + modulus +
                '}';
    }
}
