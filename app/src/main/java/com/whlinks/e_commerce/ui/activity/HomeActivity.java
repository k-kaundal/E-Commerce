package com.whlinks.e_commerce.ui.activity;

import static com.whlinks.e_commerce.R.id.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.whlinks.e_commerce.R;
import com.whlinks.e_commerce.ui.auth.LoginActivity;
import com.whlinks.e_commerce.ui.fragments.AddItemFragment;
import com.whlinks.e_commerce.ui.fragments.AllItemFragment;
import com.whlinks.e_commerce.ui.fragments.CartFragment;
import com.whlinks.e_commerce.ui.fragments.FavoriteItemsFragment;
import com.whlinks.e_commerce.ui.fragments.TopItemsFragment;
import com.whlinks.e_commerce.ui.fragments.user.HomeFragment;
import com.whlinks.e_commerce.ui.fragments.LatestItemFragment;
import com.whlinks.e_commerce.ui.fragments.ProfileFragment;
import com.whlinks.e_commerce.ui.fragments.UpdateItemFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        frameLayout = findViewById(frame);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(drawer);
        setSupportActionBar(toolbar);
        navigationView = findViewById(navigation);
        actionBarDrawerToggle = new ActionBarDrawerToggle(HomeActivity.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        actionBarDrawerToggle.syncState();

        if (savedInstanceState == null) {
            setTitle("Home");
            getSupportFragmentManager().
                    beginTransaction().replace(frame, new HomeFragment()).commit();
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentManager fragmentManager = getSupportFragmentManager();
                switch (item.getItemId()) {
                    case home:
                        setTitle("Home");
                        fragmentManager.
                                beginTransaction().replace(frame, new HomeFragment()).commit();
                        break;
//                    case topitems:
//                        setTitle("Top Items");
//                        fragmentManager.
//                                beginTransaction().replace(frame, new TopItemsFragment()).commit();
//                        break;
                    case latestitems:
                        setTitle("Latest Items");
                        fragmentManager.
                                beginTransaction().replace(frame, new LatestItemFragment()).commit();
                        break;
                    case allitems:
                        setTitle("All Items");
                        fragmentManager.
                                beginTransaction().replace(frame, new AllItemFragment()).commit();
                        break;
                    case favorite:
                        setTitle("Favorite Items");
                        fragmentManager.
                                beginTransaction().replace(frame, new FavoriteItemsFragment()).commit();
                        break;
                    case cart:
                        setTitle("My Cart");
                        fragmentManager.
                                beginTransaction().replace(frame, new CartFragment()).commit();
                        break;
                    case additem:
                        setTitle("Add Item");
                        fragmentManager.
                                beginTransaction().replace(frame, new AddItemFragment()).commit();
                        break;
                    case updateitem:
                        setTitle("Update");
                        fragmentManager.
                                beginTransaction().replace(frame, new UpdateItemFragment()).commit();
                        break;

                    case profile:
                        setTitle("Profile");
                        fragmentManager.
                                beginTransaction().replace(frame, new ProfileFragment()).commit();
                        break;
                    case logout:
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    default:
                        fragmentManager.
                                beginTransaction().replace(frame, new HomeFragment()).commit();

                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("Done>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.");
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);
                    bitmap=getResizedBitmap(bitmap, 400);
//                    IDProf.setImageBitmap(bitmap);
                    BitMapToString(bitmap);
                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                thumbnail=getResizedBitmap(thumbnail, 400);
                Log.w("path of image from gallery......******************.........", picturePath+"");

//                IDProf.setImageBitmap(thumbnail);
                BitMapToString(thumbnail);
            }
        }
    }
    public String BitMapToString(Bitmap userImage1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage1.compress(Bitmap.CompressFormat.PNG, 60, baos);
        byte[] b = baos.toByteArray();
//        Document_img1 = Base64.encodeToString(b, Base64.DEFAULT);
//        return Document_img1;
        return "";
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

}
