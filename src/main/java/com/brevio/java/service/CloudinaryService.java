package com.brevio.java.service;

import com.brevio.java.listener.CloudinaryListener;

public interface CloudinaryService {

    void upload(byte[] file, CloudinaryListener listener, String path);

}
