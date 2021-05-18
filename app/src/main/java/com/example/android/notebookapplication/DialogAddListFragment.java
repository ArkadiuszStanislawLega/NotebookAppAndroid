package com.example.android.notebookapplication;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DialogAddListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DialogAddListFragment extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText _etListName;
    private Button _bConfirm;
    private Button _bCancel;
    private View _currentView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DialogAddListFragment() {
        // Required empty public constructor
    }

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_dialog_add_list, null);

        builder.setView(inflater.inflate(R.layout.fragment_dialog_add_list, null))
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String value = _etListName.getText().toString();
                        Toast.makeText(getActivity(), value, Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                })
                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        DialogAddListFragment.this.getDialog().cancel();
                    }
                });

        _etListName = view.findViewById(R.id.et_name_new_list);

        return builder.create();

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DialogAddListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DialogAddListFragment newInstance(String param1, String param2) {
        DialogAddListFragment fragment = new DialogAddListFragment();
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
        this._currentView = inflater.inflate(R.layout.fragment_dialog_add_list, container, false);

        this.initControls();
        this.initListeners();
        return this._currentView;
    }

    private void initControls(){
        this._etListName = this._currentView.findViewById(R.id.et_name_new_list);
        this._bConfirm = this._currentView.findViewById(R.id.b_dialog_confirm);
        this._bCancel = this._currentView.findViewById(R.id.b_dialog_cancel);
    }

    private void initListeners(){
        this._bConfirm.setOnClickListener(view -> {
            String value = this._etListName.getText().toString();
            Toast.makeText(this.getActivity(), value, Toast.LENGTH_SHORT).show();
            this.dismiss();
        });

        this._bCancel.setOnClickListener(view -> {
            DialogAddListFragment.this.getDialog().cancel();
        });
    }
}