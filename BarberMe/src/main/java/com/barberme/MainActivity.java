package com.barberme;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.barberme.custom.CustomActivity;
import com.barberme.custom.CustomFragment;
import com.barberme.model.Data;
import com.barberme.ui.LeftNavAdapter;
import com.barberme.ui.MainFragment;
import com.barberme.ui.TakeAppointmentFragment;
import com.barberme.ui.TakeTicketFragment;

import java.util.ArrayList;

/**
 * The Activity MainActivity will launched after the Login and it is the
 * Home/Base activity of the app which holds all the Fragments and also show the
 * Sliding Navigation drawer. You can write your code for displaying actual
 * items on Drawer layout.
 */
public class MainActivity extends CustomActivity
{

	/** The drawer layout. */
	private DrawerLayout drawerLayout;

	/** ListView for left side drawer. */
	private ListView drawerLeft;

	/** The drawer toggle. */
	private ActionBarDrawerToggle drawerToggle;

	/** The lbl title. */
	private TextView lblTitle;

	/* (non-Javadoc)
	 * @see com.newsfeeder.custom.CustomActivity#onCreate(android.os.Bundle)
	 */
	@SuppressLint("InflateParams")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		View c = getLayoutInflater().inflate(R.layout.notify, null);
		lblTitle = (TextView) c.findViewById(R.id.title);
		lblTitle.setOnClickListener(this);
		lblTitle.setOnTouchListener(TOUCH);
		c.findViewById(R.id.feed).setOnClickListener(this);
		c.findViewById(R.id.feed).setOnTouchListener(TOUCH);
		getActionBar().setCustomView(c);
		getActionBar().setDisplayShowCustomEnabled(true);

		setupDrawer();
		setupContainer(1);
	}

	/* (non-Javadoc)
	 * @see com.dashboard.custom.CustomActivity#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v)
	{
		super.onClick(v);
		if (v.getId() == R.id.feed)
			startActivity(new Intent(this, MessageHistory.class));
		else if (v == lblTitle)
		{
			if (drawerLayout.isDrawerOpen(drawerLeft))
				drawerLayout.closeDrawers();
			else
				drawerLayout.openDrawer(drawerLeft);
		}
	}

	/**
	 * Setup the drawer layout. This method also includes the method calls for
	 * setting up the Left & Right side drawers.
	 */
	private void setupDrawer()
	{
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			@Override
			public void onDrawerClosed(View view)
			{
			}

			@Override
			public void onDrawerOpened(View drawerView)
			{
			}
		};
		drawerLayout.setDrawerListener(drawerToggle);
		drawerLayout.closeDrawers();

		setupLeftNavDrawer();
	}

	/**
	 * Setup the left navigation drawer/slider. You can add your logic to load
	 * the contents to be displayed on the left side drawer. It will also setup
	 * the Header and Footer contents of left drawer. This method also apply the
	 * Theme for components of Left drawer.
	 */
	@SuppressLint("InflateParams")
	private void setupLeftNavDrawer()
	{
		drawerLeft = (ListView) findViewById(R.id.left_drawer);

		View header = getLayoutInflater().inflate(R.layout.left_nav_header,
				null);

		drawerLeft.addHeaderView(header);

		final ArrayList<Data> al = new ArrayList<Data>();
		al.add(new Data(new String[] { "ACCEUIL" }, new int[] {
				R.drawable.ic_nav1, R.drawable.ic_nav1_sel }));
		al.add(new Data(new String[] { "PRENDRE UN TICKET" }, new int[] {
				R.drawable.ic_nav1, R.drawable.ic_nav1_sel }));
		al.add(new Data(new String[] { "PRENDRE UN RENDEZ VOUS" }, new int[] {
				R.drawable.ic_nav2, R.drawable.ic_nav2_sel }));
		al.add(new Data(new String[] { "CONFIRMEZ VOTRE PRESENCE ICI" }, new int[] {
				R.drawable.ic_nav3, R.drawable.ic_nav3_sel }));
		al.add(new Data(new String[] { "PARAMETRE" }, new int[] {
				R.drawable.ic_nav4, R.drawable.ic_nav4_sel }));
		al.add(new Data(new String[] { "CONTACTS" }, new int[] {
				R.drawable.ic_nav5, R.drawable.ic_nav5_sel }));
		al.add(new Data(new String[] { "AIDE" }, new int[] {
				R.drawable.ic_nav6, R.drawable.ic_nav6_sel }));
		al.add(new Data(new String[] { "DECONNECTER" }, new int[] {
				R.drawable.ic_nav6, R.drawable.ic_nav6_sel }));

		final LeftNavAdapter adp = new LeftNavAdapter(this, al);
		drawerLeft.setAdapter(adp);
		drawerLeft.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> l, View arg1, int pos,
					long arg3)
			{
				drawerLayout.closeDrawers();
				setupContainer(pos);
			}
		});

	}

	/**
	 * Setup the container fragment for drawer layout. This method will setup
	 * the grid view display of main contents. You can customize this method as
	 * per your need to display specific content.
	 * 
	 * @param pos
	 *            the new up container
	 */
	private void setupContainer(int pos)
	{
		CustomFragment f = null;
		String title = getString(R.string.app_name);

		if (pos == 1)
			f = new MainFragment();
		else if (pos == 2)
			f  = new TakeTicketFragment();
		else if (pos == 3)
			f = new TakeAppointmentFragment();
		else if (pos == 4)
			Toast.makeText(getApplicationContext(), "Confirmer votre presence ici", Toast.LENGTH_SHORT).show();
		else if (pos == 5)
			Toast.makeText(getApplicationContext(), "Parametre", Toast.LENGTH_SHORT).show();
		else if (pos == 6)
			Toast.makeText(getApplicationContext(), "contact", Toast.LENGTH_SHORT).show();
		else if (pos == 7)
			Toast.makeText(getApplicationContext(), "Aide", Toast.LENGTH_SHORT).show();
		else if (pos == 8)
			Toast.makeText(getApplicationContext(), "Deconnecter", Toast.LENGTH_SHORT).show();

		if (f == null)
			return;

		getActionBar().setTitle(title);
		lblTitle.setText(title);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, f).commit();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPostCreate(android.os.Bundle)
	 */
	@Override
	protected void onPostCreate(Bundle savedInstanceState)
	{
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		drawerToggle.syncState();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onConfigurationChanged(android.content.res.Configuration)
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggle
		drawerToggle.onConfigurationChanged(newConfig);
	}

	/* (non-Javadoc)
	 * @see com.whatshere.custom.CustomActivity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (drawerToggle.onOptionsItemSelected(item))
		{
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
