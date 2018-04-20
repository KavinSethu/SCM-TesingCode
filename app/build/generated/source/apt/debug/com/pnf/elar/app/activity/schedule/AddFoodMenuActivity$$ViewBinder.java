// Generated code from Butter Knife. Do not modify!
package com.pnf.elar.app.activity.schedule;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AddFoodMenuActivity$$ViewBinder<T extends com.pnf.elar.app.activity.schedule.AddFoodMenuActivity> implements ViewBinder<T> {
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
    view = finder.findRequiredView(source, 2131559389, "field 'headerNameText' and method 'onClick'");
    target.headerNameText = finder.castView(view, 2131559389, "field 'headerNameText'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131559390, "field 'saveImage'");
    target.saveImage = finder.castView(view, 2131559390, "field 'saveImage'");
    view = finder.findRequiredView(source, 2131558720, "field 'foodMenuRv'");
    target.foodMenuRv = finder.castView(view, 2131558720, "field 'foodMenuRv'");
    view = finder.findRequiredView(source, 2131559393, "field 'startHeaderText1'");
    target.startHeaderText1 = finder.castView(view, 2131559393, "field 'startHeaderText1'");
    view = finder.findRequiredView(source, 2131559394, "field 'startDateText1' and method 'onClick'");
    target.startDateText1 = finder.castView(view, 2131559394, "field 'startDateText1'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131559395, "field 'endDateHeaderText1'");
    target.endDateHeaderText1 = finder.castView(view, 2131559395, "field 'endDateHeaderText1'");
    view = finder.findRequiredView(source, 2131559396, "field 'endDateText1' and method 'onClick'");
    target.endDateText1 = finder.castView(view, 2131559396, "field 'endDateText1'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558722, "field 'uploadText'");
    target.uploadText = finder.castView(view, 2131558722, "field 'uploadText'");
    view = finder.findRequiredView(source, 2131558723, "field 'uploadIconImage' and method 'onClick'");
    target.uploadIconImage = finder.castView(view, 2131558723, "field 'uploadIconImage'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558724, "field 'uploadImage' and method 'onClick'");
    target.uploadImage = finder.castView(view, 2131558724, "field 'uploadImage'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558718, "field 'activityAddNotes'");
    target.activityAddNotes = finder.castView(view, 2131558718, "field 'activityAddNotes'");
    view = finder.findRequiredView(source, 2131558719, "field 'noFilesText'");
    target.noFilesText = finder.castView(view, 2131558719, "field 'noFilesText'");
    view = finder.findRequiredView(source, 2131558743, "field 'startLineView'");
    target.startLineView = view;
    view = finder.findRequiredView(source, 2131558744, "field 'endLineView'");
    target.endLineView = view;
    view = finder.findRequiredView(source, 2131558725, "field 'uploadFoodMenu' and method 'onClick'");
    target.uploadFoodMenu = finder.castView(view, 2131558725, "field 'uploadFoodMenu'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558726, "field 'tv_uploadFoodMenu'");
    target.tv_uploadFoodMenu = finder.castView(view, 2131558726, "field 'tv_uploadFoodMenu'");
    view = finder.findRequiredView(source, 2131558721, "field 'header_upload'");
    target.header_upload = finder.castView(view, 2131558721, "field 'header_upload'");
  }

  @Override public void unbind(T target) {
    target.backbtnImage = null;
    target.headerNameText = null;
    target.saveImage = null;
    target.foodMenuRv = null;
    target.startHeaderText1 = null;
    target.startDateText1 = null;
    target.endDateHeaderText1 = null;
    target.endDateText1 = null;
    target.uploadText = null;
    target.uploadIconImage = null;
    target.uploadImage = null;
    target.activityAddNotes = null;
    target.noFilesText = null;
    target.startLineView = null;
    target.endLineView = null;
    target.uploadFoodMenu = null;
    target.tv_uploadFoodMenu = null;
    target.header_upload = null;
  }
}
