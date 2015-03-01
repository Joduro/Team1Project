package ca.ualberta.cs.team1travelexpenseapp;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ClaimantExpenseListActivity extends Activity {

	public Claim claim;
	private ArrayAdapter<String> expenselistAdapter ;
 	private ListView expenseListView ;
 	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.claimant_activity_main);
		
		//As an approver, I want to list all the expense items for a submitted claim,
		//in order of entry, showing for each expense item: the date the expense was incurred,
		//the category, the textual description, amount spent, unit of currency, 
		//and whether there is a photographic receipt.

        expenseListView = (ListView) findViewById(R.id.expensesList);
        expenselistAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,claim.getExpenses());
        expenseListView.setAdapter(expenselistAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.claimant_expense_list, menu);
		return true;
	}

}
