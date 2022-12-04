package org.b07boys.walnut.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.UserManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

import org.b07boys.walnut.R;
import org.b07boys.walnut.auth.AccountUtils;
import org.b07boys.walnut.auth.UserType;
import org.b07boys.walnut.courses.CourseCatalogue;
import org.b07boys.walnut.database.PromiseReceivedData;
import org.b07boys.walnut.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // To instentiate course since they start before doing anything
        CourseCatalogue.getInstance();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());

            boolean imeVisible = windowInsets.isVisible(WindowInsetsCompat.Type.ime());
            int imeHeight = windowInsets.getInsets(WindowInsetsCompat.Type.ime()).bottom;

            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            mlp.leftMargin = insets.left;
            //mlp.bottomMargin = imeVisible ? imeHeight : insets.bottom;
            mlp.rightMargin = insets.right;
            mlp.topMargin = insets.top;
            v.setLayoutParams(mlp);
            return windowInsets;
        });
    }



    @Override
    protected void onStart() {
        super.onStart();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        BottomNavigationView bottomNavigationView = binding.bottomNavigation;
        AppBarLayout appBarLayout = binding.appBarLayout;
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                if ((navDestination.equals(navController.findDestination(R.id.loginFragment))) ||
                        (navDestination.equals(navController.findDestination(R.id.signInFragment))) ||
                        (navDestination.equals(navController.findDestination(R.id.welcomeFragment)))
                ) {
                    bottomNavigationView.setVisibility(View.GONE);
                    appBarLayout.setVisibility(View.GONE);
                } else {
                    bottomNavigationView.setVisibility(View.VISIBLE);
                    appBarLayout.setVisibility(View.VISIBLE);
                }
                AccountUtils.getUserType(FirebaseAuth.getInstance().getCurrentUser(), userType -> {
                    if (userType == UserType.ADMIN) bottomNavigationView.setVisibility(View.GONE);
                });
                if ((navDestination.equals(navController.findDestination(R.id.chooseCoursesDesiredFragment)))) appBarLayout.setVisibility(View.GONE);
            }
        });

        setSupportActionBar(binding.topAppBar);

        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.generatedTimelinesFragment:
                        navController.navigate(R.id.generatedTimelinesFragment);
                        break;
                    case R.id.studentHomescreenFragment:
                        navController.navigate(R.id.studentHomescreenFragment);
                        break;
                }
                return true;
            }
        });

        final String[] accountType = {""};

        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                AccountUtils.getUserType(FirebaseAuth.getInstance().getCurrentUser(), new PromiseReceivedData<UserType>() {
                    @Override
                    public void onReceive(UserType object) {
                        if (object == UserType.ADMIN) accountType[0] = "Admin";
                        else if (object == UserType.STUDENT) accountType[0] = "Student";
                        else accountType[0] = "Unknown";
                    }
                });
            }
        });

        binding.topAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.account_info:
                        new MaterialAlertDialogBuilder(MainActivity.this, com.google.android.material.R.style.ThemeOverlay_Material3_MaterialAlertDialog_Centered)
                                .setTitle("Account")
                                .setMessage("Email: " + FirebaseAuth.getInstance().getCurrentUser().getEmail() + "\nAccount Type: " + accountType[0])
                                .setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_account, getTheme()))
                                .setNeutralButton("Log out", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        FirebaseAuth.getInstance().signOut();
                                        navController.navigate(R.id.welcomeFragment);
                                    }
                                })
                                .setNeutralButtonIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_logout, getTheme()))
                                .show();
                        break;
                    case R.id.search:
                        break;
                }
                return true;
            }
        });
    }

    public void setActionBarTitles(String title, String subtitle) {
        binding.topAppBar.setTitle(title);
        binding.topAppBar.setSubtitle(subtitle);
    }

}