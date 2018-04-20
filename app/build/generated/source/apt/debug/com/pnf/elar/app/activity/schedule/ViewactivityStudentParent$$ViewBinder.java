// Generated code from Butter Knife. Do not modify!
package com.pnf.elar.app.activity.schedule;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ViewactivityStudentParent$$ViewBinder<T extends com.pnf.elar.app.activity.schedule.ViewactivityStudentParent> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
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
    view = finder.findRequiredView(source, 2131559389, "field 'headerNameText'");
    target.headerNameText = finder.castView(view, 2131559389, "field 'headerNameText'");
    view = finder.findRequiredView(source, 2131559390, "field 'saveImage'");
    target.saveImage = finder.castView(view, 2131559390, "field 'saveImage'");
    view = finder.findRequiredView(source, 2131558741, "field 'childrenHeaderText'");
    target.childrenHeaderText = finder.castView(view, 2131558741, "field 'childrenHeaderText'");
    view = finder.findRequiredView(source, 2131558742, "field 'childrenRv'");
    target.childrenRv = finder.castView(view, 2131558742, "field 'childrenRv'");
    view = finder.findRequiredView(source, 2131558706, "field 'startHeaderText'");
    target.startHeaderText = finder.castView(view, 2131558706, "field 'startHeaderText'");
    view = finder.findRequiredView(source, 2131558707, "field 'startDateText' and method 'onClick'");
    target.startDateText = finder.castView(view, 2131558707, "field 'startDateText'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558708, "field 'startTimeHeaderText'");
    target.startTimeHeaderText = finder.castView(view, 2131558708, "field 'startTimeHeaderText'");
    view = finder.findRequiredView(source, 2131558709, "field 'startTimeText' and method 'onClick'");
    target.startTimeText = finder.castView(view, 2131558709, "field 'startTimeText'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558710, "field 'endDateHeaderText'");
    target.endDateHeaderText = finder.castView(view, 2131558710, "field 'endDateHeaderText'");
    view = finder.findRequiredView(source, 2131558711, "field 'endDateText' and method 'onClick'");
    target.endDateText = finder.castView(view, 2131558711, "field 'endDateText'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558712, "field 'endTimeHeaderText'");
    target.endTimeHeaderText = finder.castView(view, 2131558712, "field 'endTimeHeaderText'");
    view = finder.findRequiredView(source, 2131558713, "field 'endTimeText' and method 'onClick'");
    target.endTimeText = finder.castView(view, 2131558713, "field 'endTimeText'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558705, "field 'startEndDateLayout'");
    target.startEndDateLayout = finder.castView(view, 2131558705, "field 'startEndDateLayout'");
    view = finder.findRequiredView(source, 2131558714, "field 'scheduleText'");
    target.scheduleText = finder.castView(view, 2131558714, "field 'scheduleText'");
    view = finder.findRequiredView(source, 2131558715, "field 'scheduleEditText'");
    target.scheduleEditText = finder.castView(view, 2131558715, "field 'scheduleEditText'");
    view = finder.findRequiredView(source, 2131558716, "field 'descripText'");
    target.descripText = finder.castView(view, 2131558716, "field 'descripText'");
    view = finder.findRequiredView(source, 2131558717, "field 'descripEditText'");
    target.descripEditText = finder.castView(view, 2131558717, "field 'descripEditText'");
    view = finder.findRequiredView(source, 2131558744, "field 'endLineView'");
    target.endLineView = view;
    view = finder.findRequiredView(source, 2131558743, "field 'startLineView'");
    target.startLineView = view;
  }

  @Override public void unbind(T target) {
    target.backbtnImage = null;
    target.headerNameText = null;
    target.saveImage = null;
    target.childrenHeaderText = null;
    target.childrenRv = null;
    target.startHeaderText = null;
    target.startDateText = null;
    target.startTimeHeaderText = null;
    target.startTimeText = null;
    target.endDateHeaderText = null;
    target.endDateText = null;
    target.endTimeHeaderText = null;
    target.endTimeText = null;
    target.startEndDateLayout = null;
    target.scheduleText = null;
    target.scheduleEditText = null;
    target.descripText = null;
    target.descripEditText = null;
    target.endLineView = null;
    target.startLineView = null;
  }
}
