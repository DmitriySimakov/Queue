package com.dmitry_simakov.queue;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import static com.dmitry_simakov.queue.fragments.ModelDescriptionFragment.IMAGE;

public class ImageViewDialog extends AppCompatDialogFragment {
    
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_image_view, null);
    
        ImageView imageView = view.findViewById(R.id.image);
    
        Bundle bundle = getArguments();
        if (bundle != null) {
            int image = bundle.getInt(IMAGE);
            imageView.setImageResource(image);
        }
    
        builder.setView(view);
        return builder.create();
    }
    
    public static void createDialog(int image, FragmentActivity activity) {
        ImageViewDialog dialog = new ImageViewDialog();
        Bundle args = new Bundle();
        args.putInt(IMAGE, image);
        dialog.setArguments(args);
        dialog.show(activity.getSupportFragmentManager(), "IMAGE_VIEW_DIALOG");
    }
}
