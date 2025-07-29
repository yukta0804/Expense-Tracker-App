package com.example.expensetracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private final List<Transaction> transactions;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM", Locale.getDefault());

    public TransactionAdapter(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);

        holder.date.setText(dateFormat.format(transaction.getDate()));
        holder.description.setText(transaction.getDescription());

        if (transaction.isIncome()) {
            holder.amount.setText(String.format("+₹%.2f", transaction.getAmount()));
            holder.amount.setTextColor(holder.itemView.getContext().getColor(android.R.color.holo_green_dark));
        } else {
            holder.amount.setText(String.format("-₹%.2f", transaction.getAmount()));
            holder.amount.setTextColor(holder.itemView.getContext().getColor(android.R.color.holo_red_dark));
        }
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    // Add a new transaction at the top of the list
    public void addTransaction(Transaction transaction) {
        transactions.add(0, transaction);
        notifyItemInserted(0);
    }

    // ✅ Clear all transactions and refresh the list
    public void clearTransactions() {
        transactions.clear();
        notifyDataSetChanged();
    }

    // ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView date, description, amount;

        public ViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.textDate);
            description = itemView.findViewById(R.id.textDescription);
            amount = itemView.findViewById(R.id.textAmount);
        }
    }
}
