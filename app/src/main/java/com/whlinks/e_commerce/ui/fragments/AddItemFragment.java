package com.whlinks.e_commerce.ui.fragments;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.whlinks.e_commerce.R;
import com.whlinks.e_commerce.service.CommonDBCall;
import com.whlinks.e_commerce.service.FirebaseStorageManager;


public class AddItemFragment extends Fragment {
    ImageView itemImage;
    EditText itemName, itemDescription, itemPrice;
    Button add;
    CommonDBCall commonDBCall;
    Uri file;
    CheckBox addToLatest;
    String imageUrl = "";
    private static final int IMAGE_REQUEST = 1;
    private Uri imageUri;
    private StorageTask<UploadTask.TaskSnapshot> uploadTask;
    FirebaseStorage storageReference ;


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
        addToLatest = view.findViewById(R.id.addtonew);
        commonDBCall = new CommonDBCall();
        storageReference = FirebaseStorage.getInstance();


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
//                selectImage();
                openImage();
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
                } else if (addToLatest.isChecked()) {
                    commonDBCall.addItem(name, description, price, getActivity(), itemImage,imageUrl);
                    commonDBCall.addLatestItem(name, description, price, getActivity(), itemImage,imageUrl);
                } else {
                    commonDBCall.addItem(name, description, price, getActivity(), itemImage,imageUrl);
                }
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage() {
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setIndeterminateDrawable(getResources().getDrawable(R.drawable.background));

        pd.setMessage("Uploading...");
        pd.show();

        if (imageUri != null) {
          StorageReference  fileReference = storageReference.getReference().child("Items").child(System.currentTimeMillis()
                    + "." + getFileExtension(imageUri));

            uploadTask = fileReference.putFile(Uri.parse(String.valueOf(imageUri)));
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        String mUri = downloadUri.toString();
                        imageUrl = mUri;
                        Glide.with(getActivity()).load(mUri).into(itemImage);
//                        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
//                        HashMap<String, Object> map = new HashMap<>();
//                        map.put("imageURL", "" + mUri);
//                        reference.updateChildren(map);
                        pd.dismiss();
                    } else {
                        Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        } else {
            Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();

            if (uploadTask != null && uploadTask.isInProgress()) {
                Toast.makeText(getContext(), "Upload in progress", Toast.LENGTH_SHORT).show();
            } else {
                uploadImage();
            }
        }
    }

}