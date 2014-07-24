package com.unleashurgeek.lcsapp.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

public class ImageCache implements ComponentCallbacks2, Serializable {
	private static final long serialVersionUID = -3758978804255067014L;
	
	private ImageLruCache cache;
	
	public ImageCache() {
		// Makes the cache half the size of the available memory.
		int cacheSize = (int)(Runtime.getRuntime().maxMemory() / 1024);
		cache = new ImageLruCache(cacheSize);
	}
	
	public Bitmap getImageAsync(String url) {
		if (url == null)
			return null;
		Bitmap image = cache.get(url);
		if (image == null)
			try {
				image = new ImageDownloadTask(url).execute().get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		return image;
	}
	
	public Bitmap getImage(String url) {
		if (url == null)
			return null;
		Bitmap image = cache.get(url);
		if (image == null) {
			try {
				InputStream in = new URL(url).openStream();
				image = BitmapFactory.decodeStream(in);
				image = ThumbnailUtils.extractThumbnail(image, 128, 128);
				cache.put(url, image);
			} catch (Exception e) {
				Log.e("Error", "Failed to get image from " + url);
				e.printStackTrace();
			}
		}
		return image;		
	}
	
	public void setImageView(String url, ImageView imageView) {
		if (url == null)
			return;
		Bitmap image = cache.get(url);
		if (image != null)
			imageView.setImageBitmap(image);
		else
			new ImageDownloadTask(url, imageView).execute();
	}
	
	public LruCache<String, Bitmap> getCache() {
		return cache;
	}
	
	public void onConfigurationChanged(Configuration newConfig) { }

	public void onLowMemory() {
		cache.evictAll();
	}

	public void onTrimMemory(int level) {
		if (level == TRIM_MEMORY_MODERATE)
			cache.trimToSize(cache.size() / 2);
	}
	
	private class ImageLruCache extends LruCache<String, Bitmap> {
		public ImageLruCache(int cacheSize) {
			super(cacheSize);
		}
	}
	
	private class ImageDownloadTask extends AsyncTask<String, Void, Bitmap> {
		ImageView imageView = null;
		String url = null;
		
		public ImageDownloadTask(String url) {
			this.url = url;
		}
		
		public ImageDownloadTask(String url, ImageView image) {
			this.imageView = image;
			this.url = url;
		}
		
		@Override
		protected Bitmap doInBackground(String... params) {
			Bitmap bmp = null;
			InputStream in = null;
			try {
				in = new URL(url).openStream();
				bmp = BitmapFactory.decodeStream(in);
				bmp = ThumbnailUtils.extractThumbnail(bmp, 128, 128);
			} catch (Exception e) {
				Log.e("Error", "Failed to get image from " + url);
				e.printStackTrace();
			} finally {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return bmp;
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {
			cache.put(url, result);
			if (imageView != null)
				imageView.setImageBitmap(result);
		}
		
	}
}