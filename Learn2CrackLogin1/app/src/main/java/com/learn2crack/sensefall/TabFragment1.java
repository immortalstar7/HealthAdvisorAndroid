package com.learn2crack.sensefall;/*package com.example.sensefall;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TabFragment1 extends Fragment {

    private int image;
    private int color;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        image = bundle.getInt("image");
        color = bundle.getInt("color");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, null);
        ImageView tabImage = (ImageView) view.findViewById(R.id.imageView);
        tabImage.setImageResource(image);
        view.setBackgroundResource(color);
    	 View rootView = inflater.inflate(R.layout.fragment1, container, false);
        return rootView;
    }
}*/