// Generated code from Butter Knife. Do not modify!
package com.pnf.elar.app.activity.schedule;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ViewAbsentNoteActivity$$ViewBinder<T extends com.pnf.elar.app.activity.schedule.ViewAbsentNoteActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131559023, "field 'closeAbsImage' and method 'onClick'");
    target.closeAbsImage = finder.castView(view, 2131559023, "field 'closeAbsImage'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131559024, "field 'headerAbsText'");
    target.headerAbsText = finder.castView(view, 2131559024, "field 'headerAbsText'");
    view = finder.findRequiredView(source, 2131559025, "field 'nameAbsText'");
    target.nameAbsText = finder.castView(view, 2131559025, "field 'nameAbsText'");
    view = finder.findRequiredView(source, 2131559026, "field 'desclabelText'");
    target.desclabelText = finder.castView(view, 2131559026, "field 'desclabelText'");
    view = finder.findRequiredView(source, 2131559027, "field 'descAbsText'");
    target.descAbsText = finder.castView(view, 2131559027, "field 'descAbsText'");
    view = finder.findRequiredView(source, 2131559029, "field 'writtenLabelText'");
    target.writtenLabelText = finder.castView(view, 2131559029, "field 'writtenLabelText'");
    view = finder.findRequiredView(source, 2131559028, "field 'writtenText'");
    target.writtenText = finder.castView(view, 2131559028, "field 'writtenText'");
    view = finder.findRequiredView(source, 2131559022, "field 'activityViewAbsentNote'");
    target.activityViewAbsentNote = finder.castView(view, 2131559022, "field 'activityViewAbsentNote'");
    view = finder.findRequiredView(source, 2131559030, "field 'deleteImage' and method 'onClick'");
    target.deleteImage = finder.castView(view, 2131559030, "field 'deleteImage'");
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
    target.closeAbsImage = null;
    target.headerAbsText = null;
    target.nameAbsText = null;
    target.desclabelText = null;
    target.descAbsText = null;
    target.writtenLabelText = null;
    target.writtenText = null;
    target.activityViewAbsentNote = null;
    target.deleteImage = null;
  }
}
