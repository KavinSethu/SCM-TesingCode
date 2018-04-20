// Generated code from Butter Knife. Do not modify!
package com.pnf.elar.app.activity.schedule;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AddWholedaySickParentActivity$$ViewBinder<T extends com.pnf.elar.app.activity.schedule.AddWholedaySickParentActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558750, "field 'wholeDayText'");
    target.wholeDayText = finder.castView(view, 2131558750, "field 'wholeDayText'");
    view = finder.findRequiredView(source, 2131558751, "field 'closeSickImage'");
    target.closeSickImage = finder.castView(view, 2131558751, "field 'closeSickImage'");
    view = finder.findRequiredView(source, 2131558752, "field 'descLabelText'");
    target.descLabelText = finder.castView(view, 2131558752, "field 'descLabelText'");
    view = finder.findRequiredView(source, 2131558753, "field 'descriptionEditText'");
    target.descriptionEditText = finder.castView(view, 2131558753, "field 'descriptionEditText'");
    view = finder.findRequiredView(source, 2131558754, "field 'saveBtn' and method 'onClick'");
    target.saveBtn = finder.castView(view, 2131558754, "field 'saveBtn'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick();
        }
      });
    view = finder.findRequiredView(source, 2131558749, "field 'activityAddVaccationParent'");
    target.activityAddVaccationParent = finder.castView(view, 2131558749, "field 'activityAddVaccationParent'");
  }

  @Override public void unbind(T target) {
    target.wholeDayText = null;
    target.closeSickImage = null;
    target.descLabelText = null;
    target.descriptionEditText = null;
    target.saveBtn = null;
    target.activityAddVaccationParent = null;
  }
}
