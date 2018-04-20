// Generated code from Butter Knife. Do not modify!
package com.pnf.elar.app.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class PhotoGalleryActivity$$ViewBinder<T extends com.pnf.elar.app.activity.PhotoGalleryActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558893, "field 'pager'");
    target.pager = finder.castView(view, 2131558893, "field 'pager'");
    view = finder.findRequiredView(source, 2131558894, "field 'thumbnails'");
    target.thumbnails = finder.castView(view, 2131558894, "field 'thumbnails'");
    view = finder.findRequiredView(source, 2131558892, "field 'closeButton'");
    target.closeButton = finder.castView(view, 2131558892, "field 'closeButton'");
    view = finder.findRequiredView(source, 2131558890, "field 'back_btn'");
    target.back_btn = finder.castView(view, 2131558890, "field 'back_btn'");
  }

  @Override public void unbind(T target) {
    target.pager = null;
    target.thumbnails = null;
    target.closeButton = null;
    target.back_btn = null;
  }
}
