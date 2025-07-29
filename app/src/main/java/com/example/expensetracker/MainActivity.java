package com.example.expensetracker;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText editAmount, editDescription;
    private RadioGroup radioGroup;
    private TextView textBalance;
    private RecyclerView recyclerView;
    private TransactionAdapter adapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize database
        dbHelper = new DatabaseHelper(this);

        // Initialize views
        editAmount = findViewById(R.id.editTextAmount);
        editDescription = findViewById(R.id.editTextDescription);
        radioGroup = findViewById(R.id.radioGroupType);
        textBalance = findViewById(R.id.textViewBalance);
        Button btnAdd = findViewById(R.id.buttonAdd);
        recyclerView = findViewById(R.id.recyclerViewTransactions);

        // Setup RecyclerView
        List<Transaction> transactions = dbHelper.getAllTransactions();
        adapter = new TransactionAdapter(transactions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Load initial balance
        updateBalance();

        // Add transaction button click
        btnAdd.setOnClickListener(v -> addTransaction());
    }

    private void addTransaction() {
        // Validate inputs
        String amountStr = editAmount.getText().toString();
        String description = editDescription.getText().toString().trim();

        if (TextUtils.isEmpty(amountStr)) {
            editAmount.setError("Enter amount");
            return;
        }

        if (TextUtils.isEmpty(description)) {
            editDescription.setError("Enter description");
            return;
        }

        // Get transaction type
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId == -1) {
            // No radio button selected
            return;
        }
        boolean isIncome = (selectedId == R.id.radioIncome);

        // Create transaction
        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            editAmount.setError("Invalid amount");
            return;
        }

        Transaction transaction = new Transaction(amount, description, isIncome);

        // Save to database
        long id = dbHelper.addTransaction(transaction);
        transaction.setId(id);

        // Update UI
        adapter.addTransaction(transaction);
        updateBalance();

        // Clear inputs
        editAmount.setText("");
        editDescription.setText("");
        radioGroup.clearCheck();
    }

    private void updateBalance() {
        double balance = dbHelper.getBalance();
        textBalance.setText(String.format("₹%.2f", balance));
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}