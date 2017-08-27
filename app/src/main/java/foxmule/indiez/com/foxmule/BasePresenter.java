package foxmule.indiez.com.foxmule;

/**
 * Created by ayushbagaria on 8/27/17.
 */

public interface BasePresenter <V> {

    void attachView(V view);
    void detachView();
    void rxUnsubscribe();
}
