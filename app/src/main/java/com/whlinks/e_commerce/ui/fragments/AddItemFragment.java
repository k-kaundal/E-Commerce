package com.whlinks.e_commerce.ui.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.UploadTask;
import com.whlinks.e_commerce.R;
import com.whlinks.e_commerce.service.CommonDBCall;
import com.whlinks.e_commerce.ui.auth.ForgotPasswordActivity;
import com.whlinks.e_commerce.ui.auth.LoginActivity;

import java.io.File;


public class AddItemFragment extends Fragment {
    ImageView itemImage;
    EditText itemName, itemDescription, itemPrice;
    Button add;
    CommonDBCall commonDBCall;
    Uri file;

    public AddItemFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_item, container, false);
        itemImage = view.findViewById(R.id.itemImage);
        itemName = view.findViewById(R.id.name);
        itemDescription = view.findViewById(R.id.description);
        itemPrice = view.findViewById(R.id.price);
        add = view.findViewById(R.id.ad);
        commonDBCall = new CommonDBCall();


        itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                Intent imageDownload = new Intent();
//                imageDownload.setAction(Intent.ACTION_GET_CONTENT);
//                imageDownload.setType("image/*");
//                imageDownload.putExtra("crop", "true");
//                imageDownload.putExtra("aspectX", 1);
//                imageDownload.putExtra("aspectY", 1);
//                imageDownload.putExtra("outputX", 200);
//                imageDownload.putExtra("outputY", 200);
//                imageDownload.putExtra("return-data", true);
//               getActivity().startActivityForResult(imageDownload, GALLERY_REQUEST_CODE);
                selectImage();

//                file = Uri.fromFile(new File(FILE_PATH));


            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = itemName.getText().toString().trim();
                String description = itemDescription.getText().toString().trim();
                String price = itemPrice.getText().toString().trim();
                if (name.isEmpty()) {
                    itemName.setError("Enter item name");
                } else if (description.isEmpty()) {
                    itemDescription.setError("Enter item description");
                } else if (price.isEmpty()) {
                    itemPrice.setError("Enter item price");
                } else {

                    commonDBCall.addItem(name, description, price, getActivity(), itemImage);
                }
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent
//            data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK &&
//                data != null) {
//            Bundle extras = data.getExtras();
//            image = extras.getParcelable("data");
//            photo.setImageBitmap(image);
//
//        }
//    }
//
//
//    void uploadLocalFile(){
//
//
//        // Add File Metadata
//        StorageMetadata storageMetadata = new StorageMetadata.Builder()
//                .setContentType("image/jpg").build();
//
//        UploadTask uploadTask = mUploadStorageReference.putFile(file, storageMetadata);
//        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Log.d(TAG, "uploadLocalFile : " + taskSnapshot.getTotalByteCount());
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                e.printStackTrace();
//            }
//        });
//    }

    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    getActivity().startActivityForResult(intent, 1);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    getActivity().startActivityForResult(intent, 2);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            if (requestCode == 1) {
//                File f = new File(Environment.getExternalStorageDirectory().toString());
//                for (File temp : f.listFiles()) {
//                    if (temp.getName().equals("temp.jpg")) {
//                        f = temp;
//                        break;
//                    }
//                }
//                try {
//                    Bitmap bitmap;
//                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
//                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);
//                    bitmap=getResizedBitmap(bitmap, 400);
//                    IDProf.setImageBitmap(bitmap);
//                    BitMapToString(bitmap);
//                    String path = android.os.Environment
//                            .getExternalStorageDirectory()
//                            + File.separator
//                            + "Phoenix" + File.separator + "default";
//                    f.delete();
//                    OutputStream outFile = null;
//                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
//                    try {
//                        outFile = new FileOutputStream(file);
//                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
//                        outFile.flush();
//                        outFile.close();
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            } else if (requestCode == 2) {
//                Uri selectedImage = data.getData();
//                String[] filePath = { MediaStore.Images.Media.DATA };
//                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
//                c.moveToFirst();
//                int columnIndex = c.getColumnIndex(filePath[0]);
//                String picturePath = c.getString(columnIndex);
//                c.close();
//                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
//                thumbnail=getResizedBitmap(thumbnail, 400);
//                Log.w("path of image from gallery......******************.........", picturePath+"");
//                IDProf.setImageBitmap(thumbnail);
//                BitMapToString(thumbnail);
//            }
//        }
//    }
//    public String BitMapToString(Bitmap userImage1) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        userImage1.compress(Bitmap.CompressFormat.PNG, 60, baos);
//        byte[] b = baos.toByteArray();
//        Document_img1 = Base64.encodeToString(b, Base64.DEFAULT);
//        return Document_img1;
//    }
//
//    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
//        int width = image.getWidth();
//        int height = image.getHeight();
//
//        float bitmapRatio = (float)width / (float) height;
//        if (bitmapRatio > 1) {
//            width = maxSize;
//            height = (int) (width / bitmapRatio);
//        } else {
//            height = maxSize;
//            width = (int) (height * bitmapRatio);
//        }
//        return Bitmap.createScaledBitmap(image, width, height, true);
//    }


}