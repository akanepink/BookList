package com.example.booklist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewBookActivity extends AppCompatActivity {

    private Button buttonOk,buttonCancel;
    private EditText editTextBookTitle,editTextBookPrice;
    private int insertPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_book);

        buttonOk=(Button)this.findViewById(R.id.button_ok);
        buttonCancel=(Button)this.findViewById(R.id.button_cancel);
        editTextBookTitle=(EditText)this.findViewById(R.id.edit_text_book_title);
        editTextBookPrice=(EditText)this.findViewById(R.id.edit_text_book_price);

        editTextBookTitle.setText(getIntent().getStringExtra("title"));
        editTextBookPrice.setText(String.valueOf(getIntent().getDoubleExtra("price",0)));
        insertPosition=getIntent().getIntExtra("insert_position",0);

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("title",editTextBookTitle.getText().toString());
                intent.putExtra("price",Double.parseDouble(editTextBookPrice.getText().toString()));
                intent.putExtra("insert_position",insertPosition);
                setResult(RESULT_OK,intent);
                NewBookActivity.this.finish();
            }
        });

    }
}
