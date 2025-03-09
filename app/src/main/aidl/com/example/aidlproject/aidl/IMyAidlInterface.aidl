// IMyAidlInterface.aidl
package com.example.aidlproject.aidl;

// Declare any non-default types here with import statements

interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void sendData(String data);
    String getData();
}