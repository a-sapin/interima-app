package stoil.loki.interim;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import androidx.core.content.res.ResourcesCompat;

import java.io.Serializable;

public class ItemProfil implements Serializable {
    private int id;
    private String title;
    private Drawable icon;
    private Class intent;
    private UserData ud;

    public ItemProfil(int id, String title, Drawable icon, Class intent, UserData ud) {
        this.id = id;
        this.title = title;
        this.icon = icon;
        this.intent = intent;
        this.ud = ud;
    }

    public ItemProfil(Context context) {
        this.id = 0;
        this.title = "profil nom option";
        Resources resources = context.getResources();
        Drawable ic = ResourcesCompat.getDrawable(resources, R.drawable.ic_launcher_foreground, null);
        this.icon = ic;
        this.intent = MainActivity.class;
    }

    public int getId() {
        return id;
    }

    public String getTitle(){
        return title;
    }

    public Drawable getIcon(){
        return icon;
    }

    public Class getIntentClass() {return intent;}

    public UserData getUd() {
        return ud;
    }
}
