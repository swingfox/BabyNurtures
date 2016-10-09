package com.example.willthespoderman.babynurtures.helper;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by edmore on 11/9/2015.
 */
public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    TheListener listener;
    public interface TheListener{
        void returnDate(String month, String date, String year);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        listener = (TheListener) getActivity();
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        if (listener != null) {
            listener.returnDate(Integer.toString(monthOfYear + 1), Integer.toString(dayOfMonth), Integer.toString(year));
        }

    }


}
