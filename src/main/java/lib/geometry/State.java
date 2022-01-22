package lib.geometry;

import lib.util.Interpolable;

public interface State<S> extends Interpolable<S>{
    double distance(final S other);

    boolean equals(final Object other);

}