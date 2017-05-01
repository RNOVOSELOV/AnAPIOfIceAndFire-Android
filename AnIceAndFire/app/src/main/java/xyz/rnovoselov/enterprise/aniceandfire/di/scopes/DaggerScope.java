package xyz.rnovoselov.enterprise.aniceandfire.di.scopes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by roman on 01.05.17.
 */

@Scope
@Retention(RetentionPolicy.SOURCE)
public @interface DaggerScope {
    Class<?> value();
}
