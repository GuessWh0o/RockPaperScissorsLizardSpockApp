package com.guesswho.rockpaper;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by GuessWh0o on 21.02.2018.
 * Email: developerint97@gmail.com
 */

public class DialogFragmentYesNo extends AppCompatDialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.dialog_yes_no, container, false);


        ColorDrawable colorDrawable = new ColorDrawable(Color.BLACK);
        colorDrawable.setAlpha(120);
        getDialog().getWindow().setBackgroundDrawable(colorDrawable);

        Button yes = rootView.findViewById(R.id.btnYes);
        Button no = rootView.findViewById(R.id.btnNo);

        yes.setOnClickListener(view -> {
            try {
                getActivity().finishAffinity();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        });

        no.setOnClickListener(view -> dismiss());

        return rootView;
    }
}
