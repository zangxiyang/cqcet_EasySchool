package com.imsle.cqceteasayschool.model;

import java.io.IOException;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

@ToString
@Getter
@Setter
public class UserLogin extends RequestBody {
    private String username;
    private String password;

    @Override
    public MediaType contentType() {
        return null;
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {

    }
}
