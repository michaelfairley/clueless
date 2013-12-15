package com.m12y.ld28.html;

import com.m12y.ld28.core.Clueless;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class LD28Html extends GwtApplication {
    @Override
    public ApplicationListener getApplicationListener () {
        return new Clueless();
    }

    @Override
    public GwtApplicationConfiguration getConfig () {
        return new GwtApplicationConfiguration(Clueless.WIDTH, Clueless.HEIGHT);
    }
}
