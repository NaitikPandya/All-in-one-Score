package com.example.allinonescore.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.allinonescore.R;

public class imageselectDialog extends DialogFragment {

    View view;
    LinearLayout upload,gallery;
    private ImageDialogListner listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.imageselectdialog, null);
        builder.setView(view)
                .setTitle("Add Banner");
        upload = view.findViewById(R.id.device);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.applyTexts("Device");
            }
        });
        gallery = view.findViewById(R.id.gallery);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.applyTexts("Gallery");
            }
        });
        return builder.create();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (imageselectDialog.ImageDialogListner) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement TeamnameDialogListner");
        }
    }
    public interface ImageDialogListner {
        void applyTexts(String choice);
    }
}
