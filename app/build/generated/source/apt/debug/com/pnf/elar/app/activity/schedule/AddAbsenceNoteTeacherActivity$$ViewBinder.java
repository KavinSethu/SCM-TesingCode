// Generated code from Butter Knife. Do not modify!
package com.pnf.elar.app.activity.schedule;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AddAbsenceNoteTeacherActivity$$ViewBinder<T extends com.pnf.elar.app.activity.schedule.AddAbsenceNoteTeacherActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558664, "field 'userheaderText'");
    target.userheaderText = finder.castView(view, 2131558664, "field 'userheaderText'");
    view = finder.findRequiredView(source, 2131558665, "field 'userNameText'");
    target.userNameText = finder.castView(view, 2131558665, "field 'userNameText'");
    view = finder.findRequiredView(source, 2131558667, "field 'fromDateLabel'");
    target.fromDateLabel = finder.castView(view, 2131558667, "field 'fromDateLabel'");
    view = finder.findRequiredView(source, 2131558668, "field 'fromDateText' and method 'onClick'");
    target.fromDateText = finder.castView(view, 2131558668, "field 'fromDateText'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558669, "field 'toDateLabel'");
    target.toDateLabel = finder.castView(view, 2131558669, "field 'toDateLabel'");
    view = finder.findRequiredView(source, 2131558670, "field 'toDateText' and method 'onClick'");
    target.toDateText = finder.castView(view, 2131558670, "field 'toDateText'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558666, "field 'dateLayout'");
    target.dateLayout = finder.castView(view, 2131558666, "field 'dateLayout'");
    view = finder.findRequiredView(source, 2131558671, "field 'leaveTypeLabel'");
    target.leaveTypeLabel = finder.castView(view, 2131558671, "field 'leaveTypeLabel'");
    view = finder.findRequiredView(source, 2131558672, "field 'leaveTypeSpinner'");
    target.leaveTypeSpinner = finder.castView(view, 2131558672, "field 'leaveTypeSpinner'");
    view = finder.findRequiredView(source, 2131558673, "field 'noteLabel'");
    target.noteLabel = finder.castView(view, 2131558673, "field 'noteLabel'");
    view = finder.findRequiredView(source, 2131558674, "field 'leaveNoteEditText'");
    target.leaveNoteEditText = finder.castView(view, 2131558674, "field 'leaveNoteEditText'");
    view = finder.findRequiredView(source, 2131558676, "field 'markAbsentChk'");
    target.markAbsentChk = finder.castView(view, 2131558676, "field 'markAbsentChk'");
    view = finder.findRequiredView(source, 2131558677, "field 'markAbsentText'");
    target.markAbsentText = finder.castView(view, 2131558677, "field 'markAbsentText'");
    view = finder.findRequiredView(source, 2131558675, "field 'markLayout'");
    target.markLayout = finder.castView(view, 2131558675, "field 'markLayout'");
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
    view = finder.findRequiredView(source, 2131558680, "field 'btnForSend' and method 'onClick'");
    target.btnForSend = finder.castView(view, 2131558680, "field 'btnForSend'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558678, "field 'buttonLayout'");
    target.buttonLayout = finder.castView(view, 2131558678, "field 'buttonLayout'");
    view = finder.findRequiredView(source, 2131558663, "field 'activityAddAbsenceNote'");
    target.activityAddAbsenceNote = finder.castView(view, 2131558663, "field 'activityAddAbsenceNote'");
  }

  @Override public void unbind(T target) {
    target.userheaderText = null;
    target.userNameText = null;
    target.fromDateLabel = null;
    target.fromDateText = null;
    target.toDateLabel = null;
    target.toDateText = null;
    target.dateLayout = null;
    target.leaveTypeLabel = null;
    target.leaveTypeSpinner = null;
    target.noteLabel = null;
    target.leaveNoteEditText = null;
    target.markAbsentChk = null;
    target.markAbsentText = null;
    target.markLayout = null;
    target.btnForCancel = null;
    target.btnForSend = null;
    target.buttonLayout = null;
    target.activityAddAbsenceNote = null;
  }
}
