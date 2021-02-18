package com.example.resultadosvotacion.interfaces;
import java.math.BigInteger;

/**
 * This interface defines the skeletal structure of the functionality
 * expected from an additively homomorphic cryptosystem. The cryptosystem may or
 * may not be able to decrypt depending on how the object of an implementing
 * class is created.
 *
 * @author Anirban Basu
 *
 */
public interface AdditivelyHomomorphicCryptosystem {
    /**
     * Given a ciphertext, this function decrypts it into
     * a plaintext, only if the cryptosystem has the associated
     * decryption (private) key.
     * @param ciphertext
     * @return
     */
    public BigInteger decrypt(BigInteger ciphertext);
    /**
     * Given a plaintext, this function encrypts it into
     * a ciphertext using the associated encryption (public) key.
     * @param plaintext
     * @return
     */
    public BigInteger encrypt(BigInteger plaintext);
    /**
     * Obtains the public key.
     * @return
     */
    public PublicKey getPublicKey();
    /**
     * Obtains the private key, if associated with this cryptosystem.
     * @return
     */
    public PrivateKey getPrivateKey();
    /**
     * Given two ciphertext in the same encrypted domain, this function
     * adds them homomorphically over the encrypted domain using
     * the public key only.
     *
     * @param ciphertext1
     * @param ciphertext2
     * @return
     */
    public BigInteger homomorphicAdd(BigInteger ciphertext1, BigInteger ciphertext2);
    /**
     * Given a ciphertext and a plaintext multiplicand, this function multiplies
     * the ciphertext with the plaintext multiplicand to produce a result ciphertext in the same
     * encrypted domain using the public key only.
     * @param ciphertext
     * @param plaintextMultiplicand
     * @return
     */
    public BigInteger homomorphicMultiply(BigInteger ciphertext, BigInteger plaintextMultiplicand);
}
