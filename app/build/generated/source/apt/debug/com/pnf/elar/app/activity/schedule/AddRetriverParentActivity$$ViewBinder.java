// Generated code from Butter Knife. Do not modify!
package com.pnf.elar.app.activity.schedule;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AddRetriverParentActivity$$ViewBinder<T extends com.pnf.elar.app.activity.schedule.AddRetriverParentActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558625, "field 'txtForUserName'");
    target.txtForUserName = finder.castView(view, 2131558625, "field 'txtForUserName'");
    view = finder.findRequiredView(source, 2131559037, "field 'Date'");
    target.Date = finder.castView(view, 2131559037, "field 'Date'");
    view = finder.findRequiredView(source, 2131559050, "field 'txtForDateFrom' and method 'onClick'");
    target.txtForDateFrom = finder.castView(view, 2131559050, "field 'txtForDateFrom'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131559039, "field 'To'");
    target.To = finder.castView(view, 2131559039, "field 'To'");
    view = finder.findRequiredView(source, 2131559040, "field 'txtForToDate' and method 'onClick'");
    target.txtForToDate = finder.castView(view, 2131559040, "field 'txtForToDate'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131559036, "field 'layoutForEdts'");
    target.layoutForEdts = finder.castView(view, 2131559036, "field 'layoutForEdts'");
    view = finder.findRequiredView(source, 2131559043, "field 'txt1'");
    target.txt1 = finder.castView(view, 2131559043, "field 'txt1'");
    view = finder.findRequiredView(source, 2131559052, "field 'edtForRname'");
    target.edtForRname = finder.castView(view, 2131559052, "field 'edtForRname'");
    view = finder.findRequiredView(source, 2131558658, "field 'txt2'");
    target.txt2 = finder.castView(view, 2131558658, "field 'txt2'");
    view = finder.findRequiredView(source, 2131559053, "field 'edtForContact'");
    target.edtForContact = finder.castView(view, 2131559053, "field 'edtForContact'");
    view = finder.findRequiredView(source, 2131559054, "field 'Portrait'");
    target.Portrait = finder.castView(view, 2131559054, "field 'Portrait'");
    view = finder.findRequiredView(source, 2131559055, "field 'profileImage' and method 'onClick'");
    target.profileImage = finder.castView(view, 2131559055, "field 'profileImage'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131559051, "field 'layout1'");
    target.layout1 = finder.castView(view, 2131559051, "field 'layout1'");
    view = finder.findRequiredView(source, 2131559056, "field 'txt3'");
    target.txt3 = finder.castView(view, 2131559056, "field 'txt3'");
    view = finder.findRequiredView(source, 2131559044, "field 'edtForNotes'");
    target.edtForNotes = finder.castView(view, 2131559044, "field 'edtForNotes'");
    view = finder.findRequiredView(source, 2131558836, "field 'layout3'");
    target.layout3 = finder.castView(view, 2131558836, "field 'layout3'");
    view = finder.findRequiredView(source, 2131559057, "field 'btnForSave' and method 'onClick'");
    target.btnForSave = finder.castView(view, 2131559057, "field 'btnForSave'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558679, "field 'btnForCancel' and method 'onClick'");
    target.btnForCancel = finder.castView(view, 2131558679, "field 'btnForCancel'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131559049, "field 'retrivalNote'");
    target.retrivalNote = finder.castView(view, 2131559049, "field 'retrivalNote'");
    view = finder.findRequiredView(source, 2131558745, "field 'activityAddRetriverParent'");
    target.activityAddRetriverParent = finder.castView(view, 2131558745, "field 'activityAddRetriverParent'");
    view = finder.findRequiredView(source, 2131558746, "field 'removeRetBtn' and method 'onClick'");
    target.removeRetBtn = finder.castView(view, 2131558746, "field 'removeRetBtn'");
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
    target.txtForUserName = null;
    target.Date = null;
    target.txtForDateFrom = null;
    target.To = null;
    target.txtForToDate = null;
    target.layoutForEdts = null;
    target.txt1 = null;
    target.edtForRname = null;
    target.txt2 = null;
    target.edtForContact = null;
    target.Portrait = null;
    target.profileImage = null;
    target.layout1 = null;
    target.txt3 = null;
    target.edtForNotes = null;
    target.layout3 = null;
    target.btnForSave = null;
    target.btnForCancel = null;
    target.retrivalNote = null;
    target.activityAddRetriverParent = null;
    target.removeRetBtn = null;
  }
}
