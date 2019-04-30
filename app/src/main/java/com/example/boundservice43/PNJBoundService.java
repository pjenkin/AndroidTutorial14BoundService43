package com.example.boundservice43;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/* Activity will (1) go to onBind (2) that'll return an instance of local class
* (3) local class instance will give reference to this Bound Service class
* Activity can then use the BoundService to do whatever
*/

public class PNJBoundService extends Service {

    private final IBinder pnjBinder = new PNJLocalBinder();

    // similar to how it was with Service, must have (1) constructor (2) onBind implemented
    public PNJBoundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");     // boilerplate
        return pnjBinder;
    }


    // must define own class subclassing Binder -
    // NB nested class inside Bound Service class, hence 'local'
    // could perhaps be in another file
    public class PNJLocalBinder extends Binder{
        PNJBoundService getService()
        {
            return PNJBoundService.this;
            // return reference to Bound Service class, for use within Bound Class itself (!!?)
        }
    }

    public String getCurrentTime()
    {
        SimpleDateFormat dateformat = new SimpleDateFormat("HH:mm:ss", Locale.UK);
        return ( dateformat.format( new Date() ) );
    }

}

