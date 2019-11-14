package com.example.fodmap.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import com.example.fodmap.R;
import com.example.fodmap.adapters.IngredientListAdapter;
import com.example.fodmap.data.Ingredient;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class IngredientsActivity extends AppCompatActivity {
    ActionMode editactionmode;
    IngredientListAdapter ingredientListAdapter;
    RecyclerView recyclerView;
    Toolbar topbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);
        topbar=findViewById(R.id.topbar);
        setSupportActionBar(topbar);
        getSupportActionBar().setTitle(R.string.ingredientlist);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView=findViewById(R.id.ingredient_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ingredientListAdapter=new IngredientListAdapter(getlistdata());
        recyclerView.setAdapter(ingredientListAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    class EditActionModeCallback implements ActionMode.Callback{
        MenuItem itemgood;
        MenuItem itemok;
        MenuItem itembad;
        int index;
        EditActionModeCallback(int editindex){
            index=editindex;
        }
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            actionMode.getMenuInflater().inflate(R.menu.editsafetyaction,menu);
            itemgood=findViewById(R.id.editsafety_good);
            itembad=findViewById(R.id.editsafety_bad);
            itemok=findViewById(R.id.editsafety_ok);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            List<Ingredient> list=ingredientListAdapter.getDatalist();
            Ingredient ingredient=list.get(index);
            switch(menuItem.getItemId()){
                case R.id.editsafety_bad: {
                    ingredient.setSafety(Ingredient.Safety.bad);
                    break;
                }
                case R.id.editsafety_good:{
                    ingredient.setSafety(Ingredient.Safety.good);
                    break;
                }
                case R.id.editsafety_ok:{
                    ingredient.setSafety(Ingredient.Safety.ok);
                    break;
                }
            }
            list.set(index,ingredient);
            ingredientListAdapter.update(list);
            actionMode.finish();
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            editactionmode=null;
        }
    }

    public void startEditaction(int index){
        editactionmode=startActionMode(new EditActionModeCallback(index));
    }


//    get ingredientlist from server
    List<Ingredient> getlistdata(){
        Ingredient item1=new Ingredient("bread", Ingredient.Safety.ok);
        Ingredient item2=new Ingredient("banana", Ingredient.Safety.bad);
        List<Ingredient> list=new ArrayList<>();
        list.add(item1);
        list.add(item2);
        return list;
    }
    void setlistdata(){}
}
