package www.charles.whatsapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class MainActivity extends
        AppCompatActivity implements
        TabCamera.OnFragmentInteractionListener,
        TabChats.OnFragmentInteractionListener,
        TabStatus.OnFragmentInteractionListener,
        TabCalls.OnFragmentInteractionListener {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ImageView mImageView;
    public FloatingActionButton fabCamera;




    String pathToFile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.layout_main_page_tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("WhatsApp");

       //This code deals with the camera fragment
        mImageView = (ImageView)findViewById(R.id.iv_myImage_id);
        fabCamera = (FloatingActionButton)findViewById(R.id.fab_camera);
        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispathPictureTakerAction();

            }
        });







        if (Build.VERSION.SDK_INT > 23){
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
        }



        //Finding the layout by id then setting the text on the tabs and setting the tabs gravity
        tabLayout = (TabLayout)findViewById(R.id.lt_tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("")
                .setIcon(R.drawable.ic_mcamera));

        tabLayout.addTab(tabLayout.newTab().setText("Chats"));
        tabLayout.addTab(tabLayout.newTab().setText("Status"));
        tabLayout.addTab(tabLayout.newTab().setText("Calls"));
        tabLayout.setTabGravity(tabLayout.GRAVITY_FILL);


        final ViewPager viewPager = (ViewPager)findViewById(R.id.view_pager_id);
        //creating an object for the custom pager adapter
        final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        dispathPictureTakerAction();

    }

    private void openCharlesActivity() {

        Intent chatlesIntent = new Intent(this,Charles.class);
        startActivity(chatlesIntent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK){
            if (requestCode == 1){
                Bitmap bitmap = BitmapFactory.decodeFile(pathToFile);
                mImageView.setImageBitmap(bitmap);

            }
        }
    }

    //THis method open up the camera
    private void dispathPictureTakerAction() {
       //Intent to open the camera when the button is clicked
        Intent takePicIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //Checking if there is an app available to handle our intent
        if (takePicIntent.resolveActivity(getPackageManager()) != null){

            //Defining a file where our photo will be stored
            File photoFile = null;
            photoFile = createPhotoFile();

            //Saving the photo in the photoFile varriable
            if (photoFile != null){
                String pathToFile = photoFile.getAbsolutePath();
                //The file provider allows other apps to access our files with the same permission
                Uri photoUri = FileProvider.getUriForFile(MainActivity.this,"com.charleswhatsapp.fileprovider", photoFile);
                takePicIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePicIntent, 1);
            }



        }



    }

    private File createPhotoFile() {
        //Creating a name for the file where the photo will be stored
        String fileName = new  SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        //Creating a directory to allow all apps to access our photo
         File storageDirectory = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
         //Creating the actual file
        File imageFile = null;
        try{
             imageFile = File.createTempFile(fileName,"jpg", storageDirectory);
        }catch (Exception e){
            Log.d("myLog", "Except : " + e.toString());
        }
        return imageFile;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
