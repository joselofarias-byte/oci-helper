package com.yohann.ocihelper.service;

import org.junit.jupiter.api.Test;
import java.time.*;
import static org.junit.jupiter.api.Assertions.*;

class IpSecurityLockoutTest {
    private static class MutableClock extends Clock {
        long millis = 1_000_000L;
        public ZoneId getZone() { return ZoneOffset.UTC; }
        public Clock withZone(ZoneId zone) { return this; }
        public Instant instant() { return Instant.ofEpochMilli(millis); }
        public long millis() { return millis; }
    }
    private void failFiveTimes(IpSecurityService service) {
        for (int i=0;i<4;i++) assertFalse(service.recordLoginFailure("172.18.0.1"));
        assertTrue(service.recordLoginFailure("172.18.0.1"));
    }
    @Test void automaticBlockExpiresWithoutRestart() {
        MutableClock clock = new MutableClock();IpSecurityService service = new IpSecurityService(clock);
        failFiveTimes(service);assertFalse(service.isIpAllowed("172.18.0.1"));assertEquals(60,service.getRetryAfterSeconds("172.18.0.1"));
        clock.millis += 59_999;assertFalse(service.isIpAllowed("172.18.0.1"));
        clock.millis++;assertTrue(service.isIpAllowed("172.18.0.1"));assertEquals(0,service.getRetryAfterSeconds("172.18.0.1"));
        assertTrue(service.getAllBlacklistEntries().isEmpty());
    }
    @Test void failedRequestsDuringBlockDoNotExtendIt() {
        MutableClock clock = new MutableClock();IpSecurityService service = new IpSecurityService(clock);
        failFiveTimes(service);clock.millis+=30_000;assertFalse(service.recordLoginFailure("172.18.0.1"));
        clock.millis+=30_000;assertTrue(service.isIpAllowed("172.18.0.1"));
    }
    @Test void manualBlacklistSurvivesAutomaticExpiry() {
        MutableClock clock = new MutableClock();IpSecurityService service = new IpSecurityService(clock);
        service.addToBlacklist("192.0.2.0/24");clock.millis+=120_000;
        assertFalse(service.isIpAllowed("192.0.2.8"));assertTrue(service.removeFromBlacklist("192.0.2.0/24"));assertTrue(service.isIpAllowed("192.0.2.8"));
    }
    @Test void successfulLoginResetsTheFailureCount() {
        IpSecurityService service = new IpSecurityService();for(int i=0;i<4;i++)service.recordLoginFailure("172.18.0.1");
        service.clearLoginFailures("172.18.0.1");assertFalse(service.recordLoginFailure("172.18.0.1"));assertTrue(service.isIpAllowed("172.18.0.1"));
    }
}
