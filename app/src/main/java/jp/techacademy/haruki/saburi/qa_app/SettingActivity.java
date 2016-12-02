package jp.techacademy.haruki.saburi.qa_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SettingActivity extends AppCompatActivity {

    DatabaseReference mDataBaseReference;
    private EditText mNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String name = sp.getString(Const.NameKEY, "");
        EditText nameText = (EditText)findViewById(R.id.nameText);
        nameText.setText(name);

        mDataBaseReference = FirebaseDatabase.getInstance().getReference();

        setTitle("設定");
        mNameText = (EditText) findViewById(R.id.nameText);
        Button changeButton = (Button) findViewById(R.id.changeButton);
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (user == null){
                    Snackbar.make(v, "ログインしていません", Snackbar.LENGTH_LONG).show();
                    return;
                }

                String name = mNameText.getText().toString();
                DatabaseReference userRef = mDataBaseReference.child(Const.UsersPATH).child(user.getUid());
                Map<String, String> data = new HashMap<String, String>();
                data.put("name", name);
                userRef.setValue(data);

                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(Const.NameKEY, name);
                editor.commit();

                Snackbar.make(v, "表示名を変更しました", Snackbar.LENGTH_LONG).show();
            }
        });

        Button logoutButton = (Button)findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                mNameText.setText("");
                Snackbar.make(v, "ログアウトしました", Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
