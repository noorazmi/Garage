package networking.dialogs;

import android.app.ProgressDialog;
import android.content.Context;
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

    public DialogParams showProgressDialog(DialogParams dialogParams) {
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage(dialogParams.getMessage());
        mProgressDialog.setCancelable(dialogParams.isCancellable());
        mProgressDialog.setIndeterminate(dialogParams.isIndeterminate());
        mProgressDialog.setCanceledOnTouchOutside(dialogParams.isCanceledOnTouchOutside());
        mProgressDialog.setOnCancelListener(dialogParams.getOnCancelListener());
        mProgressDialog.show();
        return dialogParams;
    }

    public void showDefaultProgressDialog(){
        showProgressDialog(new DialogParams());
    }

    public void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
