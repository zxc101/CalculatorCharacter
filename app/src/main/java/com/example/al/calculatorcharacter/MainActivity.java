package com.example.al.calculatorcharacter;

import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.al.calculatorcharacter.Fragments.Hero;
import com.example.al.calculatorcharacter.Fragments.Enemy1;
import com.example.al.calculatorcharacter.Fragments.Enemy2;
import com.example.al.calculatorcharacter.Fragments.Enemy3;
import com.example.al.calculatorcharacter.Fragments.Enemy4;
import com.example.al.calculatorcharacter.Fragments.Enemy5;

import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int COUNT_ENEMYS = 5;

    EditText countMatchesET;
    TextView countMatchesTV;
    Button vsBTN;


    Fragment hero;
    Fragment[] enemys = new Fragment[5];
    Fragment enemysStat;
    Fragment heroesStat;

    View heroFragments;
    View[] enemysFragments = new View[5];
    View heroesStatFragments;
    View enemysStatFragments;

    Random r;
    boolean isDodge;

    int heroesAttack;
    int heroesLife;
    int heroesCrit;
    int heroesAttackPercent;
    int heroesCritPercent;
    int heroesDodgePercent;

    int enemysAttack[] = new int[COUNT_ENEMYS];
    int enemysLife[] = new int[COUNT_ENEMYS];
    int enemysCrit[] = new int[COUNT_ENEMYS];
    int enemysAttackPercent[] = new int[COUNT_ENEMYS];
    int enemysCritPercent[] = new int[COUNT_ENEMYS];
    int enemysDodgePercent[] = new int[COUNT_ENEMYS];

    int countWin;

    int damage[] = new int[COUNT_ENEMYS+1];
    int countFirstAttack[] = new int[COUNT_ENEMYS+1];
    int countAllAttack[] = new int[COUNT_ENEMYS+1];
    int countAttack[] = new int[COUNT_ENEMYS+1];
    int countCriticalAttack[] = new int[COUNT_ENEMYS+1];
    int countDodge[] = new int[COUNT_ENEMYS+1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        hero = getFragmentManager().findFragmentById(R.id.fragment_hero);
        enemys[0] = getFragmentManager().findFragmentById(R.id.fragment_enemy1);
        enemys[1] = getFragmentManager().findFragmentById(R.id.fragment_enemy2);
        enemys[2] = getFragmentManager().findFragmentById(R.id.fragment_enemy3);
        enemys[3] = getFragmentManager().findFragmentById(R.id.fragment_enemy4);
        enemys[4] = getFragmentManager().findFragmentById(R.id.fragment_enemy5);

        heroesStat = getFragmentManager().findFragmentById(R.id.fragment_heroes_stats);
        enemysStat = getFragmentManager().findFragmentById(R.id.fragment_enemys_stats);

        heroFragments = findViewById(R.id.fragment_hero);
        enemysFragments[0] = findViewById(R.id.fragment_enemy1);
        enemysFragments[1] = findViewById(R.id.fragment_enemy2);
        enemysFragments[2] = findViewById(R.id.fragment_enemy3);
        enemysFragments[3] = findViewById(R.id.fragment_enemy4);
        enemysFragments[4] = findViewById(R.id.fragment_enemy5);

        heroesStatFragments = findViewById(R.id.fragment_heroes_stats);
        enemysStatFragments = findViewById(R.id.fragment_enemys_stats);

        allFragmentsInvisible();

        countMatchesET = (EditText) findViewById(R.id.countMatchesET);
        countMatchesTV = (TextView) findViewById(R.id.countMatchesTV);
        vsBTN = (Button) findViewById(R.id.vsBTN);

        r = new Random();
    }

    private void heroesInitialization(){
        String heroesAttackStr = ((EditText) hero.getView().findViewById(R.id.attackET)).getText().toString();
        String heroesLifeStr = ((EditText) hero.getView().findViewById(R.id.lifeET)).getText().toString();
        String heroesCritStr = ((EditText) hero.getView().findViewById(R.id.critET)).getText().toString();
        String heroesAttackPercentStr = ((EditText) hero.getView().findViewById(R.id.attackPercentET)).getText().toString();
        String heroesCritPercentStr = ((EditText) hero.getView().findViewById(R.id.critPercentET)).getText().toString();
        String heroesDodgePercentStr = ((EditText) hero.getView().findViewById(R.id.dodgePercentET)).getText().toString();

//        Toast.makeText(this, heroesAttackStr, Toast.LENGTH_SHORT).show();

        if(heroesAttackStr.equals("")){
            heroesAttack = 0;
        } else {
            heroesAttack = Integer.parseInt(heroesAttackStr);
        }

//        Toast.makeText(this, String.valueOf(heroesAttack), Toast.LENGTH_SHORT).show();

        if(heroesLifeStr.equals("")){
            heroesLife = 0;
        } else {
            heroesLife = Integer.parseInt(heroesLifeStr);
        }

        if(heroesCritStr.equals("")){
            heroesCrit = 0;
        } else {
            heroesCrit = Integer.parseInt(heroesCritStr);
        }

        if(heroesAttackPercentStr.equals("")){
            heroesAttackPercent = 0;
        }else {
            heroesAttackPercent = Integer.parseInt(heroesAttackPercentStr);
        }

        if(heroesCritPercentStr.equals("")){
            heroesCritPercent = 0;
        }else {
            heroesCritPercent = Integer.parseInt(heroesCritPercentStr);
        }

        if(heroesDodgePercentStr.equals("")){
            heroesDodgePercent = 0;
        }else {
            heroesDodgePercent = Integer.parseInt(heroesDodgePercentStr);
        }
    }

    private void enemysInitialization(Fragment f, int i){
        String enemysAttackStr = ((EditText) f.getView().findViewById(R.id.attackET)).getText().toString();
        String enemysLifeStr = ((EditText) f.getView().findViewById(R.id.lifeET)).getText().toString();
        String enemysCritStr = ((EditText) f.getView().findViewById(R.id.critET)).getText().toString();
        String enemysAttackPercentStr = ((EditText) f.getView().findViewById(R.id.attackPercentET)).getText().toString();
        String enemysCritPercentStr = ((EditText) f.getView().findViewById(R.id.critPercentET)).getText().toString();
        String enemysDodgePercentStr = ((EditText) f.getView().findViewById(R.id.dodgePercentET)).getText().toString();

        if(enemysAttackStr.equals("")){
            enemysAttack[i] = 0;
        }else {
            enemysAttack[i] = Integer.parseInt(enemysAttackStr);
        }

        if(enemysLifeStr.equals("")){
            enemysLife[i] = 0;
        }else {
            enemysLife[i] = Integer.parseInt(enemysLifeStr);
        }

        if(enemysCritStr.equals("")){
            enemysCrit[i] = 0;
        }else {
            enemysCrit[i] = Integer.parseInt(enemysCritStr);
        }

        if(enemysAttackPercentStr.equals("")){
            enemysAttackPercent[i] = 0;
        }else {
            enemysAttackPercent[i] = Integer.parseInt(enemysAttackPercentStr);
        }

        if(enemysCritPercentStr.equals("")){
            enemysCritPercent[i] = 0;
        }else {
            enemysCritPercent[i] = Integer.parseInt(enemysCritPercentStr);
        }

        if(enemysDodgePercentStr.equals("")){
            enemysDodgePercent[i] = 0;
        }else {
            enemysDodgePercent[i] = Integer.parseInt(enemysDodgePercentStr);
        }
    }

    private void enemyIsAttack(int enemysId){
        isDodge = false;
        if (enemysLife[enemysId] != 0) {
            countAllAttack[enemysId+1]++;
            if (r.nextInt(99)+1 <= enemysAttackPercent[enemysId]) {
                if (r.nextInt(99)+1 <= 100 - heroesDodgePercent) {
                    if (r.nextInt(99)+1 <= enemysCritPercent[enemysId]) {
                        if (heroesLife >= enemysCrit[enemysId] + enemysAttack[enemysId]) {
                            heroesLife = heroesLife - enemysCrit[enemysId] - enemysAttack[enemysId];
                            damage[enemysId+1] = damage[enemysId+1] + enemysCrit[enemysId] + enemysAttack[enemysId];
                        } else {
                            damage[enemysId+1] = damage[enemysId+1] + heroesLife;
                            heroesLife = 0;
                        }
                        countCriticalAttack[enemysId+1]++;
                    } else {
                        if (heroesLife >= enemysAttack[enemysId]) {
                            heroesLife = heroesLife - enemysAttack[enemysId];
                            damage[enemysId+1] = damage[enemysId+1] + enemysAttack[enemysId];
                        } else {
                            damage[enemysId+1] = damage[enemysId+1] + heroesLife;
                            heroesLife = 0;
                        }
                    }
                    countAttack[enemysId+1]++;
                } else {
                    isDodge = true;
                    countDodge[0]++;
                }
            } else {
                if (!isDodge) {
                    countDodge[0]++;
                }
            }
        }
    }

    private void heroIsAttack(){
        int enemysId;
        do {
            enemysId = r.nextInt(4);
        } while (enemysLife[enemysId] == 0);

        isDodge = false;
        countAllAttack[0]++;
        if (r.nextInt(99)+1 <= heroesAttackPercent) {
            if (r.nextInt(99)+1 <= 100 - enemysDodgePercent[enemysId]) {
                if (enemysLife[enemysId] != 0) {
                    if (r.nextInt(99)+1 <= heroesCritPercent) {
                        if (enemysLife[enemysId] >= heroesCrit + heroesAttack) {
                            enemysLife[enemysId] = enemysLife[enemysId] - heroesCrit - heroesAttack;
                            damage[0] = damage[0] + heroesCrit + heroesAttack;
                        } else {
                            damage[0] = damage[0] + enemysLife[enemysId];
                            enemysLife[enemysId] = 0;
                        }
                        countCriticalAttack[0]++;
                    } else {
                        if (enemysLife[enemysId] >= heroesAttack) {
                            enemysLife[enemysId] = enemysLife[enemysId] - heroesAttack;
                            damage[0] = damage[0] + heroesAttack;
                        } else {
                            damage[0] = damage[0] + enemysLife[enemysId];
                            enemysLife[enemysId] = 0;
                        }
                        countAttack[0]++;
                    }
                    if (enemysLife[enemysId] <= 0) {
                        countWin++;
                    }
                }
            } else {
                isDodge = true;
                countDodge[enemysId+1]++;
            }
        } else {
            if (!isDodge) {
                countDodge[enemysId+1]++;
            }
        }
    }

    private void enemyIsFirstAttack(){
        for(int i = 0; i < COUNT_ENEMYS; i++)
        {
            if(enemysLife[i] != 0)
            {
                countFirstAttack[i+1]++;
                break;
            }
        }
        while (heroesLife > 0 &&
                enemysLife[0] > 0 || enemysLife[1] > 0 || enemysLife[2] > 0 || enemysLife[3] > 0 || enemysLife[4] > 0){
            enemyIsAttack(0);
            enemyIsAttack(1);
            enemyIsAttack(2);
            enemyIsAttack(3);
            enemyIsAttack(4);
            heroIsAttack();
        }
    }

    private void heroIsFirstAttack(){
        countFirstAttack[0]++;
        while (heroesLife > 0 &&
                enemysLife[0] > 0 || enemysLife[1] > 0 || enemysLife[2] > 0 || enemysLife[3] > 0 || enemysLife[4] > 0){
            heroIsAttack();
            enemyIsAttack(0);
            enemyIsAttack(1);
            enemyIsAttack(2);
            enemyIsAttack(3);
            enemyIsAttack(4);
        }
    }

    private void restorationEnemy(int enemysId){
        String enemysLifeStr = ((EditText) enemys[enemysId].getView().findViewById(R.id.lifeET)).getText().toString();
        if(enemysLifeStr.equals("")){
            enemysLife[enemysId] = 0;
        }else {
            enemysLife[enemysId] = Integer.parseInt(enemysLifeStr);
        }
    }

    private void restorationStats(){
        countWin = 0;

        for(int i = 0; i < COUNT_ENEMYS + 1; i++) {
            damage[i] = 0;
            countFirstAttack[i] = 0;
            countAllAttack[i] = 0;
            countAttack[i] = 0;
            countCriticalAttack[i] = 0;
            countDodge[i] = 0;
        }
    }

    public void onClickVS(View v){

        restorationStats();

        heroesInitialization();
        for(int i = 0; i < COUNT_ENEMYS; i++){
            enemysInitialization(enemys[i], i);
        }

        int startlimitMatches = 0;
        int countMatches;
        String countMatchesStr = ((EditText) findViewById(R.id.countMatchesET)).getText().toString();
        if(countMatchesStr.equals("")){
            countMatches = 1;
        }else {
            countMatches = Integer.parseInt(countMatchesStr);
        }

        while (countMatches != startlimitMatches && heroesLife > 0)
        {
            switch (r.nextInt(1)) {
                case 0:
                    enemyIsFirstAttack();
                    break;
                case 1:
                    heroIsFirstAttack();
                    break;
            }
            startlimitMatches++;

            restorationEnemy(0);
            restorationEnemy(1);
            restorationEnemy(2);
            restorationEnemy(3);
            restorationEnemy(4);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void allFragmentsInvisible(){
        heroFragments.setVisibility(View.INVISIBLE);
        for(int i = 0; i < COUNT_ENEMYS; i++) {
            enemysFragments[i].setVisibility(View.INVISIBLE);
        }
        heroesStatFragments.setVisibility(View.INVISIBLE);
        enemysStatFragments.setVisibility(View.INVISIBLE);
    }

    private void CreateHeroesStats(){
        ((TextView) heroesStat.getView().findViewById(R.id.countWinTV)).setText( getString(R.string.countWin) + " " + String.valueOf(countWin));
        ((TextView) heroesStat.getView().findViewById(R.id.damageTV)).setText(getString(R.string.damage) + " " + String.valueOf(damage[0]));
        ((TextView) heroesStat.getView().findViewById(R.id.countFirstAttackTV)).setText(getString(R.string.countFirstAttack) + " " + String.valueOf(countFirstAttack[0]));
        ((TextView) heroesStat.getView().findViewById(R.id.countAllAttackTV)).setText(getString(R.string.countAllAttack) + " " + String.valueOf(countAllAttack[0]));
        ((TextView) heroesStat.getView().findViewById(R.id.countAttackTV)).setText(getString(R.string.countAttack) + " " + String.valueOf(countAttack[0]));
        ((TextView) heroesStat.getView().findViewById(R.id.countCriticalAttackTV)).setText(getString(R.string.countCriticalAttack) + " " + String.valueOf(countCriticalAttack[0]));
        ((TextView) heroesStat.getView().findViewById(R.id.countDodgeTV)).setText(getString(R.string.countDodge) + " " + String.valueOf(countDodge[0]));
    }

    private void CreateEnemysStats(Fragment f, int enemysId){
        ((TextView) f.getView().findViewById(R.id.damageTV)).setText(getString(R.string.damage) + " " + String.valueOf(damage[enemysId]));
        ((TextView) f.getView().findViewById(R.id.countFirstAttackTV)).setText(getString(R.string.countFirstAttack) + " " + String.valueOf(countFirstAttack[enemysId]));
        ((TextView) f.getView().findViewById(R.id.countAllAttackTV)).setText(getString(R.string.countAllAttack) + " " + String.valueOf(countAllAttack[enemysId]));
        ((TextView) f.getView().findViewById(R.id.countAttackTV)).setText(getString(R.string.countAttack) + " " + String.valueOf(countAttack[enemysId]));
        ((TextView) f.getView().findViewById(R.id.countCriticalAttackTV)).setText(getString(R.string.countCriticalAttack) + " " + String.valueOf(countCriticalAttack[enemysId]));
        ((TextView) f.getView().findViewById(R.id.countDodgeTV)).setText(getString(R.string.countDodge) + " " + String.valueOf(countDodge[enemysId]));
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        allFragmentsInvisible();

        if (id == R.id.nav_hero) {
            heroFragments.setVisibility(View.VISIBLE);
            countMatchesET.setVisibility(View.VISIBLE);
            countMatchesTV.setVisibility(View.VISIBLE);
            vsBTN.setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_enemy1) {
            enemysFragments[0].setVisibility(View.VISIBLE);
            countMatchesET.setVisibility(View.VISIBLE);
            countMatchesTV.setVisibility(View.VISIBLE);
            vsBTN.setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_enemy2) {
            enemysFragments[1].setVisibility(View.VISIBLE);
            countMatchesET.setVisibility(View.VISIBLE);
            countMatchesTV.setVisibility(View.VISIBLE);
            vsBTN.setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_enemy3) {
            enemysFragments[2].setVisibility(View.VISIBLE);
            countMatchesET.setVisibility(View.VISIBLE);
            countMatchesTV.setVisibility(View.VISIBLE);
            vsBTN.setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_enemy4) {
            enemysFragments[3].setVisibility(View.VISIBLE);
            countMatchesET.setVisibility(View.VISIBLE);
            countMatchesTV.setVisibility(View.VISIBLE);
            vsBTN.setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_enemy5) {
            enemysFragments[4].setVisibility(View.VISIBLE);
            countMatchesET.setVisibility(View.VISIBLE);
            countMatchesTV.setVisibility(View.VISIBLE);
            vsBTN.setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_stat_hero) {
            heroesStatFragments.setVisibility(View.VISIBLE);
            CreateHeroesStats();
            countMatchesET.setVisibility(View.INVISIBLE);
            countMatchesTV.setVisibility(View.INVISIBLE);
            vsBTN.setVisibility(View.INVISIBLE);
        } else if (id == R.id.nav_stat_enemy1) {
            enemysStatFragments.setVisibility(View.VISIBLE);
            CreateEnemysStats(enemysStat, 1);
            countMatchesET.setVisibility(View.INVISIBLE);
            countMatchesTV.setVisibility(View.INVISIBLE);
            vsBTN.setVisibility(View.INVISIBLE);
        } else if (id == R.id.nav_stat_enemy2) {
            enemysStatFragments.setVisibility(View.VISIBLE);
            CreateEnemysStats(enemysStat, 2);
            countMatchesET.setVisibility(View.INVISIBLE);
            countMatchesTV.setVisibility(View.INVISIBLE);
            vsBTN.setVisibility(View.INVISIBLE);
        } else if (id == R.id.nav_stat_enemy3) {
            enemysStatFragments.setVisibility(View.VISIBLE);
            CreateEnemysStats(enemysStat, 3);
            countMatchesET.setVisibility(View.INVISIBLE);
            countMatchesTV.setVisibility(View.INVISIBLE);
            vsBTN.setVisibility(View.INVISIBLE);
        } else if (id == R.id.nav_stat_enemy4) {
            enemysStatFragments.setVisibility(View.VISIBLE);
            CreateEnemysStats(enemysStat, 4);
            countMatchesET.setVisibility(View.INVISIBLE);
            countMatchesTV.setVisibility(View.INVISIBLE);
            vsBTN.setVisibility(View.INVISIBLE);
        } else if (id == R.id.nav_stat_enemy5) {
            enemysStatFragments.setVisibility(View.VISIBLE);
            CreateEnemysStats(enemysStat, 5);
            countMatchesET.setVisibility(View.INVISIBLE);
            countMatchesTV.setVisibility(View.INVISIBLE);
            vsBTN.setVisibility(View.INVISIBLE);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
