package com.monokaijs.progressive1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ImageViewer extends Fragment {
  public static ImageViewer instance;
  public static int currentIndex = 0;

  public List<String> imageList = new ArrayList<String>();
  public ImageViewer() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ImageViewer.instance = this;
    imageList.add("https://i.imgur.com/FhKzmRn_d.webp?maxwidth=800&fidelity=grand");
    imageList.add("https://i.imgur.com/LRoLTlK.jpeg");
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_image_viewer, container, false);

    rootView.findViewById(R.id.btnNext).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // go to next image
        setImageIndex(1);
      }
    });

    rootView.findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // go to previous image
        setImageIndex(-1);
      }
    });
    return rootView;
  }

  public void setImageIndex(int navigateIndex) {
    ImageView imageView = MainActivity.instance.findViewById(R.id.imageView);
    if (imageView == null) {
      Log.i("ACTIVITY", "IMAGE VIEW NOT AVAILABLE");
      return;
    }
    currentIndex = currentIndex + navigateIndex;

    // image rotation
    // if over last index -> go to first index
    // if under first index -> go to last index
    if (currentIndex > imageList.size() - 1) {
      currentIndex = 0;
    } else if (currentIndex < 0) {
      currentIndex = imageList.size() - 1;
    }
    Log.i("INDEX", String.valueOf(currentIndex));

    CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(MainActivity.instance);
    circularProgressDrawable.setStrokeWidth(5f);
    circularProgressDrawable.setCenterRadius(30f);
    circularProgressDrawable.start();
    // load image, showing a circular progress as indicator
    Glide.with(this)
        .load(imageList.get(currentIndex))
        .placeholder(circularProgressDrawable)
        .into(imageView);
  }

  @Override
  public void onStart() {
    super.onStart();
    Log.i("ACTIVITY", "ON_START");
  }

  @Override
  public void onResume() {
    super.onResume();
    Log.i("ACTIVITY", "RESUME");
    setImageIndex(0);
  }
}