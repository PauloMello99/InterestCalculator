package br.edu.fatec.aula1.interestcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultActivity extends AppCompatActivity {

    @BindView(R.id.showValor)
    TextView valor;

    @BindView(R.id.showVencimento)
    TextView vencimento;

    @BindView(R.id.showPagamento)
    TextView pagamento;

    @BindView(R.id.showTotalJuros)
    TextView showJuros;

    @BindView(R.id.showTotalMulta)
    TextView showMulta;

    @BindView(R.id.diferenca)
    TextView showDiferenca;

    @BindView(R.id.total)
    TextView showTotal;

    @BindView(R.id.back_button)
    TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResultActivity.super.onBackPressed();
            }
        });

        try
        {
            Intent intent = getIntent();
            double value = intent.getDoubleExtra("VALOR",0);
            String vencStr = intent.getStringExtra("VENC");
            String pagStr = intent.getStringExtra("PAG");
            double juro = intent.getDoubleExtra("JUROS",0);
            String tipo_juros = intent.getStringExtra("JUROS_TIPO");
            String limite_juros = intent.getStringExtra("JUROS_LIMITE");
            double multa = intent.getDoubleExtra("MULTA",0);
            String tipo_multa = intent.getStringExtra("MULTA_TIPO");

            String ft = String.format("%.2f",value);
            valor.setText("R$ "+ft);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date venc = sdf.parse(vencStr);
            Date pag = sdf.parse(pagStr);

            long difDouble = pag.getTime() - venc.getTime();
            difDouble = TimeUnit.DAYS.convert(difDouble, TimeUnit.MILLISECONDS);
            double total = value;
            double diferenca = 0;
            vencimento.setText(vencStr);
            pagamento.setText(pagStr);
            if(difDouble < 0)
            {
                difDouble = 0;
            }
            else
            {
                ft = String.format("%.2f",juro);
                if(tipo_juros.equals("MONEY"))
                {
                    showJuros.setText("R$ "+ft);
                    if(limite_juros.equals("DAY"))
                    {
                        total+=juro*difDouble;
                    }
                    else
                    {
                        if(difDouble>30)
                        {
                            int tmp = (int) difDouble/30;
                            total += juro*tmp;
                        }
                    }
                }
                else
                {
                    showJuros.setText(ft+"%");
                    double tmp = (value/100)*juro;
                    if(limite_juros.equals("DAY"))
                    {
                        total += difDouble*tmp;
                    }
                    else
                    {
                        if(difDouble>30)
                        {
                            int tm = (int) difDouble/30;
                            total += tmp*tm;
                        }
                    }
                }
                ft = String.format("%.2f",multa);
                if(tipo_multa.equals("MONEY"))
                {
                    showMulta.setText("R$ "+ft);
                    total += multa;
                }
                else
                {
                    showMulta.setText(ft+"%");
                    total += (total/100)*multa;
                }
                diferenca = total - value;
            }
            ft = String.format("%.2f",diferenca);
            showDiferenca.setText("R$ "+ft);
            ft = String.format("%.2f",total);
            showTotal.setText("R$ "+ft);
        }
        catch(Exception e)
        {
            AlertDialog.Builder alerta = new AlertDialog.Builder(this);
            alerta.setMessage(String.valueOf(e));
            alerta.show();
        }
    }



}
