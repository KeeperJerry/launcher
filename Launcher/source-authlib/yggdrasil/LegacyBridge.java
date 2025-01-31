package com.mojang.authlib.yggdrasil;

import launcher.LauncherAPI;
import launcher.client.ClientLauncher;
import launcher.helper.CommonHelper;
import launcher.helper.IOHelper;
import launcher.helper.LogHelper;
import launcher.request.auth.CheckServerRequest;
import launcher.request.auth.JoinServerRequest;

// Used by 1.6.4 and below versions
@LauncherAPI
public class LegacyBridge {
    private LegacyBridge() { }

    @SuppressWarnings("unused")
    public static boolean checkServer(String username, String serverID) throws Throwable {
        LogHelper.debug("LegacyBridge.checkServer, Username: '%s', Server ID: %s", username, serverID);
        return new CheckServerRequest(username, serverID).request() != null;
    }

    @SuppressWarnings("unused")
    public static String getCloakURL(String username) {
        LogHelper.debug("LegacyBridge.getCloakURL: '%s'", username);
        return CommonHelper.replace(System.getProperty("launcher.legacy.cloaksURL",
                "http://skins.minecraft.net/MinecraftCloaks/%username%.png"), "username", IOHelper.urlEncode(username));
    }

    @SuppressWarnings("unused")
    public static String getSkinURL(String username) {
        LogHelper.debug("LegacyBridge.getSkinURL: '%s'", username);
        return CommonHelper.replace(System.getProperty("launcher.legacy.skinsURL",
                "http://skins.minecraft.net/MinecraftSkins/%username%.png"), "username", IOHelper.urlEncode(username));
    }

    @SuppressWarnings("unused")
    public static String joinServer(String username, String accessToken, String serverID) {
        if (!ClientLauncher.isLaunched()) {
            return "Bad Login (Cheater)";
        }

        // Join server
        LogHelper.debug("LegacyBridge.joinServer, Username: '%s', Access token: %s, Server ID: %s", username, accessToken, serverID);
        try {
            return new JoinServerRequest(username, accessToken, serverID).request() ? "OK" : "Bad Login (Clientside)";
        }
        catch (Throwable exc) {
            return exc.toString();
        }
    }
}
