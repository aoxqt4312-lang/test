package duress.keyboard;

import android.app.*;
import android.app.admin.*;
import android.content.*;
import android.content.pm.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.graphics.*;
import android.util.*;
import java.util.*;

public class AdditionalOptionsActivity extends Activity {

	private void showWipeLimitDialog() {
    final android.widget.EditText input = new android.widget.EditText(this);
    input.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
    android.widget.FrameLayout container = new android.widget.FrameLayout(this);
    android.widget.FrameLayout.LayoutParams params = new android.widget.FrameLayout.LayoutParams(-1, -2);
    int margin = (int) android.util.TypedValue.applyDimension(android.util.TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
    params.leftMargin = margin; params.rightMargin = margin;
    input.setLayoutParams(params);
    container.addView(input);
	String dialogLangText="Hello. This is Auto-Wipe Settings. Please set limit of failed password attempts to wipe phone data. This wipe can be initialized by system (even in safe mode). Unfortunately, it can be without wipe Esim and External storage. System may display warning about unsuccessful attempts. This wipe can work if you grant administrator permission to app after installing app starting version 5.2 (because app have <watch-login/> policy, required to set limit, starting this version. if you provided earlier: pls regrant or reinstall app with new version. \n\nSet any number. If you want wipe immediatelly after 1 incorrect attempt, set 1. if you set 0 - this is no limit.";
       
	
	if ("ru".equalsIgnoreCase(Locale.getDefault().getLanguage())) dialogLangText="Привет. Это Настройки Авто-Сброса. Пожалуйста, установите лимит на количество неудачных попыток ввода пароля для очистки данных телефона. Эта очистка может быть инициирована системой (даже в безопасном режиме). К сожалению, она может быть без очистки Esim и Внешнего хранилища. Система может показывать предупрежление о неудачных попытках. Эта очистка будет работать, если вы предоставите приложению права администратора после установки приложения, начиная с версии 5.2 (поскольку приложение имеет политику <watch-login/>, необходимую для установки лимита, начиная с этой версии. Если вы предоставили раньше: пожалуйста, перепредоставьте или переустановите приложение с новой версией). \n\nУстановите любое число. Если хотите, чтобы очистка происходила немедленно после 1 неверной попытки, установите 1. Если установите 0 - это нет лимита.";

    final android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(this).setMessage(dialogLangText).setView(container).setCancelable(false).setPositiveButton("OK", new android.content.DialogInterface.OnClickListener() {
        @Override
        public void onClick(android.content.DialogInterface dialog, int which) {
            String val = input.getText().toString();
            if (val.isEmpty()) {showWipeLimitDialog(); return;}
            try {
                int limit = Integer.parseInt(val);
                android.app.admin.DevicePolicyManager dpm = (android.app.admin.DevicePolicyManager) getSystemService(android.content.Context.DEVICE_POLICY_SERVICE);
                android.content.ComponentName adminComponent = new android.content.ComponentName(AdditionalOptionsActivity.this, MyDeviceAdminReceiver.class);
				dpm.setMaximumFailedPasswordsForWipe(adminComponent, limit);                
                int factLimit = dpm.getMaximumFailedPasswordsForWipe(adminComponent);				
                android.widget.Toast.makeText(AdditionalOptionsActivity.this, "Password failed attempts for wipe: " + factLimit + ".", android.widget.Toast.LENGTH_LONG).show();
				showWipeLimitDialog();
				try {					
					finish();					
				} catch (Throwable tirex) {}
				return;
			} catch (Throwable t) {
                android.widget.TextView errorView = new android.widget.TextView(AdditionalOptionsActivity.this);
                errorView.setText(t.getMessage()); errorView.setTextIsSelectable(true); errorView.setPadding(60, 40, 60, 0);
                new android.app.AlertDialog.Builder(AdditionalOptionsActivity.this).setTitle("Err:").setView(errorView).setPositiveButton("OK", null).show();
            }
        }
    }).create();
    dialog.getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_SECURE);
    dialog.getWindow().getDecorView().setSystemUiVisibility(android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | android.view.View.SYSTEM_UI_FLAG_FULLSCREEN);
    dialog.getWindow().setSoftInputMode(android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    
	android.view.Window window = dialog.getWindow();
	if (window != null) {
    window.setSoftInputMode(android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    android.view.WindowManager.LayoutParams lp = window.getAttributes();
    lp.gravity = android.view.Gravity.CENTER;
    lp.y = 0; 
    lp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT; 
    window.setAttributes(lp);
	}
	dialog.show();
	input.requestFocus();
    input.requestFocus();
	}

    @Override
    protected void onResume() {
        super.onResume();
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
		getWindow().getDecorView().setKeepScreenOn(true);
        getWindow().getDecorView().setSystemUiVisibility(
			View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
			| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
			| View.SYSTEM_UI_FLAG_FULLSCREEN
			| View.SYSTEM_UI_FLAG_LAYOUT_STABLE
			| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
			| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
		}
	
	
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        super.onCreate(savedInstanceState);
        showWipeLimitDialog();
    }
}
