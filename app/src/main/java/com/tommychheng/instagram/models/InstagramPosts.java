package com.tommychheng.instagram.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tchheng on 11/3/15.
 */
public class InstagramPosts implements Serializable {
    public List<InstagramPost> posts;

    public InstagramPosts(List<InstagramPost> p) {
        posts = p;
    }
}
