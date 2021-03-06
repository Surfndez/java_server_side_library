package com.gsma.mobileconnect.r2.cache;

import com.gsma.mobileconnect.r2.discovery.SessionData;
import com.gsma.mobileconnect.r2.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionCache extends ConcurrentCache {
    protected SessionCache(Builder builder) {
        super(builder);
    }
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionCache.class);

    public SessionData get(String key) {
        SessionData sessionData = null;
        if (!hasKey(key)) {
            return null;
        }
        try {
            sessionData = this.get(key, SessionData.class);
        } catch (CacheAccessException e) {
            LOGGER.warn(e.getMessage());
        }
        this.remove(key);

        return sessionData;
    }

    @Override
    protected boolean hasKey(String key) {
        try {
            return this.get(key, SessionData.class) != null;
        } catch (CacheAccessException e) {
            LOGGER.warn(e.getMessage());
            return false;
        }
    }

    public static final class Builder extends ConcurrentCache.Builder {

        @Override
        public ConcurrentCache build() {
            ObjectUtils.requireNonNull(this.jsonService, "jsonService");
            return new SessionCache(this);
        }
    }
}
