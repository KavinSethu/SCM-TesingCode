// Generated code from Butter Knife. Do not modify!
package com.pnf.elar.app.activity.schedule;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ActivityListByDateActivity$$ViewBinder<T extends com.pnf.elar.app.activity.schedule.ActivityListByDateActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558835, "field 'activityRv'");
    target.activityRv = finder.castView(view, 2131558835, "field 'activityRv'");
    view = finder.findRequiredView(source, 2131559388, "field 'backbtnImage' and method 'onClick'");
    target.backbtnImage = finder.castView(view, 2131559388, "field 'backbtnImage'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558834, "field 'titleText'");
    target.titleText = finder.castView(view, 2131558834, "field 'titleText'");
    view = finder.findRequiredView(source, 2131559389, "field 'activityDateText'");
    target.activityDateText = finder.castView(view, 2131559389, "field 'activityDateText'");
    view = finder.findRequiredView(source, 2131559390, "field 'saveImage' and method 'onClick'");
    target.saveImage = finder.castView(view, 2131559390, "field 'saveImage'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
  }

  @Override public void unbind(T target) {
    target.activityRv = null;
    target.backbtnImage = null;
    target.titleText = null;
    target.activityDateText = null;
    target.saveImage = null;
  }
}
