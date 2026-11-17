package com.yohann.ocihelper.security;
import com.yohann.ocihelper.config.StartupSecurityGuard;
import com.yohann.ocihelper.config.auth.AuthInterceptor;
import com.yohann.ocihelper.config.ws.LogWebSocketHandler;
import com.yohann.ocihelper.exception.OciException;
import com.yohann.ocihelper.service.IpSecurityService;
import com.yohann.ocihelper.utils.CommonUtils;
import com.yohann.ocihelper.utils.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.socket.*;
import java.net.URI;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Proxy;
import java.util.concurrent.atomic.AtomicReference;

class AccessSecurityTest {
    private static final String KEY="test-signing-key-with-at-least-32-characters";
    private AuthInterceptor auth(){AuthInterceptor a=new AuthInterceptor();ReflectionTestUtils.setField(a,"jwtSecret",KEY);ReflectionTestUtils.setField(a,"ipSecurityService",new IpSecurityService());return a;}
    @Test void blocksMissingApiToken(){assertThrows(OciException.class,()->auth().preHandle(new MockHttpServletRequest("POST","/api/oci/list"),new MockHttpServletResponse(),new Object()));}
    @Test void allowsSignedApiToken() throws Exception {
        MockHttpServletRequest r=new MockHttpServletRequest("POST","/api/oci/list");r.addHeader("Authorization","Bearer "+JwtUtils.genToken(Map.of("account","a"),KEY,3));
        assertTrue(auth().preHandle(r,new MockHttpServletResponse(),new Object()));
    }
    @Test void allowsLoginWithoutToken() throws Exception {assertTrue(auth().preHandle(new MockHttpServletRequest("POST","/api/sys/login"),new MockHttpServletResponse(),new Object()));}
    @Test void ignoresForgedProxyHeaders(){MockHttpServletRequest r=new MockHttpServletRequest();r.setRemoteAddr("192.0.2.1");r.addHeader("X-Forwarded-For","198.51.100.2");r.addHeader("X-Real-IP","198.51.100.3");assertEquals("192.0.2.1",CommonUtils.getClientIP(r));}
    @Test void closesUnauthorizedLogSocket() throws Exception {
        AtomicReference<CloseStatus> closed = new AtomicReference<>();
        WebSocketSession s=(WebSocketSession)Proxy.newProxyInstance(getClass().getClassLoader(),new Class<?>[]{WebSocketSession.class},(proxy,method,args)->{
            if (method.getName().equals("getUri")) return URI.create("ws://localhost/logs?token=invalid");
            if (method.getName().equals("close")) closed.set((CloseStatus)args[0]);
            return null;
        });
        new LogWebSocketHandler().afterConnectionEstablished(s);assertEquals(CloseStatus.POLICY_VIOLATION,closed.get());
    }
    private StartupSecurityGuard guard(String account,String password,String key){StartupSecurityGuard g=new StartupSecurityGuard();ReflectionTestUtils.setField(g,"account",account);ReflectionTestUtils.setField(g,"password",password);ReflectionTestUtils.setField(g,"jwtSecret",key);return g;}
    @Test void rejectsDefaultAccount(){assertThrows(IllegalStateException.class,()->guard("yohann1","long-and-valid-password",KEY).validate());}
    @Test void rejectsWeakPassword(){assertThrows(IllegalStateException.class,()->guard("jolufa","short",KEY).validate());}
    @Test void rejectsReusedJwtSecret(){assertThrows(IllegalStateException.class,()->guard("jolufa",KEY,KEY).validate());}
    @Test void acceptsIndependentStrongCredentials(){assertDoesNotThrow(()->guard("jolufa","long-and-valid-password",KEY).validate());}
}
