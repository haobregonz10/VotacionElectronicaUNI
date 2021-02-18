package com.example.resultadosvotacion.paillier;
import com.example.resultadosvotacion.interfaces.PrivateKey;
import java.math.BigInteger;

public class PaillierPrivateKey extends PaillierPublicKey implements PrivateKey {
    public PaillierPrivateKey() {}

    public PaillierPrivateKey(BigInteger n, BigInteger halfN,
                              BigInteger nSquared, int bitSize, BigInteger lambda, BigInteger mu) {
        super(n, halfN, nSquared, bitSize);
        this.lambda = lambda;
        this.mu = mu;
    }

    private static final long serialVersionUID = 1L;
    protected BigInteger lambda = null, mu = null;
    public BigInteger getLambda() {
        return lambda;
    }
    public void setLambda(BigInteger lambda) {
        this.lambda = lambda;
    }
    public BigInteger getMu() {
        return mu;
    }
    public void setMu(BigInteger mu) {
        this.mu = mu;
    }
}
