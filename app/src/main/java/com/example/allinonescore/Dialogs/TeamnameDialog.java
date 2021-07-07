package com.example.allinonescore.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.allinonescore.R;

public class TeamnameDialog extends DialogFragment {

    private EditText Teamname;
    private TeamnameDialogListner listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.team_name_dailog, null);
        builder.setView(view)
                .setTitle("Team Name")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String Name = Teamname.getText().toString();
                        listener.applyTexts(Name);
                    }
                });
        Teamname = view.findViewById(R.id.dialog_teamname);
        return builder.create();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (TeamnameDialogListner) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement TeamnameDialogListner");
        }
    }
    public interface TeamnameDialogListner {
        void applyTexts(String teamname);
    }
}

