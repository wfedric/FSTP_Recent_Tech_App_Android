/**************************************************************
 * Created by William Fedric for Mastec Advanced Technologies *
 *                                                            *
 *                     December 2014                          *
 **************************************************************/
package com.mastec.fstprta;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

import static android.os.Environment.getExternalStorageDirectory;
import static android.widget.Toast.makeText;

public class FSTPsearch extends BroadcastReceiver {
    private static String lMod = null;
    private static String foundTech = null;
    private static String dateLogged = null;

    public void onReceive(Context context, Intent intent) {
        // makeText(context, com.mastec.fstprta.R.string.run_toast, Toast.LENGTH_SHORT).show();
        //makeSound.methodRun(context);

        File fstpLog = new File(String.valueOf(getExternalStorageDirectory()) + "/FSTP/LOGS/");
        if (fstpLog.exists()) {
            try {
                recentLog(context);
                runMatcher(context);
                runLogger(context);
                timeLogger(context);
            } catch (Exception e) {
                e.printStackTrace();
                makeText(context, "MASTEC ERROR: 0002", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "FSTP has not run yet.", Toast.LENGTH_SHORT).show();
        }
    }
    public static void SetAlarm(Context context) {
        Intent i = new Intent(context, FSTPsearch.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), 1000 * 60 * 30, pi);
    }
    private static File recentLog(Context recent) {
        File dir = new File(String.valueOf(Environment.getExternalStorageDirectory() + "/FSTP/LOGS/"));
        if (dir.exists()) {
            File[] files = dir.listFiles();
            {
                if (files.length == 0) return null;
                File lastModifiedFile = files[0];
                for (int i = 1; i < files.length; i++) {
                    if (lastModifiedFile.lastModified() < files[i].lastModified())
                        lastModifiedFile = files[i];
                }
                lMod = lastModifiedFile.getAbsolutePath();

            }
            return null;
        } else {
            Toast.makeText(recent, "Please Run FSTP!", Toast.LENGTH_SHORT).show();
        }
        return dir;
    }
    private static void runMatcher(Context matcher) throws IOException {
        final String searchTech = "DEBUG - MA";
        File logs = new File(lMod);
        try (Scanner scanner = new Scanner(logs)) {
            int s;
            String sTech;
            String s1 = scanner.next();
            String dTech;

            while (scanner.hasNextLine() && !s1.equals("exit")) {
                String line = scanner.nextLine();
                if (s1.equals(("exit"))) scanner.close();
                if (line.contains(searchTech)) {

                    s = line.indexOf(searchTech);
                    sTech = line.substring(s + 8, s + 18);
                    dTech = line.substring(1, 27);
                    foundTech = sTech;
                    dateLogged = dTech;
                }
                if (line.contains("DEBUG - null") && !(line.contains("DEBUG - MA") || line.contains("INFO - MA"))) {
                    foundTech = null;
                    dateLogged = null;
                }
            }
        } catch (IOException eM) {
            eM.printStackTrace();
            Toast.makeText(matcher, "Matcher IOException", Toast.LENGTH_LONG).show();
        }
    }
    private static String runLogger(Context logger) throws IOException, XmlPullParserException {

        File dir = new File(String.valueOf(getExternalStorageDirectory()));
        File log = new File(dir, "log.xml");

        final String fTech = foundTech;
        final String date = dateLogged;

        if (!log.exists()) log.createNewFile();
        if (fTech != null) {
            log.delete();
            log.createNewFile();
            try {
                FileOutputStream fosTrue;
                fosTrue = new FileOutputStream(log, true);
                XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
                parserFactory.setNamespaceAware(true);
                XmlSerializer xml = parserFactory.newSerializer();
                xml.setOutput(fosTrue, "UTF-8");
                xml.startDocument(null, true);
                xml.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                xml.startTag(null, "root");
                xml.startTag(null, "tech");
                xml.attribute(null, "foundTechs", "value");
                xml.text(fTech);
                xml.endTag(null, "tech");
                xml.startTag(null, "time");
                xml.attribute(null, "runTime", "timeValue");
                xml.text(date);
                xml.endTag(null, "time");
                xml.endTag(null, "root");
                xml.endDocument();
                xml.flush();
            } catch (IOException e) {
                e.printStackTrace();
                makeText(logger, "runLogger IOException", Toast.LENGTH_LONG).show();
            } catch (XmlPullParserException eX) {
                eX.printStackTrace();
                makeText(logger, "runLogger XML Exception", Toast.LENGTH_LONG).show();
            }
            return null;
        }
        return null;
    }
    private static String timeLogger(Context timeLogger) throws IOException {

        File dirTime = new File(String.valueOf(getExternalStorageDirectory()));
        File logTime = new File(dirTime, "logTime.xml");

        Calendar now = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
        String dateRun = String.valueOf(df.format(now.getTime()));
        FileOutputStream fosTime;

        if (!logTime.exists()) {
            logTime.createNewFile();
        }
        if (logTime.exists()) {
            logTime.delete();
            logTime.createNewFile();
            try {
                fosTime = new FileOutputStream(logTime, true);
                XmlPullParserFactory parserFactory2 = XmlPullParserFactory.newInstance();
                parserFactory2.setNamespaceAware(true);
                XmlSerializer xml2 = parserFactory2.newSerializer();
                xml2.setOutput(fosTime, "UTF-8");
                xml2.startDocument(null, true);
                xml2.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                xml2.startTag(null, "root");
                xml2.startTag(null, "scriptTime");
                xml2.attribute(null, "runVerify", "timeValue");
                xml2.text(dateRun);
                xml2.endTag(null, "scriptTime");
                xml2.endTag(null, "root");
                xml2.endDocument();
                xml2.flush();
                return fosTime.toString();
            } catch (IOException e) {
                e.printStackTrace();
                makeText(timeLogger, "timeLogger IOException", Toast.LENGTH_SHORT).show();
            } catch (XmlPullParserException eX) {
                eX.printStackTrace();
                makeText(timeLogger, "timeLogger XML Exception", Toast.LENGTH_SHORT).show();
            }
        }
        return null;
    }
}