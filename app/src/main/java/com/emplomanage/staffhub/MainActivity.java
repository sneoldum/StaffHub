package com.emplomanage.staffhub;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.emplomanage.staffhub.adapter.ItemAdapter;
import com.emplomanage.staffhub.model.Item;
import com.emplomanage.staffhub.viewmodel.ItemViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ItemAdapter.OnItemClickListener {

    private ItemViewModel itemViewModel;
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private FloatingActionButton fabAddItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // Use GridLayoutManager for horizontal scroll

        itemAdapter = new ItemAdapter(this); // Pass 'this' for the listener
        recyclerView.setAdapter(itemAdapter);

        itemViewModel.getAllItems().observe(this, items -> {
            itemAdapter.setItems(items);
        });

        fabAddItem = findViewById(R.id.fab_add_item);
        fabAddItem.setOnClickListener(v -> showAddItemDialog());
    }

    @Override
    public void onItemClick(Item item) {
        showUpdateDeleteDialog(item);
    }

    private void showAddItemDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_add_item, null);

        final EditText itemNameEditText = view.findViewById(R.id.itemNameEditText);
        final EditText itemQuantityEditText = view.findViewById(R.id.itemQuantityEditText);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(view);
        dialogBuilder.setPositiveButton("Add", (dialog, which) -> {
            String itemName = itemNameEditText.getText().toString().trim();
            int itemQuantity = Integer.parseInt(itemQuantityEditText.getText().toString().trim());
            itemViewModel.getItemByName(itemName).observe(this, existingItem -> {
                if (existingItem != null) {
                    existingItem.setQuantity(existingItem.getQuantity() + itemQuantity);
                    itemViewModel.update(existingItem);
                } else {
                    Item newItem = new Item(itemName, itemQuantity);
                    itemViewModel.insert(newItem);
                }
            });
        });
        dialogBuilder.setNegativeButton("Cancel", null);

        dialogBuilder.create().show();
    }


    private void showUpdateDeleteDialog(Item item) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_add_item, null);

        final EditText itemNameEditText = view.findViewById(R.id.itemNameEditText);
        final EditText itemQuantityEditText = view.findViewById(R.id.itemQuantityEditText);

        itemNameEditText.setText(item.getName());
        itemQuantityEditText.setText(String.valueOf(item.getQuantity()));

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(view);
        dialogBuilder.setPositiveButton("Update", (dialog, which) -> {
            String newName = itemNameEditText.getText().toString().trim();
            int newQuantity = Integer.parseInt(itemQuantityEditText.getText().toString().trim());

            item.setName(newName);
            item.setQuantity(newQuantity);
            itemViewModel.update(item);
        });
        dialogBuilder.setNegativeButton("Delete", (dialog, which) -> {
            itemViewModel.delete(item);
        });
        dialogBuilder.setNeutralButton("Cancel", null);

        dialogBuilder.create().show();
    }
}
