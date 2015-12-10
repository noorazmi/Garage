package com.arsalan.garage.adapters;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;

import com.arsalan.garage.R;
import com.arsalan.garage.models.ImageInfo;
import com.arsalan.garage.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * <p/>
 * Created by: Noor  Alam on 07/11/15.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class GalleryImageAdapter extends BaseAdapter {

    private Context mContext;
    private int IMAGE_WIDTH = 120;
    private int IMAGE_HEIGHT = 160;

    private List<ImageInfo> mProductImagesList;

    public GalleryImageAdapter(Context c, List<ImageInfo> productImagesList) {
        mContext = c;
        mProductImagesList = productImagesList;
    }

    public int getCount() {
        return mProductImagesList.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(R.mipmap.ic_launcher);
        imageView.setAdjustViewBounds(true);
        imageView.setLayoutParams(new Gallery.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        Uri ImageUrl = Uri.parse(mProductImagesList.get(position).getPhoto_name());
        ImageLoader imageLoader = ImageLoader.getInstance();
        if(!TextUtils.isEmpty(ImageUrl.toString())){
            imageLoader.displayImage(ImageUrl.toString(), imageView, Utils.gerDisplayImageOptions());
        }
        Gallery.LayoutParams lp = new Gallery.LayoutParams(IMAGE_WIDTH, IMAGE_HEIGHT);
        imageView.setLayoutParams(lp);

        return imageView;
    }


}
