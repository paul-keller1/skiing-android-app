package com.example.skiing;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class MenuActivity extends Activity {
    private ListView listView;
    private CustomAdapter customAdapter;
    private ArrayList<MenuListItem> menuItems;

    // Define menu item configurations
    private static final class MenuConfig {
        //TODO: Add second constructor without future activity
        static final class Item {
            String displayText;
            String iconResourceName;
            String targetActivity;

            Item(String displayText, String iconResourceName, String targetActivity) {
                this.displayText = displayText;
                this.iconResourceName = iconResourceName;
                this.targetActivity = targetActivity;
            }
        }

        // Centralized menu item definitions
        static final Item[] ITEMS = {
                new Item("Start Game", "start_game_icon", "GameActivity"),
                new Item("Mute", "music_on_icon", "MusicActivity"),
                new Item("Character Shop", "character_shop_icon", "CharacterShopActivity"),
                new Item("Item1", "item1_icon", "Item1Activity"),
                new Item("Item2", "item2_icon", "Item2Activity"),
                new Item("Item3", "item3_icon", "Item3Activity"),
                new Item("Item4", "item4_icon", "Item4Activity"),
                new Item("Item5", "item5_icon", "Item5Activity")
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        listView = findViewById(R.id.my_list_view);
        menuItems = new ArrayList<>();
        customAdapter = new CustomAdapter(this, R.layout.menu_list_element, menuItems);
        listView.setAdapter(customAdapter);

        // Populate menu items
        populateMenuItems();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MenuListItem menuItem = (MenuListItem) listView.getItemAtPosition(position);
                String str = menuItem.getText();

                // Show Toast
                Toast.makeText(getApplicationContext(),
                                "Click on position " + position + ": " + str, Toast.LENGTH_LONG)
                        .show();

                // Handle item clicks using the configuration
                handleMenuItemClick(menuItem, position);
            }
        });
    }

    private void populateMenuItems() {
        for (MenuConfig.Item itemConfig : MenuConfig.ITEMS) {
            // Dynamically get drawable resource
            int resourceId = getResources().getIdentifier(
                    itemConfig.iconResourceName,
                    "drawable",
                    getPackageName()
            );

            // Safely get drawable
            Drawable iconDrawable = resourceId != 0
                    ? ContextCompat.getDrawable(this, resourceId)
                    : null;

            // Create and add menu item
            MenuListItem menuItem = new MenuListItem(
                    itemConfig.displayText,
                    iconDrawable
            );
            customAdapter.add(menuItem);
        }
    }

    private void handleMenuItemClick(MenuListItem menuItem, int position) {
        // FIX 3: Use getText() here as well
        String itemName = menuItem.getText();

        // --- NEW LOGIC FOR MUTE/UNMUTE ---
        if (itemName.equals("Mute")) {
            // Update the item's text
            menuItem.setText("Unmute");

            // Update the item's icon
            Drawable unmuteIcon = ContextCompat.getDrawable(this, R.drawable.music_off_icon);
            menuItem.setIcon(unmuteIcon);

            // Notify the adapter that the data at this specific position has changed
            customAdapter.notifyDataSetChanged();
            return; // Action is complete, no need to launch an activity

        } else if (itemName.equals("Unmute")) {
            // Update the item's text back to "Mute"
            menuItem.setText("Mute");

            // Update the item's icon back to the "on" state
            Drawable muteIcon = ContextCompat.getDrawable(this, R.drawable.music_on_icon);
            menuItem.setIcon(muteIcon);

            // Notify the adapter to refresh the view
            customAdapter.notifyDataSetChanged();
            return; // Action is complete
        }
        // --- END OF MUTE/UNMUTE LOGIC ---


        // Find the corresponding configuration for other items
        for (MenuConfig.Item config : MenuConfig.ITEMS) {
            if (config.displayText.equals(itemName)) {
                try {
                    // Dynamically load the target activity
                    Class<?> targetActivityClass = Class.forName(
                            getPackageName() + "." + config.targetActivity
                    );

                    Intent intent = new Intent(this, targetActivityClass);
                    startActivity(intent);
                    return; // Activity launched, exit method
                } catch (ClassNotFoundException e) {
                    Toast.makeText(this, "Activity not found", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }
    }
}
