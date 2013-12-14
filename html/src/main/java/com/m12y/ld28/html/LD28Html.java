package com.m12y.ld28.html;

import com.m12y.ld28.core.LD28;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class LD28Html extends GwtApplication {
    @Override
    public ApplicationListener getApplicationListener () {
        return new LD28();
    }

    @Override
    public GwtApplicationConfiguration getConfig () {
        return new GwtApplicationConfiguration(LD28.WIDTH, LD28.HEIGHT);
    }
}
