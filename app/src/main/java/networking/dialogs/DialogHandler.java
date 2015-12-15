package networking.dialogs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;

/**
 * Created by noor on 04/05/15.
 */
public class DialogHandler {


    private Context mContext;
    private ProgressDialog mProgressDialog;

    public DialogHandler(@NonNull Context mContext) {
        this.mContext = mContext;
    }

    public void showProgressDialog(DialogParams dialogParams) {
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage(dialogParams.getMessage());
        mProgressDialog.setCancelable(dialogParams.isCancellable());
        mProgressDialog.setIndeterminate(dialogParams.isIndeterminate());
        mProgressDialog.setCanceledOnTouchOutside(dialogParams.isCanceledOnTouchOutside());
        mProgressDialog.setOnCancelListener(dialogParams.getOnCancelListener());
//        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialog) {
//                ((Activity)mContext).getLoaderManager().destroyLoader(6);
//            }
//        });
        mProgressDialog.show();
    }

    public void showDefaultProgressDialog(DialogInterface.OnCancelListener onCancelListener ){
        DialogParams dialogParams = new DialogParams();
        dialogParams.setOnCancelListener(onCancelListener);
        showProgressDialog(dialogParams);
    }

    public void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
