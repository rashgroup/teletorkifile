/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package telegramplus.markers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.crash.FirebaseCrash;

import com.seyedekhorasani.teletorki.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


class About {
    static char buf[] = new char[1024];

    static String loadFileText(Context context, String filename) {
        try {
            StringBuffer fileData = new StringBuffer();
            final BufferedReader reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open(filename)));
            String line;
            while ( (line = reader.readLine()) != null ) {
                fileData.append(line);
            }
            return fileData.toString();
        } catch (IOException e) {
            FirebaseCrash.report(e);
            return null;
        }
    }

    static String getVersionString(final Activity activity) {
        String version = "";
        try {
            PackageInfo pi = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
            if (pi != null) {
                version = pi.versionName;
            }
        } catch (NameNotFoundException e) {
            FirebaseCrash.report(e);
            //pass
        }
        return version;
    }

	static void show(final MarkersActivity activity) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(null);
        builder.setCancelable(true);

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.about_box, null);

        TextView title = (TextView) layout.findViewById(R.id.title);
        Typeface light = Typeface.create("sans-serif-light", Typeface.NORMAL);
        title.setTypeface(light);
        title.setText(activity.getString(R.string.AppName) + " " + getVersionString(activity));


//        builder.setNegativeButton("Neat!", new OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }});
		builder.create().show();
	}
}
