// Generated code from Butter Knife. Do not modify!
package com.pnf.elar.app.activity.schedule;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class FoodNoteActivity$$ViewBinder<T extends com.pnf.elar.app.activity.schedule.FoodNoteActivity> implements ViewBinder<T> {
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
    view = finder.findRequiredView(source, 2131558729, "field 'foodNoteDateLabelText'");
    target.foodNoteDateLabelText = finder.castView(view, 2131558729, "field 'foodNoteDateLabelText'");
    view = finder.findRequiredView(source, 2131558730, "field 'foodnoteDatetext' and method 'onClick'");
    target.foodnoteDatetext = finder.castView(view, 2131558730, "field 'foodnoteDatetext'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558732, "field 'fnAttendanceText'");
    target.fnAttendanceText = finder.castView(view, 2131558732, "field 'fnAttendanceText'");
    view = finder.findRequiredView(source, 2131558735, "field 'grpLabelText'");
    target.grpLabelText = finder.castView(view, 2131558735, "field 'grpLabelText'");
    view = finder.findRequiredView(source, 2131558737, "field 'allGrpRadio'");
    target.allGrpRadio = finder.castView(view, 2131558737, "field 'allGrpRadio'");
    view = finder.findRequiredView(source, 2131558738, "field 'myGrpRadio'");
    target.myGrpRadio = finder.castView(view, 2131558738, "field 'myGrpRadio'");
    view = finder.findRequiredView(source, 2131558736, "field 'fnRadioGroup'");
    target.fnRadioGroup = finder.castView(view, 2131558736, "field 'fnRadioGroup'");
    view = finder.findRequiredView(source, 2131558739, "field 'allergiesLabelText'");
    target.allergiesLabelText = finder.castView(view, 2131558739, "field 'allergiesLabelText'");
    view = finder.findRequiredView(source, 2131558740, "field 'allergiesRv'");
    target.allergiesRv = finder.castView(view, 2131558740, "field 'allergiesRv'");
    view = finder.findRequiredView(source, 2131558733, "field 'scheduledText'");
    target.scheduledText = finder.castView(view, 2131558733, "field 'scheduledText'");
    view = finder.findRequiredView(source, 2131558734, "field 'currentlyText'");
    target.currentlyText = finder.castView(view, 2131558734, "field 'currentlyText'");
  }

  @Override public void unbind(T target) {
    target.backbtnImage = null;
    target.headerNameText = null;
    target.saveImage = null;
    target.foodNoteDateLabelText = null;
    target.foodnoteDatetext = null;
    target.fnAttendanceText = null;
    target.grpLabelText = null;
    target.allGrpRadio = null;
    target.myGrpRadio = null;
    target.fnRadioGroup = null;
    target.allergiesLabelText = null;
    target.allergiesRv = null;
    target.scheduledText = null;
    target.currentlyText = null;
  }
}
