package com.whlinks.e_commerce.ui.fragments.user;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.whlinks.e_commerce.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }

    CircleImageView imageView;
    TextView name, email, phone;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        assert user != null;
        firebaseFirestore.collection("User").document(user.getUid().toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshots = task.getResult();
                name.setText(documentSnapshots.getString("fName")+" "+documentSnapshots.getString("lName"));
                email.setText(documentSnapshots.getString("email"));
                phone.setText(documentSnapshots.getString("phone"));
                System.out.println(documentSnapshots.getString("imageUrl"));
                Glide.with(getActivity()).load(Uri.parse(documentSnapshots.getString("imageUrl"))).into(imageView);

            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        imageView = view.findViewById(R.id.profileImage);
        name = view.findViewById(R.id.profilename);
        email = view.findViewById(R.id.profileemail);
        phone = view.findViewById(R.id.profilephone);
        imageView = view.findViewById(R.id.profileImage);

        return view;
    }
}