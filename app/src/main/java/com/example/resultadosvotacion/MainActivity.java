package com.example.resultadosvotacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.resultadosvotacion.interfaces.AdditivelyHomomorphicCryptosystem;
import com.example.resultadosvotacion.paillier.PaillierCryptosystem;
import com.example.resultadosvotacion.paillier.PaillierPrivateKey;
import com.example.resultadosvotacion.paillier.PaillierPublicKey;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class MainActivity extends AppCompatActivity {
Button BTVerResultados, BTVerResultadosCeiis;
    FirebaseFirestore mFirestore;
    String Blaco1, Blanco2, Blanco3, T1L1,T1L2,T2L1,T2L2,T3L1,T3L2, TextoCifrado, TextoDesc;
    TextView TvT1l1, TvT1l2,TvT2l1,TvT2l2,TvT3l1,TvT3l2,TvB1,TvB2,TvB3;
    ProgressBar PBTeuni2;
    public  static AdditivelyHomomorphicCryptosystem paillier;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BTVerResultados = findViewById(R.id.btn_VerResCeiisFinal);
        mFirestore= FirebaseFirestore.getInstance();
        TvT1l1 = findViewById(R.id.TVT1L1);
        TvT1l2 = findViewById(R.id.CL1);
        TvT2l1 = findViewById(R.id.TVT2L1);
        TvT2l2 = findViewById(R.id.CL2);
        TvT3l1 = findViewById(R.id.CL3);
        TvT3l2 = findViewById(R.id.TVT3L2);
        TvB1 = findViewById(R.id.TVT1B);
        TvB2 = findViewById(R.id.TVT2B);
        TvB3 = findViewById(R.id.TVT3B);
        PBTeuni2 = findViewById(R.id.PBCeiis);
        BTVerResultadosCeiis = findViewById(R.id.btn_VerResCeiss);
        BigInteger n = new BigInteger("19165724925437947720793318169031746538947523761277");
        BigInteger lambda = new BigInteger("4791431231359486930198327351997281007546378935864");
        BigInteger mu = new BigInteger("16389292834899549512112951548712844318589924393296");
        PBTeuni2.setVisibility(View.INVISIBLE);
        paillier= new PaillierCryptosystem(n, lambda, mu,164);
        BTVerResultados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PBTeuni2.setVisibility(View.VISIBLE);
                testCryptographicOperations();
                try {
                    Desencriptar("1sb81jhlfgbxi7f66qu3etvb6j6lh50xipn6qswujnas4wg16pzi4tbcq2gv150918utwjtsageo11qamdc5ea1mdimo7mhznex3");
                    CalcularVotos();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                } catch (NoSuchProviderException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        });
        BTVerResultadosCeiis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ResultadosCeiis.class);
                startActivity(i);
            }
        });

         }

 private void CalcularVotos(){

     mFirestore.collection("Votos")
             .get()
             .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                 @Override
                 public void onComplete(@NonNull Task<QuerySnapshot> task) {
                     int ti1l1=0,ti1l2=0,ti2l1=0,ti2l2=0,ti3l1=0,ti3l2=0,vblanco1=0,vblanco2=0,vblanco3=0;
                     if (task.isSuccessful()) {
                         for (QueryDocumentSnapshot document : task.getResult()) {
                             Log.d("Votos", document.getId() + " => " + document.getData().get("VotoEncriptado").toString());
                             TextoCifrado=document.getData().get("VotoEncriptado").toString();
                             try {

                                 TextoDesc= Desencriptar(TextoCifrado);
                                 System.out.println("TEXTO DESCIFRADO: "+ TextoDesc);
                                 if(TextoDesc.equals("T-S1-1")){
                                     ti1l1=ti1l1+1;
                                 }
                                 if(TextoDesc.equals("T-S1-2")){
                                     ti1l2=ti1l2+1;
                                 }
                                 if(TextoDesc.equals("T-S2-1")){
                                     ti2l1=ti2l1+1;
                                 }
                                 if(TextoDesc.equals("T-S2-2")){
                                     ti2l2=ti2l2+1;
                                 }
                                 if(TextoDesc.equals("T-S3-1")){
                                     ti3l1=ti3l1+1;
                                 }
                                 if(TextoDesc.equals("T-S3-2")){
                                     ti3l2=ti3l2+1;
                                 }
                                 if(TextoDesc.equals("blanco1")){
                                     vblanco1=vblanco1+1;
                                 }if(TextoDesc.equals("blanco2")){
                                     vblanco2=vblanco2+1;
                                 }if(TextoDesc.equals("blanco3")){
                                     vblanco3=vblanco3+1;
                                 }

                             } catch (IllegalBlockSizeException e) {
                                 e.printStackTrace();
                             } catch (InvalidKeyException e) {
                                 e.printStackTrace();
                             } catch (BadPaddingException e) {
                                 e.printStackTrace();
                             } catch (NoSuchAlgorithmException e) {
                                 e.printStackTrace();
                             } catch (NoSuchPaddingException e) {
                                 e.printStackTrace();
                             } catch (InvalidKeySpecException e) {
                                 e.printStackTrace();
                             } catch (NoSuchProviderException e) {
                                 e.printStackTrace();
                             } catch (UnsupportedEncodingException e) {
                                 e.printStackTrace();
                             }
                         }
                         TvT1l1.setText(""+ti1l1);
                         TvT1l2.setText(""+ti1l2);
                         TvT2l1.setText(""+ti2l1);
                         TvT2l2.setText(""+ti2l2);
                         TvT3l1.setText(""+ti3l1);
                         TvT3l2.setText(""+ti3l2);
                         TvB1.setText(""+vblanco1);
                         TvB2.setText(""+vblanco2);
                         TvB3.setText(""+vblanco3);
                         PBTeuni2.setVisibility(View.INVISIBLE);
                     } else {
                         Log.w("Votos", "Error getting documents.", task.getException());
                     }
                 }
             });
 }

    private String Desencriptar(String textoaDescifrar) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, NoSuchProviderException, UnsupportedEncodingException {
        //Definimos un texto a cifrar
        String str = "Este es el texto a cifrar";

        //System.out.println("\nTexto a descifrar:");
        //System.out.println(textoaDescifrar);

        //Instanciamos la clase
        RSA rsa = new RSA();

        //Generamos un par de claves
        //Admite claves de 512, 1024, 2048 y 4096 bits
        rsa.genKeyPair(512);
        rsa.setPublicKeyString("63uxcxggq6kqjjsgwagub2k877w562v0qnqgkzrl2yo92a9g9cv4pbxhdz3s3fghz8mdtiidbbok38p322z39cd36857ivpjujnhb2pp5z3vg70njky9nte565wv6kkqp482dlxaor9l2219fl");
        rsa.setPrivateKeyString("3lsuc5xvukco4aqywk6eify6yeozykr4tlhyd0r3snlo6qw624fret5hlgua67i812qxq375iofefqyzh92vxir4xytnmeqfvvxhe8yojysqpvnblb0b2lbtvx79xab79h2jii82z2otodr35a405w4ooen8p4cwg5hsjfcfzpvdsxsxgymwa6ftc36tsz1mnb8ehcnmokv7elkceh8kd5ssreuzx00wsrdzztrlvzy3smtvss2a8ztg86ma5mnvcxksl00vkwqw71tljf49ckil2fw4kw0ysxcahdch0vcs710fy7nlyogz952kvy5skwadrcqni5ec33jjgnqztxcaha7wwywrc7my7q1ugx3qf3f8sca1pzuwx8zh138iv762649sotn8scyyh2yytvdtic3w7188y9mk2htr70oq3tkazqcm508sc1lvwhu3ft2s3d94zjlwbxtr47fdpfp8sz3cge6ya5d62dr64bzt84zn7i9cffzdyjs9weufhed2qb17nzp803uitukis");




        //Las guardamos asi podemos usarlas despues
        //a lo largo del tiempo
        String PrivK= rsa.getPrivateKeyString();//saveToDiskPrivateKey("/tmp/rsa.pri");
        String PublicK= rsa.getPublicKeyString();//rsa.saveToDiskPublicKey("/tmp/rsa.pub");

        RSA rsa2 = new RSA();

        rsa2.setPrivateKeyString(PrivK);
        rsa2.setPublicKeyString(PublicK);


        String unsecure = rsa2.Decrypt(textoaDescifrar);

        return unsecure;
    }


    public static void testCryptographicOperations()
    {
        Random rnd = new Random();

        PaillierPrivateKey xd = (PaillierPrivateKey) paillier.getPrivateKey();
        PaillierPublicKey xd1 = (PaillierPublicKey) paillier.getPublicKey(); //n,halfN,
        System.out.println("LAMBDA: "+xd.getLambda());
        System.out.println("Mu: "+xd.getMu());
        System.out.println("BigSize: "+xd1.getBitSize());
        System.out.println("n: "+xd1.getN());
        System.out.println("Square: "+xd1.getN().multiply(xd1.getN()));
        System.out.println("Square: "+xd1.getN().divide(BigInteger.valueOf(2)));
        BigInteger p1 = BigInteger.valueOf(10);
        System.out.println("P1: "+p1);
        BigInteger p2 = BigInteger.valueOf(10);
        System.out.println("P2: "+p2);
        BigInteger p3 = BigInteger.valueOf(30);
        System.out.println("P3: "+p3);
        BigInteger c1 = paillier.encrypt(p1);
        System.out.println("C1: "+c1);
        BigInteger c2 = paillier.encrypt(p2);
        System.out.println("C2: "+c2);
        BigInteger c3 = paillier.homomorphicAdd(c1, c2);
        System.out.println("C3: "+c3);
        BigInteger c4 = paillier.homomorphicMultiply(c1, p3);
        System.out.println("C4: "+c4);
        BigInteger d1 = paillier.decrypt(c3);
        System.out.println("D1: "+d1);
        BigInteger d2 = paillier.decrypt(c4);
        System.out.println("D2: "+d2);
        BigInteger d200 = paillier.decrypt(c2);
        System.out.println("D200: "+d200);
        System.out.println("*_*-*-*-*-*-*-*-------------------");
        BigInteger n11 = new BigInteger("64261083458297731257616956672731252774591753683508022035780096009399670316119508518285733787276748");
        BigInteger n12 = new BigInteger("108964497219850472661261645927605372049356793457453582252390281388484740151314680614648084535182405");
        BigInteger c300 = paillier.homomorphicAdd(n11, n12);
        System.out.println("SUMA Encriptado DE N11 Y N12: "+c300);
        BigInteger d11 = paillier.decrypt(c300);
        System.out.println("SUMA DE N11 Y N12: "+d11);
        System.out.println("*_*-*-*-*-*-*-*-------------------");

    }
}