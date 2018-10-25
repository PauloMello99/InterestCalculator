package br.edu.fatec.aula1.interestcalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.dia_vencimento)
    EditText vencimento;

    @BindView(R.id.dia_pagamento)
    EditText pagamento;

    @BindView(R.id.valor)
    EditText valor;

    @BindView(R.id.button_juros_dia)
    RadioButton juros_day;

    @BindView(R.id.button_juros_mes)
    RadioButton juros_month;

    @BindView(R.id.button_juros_dinheiro)
    RadioButton juros_money;

    @BindView(R.id.button_juros_porcentagem)
    RadioButton juros_percent;

    @BindView(R.id.button_multa_dinheiro)
    RadioButton multa_money;

    @BindView(R.id.button_multa_porcentagem)
    RadioButton multa_percent;

    @BindView(R.id.clear)
    Button clear;

    @BindView(R.id.calculate)
    Button calculate;

    @BindView(R.id.multa)
    EditText multa;

    @BindView(R.id.juros)
    EditText juros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        juros_money.setChecked(true);
        juros_day.setChecked(true);
        multa_money.setChecked(true);
        DatePickerDialogHelper.setDatePickerDialog(vencimento, this, new SimpleDateFormat(this.getString(R.string.date_formatter), new Locale("pt", "BR")));
        DatePickerDialogHelper.setDatePickerDialog(pagamento, this, new SimpleDateFormat(this.getString(R.string.date_formatter), new Locale("pt", "BR")));
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkFields())
                {
                    changeToResultActivity();
                }
            }
        });
    }


    public void clearAll(View view)
    {
        juros_money.setChecked(true);
        juros_day.setChecked(true);
        multa_money.setChecked(true);
        valor.setText("");
        vencimento.setText("");
        pagamento.setText("");
        juros.setText("");
        multa.setText("");
    }

    private void changeToResultActivity()
    {
            Intent intent = new Intent(this, ResultActivity.class);
            String valueStr = valor.getText().toString();
            String jurosStr =  juros.getText().toString();
            String multaStr = multa.getText().toString();
            String juros_limite;
            String juros_tipo;
            String multa_tipo;
            if(juros_day.isChecked())
            {
                juros_limite = "DAY";
            }
            else
            {
                juros_limite = "MONTH";
            }
            if(juros_money.isChecked())
            {
                juros_tipo = "MONEY";
            }
            else
            {
                juros_tipo = "PERCENT";
            }
            if(multa_money.isChecked())
            {
                multa_tipo = "MONEY";
            }
            else
            {
                multa_tipo = "PERCENT";
            }
            double valor = Double.parseDouble(valueStr);
            String venc = vencimento.getText().toString();
            String pag = pagamento.getText().toString();
            double juros = Double.parseDouble(jurosStr);
            double multa = Double.parseDouble(multaStr);
            intent.putExtra("VALOR",valor);
            intent.putExtra("VENC",venc);
            intent.putExtra("PAG",pag);
            intent.putExtra("JUROS",juros);
            intent.putExtra("JUROS_TIPO",juros_tipo);
            intent.putExtra("JUROS_LIMITE", juros_limite);
            intent.putExtra("MULTA",multa);
            intent.putExtra("MULTA_TIPO",multa_tipo);
            startActivity(intent);

    }

    private boolean checkFields() {
        boolean check = true;
        if(valor.getText().toString().isEmpty())
        {
            valor.setError("Insira o valor");
            check = false;
        }
        if(vencimento.getText().toString().isEmpty())
        {
            vencimento.setError("Escolha a data do vencimento");
            check = false;
        }
        if(pagamento.getText().toString().isEmpty())
        {
            pagamento.setError("Escolha a data do pagamento");
            check = false;
        }
        if(juros.getText().toString().isEmpty())
        {
            juros.setError("Insira o valor dos juros");
            check = false;
        }
        if(multa.getText().toString().isEmpty())
        {
            multa.setError("Insira o valor da multa");
            check = false;
        }
        return check;
    }

}
