package com.example.roc06.staffciao;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EventAdderDialogue extends DialogFragment {
    public static EventAdderDialogue newInstance(int num) {
        EventAdderDialogue f = new EventAdderDialogue();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);
        final ScheduleDisplay activity = (ScheduleDisplay) getActivity();

        activity.Refresh();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        final int index = getArguments().getInt("num");
        ScheduleEvents event = InternalData.scheduleEvents.get(index);
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        //Inflate custom layout, and pre-populate fields with data from 'event'
        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.schedule_event_changer_dialogue, null);

        builder.setView(layout)
                // Add action buttons
                .setPositiveButton("Change Event", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Validate all input, and then change the event.
                        String name, location, description;
                        int reminderTime;
                        Date time = null;

                        //Name
                        EditText eText = (EditText) layout.getChildAt(0);
                        name = eText.getText().toString();
                        //Location
                        eText = (EditText) layout.getChildAt(1);
                        location = eText.getText().toString();
                        //Time
                        eText = (EditText) layout.getChildAt(2);
                        String temp = eText.getText().toString();
                        DateFormat formatter = new SimpleDateFormat("hh:mm");
                        try {
                            time = formatter.parse(temp);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        //Reminder
                        eText = (EditText) layout.getChildAt(3);
                        reminderTime = Integer.parseInt(eText.getText().toString());
                        //Description
                        eText = (EditText) layout.getChildAt(4);
                        description = eText.getText().toString();

                        //TODO Validation


                        ScheduleEvents tempEvent = new ScheduleEvents(time, name, reminderTime, location);
                        InternalData.scheduleEvents.set(index, tempEvent);


                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EventAdderDialogue.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

}
