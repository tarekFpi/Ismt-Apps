package com.ismt.world.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.ismt.world.R;
import com.ismt.world.StoreSession.AppSessionManager;
import com.squareup.picasso.Picasso;

import java.util.HashMap;


public class ImageUplodeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private static final int REQUEST_PERMISSIONS = 100;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_CAMERA_REQUEST = 2 ;
    private Bitmap bitmap;
    private String filePath;
    ImageView editProfile_image;
    Button profile_uplodeImage;
    ConstraintLayout choseImage;
    AppSessionManager sessionManager;

    String mainUrl, id, security;

    Context context;
    String encodeImageString;

    Uri picUri;

    public ImageUplodeFragment() {
        // Required empty public constructor
    }


    public static ImageUplodeFragment newInstance(String param1, String param2) {
        ImageUplodeFragment fragment = new ImageUplodeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_image_uplode, container, false);



        return  view;
    }
}