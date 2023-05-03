//Implemented Template Pattern:
package test;

public abstract class Observer {
    public final void update() {
        // step 1: prepare
        prepare();

        // step 2: update
        updateObserver();

        // step 3: cleanup
        cleanup();
    }

    protected abstract void updateObserver();

    protected void prepare() {
        // default implementation
    }

    protected void cleanup() {
        // default implementation
    }
}


