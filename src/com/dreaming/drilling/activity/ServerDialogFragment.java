package com.dreaming.drilling.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.dreaming.drilling.R;

public class ServerDialogFragment extends DialogFragment {
	
	String serverip;
	
	public ServerDialogFragment() {
	}
	
	/* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
	public interface ServerDialogListener {
		public void onDialogPositiveClick(DialogFragment dialog, String editText);
        public void onDialogNegativeClick(DialogFragment dialog);
	}
	
	// Use this instance of the interface to deliver action events
	ServerDialogListener mListener;
	
	// Override the Fragment.onAttach() method to instantiate the ServerDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the ServerDialogListener so we can send events to the host
            mListener = (ServerDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement ServerDialogListener");
        }
    }
	
	static ServerDialogFragment newInstance() {
		ServerDialogFragment s = new ServerDialogFragment();
		return s;
	}
	
	public static DialogFragment newInstance(String serverip) {
		ServerDialogFragment s = new ServerDialogFragment();
		
		// Supply server_ip input as an argument.
		Bundle args = new Bundle();
		args.putString("serverip", serverip);
		s.setArguments(args);
		
		return s;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		serverip = getArguments().getString("serverip");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    // Get the layout inflater
	    LayoutInflater inflater = getActivity().getLayoutInflater();
	    
	    View view = inflater.inflate(R.layout.dialog_server_ip, null);
	    
	    final EditText editText = (EditText) view.findViewById(R.id.server_ip_value);
	    editText.setText(serverip);
	    
	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    builder.setView(view)
	    // Add action buttons
	           .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	            	   // Send the positive button event back to the host activity
	            	   mListener.onDialogPositiveClick(ServerDialogFragment.this, editText.getText().toString());
	               }
	           })
	           .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	            	   // Send the negative button event back to the host activity
                       mListener.onDialogNegativeClick(ServerDialogFragment.this);
	                   ServerDialogFragment.this.getDialog().cancel();
	               }
	           });      
	    return builder.create();
	}

	
}
