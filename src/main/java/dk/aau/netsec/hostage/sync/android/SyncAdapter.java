package dk.aau.netsec.hostage.sync.android;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SyncResult;
import android.os.Bundle;
import android.preference.PreferenceManager;

import java.util.ArrayList;

import dk.aau.netsec.hostage.HostageApplication;
import dk.aau.netsec.hostage.logging.DaoSession;
import dk.aau.netsec.hostage.logging.RecordAll;
import dk.aau.netsec.hostage.persistence.DAO.DAOHelper;


/**
 * Created by abrakowski
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {
    public static final String TAG = "SyncAdapter";
    private final SharedPreferences pref;
    private final SharedPreferences.Editor editor;
    private final DaoSession dbSession;
    private final DAOHelper daoHelper;
    private ArrayList<RecordAll> records = new ArrayList<>();
    private static int offset=0;
    private int limit=50;

    private Context context;

    public SyncAdapter(Context context, boolean autoInitialize) {
        this(context, autoInitialize, false);
    }

    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        this.context = context;

        pref = PreferenceManager.getDefaultSharedPreferences(context);
        editor = pref.edit();
        dbSession = HostageApplication.getInstances().getDaoSession();
        daoHelper = new DAOHelper(dbSession,getContext());
    }

    /**
     * Called by the Android system in response to a request to run the sync adapter. The work
     * required to read data from the network, parse it, and store it in the content provider is
     * done here. Extending AbstractThreadedSyncAdapter ensures that all methods within SyncAdapter
     * run on a background thread. For this reason, blocking I/O and other long-running tasks can be
     * run <em>in situ</em>, and you don't have to set up a separate thread for them.
     .
     *
     * <p>This is where we actually perform any work required to perform a sync.
     * {@link AbstractThreadedSyncAdapter} guarantees that this will be called on a non-UI thread,
     * so it is safe to peform blocking I/O here.
     *
     * <p>The syncResult argument allows you to pass information back to the method that triggered
     * the sync.
     */
@Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        // We will only do a one-direction sync. That is, only sending data to the tracing server.
        // For bidirectional syncing, we will still have to decide how much data should be requested from the server,
        // because the server has relatively to a mobile device almost unlimited space.
        //TODO temporary remove tracemonitoring
//        Log.i(TAG, "Beginning network synchronization");
//
//        long lastSyncTime = pref.getLong("LAST_SYNC_TIME", 0);
//
//        String serverAddress = pref.getString("pref_upload_server", "https://www.tracingmonitor.org"); //"https://192.168.1.118:9999"
//
//        Log.i(TAG, "Connecting to " + serverAddress);
//        LogFilter filter = new LogFilter();
//        filter.setAboveTimestamp(lastSyncTime);
//        //TODO recordCound for performance
//        records = this.daoHelper.getAttackRecordDAO().getRecordsForFilter(filter, offset);
//
//        StringWriter writer = new StringWriter();
//
//        int size = records.size();
//        int offset = 1;
//        int currOffset = 1;
//        boolean error = false;
//        for (RecordAll record : records) {
//            SyncUtils.appendRecordToStringWriter(record, writer);
//
//            if(currOffset == 100 || offset == size){
//                boolean success = SyncUtils.uploadRecordsToServer(writer.toString(), serverAddress);
//                if(success){
//                    syncResult.stats.numIoExceptions++;
//                    error =  true;
//                    break;
//                }
//
//                writer.getBuffer().setLength(0);
//                currOffset = 0;
//            }
//
//            offset++;
//            currOffset++;
//        }
//
//        if(!error) pref.edit().putLong("LAST_SYNC_TIME", System.currentTimeMillis()).apply();
//        Log.i(TAG, "Network synchronization complete");
    }
}
