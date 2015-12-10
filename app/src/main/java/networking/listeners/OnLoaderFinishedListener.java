package networking.listeners;


import android.content.Loader;

import networking.models.HTTPModel;

/**
 * Created by noor on 04/05/15.
 */
public interface OnLoaderFinishedListener {

    void onLoaderFinished(Loader loader, HTTPModel httpModel);

}
