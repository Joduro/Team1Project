package ca.ualberta.cs.team1travelexpenseapp;

import java.util.ArrayList;
import java.util.Collection;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

/**
 * Displays a list of submitted claims for the approver and allows them to click individual claims
 * to view the contained expenses. (not yet fully implemented)
 *
 */
public class ApproverClaimsListActivity extends Activity {

	
	private ClaimList claimList;
 	private ListView mainListView;
 	public  AlertDialog editClaimDialog;
 	private Listener listener;

 	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.approver_display_claims);

		//As an approver, I want to view a list of all the expense claims that were submitted 
		//for approval, which have their claim status as submitted, showing for each claim:
		//the claimant name, the starting date of travel, the destination(s) of travel, the 
		//claim status, total currency amounts, and any approver name.

        
      //taken from https://github.com/abramhindle/student-picker (March 15, 2015) and modified
		// TODO: show approved claims and make sure the approver doesn't see their own claims
		final ListView claimsListView = (ListView) findViewById(R.id.approverclaimList);
  		claimList=ClaimListController.getSubmittedClaims();
  		Collection<Claim> claims = claimList.getClaims();
		final ArrayList<Claim> claimsList = new ArrayList<Claim>(claims);
  		final ArrayAdapter<Claim> claimsAdapter = new ArrayAdapter<Claim>(this, android.R.layout.simple_list_item_1, claimsList);
  		claimsListView.setAdapter(claimsAdapter);
  		
  		listener=new Listener() {			
			@Override
			public void update() {
				claimsList.clear();
		  		claimList=ClaimListController.getSubmittedClaims();
				Collection<Claim> claims = claimList.getClaims();
				claimsList.addAll(claims);
				claimsAdapter.notifyDataSetChanged();
			}
		};
        
        claimList.addListener(listener);
        
        
        claimsListView.setOnItemClickListener(new OnItemClickListener(){
        	public void onItemClick( AdapterView Parent, View v, int position, long id){
        		ClaimListController.setCurrentClaim(ClaimListController.getClaimList().getClaim(position));
        		Intent intent= new Intent(getBaseContext(),ApproverExpenseListActivity.class);	
        		startActivity(intent);
        	}
        });
        	

	}
	
	@Override
	public void onStart(){
		super.onStart();
		final ListView claimsListView = (ListView) findViewById(R.id.approverclaimList);
  		claimList=ClaimListController.getSubmittedClaims();
  		Collection<Claim> claims = claimList.getClaims();
		final ArrayList<Claim> claimsList = new ArrayList<Claim>(claims);
  		final ArrayAdapter<Claim> claimsAdapter = new ArrayAdapter<Claim>(this, android.R.layout.simple_list_item_1, claimsList);
  		claimsListView.setAdapter(claimsAdapter);
  		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Opens TagManagerActivity which allows the user to add, edit and delete tags which can 
	 * be added to claims.
	 */
	public void onManageTagsClick(View v){
		Intent intent= new Intent(this, TagManagerActivity.class);
		startActivity(intent);
	}
	
	/**
	 * On destroy remove the listener so that the claimList no longer attempts to update it
	 */
	public void onDestroy(){
		super.onDestroy();
		claimList.removeListener(listener);
	}
	
}
