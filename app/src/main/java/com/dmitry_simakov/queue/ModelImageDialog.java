package com.dmitry_simakov.queue;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ModelImageDialog extends AppCompatDialogFragment implements View.OnTouchListener {
    
    public static final String IMAGE = "IMAGE";
    public static final String HAS_QUEUE = "HAS_QUEUE";
    private int last_id = -1;
    
    TextView textView;
    
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_model_image, null);
        
        ImageView imageView = view.findViewById(R.id.image);
        textView = view.findViewById(R.id.description);
        Button btn1 = view.findViewById(R.id.btn1);
        Button btn2 = view.findViewById(R.id.btn2);
        Button btn3 = view.findViewById(R.id.btn3);
        Button btn4 = view.findViewById(R.id.btn4);
        btn1.setOnTouchListener(this);
        btn3.setOnTouchListener(this);
        btn4.setOnTouchListener(this);
        
        Bundle bundle = getArguments();
        if (bundle != null) {
            int image = bundle.getInt(IMAGE);
            imageView.setImageResource(image);
            textView.setText("Схема модели");
            
            if (bundle.getBoolean(HAS_QUEUE)) {
                btn2.setOnTouchListener(this);
            }
        };
        
        builder.setView(view);
        return builder.create();
    }
    
    public static void createDialog(int image, boolean hasQueue, FragmentActivity activity) {
        
        ModelImageDialog dialog = new ModelImageDialog();
        Bundle args = new Bundle();
        args.putInt(IMAGE, image);
        args.putBoolean(HAS_QUEUE, hasQueue);
        dialog.setArguments(args);
        dialog.show(activity.getSupportFragmentManager(), "MODEL_IMAGE_DIALOG");
    }
    
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        
        switch (motionEvent.getAction()) {
            
            case MotionEvent.ACTION_DOWN:
                
                switch (view.getId()) {
                    case R.id.btn1:
                        textView.setText("Источники");
                        break;
                    case R.id.btn2:
                        textView.setText("Очередь");
                        break;
                    case R.id.btn3:
                        textView.setText("Каналы");
                        break;
                    case R.id.btn4:
                        textView.setText("Выходной поток");
                        break;
                }
                break;
                
            case MotionEvent.ACTION_UP:
                textView.setText("Схема модели");
                break;
        }
        return false;
    }
}
