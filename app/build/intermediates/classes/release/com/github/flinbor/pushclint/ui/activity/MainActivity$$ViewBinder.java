// Generated code from Butter Knife. Do not modify!
package com.github.flinbor.pushclint.ui.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MainActivity$$ViewBinder<T extends com.github.flinbor.pushclint.ui.activity.MainActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558486, "field 'progress'");
    target.progress = view;
    view = finder.findRequiredView(source, 2131558489, "field 'imageViewPushStatus'");
    target.imageViewPushStatus = finder.castView(view, 2131558489, "field 'imageViewPushStatus'");
    view = finder.findRequiredView(source, 2131558491, "field 'textViewLog'");
    target.textViewLog = finder.castView(view, 2131558491, "field 'textViewLog'");
    view = finder.findRequiredView(source, 2131558487, "field 'editTextPush'");
    target.editTextPush = finder.castView(view, 2131558487, "field 'editTextPush'");
    view = finder.findRequiredView(source, 2131558490, "field 'scroll'");
    target.scroll = finder.castView(view, 2131558490, "field 'scroll'");
    view = finder.findRequiredView(source, 2131558488, "method 'onClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick();
        }
      });
  }

  @Override public void unbind(T target) {
    target.progress = null;
    target.imageViewPushStatus = null;
    target.textViewLog = null;
    target.editTextPush = null;
    target.scroll = null;
  }
}
