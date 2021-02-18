package com.example.resultadosvotacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.resultadosvotacion.interfaces.AdditivelyHomomorphicCryptosystem;
import com.example.resultadosvotacion.paillier.PaillierCryptosystem;
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

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class ResultadosCeiis extends AppCompatActivity {
    ProgressBar PBCeiis1;
    TextView TVCL1, TVCL2, TVCL3;
    Button BTVerResCeiis ;
    String TextoCifradoBlanco,TextoCifradoL1,TextoCifradoL2, TextoDesc;
    FirebaseFirestore mFirestore2;
    public  static AdditivelyHomomorphicCryptosystem paillier3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados_ceiis);
        PBCeiis1=findViewById(R.id.PBCeiis);
        TVCL1=findViewById(R.id.CL1);
        TVCL2=findViewById(R.id.CL2);
        TVCL3=findViewById(R.id.CL3);
        mFirestore2= FirebaseFirestore.getInstance();
        BTVerResCeiis=findViewById(R.id.btn_VerResCeiisFinal);
        BigInteger n = new BigInteger("19165724925437947720793318169031746538947523761277");
        BigInteger lambda = new BigInteger("4791431231359486930198327351997281007546378935864");
        BigInteger mu = new BigInteger("16389292834899549512112951548712844318589924393296");
        PBCeiis1.setVisibility(View.INVISIBLE);
        paillier3= new PaillierCryptosystem(n, lambda, mu,164);
        BTVerResCeiis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PBCeiis1.setVisibility(View.VISIBLE);
                CalcularVotosCeiis();
            }
        });
    }
    private void CalcularVotosCeiis(){
        mFirestore2.collection("VotosCeiis")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        BigInteger BlancoTotal=BigInteger.valueOf(0);
                        BigInteger L1Total=BigInteger.valueOf(0);
                        BigInteger L2Total=BigInteger.valueOf(0);
                        BlancoTotal = paillier3.encrypt(BlancoTotal);
                        L1Total = paillier3.encrypt(L1Total);
                        L2Total = paillier3.encrypt(L2Total);
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d("Votos", document.getId() + " => " + document.getData().get("VotoEncriptado").toString());
                                TextoCifradoBlanco=document.getData().get("VotoBlanco").toString();
                                TextoCifradoL1=document.getData().get("VotoL1").toString();
                                TextoCifradoL2=document.getData().get("VotoL2").toString();
                                BigInteger ValL1= new BigInteger(TextoCifradoL1);
                                BigInteger ValL2= new BigInteger(TextoCifradoL2);
                                BigInteger ValBlanco= new BigInteger(TextoCifradoBlanco);
                                BlancoTotal = paillier3.homomorphicAdd(ValBlanco, BlancoTotal);
                                L1Total = paillier3.homomorphicAdd(ValL1, L1Total);
                                L2Total = paillier3.homomorphicAdd(ValL2, L2Total);

                            }
                            BigInteger DesencriptadoBlanco = paillier3.decrypt(BlancoTotal);
                            BigInteger DesencriptadoL1 = paillier3.decrypt(L1Total);
                            BigInteger DesencriptadoL2 = paillier3.decrypt(L2Total);
                            TVCL1.setText(""+DesencriptadoL1);
                            TVCL2.setText(""+DesencriptadoL2);
                            TVCL3.setText(""+DesencriptadoBlanco);
                            PBCeiis1.setVisibility(View.INVISIBLE);
                        } else {
                            Log.w("Votos", "Error getting documents.", task.getException());
                        }
                    }
                });
    }


}


