package com.example.project02_group09_mohammed_diksha_fidel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

// Utility class to get the value from a LiveData object synchronously in tests.
public class LiveDataTestUtil {

    // Gets the value from a LiveData object, waiting max 2 seconds.
    public static <T> T getValue(final LiveData<T> liveData) throws InterruptedException {
        final Object[] data = new Object[1];
        final CountDownLatch latch = new CountDownLatch(1);

        Observer<T> observer = new Observer<T>() {
            @Override
            public void onChanged(T o) {
                data[0] = o;
                latch.countDown();
                liveData.removeObserver(this);
            }
        };

        liveData.observeForever(observer);

        // Wait for the data to be set
        latch.await(2, TimeUnit.SECONDS);

        //noinspection unchecked
        return (T) data[0];
    }
}