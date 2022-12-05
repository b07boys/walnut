package org.b07boys.walnut.main;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;

import org.b07boys.walnut.R;
import org.b07boys.walnut.auth.AccountUtils;
import org.b07boys.walnut.auth.UserType;
import org.b07boys.walnut.courses.CourseCatalogue;
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

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());

            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            mlp.leftMargin = insets.left;
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
        navController.addOnDestinationChangedListener((navController1, navDestination, bundle) -> {
            if ((navDestination.equals(navController1.findDestination(R.id.loginFragment))) ||
                    (navDestination.equals(navController1.findDestination(R.id.signInFragment))) ||
                    (navDestination.equals(navController1.findDestination(R.id.welcomeFragment)))
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
            if ((navDestination.equals(navController1.findDestination(R.id.chooseCoursesDesiredFragment)))) appBarLayout.setVisibility(View.GONE);
            if ((navDestination.equals(navController1.findDestination(R.id.chooseCoursesTakenFragment)))) appBarLayout.setVisibility(View.GONE);
        });

        setSupportActionBar(binding.topAppBar);

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.generatedTimelinesFragment:
                    navController.navigate(R.id.generatedTimelinesFragment);
                    break;
                case R.id.studentHomescreenFragment:
                    navController.navigate(R.id.studentHomescreenFragment);
                    break;
            }
            return true;
        });

        final String[] accountType = {""};

        FirebaseAuth.getInstance().addAuthStateListener(firebaseAuth -> AccountUtils.getUserType(FirebaseAuth.getInstance().getCurrentUser(), object -> {
            if (object == UserType.ADMIN) accountType[0] = "Admin";
            else if (object == UserType.STUDENT) accountType[0] = "Student";
            else accountType[0] = "Unknown";
        }));

        binding.topAppBar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.account_info:
                    new MaterialAlertDialogBuilder(MainActivity.this, com.google.android.material.R.style.ThemeOverlay_Material3_MaterialAlertDialog_Centered)
                            .setTitle("Account")
                            .setMessage("Email: " + FirebaseAuth.getInstance().getCurrentUser().getEmail() + "\nAccount Type: " + accountType[0])
                            .setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_account, getTheme()))
                            .setNeutralButton("Log out", (dialogInterface, i) -> {
                                FirebaseAuth.getInstance().signOut();
                                navController.navigate(R.id.welcomeFragment);
                            })
                            .setNeutralButtonIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_logout, getTheme()))
                            .show();
                    break;
            }
            return true;
        });
    }

    public void setActionBarTitles(String title, String subtitle) {
        binding.topAppBar.setTitle(title);
        binding.topAppBar.setSubtitle(subtitle);
    }

}